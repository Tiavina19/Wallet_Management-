CREATE ROLE wallet_admin WITH LOGIN PASSWORD '123456';

CREATE DATABASE wallet_management;

\c wallet_management

-- Création de la table Currency s'il n'existe pas
CREATE TABLE IF NOT EXISTS Currency (
    currency_id SERIAL PRIMARY KEY,
    currency_name VARCHAR(255) NOT NULL,
    currency_code VARCHAR(255) NOT NULL
);

-- Création de la table AccountType s'il n'existe pas
CREATE TABLE IF NOT EXISTS AccountType (
    type_id SERIAL PRIMARY KEY,
    type_name VARCHAR(255) NOT NULL
);

-- Création de la table Account s'il n'existe pas
CREATE TABLE IF NOT EXISTS Account (
    account_id SERIAL PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    balance DOUBLE PRECISION NOT NULL,
    last_update TIMESTAMP,
    currency_id INTEGER REFERENCES Currency(currency_id) ON DELETE CASCADE,
    type_id INTEGER REFERENCES AccountType(type_id) ON DELETE CASCADE
);

-- Création de la table TransactionType s'il n'existe pas
CREATE TABLE IF NOT EXISTS TransactionType (
    type_id SERIAL PRIMARY KEY,
    type_name VARCHAR(255) NOT NULL
);

-- Création de la table Transaction s'il n'existe pas
CREATE TABLE IF NOT EXISTS Transaction (
    transaction_id SERIAL PRIMARY KEY,
    transaction_label VARCHAR(255) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    transaction_date TIMESTAMP,
    type_id INTEGER REFERENCES TransactionType(type_id) ON DELETE CASCADE,
    account_source_id INTEGER REFERENCES Account(account_id) ON DELETE CASCADE,
    account_destination_id INTEGER REFERENCES Account(account_id) ON DELETE CASCADE
);
-- Création de la table BalanceHistory s'il n'existe pas
CREATE TABLE IF NOT EXISTS BalanceHistory (
    history_id SERIAL PRIMARY KEY,
    value DOUBLE PRECISION NOT NULL,
    update_date_time TIMESTAMP,
    account_id INTEGER REFERENCES Account(account_id) ON DELETE CASCADE
);

-- Création de la table Category s'il n'existe pas
CREATE TABLE IF NOT EXISTS Category (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL
);

-- Création de la table CurrencyValue s'il n'existe pas
CREATE TABLE IF NOT EXISTS CurrencyValue (
    value_id SERIAL PRIMARY KEY,
    currency_source INTEGER REFERENCES Currency(currency_id) ON DELETE CASCADE,
    currency_destination INTEGER REFERENCES Currency(currency_id) ON DELETE CASCADE,
    amount DOUBLE PRECISION NOT NULL,
    date_effect TIMESTAMP
);
-- Création de la table TotalAmount s'il n'existe pas
CREATE TABLE IF NOT EXISTS TotalAmount (
    total_amount_id SERIAL PRIMARY KEY,
    total_amount DOUBLE PRECISION NOT NULL
);

-- Création de la table CategoryAmount s'il n'existe pas
CREATE TABLE IF NOT EXISTS CategoryAmount (
    category_amount_id SERIAL PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL REFERENCES Category(category_name) ON DELETE CASCADE,
    total_amount DOUBLE PRECISION NOT NULL
);


-- Création de la table Transfer s'il n'existe pas
CREATE TABLE IF NOT EXISTS Transfer (
    transfer_id SERIAL PRIMARY KEY,
    debtor_id INTEGER REFERENCES Account(account_id) ON DELETE CASCADE,
    creditor_id INTEGER REFERENCES Account(account_id) ON DELETE CASCADE,
    amount DOUBLE PRECISION NOT NULL,
    date_time TIMESTAMP
);

-- Inserts factices pour la table Currency
INSERT INTO Currency (currency_name, currency_code) VALUES
    ('Euro', 'EUR'),
    ('Dollar', 'USD'),
    ('Pound Sterling', 'GBP');

-- Inserts factices pour la table AccountType
INSERT INTO AccountType (type_name) VALUES
    ('Savings'),
    ('Checking'),
    ('Credit');

-- Inserts factices pour la table Account
INSERT INTO Account (user_name, balance, last_update, currency_id, type_id) VALUES
    ('John Doe', 1000.00, NOW(), 1, 1),
    ('Jane Doe', 500.50, NOW(), 2, 2),
    ('Bob Smith', 1500.75, NOW(), 3, 3);

-- Inserts factices pour la table TransactionType
INSERT INTO TransactionType (type_name) VALUES
    ('Credit'),
    ('Debit');

-- Inserts factices pour la table Transaction
INSERT INTO Transaction (transaction_label, amount, transaction_date, type_id, account_source_id, account_destination_id) VALUES
    ('Salary', 2000.00, NOW(), 1, 1, 2),
    ('Groceries', 50.25, NOW(), 2, 3, 1),
    ('Online Shopping', 100.50, NOW(), 2, 2, 3);

-- Inserts factices pour la table BalanceHistory
INSERT INTO BalanceHistory (value, update_date_time, account_id) VALUES
    (1000.00, NOW(), 1),
    (500.50, NOW(), 2),
    (1500.75, NOW(), 3);

-- Inserts factices pour la table Category
INSERT INTO Category (category_name) VALUES
    ('Food'),
    ('Utilities'),
    ('Entertainment');

-- Inserts factices pour la table CurrencyValue
INSERT INTO CurrencyValue (currency_source, currency_destination, amount, date_effect) VALUES
    (1, 2, 0.85, NOW()),
    (2, 3, 1.25, NOW()),
    (3, 1, 0.75, NOW());

-- Inserts factices pour la table TotalAmount
INSERT INTO TotalAmount (total_amount) VALUES
    (5000.00);

-- Inserts factices pour la table CategoryAmount
INSERT INTO CategoryAmount (category_name, total_amount) VALUES
    ('Food', 300.00),
    ('Utilities', 150.00),
    ('Entertainment', 200.00);

-- Inserts factices pour la table Transfer
INSERT INTO Transfer (debtor_id, creditor_id, amount, date_time) VALUES
    (1, 2, 50.00, NOW()),
    (3, 1, 75.25, NOW());


-- Fonction pour calculer la somme des entrées et sorties d'argent pour un compte donné dans une plage de dates spécifiée
CREATE OR REPLACE FUNCTION total_amount(account_id INT, start_date TIMESTAMP, end_date TIMESTAMP)
RETURNS DOUBLE PRECISION AS $$
DECLARE
    total DOUBLE PRECISION;
BEGIN
    SELECT (
        SUM(CASE WHEN tr.transactiontype = 'DEBIT' THEN tr.amount ELSE 0 END) -
        SUM(CASE WHEN tr.transactiontype = 'CREDIT' THEN tr.amount ELSE 0 END)
    ) INTO total
    FROM "balance_history" bh
    INNER JOIN "account" acc ON acc.id = bh.accountid
    INNER JOIN "transaction" tr ON tr.accountid = acc.id
    WHERE bh.accountId = account_id
    AND bh.updateDateTime BETWEEN start_date AND end_date;

    RETURN total;
END; $$
LANGUAGE plpgsql;

-- Fonction pour calculer la somme des montants de chaque catégorie pour un compte donné dans une plage de dates spécifiée
CREATE OR REPLACE FUNCTION total_amount_per_category(account_id INT, start_date TIMESTAMP, end_date TIMESTAMP)
RETURNS TABLE(category_id INT, category_name VARCHAR(255), total_amount DOUBLE PRECISION) AS $$
BEGIN
    RETURN QUERY
    SELECT c.id AS category_id,
    c.name AS category_name,
    COALESCE(SUM(bh.value), 0) AS total_amount
    FROM "category" c
    LEFT JOIN "transaction" tr ON tr.categoryid = c.id
    LEFT JOIN "account" acc ON acc.id = tr.accountid
    LEFT JOIN "balance_history" bh ON bh.accountid = acc.id
    WHERE bh.accountId = account_id
    AND bh.updateDateTime BETWEEN start_date AND end_date
    GROUP BY c.id, c.name;
END; $$
LANGUAGE plpgsql;
