package Models;

import java.util.Currency;

public class Account {
    private Long accountId;
    private String username;
    private double balance;
    private Currency currency;

    public Account(Long accountId, String username, double balance, Currency currency) {
        this.accountId = accountId;
        this.username = username;
        this.balance = balance;
        this.currency = currency;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", username='" + username + '\'' +
                ", balance=" + balance +
                ", currency=" + currency +
                '}';
    }
}

