package Models;

import java.sql.Timestamp;

public class Transfer {
    private String id;
    private String debtorId;
    private String creditorId;
    private Double amount;
    private Timestamp dateTime;

    public Transfer(String id, String debtorId, String creditorId, Double amount, Timestamp dateTime) {
        this.id = id;
        this.debtorId = debtorId;
        this.creditorId = creditorId;
        this.amount = amount;
        this.dateTime = dateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDebtorId() {
        return debtorId;
    }

    public void setDebtorId(String debtorId) {
        this.debtorId = debtorId;
    }

    public String getCreditorId() {
        return creditorId;
    }

    public void setCreditorId(String creditorId) {
        this.creditorId = creditorId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id='" + id + '\'' +
                ", debtorId='" + debtorId + '\'' +
                ", creditorId='" + creditorId + '\'' +
                ", amount=" + amount +
                ", dateTime=" + dateTime +
                '}';
    }
}