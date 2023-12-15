package Services;

import Models.Account;
import Models.Transaction;
import Repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private TransactionRepository transactionRepo = new TransactionRepository();

    public Transaction getTransactionById(String id) {
        return transactionRepo.getById(id);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepo.getAllTransactions();
    }

    public Account saveTransaction(Transaction transaction) {
        AccountService accountService = new AccountService();
        Account account = accountService.getAccountById(transaction.getId());
        Double balance = account.getBalance();
        if (!account.getType().equals("Bank") && balance < transaction.getAmount() && transaction.getType().equals("DEBIT")) {
            System.out.println("Transaction failed: balance not enough.");
            return null;
        }
        transactionRepo.addTransaction(transaction);
        return accountService.getAccountById(transaction.getId());
    }

    public List<Account> saveAllTransactions(List<Transaction> transactions) {
        List<Account> accounts = new ArrayList<>();
        for (Transaction transaction : transactions) {
            accounts.add(saveTransaction(transaction));
        }
        return accounts;
    }

}
