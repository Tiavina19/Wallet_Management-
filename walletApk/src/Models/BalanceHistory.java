package Models;

import java.util.Date;

public class BalanceHistory {
    private Date date;
    private double balance;

    public BalanceHistory(Date date, double balance) {
        this.date = date;
        this.balance = balance;
    }

    // Getters et setters pour chaque attribut

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
