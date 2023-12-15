package Services;

import Models.Account;
import Models.BalanceHistory;
import Models.Currency;
import Models.Transaction;
import Repository.AccountRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private AccountRepository accountRepo = new AccountRepository();
    private CurrencyService currencyService = new CurrencyService();

    private TransactionService transactionService = new TransactionService();

    public Account getAccountById(String id) {
        Account account = accountRepo.getById(id);
        return account;
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = accountRepo.getAllAccounts();
        return accounts;
    }

    public Account saveAccount(Account account, String currencyCode) {
        for (Currency curr : currencyService.getAllCurrencies()) {
            if(curr.getCode().equals(currencyCode)) {
                account.setCurrency(curr);
            }
        }
        accountRepo.addAccount(account);
        return account;
    }

    public List<Account> saveAllAccounts(List<Account> accounts) {
        for (Account account : accounts) {
            saveAccount(account, "MGA");
            accounts.add(account);
        }
        return accounts;
    }

}
