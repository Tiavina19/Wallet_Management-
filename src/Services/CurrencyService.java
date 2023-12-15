package Services;

import Models.Currency;
import Models.CurrencyValue;
import Repository.CurrencyRepository;
import Repository.CurrencyValueRepository;

import java.util.List;

public class CurrencyService {
    private CurrencyRepository currencyRepo = new CurrencyRepository();
    private CurrencyValueRepository currencyValueRepo = new CurrencyValueRepository();

    public Currency getCurrencyById(String id) {
        return currencyRepo.getById(id);
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepo.getAllCurrencies();
    }

    public Currency saveCurrency(Currency currency) {
        currencyRepo.addCurrency(currency);
        return currency;
    }

    public List<Currency> saveAllCurrencies(List<Currency> currencies) {
        for (Currency currency :
             currencies) {
            saveCurrency(currency);
        }
        return currencies;
    }

    public List<CurrencyValue> getAllCurrencyValues() {
        return currencyValueRepo.getAllCurrencyValues();
    }

    public CurrencyValue getCurrencyValueById(String id) {
        return currencyValueRepo.getById(id);
    }

    public CurrencyValue saveCurrencyValue(CurrencyValue toSave) {
        currencyValueRepo.addCurrencyValue(toSave);
        return toSave;
    }
}