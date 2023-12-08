import Models.*;
import Repository.*;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    @Test
    public void testAccountCrudOperations() {
        // Create a test account
        Account testAccount = new Account("TEST_ACC", "Test Account", 1000.0, new java.util.Date(), new Currency("USD", "US Dollar", "USD"), AccountType.BANK);

        // Add account
        AccountCrudOperations accountRepository = new AccountRepository();
        accountRepository.addAccount(testAccount);

        // Get all accounts
        List<Account> accounts = accountRepository.getAllAccounts();
        assertTrue(accounts.contains(testAccount));

        // Update account
        testAccount.setName("Updated Test Account");
        accountRepository.updateAccount(testAccount);

        // Get updated account
        Account updatedAccount = accountRepository.getAllAccounts().stream()
                .filter(account -> account.getId().equals(testAccount.getId()))
                .findFirst()
                .orElse(null);
        assertNotNull(updatedAccount);
        assertEquals("Updated Test Account", updatedAccount.getName());

        // Delete account
        accountRepository.deleteAccount(testAccount.getId());
        assertFalse(accountRepository.getAllAccounts().contains(testAccount));
    }

    @Test
    public void testCurrencyCrudOperations() {
        // Create a test currency
        Currency testCurrency = new Currency("TEST_CURR", "Test Currency", "TC");

        // Add currency
        CurrencyCrudOperations currencyRepository = new CurrencyRepository();
        currencyRepository.addCurrency(testCurrency);

        // Get all currencies
        List<Currency> currencies = currencyRepository.getAllCurrencies();
        assertTrue(currencies.contains(testCurrency));

        // Update currency
        testCurrency.setName("Updated Test Currency");
        currencyRepository.updateCurrency(testCurrency);

        // Get updated currency
        Currency updatedCurrency = currencyRepository.getAllCurrencies().stream()
                .filter(currency -> currency.getId().equals(testCurrency.getId()))
                .findFirst()
                .orElse(null);
        assertNotNull(updatedCurrency);
        assertEquals("Updated Test Currency", updatedCurrency.getName());

        // Delete currency
        currencyRepository.deleteCurrency(testCurrency.getId());
        assertFalse(currencyRepository.getAllCurrencies().contains(testCurrency));
    }

  @Test
    public void testTransactionCrudOperations() {
        TransactionCrudOperations transactionRepository = new TransactionRepository();

        // Create a test transaction
        Transaction testTransaction = new Transaction("TEST_TRANS", "Test Transaction", 500.0, TransactionType.DEBIT);

        // Add transaction
        transactionRepository.addTransaction(testTransaction);

        // Get all transactions
        List<Transaction> transactions = transactionRepository.getAllTransactions();
        assertTrue(transactions.contains(testTransaction));

        // Delete transaction
        transactionRepository.deleteTransaction(testTransaction.getId());
        assertFalse(transactionRepository.getAllTransactions().contains(testTransaction));
    }
}