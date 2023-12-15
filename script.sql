CREATE ROLE wallet_admin WITH LOGIN PASSWORD '123456';

CREATE DATABASE wallet_management;

\c wallet_management

-- Création de la table Currency s'il n'existe pas
CREATE TABLE IF NOT EXISTS Currency (
    currency_id SERIAL PRIMARY KEY,
    currency_name VARCHAR(255) NOT NULL,
    exchange_rate DOUBLE PRECISION NOT NULL
);

-- Insertion de données factices dans la table Currency
INSERT INTO Currency (currency_name, exchange_rate) VALUES
    ('EUROS', 3000.10),
    ('ARIARY', 1.0),
    ('DOLLAR', 2000.12);

-- Création de la table Account s'il n'existe pas
CREATE TABLE IF NOT EXISTS Account (
    account_id SERIAL PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    balance DOUBLE PRECISION NOT NULL,
    currency_id INTEGER REFERENCES Currency(currency_id) ON DELETE CASCADE
);

-- Insertion de données factices dans la table Account
INSERT INTO Account (user_name, balance, currency_id) VALUES
    ('Tiavina Andriatsitohery', 500.00, 1),
    ('Aina Dylan', 700.00, 1);

-- Création de la table Transaction s'il n'existe pas
CREATE TABLE IF NOT EXISTS Transaction (
    transaction_id SERIAL PRIMARY KEY,
    amount DOUBLE PRECISION NOT NULL,
    account_source_id INTEGER REFERENCES Account(account_id) ON DELETE CASCADE,
    account_destination_id INTEGER REFERENCES Account(account_id) ON DELETE CASCADE
);

-- Insertion de données factices dans la table Transaction
INSERT INTO Transaction (amount, account_source_id, account_destination_id) VALUES
    (1500.00, 1, 2),
    (5000.00, 2, 1);

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
