package Models;

public class Transaction {
    private int transactionId;
    private double amount;
    private int accountSourceId;
    private int accountDestinationId;

    public Transaction(int transactionId, double amount, int accountSourceId, int accountDestinationId) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.accountSourceId = accountSourceId;
        this.accountDestinationId = accountDestinationId;
    }

    // Getters and setters

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getAccountSourceId() {
        return accountSourceId;
    }

    public void setAccountSourceId(int accountSourceId) {
        this.accountSourceId = accountSourceId;
    }

    public int getAccountDestinationId() {
        return accountDestinationId;
    }

    public void setAccountDestinationId(int accountDestinationId) {
        this.accountDestinationId = accountDestinationId;
    }
}
