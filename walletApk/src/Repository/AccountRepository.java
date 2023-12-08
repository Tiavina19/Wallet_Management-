package Repository;

import Models.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements AccountCrudOperations {

    private List<Account> accounts = new ArrayList<>();

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
        int index = indexOfAccount(account.getId());
        if (index != -1) {
            accounts.set(index, account);
        }
    }

    private int indexOfAccount(String accountId) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getId().equals(accountId)) {
                return i;
            }
        }
        return -1;
    }
}
