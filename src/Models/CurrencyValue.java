package Models;

import java.time.LocalDateTime;

public class CurrencyValue {
    private Integer id;
    private String currencySource;
    private String currencyDestination;
    private Double amount;
    private LocalDateTime dateEffect;

    public CurrencyValue(Integer id, String currencySource, String currencyDestination, Double amount, LocalDateTime dateEffect) {
        this.id = id;
        this.currencySource = currencySource;
        this.currencyDestination = currencyDestination;
        this.amount = amount;
        this.dateEffect = dateEffect;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrencySource() {
        return currencySource;
    }

    public void setCurrencySource(String currencySource) {
        this.currencySource = currencySource;
    }

    public String getCurrencyDestination() {
        return currencyDestination;
    }

    public void setCurrencyDestination(String currencyDestination) {
        this.currencyDestination = currencyDestination;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateEffect() {
        return dateEffect;
    }

    public void setDateEffect(LocalDateTime dateEffect) {
        this.dateEffect = dateEffect;
    }

    @Override
    public String toString() {
        return "CurrencyValue{" +
                "id=" + id +
                ", currencySource='" + currencySource + '\'' +
                ", currencyDestination='" + currencyDestination + '\'' +
                ", amount=" + amount +
                ", dateEffect=" + dateEffect +
                '}';
    }
}
