CREATE ROLE wallet_admin WITH LOGIN PASSWORD '123456';

CREATE DATABASE wallet_management;

\c wallet_management

-- Création de la table Currency
CREATE TABLE IF NOT EXISTS Currency (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255),
    code VARCHAR(10)
);

-- Inserts fictifs pour Currency
INSERT INTO Currency (id, name, code) VALUES
    ('USD', 'US Dollar', 'USD'),
    ('EUR', 'Euro', 'EUR'),
    ('JPY', 'Japanese Yen', 'JPY');

-- Création de la table TransactionType
CREATE TABLE IF NOT EXISTS TransactionType (
    id SERIAL PRIMARY KEY,
    type_name VARCHAR(50) UNIQUE
);

-- Inserts fictifs pour TransactionType
-- (Nous n'insérons rien ici, car ces données sont généralement fixes et définies dans le script de création)

-- Création de la table Transaction
CREATE TABLE IF NOT EXISTS Transaction (
    id VARCHAR(50) PRIMARY KEY,
    label VARCHAR(255),
    amount DOUBLE PRECISION,
    date TIMESTAMP,
    type_id INT REFERENCES TransactionType(id)
);

-- Inserts fictifs pour Transaction
INSERT INTO Transaction (id, label, amount, date, type_id) VALUES
    ('1', 'Purchase', 100.50, '2023-12-08 12:00:00', 1),
    ('2', 'Salary', 2000.00, '2023-12-09 09:30:00', 2);

-- Création de la table AccountType
CREATE TABLE IF NOT EXISTS AccountType (
    id SERIAL PRIMARY KEY,
    type_name VARCHAR(50) UNIQUE
);

-- Inserts fictifs pour AccountType
INSERT INTO AccountType (type_name) VALUES
    ('BANK'),
    ('CASH'),
    ('MOBILE_MONEY');

-- Création de la table Account
CREATE TABLE IF NOT EXISTS Account (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255),
    balance DOUBLE PRECISION,
    last_update TIMESTAMP,
    currency_id VARCHAR(50) REFERENCES Currency(id),
    type_id INT REFERENCES AccountType(id)
);

-- Inserts fictifs pour Account
INSERT INTO Account (id, name, balance, last_update, currency_id, type_id) VALUES
    ('A123', 'Main Bank Account', 1500.75, '2023-12-08 15:45:00', 'USD', 1),
    ('A456', 'Wallet', 50.20, '2023-12-09 10:15:00', 'EUR', 2);

-- Création de la table CurrencyValue
CREATE TABLE IF NOT EXISTS CurrencyValue (
    id SERIAL PRIMARY KEY,
    base_currency_id VARCHAR(50) REFERENCES Currency(id),
    target_currency_id VARCHAR(50) REFERENCES Currency(id),
    value DOUBLE PRECISION,
    value_date TIMESTAMP
);

-- Inserts fictifs pour CurrencyValue
INSERT INTO CurrencyValue (base_currency_id, target_currency_id, value, value_date) VALUES
    ('USD', 'EUR', 0.85, '2023-12-08 16:00:00'),
    ('USD', 'JPY', 115.20, '2023-12-08 16:00:00'),
    ('EUR', 'USD', 1.18, '2023-12-08 16:00:00'),
    ('EUR', 'JPY', 135.50, '2023-12-08 16:00:00'),
    ('JPY', 'USD', 0.0087, '2023-12-08 16:00:00'),
    ('JPY', 'EUR', 0.0074, '2023-12-08 16:00:00');






