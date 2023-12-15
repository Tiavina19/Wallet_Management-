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


SELECT (
            SUM(CASE WHEN tr.transactiontype = 'DEBIT' THEN 'DEBIT' ELSE 0 END) -
            SUM(CASE WHEN tr.transactiontype = 'CREDIT' THEN 'CREDIT' ELSE 0 END)
        ) AS total_amount
    FROM "balance_history" bh
        INNER JOIN "account" acc ON acc.id = bh.accountid
        INNER JOIN "transaction" tr ON tr.accountid = acc.id
    WHERE bh.accountId = 'ACCOUNT_ID'
    AND updateDateTime BETWEEN 'START_DATE' AND 'END_DATE';



    SELECT c.id AS category_id,
    c.name AS category_name,
    (SUM(CASE WHEN bh.value IS NOT NULL THEN bh.value ELSE 0 END)) AS total_amount
    FROM "category" c
    LEFT JOIN "transaction" tr ON tr.categoryid = c.id
    LEFT JOIN "account" acc ON acc.id = tr.accountid
    LEFT JOIN "balance_history" bh ON bh.accountid = acc.id
        AND bh.accountId = 'ACCOUNT_ID'
        AND bh.updateDateTime BETWEEN  'START_DATE' AND 'END_DATE'
    GROUP BY c.id, c.name;