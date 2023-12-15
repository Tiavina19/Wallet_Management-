package Repository;

import Models.CurrencyValue;

import java.util.ArrayList;
import java.util.List;

public class CurrencyValueRepository implements CurrencyValueCrudOperations {

    private List<CurrencyValue> currencyValues = new ArrayList<>();

    @Override
    public void addCurrencyValue(CurrencyValue currencyValue) {
        currencyValues.add(currencyValue);
    }

    @Override
    public List<CurrencyValue> getAllCurrencyValues() {
        return new ArrayList<>(currencyValues);
    }

    @Override
    public void deleteCurrencyValue(int currencyValueId) {
        currencyValues.removeIf(value -> value.getId() == currencyValueId);
    }

    @Override
    public void updateCurrencyValue(CurrencyValue currencyValue) {
        int index = indexOfCurrencyValue(currencyValue.getId());
        if (index != -1) {
            currencyValues.set(index, currencyValue);
        }
    }

    // Méthodes spécifiques aux valeurs de devise si nécessaire...

    private int indexOfCurrencyValue(int currencyValueId) {
        for (int i = 0; i < currencyValues.size(); i++) {
            if (currencyValues.get(i).getId() == currencyValueId) {
                return i;
            }
        }
        return -1;
    }
}
