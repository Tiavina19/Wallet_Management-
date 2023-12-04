package Models;

public class Account {
    private int accountId;
    private String userName;
    private double balance;
    private int currencyId;

    public Account(int accountId, String userName, double balance, int currencyId) {
        this.accountId = accountId;
        this.userName = userName;
        this.balance = balance;
        this.currencyId = currencyId;
    }

    // Getters and setters

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }
}
