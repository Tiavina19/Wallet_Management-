package Services;

import Models.Account;
import Models.BalanceHistory;
import Models.Currency;
import Models.Transaction;
import Repository.AccountRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class AccountService {
    private AccountRepository accountRepo = new AccountRepository();
    private CurrencyService currencyService = new CurrencyService();

    private TransactionService transactionService = new TransactionService();

    public Account getAccountById(String id) {
        Account account = accountRepo.getById(id);
        List<Transaction> transactionsList = transactionService.getALlTransactionsByAccoundId(id);
        account.setBalance(accountRepo.getBalanceNow(id).getValue());
        if (transactionsList != null) {
            account.setTransactionList(transactionsList);
        }

        return account;
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = accountRepo.findAll();
        for (Account account : accounts) {
            List<Transaction> transactionsList = transactionService.getALlTransactionsByAccoundId(account.getId());
            account.setBalance(accountRepo.getBalanceNow(account.getId()).getValue());
            if (transactionsList != null) {
                account.setTransactionList(transactionsList);
            }
        }
        return accounts;
    }

    public Account saveAccount(Account Account, String currencyCode) {
        for (Currency curr : currencyService.getAllCurrencies()) {
            if(curr.getCode().equals(currencyCode)) {
                Account.setCurrency(curr);
            }
        }
        return accountRepo.save(Account);
    }

    public List<Account> saveAllAccounts(List<Account> accounts) {
        return accountRepo.saveAll(accounts);
    }

    public List<BalanceHistory> getBalancesHistory(String id) {
        return accountRepo.getBalanceHistory(id, null, null);
    }

    public List<BalanceHistory> getBalancesHistoryWithDate(String id, LocalDateTime startDatetime, LocalDateTime endDatetime) {
        Timestamp start = Timestamp.valueOf(startDatetime);
        Timestamp end = Timestamp.valueOf(endDatetime);
        return accountRepo.getBalanceHistory(id, start, end);
    }
}
}
