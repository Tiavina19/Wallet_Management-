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
