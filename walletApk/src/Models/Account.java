package Models;

import java.util.*;

public class Account {
    private String id;
    private String name;
    private double balance;
    private Date lastUpdate;
    private List<Transaction> transactions;
    private Currency currency;
    private AccountType type;

    public Account(String id, String name, double balance, Date lastUpdate, Currency currency, AccountType type) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.lastUpdate = lastUpdate;
        this.currency = currency;
        this.type = type;
        this.transactions = new ArrayList<>();
    }

    // Getters et setters pour chaque attribut
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public void performTransaction(Transaction transaction) {
        // Vérifier si le solde est suffisant pour les comptes non bancaires
        if (TransactionType.DEBIT.equals(transaction.getType()) && transaction.getAmount() > this.balance) {
            throw new IllegalArgumentException("Insufficient balance to perform this transaction.");
        }

        transactions.add(transaction);

        if (TransactionType.DEBIT.equals(transaction.getType())) {
            balance -= transaction.getAmount();
        } else {
            balance += transaction.getAmount();
        }

        lastUpdate = new Date(); // La date de mise à jour est la date actuelle
    }

    // Fonction pour avoir le solde à une telle heure
    public double getBalanceAt(Date date) {
        double balanceAtDate = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getDate().before(date) || transaction.getDate().equals(date)) {
                if (transaction.getType() == TransactionType.CREDIT) {
                    balanceAtDate += transaction.getAmount();
                } else if (transaction.getType() == TransactionType.DEBIT) {
                    balanceAtDate -= transaction.getAmount();
                }
            }
        }
        return balanceAtDate;
    }

    public double getCurrentBalance() {
        return balance; // Retourne le solde actuel stocké dans l'attribut 'balance'
    }

    public SortedMap<Date, Double> getBalanceHistory(Date startDate, Date endDate) {
        SortedMap<Date, Double> balanceHistory = new TreeMap<>();
        double runningBalance = getBalanceAt(startDate); // Initialiser le solde courant au solde avant la startDate

        // Ajouter le solde initial à la date de début
        balanceHistory.put(startDate, runningBalance);

        for (Transaction transaction : transactions) {
            Date transactionDate = transaction.getDate();
            if ((transactionDate.after(startDate) || transactionDate.equals(startDate)) &&
                    (transactionDate.before(endDate) || transactionDate.equals(endDate))) {
                if (TransactionType.CREDIT.equals(transaction.getType())) {
                    runningBalance += transaction.getAmount();
                } else if (TransactionType.DEBIT.equals(transaction.getType())) {
                    runningBalance -= transaction.getAmount();
                }
                balanceHistory.put(transactionDate, runningBalance);
            }
        }

        // Ajouter le solde à la date de fin si aucune transaction n'est survenue à
        // cette date
        if (!balanceHistory.containsKey(endDate)) {
            balanceHistory.put(endDate, runningBalance);
        }
        return balanceHistory;
    }
}
