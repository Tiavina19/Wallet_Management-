package Models;

import java.util.Date;

public class Transaction {
    private String id;
    private String label;
    private double amount;
    private Date date;
    private TransactionType type;

    public Transaction(String id, String label, double amount, TransactionType type) {
        this.id = id;
        this.label = label;
        this.amount = amount;
        this.date = new Date(); // La date de la transaction est la date actuelle
        this.type = type;
    }

    public Transaction(String label, double amount) {
        this.label = label;
        this.amount = amount;
    }

    // Getters et setters pour chaque attribut
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
