package Repository;

import Models.Account;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements AccountCrudOperations {

    private List<Account> accounts = new ArrayList<>();

    public Account getById(String id) {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public void addAccount(Account account) {
        accounts.add(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts);
    }

    @Override
    public void deleteAccount(String accountId) {
        accounts.removeIf(account -> account.getId().equals(accountId));
    }

    @Override
    public void updateAccount(Account account) {
        int index = accounts.indexOf(account);
        if (index != -1) {
            accounts.set(index, account);
        }
    }
}