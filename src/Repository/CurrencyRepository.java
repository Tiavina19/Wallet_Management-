package Repository;

import Models.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyRepository implements CurrencyCrudOperations {

    private List<Currency> currencies = new ArrayList<>();

    public Currency getById(String id) {
        for (Currency currencie : currencies) {
            if (currencie.getId().equals(id)) {
                return currencie;
            }
        }
        return null;
    }

    @Override
    public void addCurrency(Currency currency) {
        currencies.add(currency);
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return new ArrayList<>(currencies);
    }

    @Override
    public void deleteCurrency(String currencyId) {
        currencies.removeIf(currency -> currency.getId().equals(currencyId));
    }

    @Override
    public void updateCurrency(Currency currency) {
        int index = indexOfCurrency(currency.getId());
        if (index != -1) {
            currencies.set(index, currency);
        }
    }

    private int indexOfCurrency(String currencyId) {
        for (int i = 0; i < currencies.size(); i++) {
            if (currencies.get(i).getId().equals(currencyId)) {
                return i;
            }
        }
        return -1;
    }
}
