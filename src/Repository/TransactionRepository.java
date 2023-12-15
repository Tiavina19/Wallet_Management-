package Repository;

import Models.Account;
import Models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository implements TransactionCrudOperations {

    private List<Transaction> transactions = new ArrayList<>();

    public Transaction getById(String id) {
        for (Transaction transaction : transactions) {
            if (transaction.getId().equals(id)) {
                return transaction;
            }
        }
        return null;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    @Override
    public void deleteTransaction(String transactionId) {
        transactions.removeIf(transaction -> transaction.getId().equals(transactionId));
    }
}
