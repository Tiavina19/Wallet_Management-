package Repository;

import Models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository implements TransactionCrudOperations {

    private List<Transaction> transactions = new ArrayList<>();

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
