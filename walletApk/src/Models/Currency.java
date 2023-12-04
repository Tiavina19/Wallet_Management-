package Models;

public class Currency {
    private int currencyId;
    private String currencyName;
    private double exchangeRate;

    public Currency(int currencyId, String currencyName, double exchangeRate) {
        this.currencyId = currencyId;
        this.currencyName = currencyName;
        this.exchangeRate = exchangeRate;
    }

    // Getters and setters

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
