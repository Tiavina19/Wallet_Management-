import Models.*;
import Repository.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Main {
    @Nested
    class FunctionTest {

        @Test
        void performTransaction() {
            Account account = new Account("1", "John Doe", 1000.0, new Date(), new Currency("USD", "US Dollar", "USD"), null);
            Transaction transaction = new Transaction("1", "Payment", 500.0, TransactionType.DEBIT);

            account.performTransaction(transaction);

            assertEquals(500.0, account.getBalance());
            assertEquals(1, account.getTransactions().size());
            assertEquals(transaction, account.getTransactions().getFirst());
        }

        @Test
        void getBalanceAt() {
            Account account = new Account("1", "John Doe", 1000.0, new Date(), new Currency("USD", "US Dollar", "USD"), null);
            Transaction transaction1 = new Transaction("1", "Payment", 500.0, TransactionType.CREDIT);
            Transaction transaction2 = new Transaction("2", "Purchase", 300.0, TransactionType.DEBIT);

            account.performTransaction(transaction1);
            account.performTransaction(transaction2);

            // Choose a date that falls between the dates of transaction1 and transaction2
            Date date = new Date();

            double balanceAtDate = account.getBalanceAt(date);
            assertEquals(200.0, balanceAtDate);
        }


        @Test
        void getCurrentBalance() {
            Account account = new Account("1", "John Doe", 1000.0, new Date(), new Currency("USD", "US Dollar", "USD"), null);

            double currentBalance = account.getCurrentBalance();
            assertEquals(1000.0, currentBalance);
        }

        @Test
        void getBalanceHistory() {
            Account account = new Account("1", "John Doe", 1000.0, new Date(), new Currency("USD", "US Dollar", "USD"), null);

            // there are transactions within a specific date range
            List<Transaction> transactionsInRange = new ArrayList<>();
            transactionsInRange.add(new Transaction("1", "Payment", 200.0, TransactionType.CREDIT));
            transactionsInRange.add(new Transaction("2", "Purchase", 300.0, TransactionType.DEBIT));
            account.setTransactions(transactionsInRange);

            Date startDate = new Date();
            Date endDate = new Date();

            account.getBalanceHistory(startDate, endDate);
        }
    }
}

