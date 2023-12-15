package Models;

import java.sql.Timestamp;

public class BalanceHistory {
    private String id;
    private Double value;
    private Timestamp updateDateTime;
    private String accountId;

    public BalanceHistory(String id, Double value, Timestamp updateDateTime, String accountId) {
        this.id = id;
        this.value = value;
        this.updateDateTime = updateDateTime;
        this.accountId = accountId;
    }

    public BalanceHistory() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Timestamp getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Timestamp updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "BalanceHistory{" +
                "id='" + id + '\'' +
                ", value=" + value +
                ", updateDateTime=" + updateDateTime +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}
