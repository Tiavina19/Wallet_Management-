import Models.Account;
import Models.Currency;
import Models.Transaction;
import Repository.AccountCrudOperations;
import Repository.CurrencyCrudOperations;
import Repository.TransactionCrudOperations;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Tests pour AccountCrudOperations
        testAccountCrudOperations();

        // Tests pour CurrencyCrudOperations
        testCurrencyCrudOperations();

        // Tests pour TransactionCrudOperations
        testTransactionCrudOperations();
    }

    private static void testAccountCrudOperations() {
        AccountCrudOperations accountCrudOperations = new AccountCrudOperations();

        // Test d'ajout d'un compte
        Account newAccount = new Account(0, "John Doe", 1000.0, 1);
        accountCrudOperations.addAccount(newAccount);

        // Test de récupération de tous les comptes
        List<Account> allAccounts = accountCrudOperations.getAllAccounts();
        System.out.println("All Accounts:");
        allAccounts.forEach(System.out::println);

        // Test de mise à jour d'un compte
        Account accountToUpdate = allAccounts.get(0);
        accountToUpdate.setBalance(1500.0);
        accountCrudOperations.updateAccount(accountToUpdate);

        // Test de suppression d'un compte
        int accountIdToDelete = accountToUpdate.getAccountId();
        accountCrudOperations.deleteAccount(accountIdToDelete);

        // Fermeture de la connexion
        accountCrudOperations.closeConnection();
    }

    private static void testCurrencyCrudOperations() {
        CurrencyCrudOperations currencyCrudOperations = new CurrencyCrudOperations();

        // Test d'ajout d'une devise
        Currency newCurrency = new Currency(0, "YEN", 120.5);
        currencyCrudOperations.addCurrency(newCurrency);

        // Test de récupération de toutes les devises
        List<Currency> allCurrencies = currencyCrudOperations.getAllCurrencies();
        System.out.println("All Currencies:");
        allCurrencies.forEach(System.out::println);

        // Test de mise à jour d'une devise
        Currency currencyToUpdate = allCurrencies.get(0);
        currencyToUpdate.setExchangeRate(130.0);
        currencyCrudOperations.updateCurrency(currencyToUpdate);

        // Test de suppression d'une devise
        int currencyIdToDelete = currencyToUpdate.getCurrencyId();
        currencyCrudOperations.deleteCurrency(currencyIdToDelete);

        // Fermeture de la connexion
        currencyCrudOperations.closeConnection();
    }

    private static void testTransactionCrudOperations() {
        TransactionCrudOperations transactionCrudOperations = new TransactionCrudOperations();

        // Test d'ajout d'une transaction
        Transaction newTransaction = new Transaction(0, 2000.0, 1, 2);
        transactionCrudOperations.addTransaction(newTransaction);

        // Test de récupération de toutes les transactions
        List<Transaction> allTransactions = transactionCrudOperations.getAllTransactions();
        System.out.println("All Transactions:");
        allTransactions.forEach(System.out::println);

        // Test de mise à jour d'une transaction
        Transaction transactionToUpdate = allTransactions.get(0);
        transactionToUpdate.setAmount(2500.0);
        transactionCrudOperations.updateTransaction(transactionToUpdate);

        // Test de suppression d'une transaction
        int transactionIdToDelete = transactionToUpdate.getTransactionId();
        transactionCrudOperations.deleteTransaction(transactionIdToDelete);

        // Fermeture de la connexion
        transactionCrudOperations.closeConnection();
    }
}
