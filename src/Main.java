import Models.Account;
import Models.Currency;
import Models.Transaction;
import Models.TransactionType;
import Models.BalanceHistory;
import Models.Category;
import Models.CurrencyValue;
import Models.Transfer;
import Repository.AccountCrudOperations;
import Repository.AccountRepository;
import Repository.BalanceHistoryCrudOperations;
import Repository.BalanceHistoryRepository;
import Repository.CategoryCrudOperations;
import Repository.CategoryRepository;
import Repository.CurrencyCrudOperations;
import Repository.CurrencyRepository;
import Repository.CurrencyValueCrudOperations;
import Repository.CurrencyValueRepository;
import Repository.TransactionCrudOperations;
import Repository.TransactionRepository;
import Repository.TransferCrudOperations;
import Repository.TransferRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        testAccountCrudOperations();
        testCurrencyCrudOperations();
        testTransactionCrudOperations();
        testBalanceHistoryCrudOperations();
        testCategoryCrudOperations();
        testCurrencyValueCrudOperations();
        testTransferCrudOperations();
    }

    private static void testAccountCrudOperations() {
        AccountCrudOperations accountRepository = new AccountRepository();

        // Test d'ajout d'un compte
        Account newAccount = new Account("John Doe", 1000.0);
        accountRepository.addAccount(newAccount);

        // Test de récupération de tous les comptes
        List<Account> allAccounts = accountRepository.getAllAccounts();
        System.out.println("All Accounts:");
        allAccounts.forEach(System.out::println);

        // Test de mise à jour du solde d'un compte
        Account accountToUpdate = allAccounts.get(0);
        accountToUpdate.setBalance(1500.0);
        accountRepository.updateAccount(accountToUpdate);

        // Test de suppression d'un compte
        String accountIdToDelete = allAccounts.get(1).getId();
        accountRepository.deleteAccount(accountIdToDelete);
    }

    private static void testCurrencyCrudOperations() {
        CurrencyCrudOperations currencyRepository = new CurrencyRepository();

        // Test d'ajout d'une devise
        Currency newCurrency = new Currency("Yen", "Japanese Yen", "JPY");
        currencyRepository.addCurrency(newCurrency);

        // Test de récupération de toutes les devises
        List<Currency> allCurrencies = currencyRepository.getAllCurrencies();
        System.out.println("All Currencies:");
        allCurrencies.forEach(System.out::println);

        // Test de mise à jour d'une devise
        Currency currencyToUpdate = allCurrencies.get(0);
        currencyToUpdate.setName("New Euro");
        currencyRepository.updateCurrency(currencyToUpdate);

        // Test de suppression d'une devise
        String currencyIdToDelete = allCurrencies.get(1).getId();
        currencyRepository.deleteCurrency(currencyIdToDelete);
    }

    private static void testTransactionCrudOperations() {
        TransactionCrudOperations transactionRepository = new TransactionRepository();

        // Test d'ajout d'une transaction
        Transaction newTransaction = new Transaction("1", "Payment", 500.0, TransactionType.DEBIT);
        transactionRepository.addTransaction(newTransaction);

        // Test de récupération de toutes les transactions
        List<Transaction> allTransactions = transactionRepository.getAllTransactions();
        System.out.println("All Transactions:");
        allTransactions.forEach(System.out::println);

        // Test de suppression d'une transaction
        String transactionIdToDelete = allTransactions.get(0).getId();
        transactionRepository.deleteTransaction(transactionIdToDelete);
    }

    private static void testBalanceHistoryCrudOperations() {
        BalanceHistoryCrudOperations balanceHistoryRepository = new BalanceHistoryRepository();

        // Test d'ajout d'un historique de solde
        BalanceHistory newBalanceHistory = new BalanceHistory("1", 1000.0, Timestamp.valueOf(LocalDateTime.now()), "1");
        balanceHistoryRepository.addBalanceHistory(newBalanceHistory);

        // Test de récupération de tous les historiques de solde
        List<BalanceHistory> allBalanceHistories = balanceHistoryRepository.getAllBalanceHistory();
        System.out.println("All Balance Histories:");
        allBalanceHistories.forEach(System.out::println);

        // Test de suppression d'un historique de solde
        String balanceHistoryIdToDelete = allBalanceHistories.get(0).getId();
        balanceHistoryRepository.deleteBalanceHistory(balanceHistoryIdToDelete);
    }

    private static void testCategoryCrudOperations() {
        CategoryCrudOperations categoryRepository = new CategoryRepository();

        // Test d'ajout d'une catégorie
        Category newCategory = new Category(1, "Food");
        categoryRepository.addCategory(newCategory);

        // Test de récupération de toutes les catégories
        List<Category> allCategories = categoryRepository.getAllCategories();
        System.out.println("All Categories:");
        allCategories.forEach(System.out::println);

        // Test de suppression d'une catégorie
        int categoryIdToDelete = allCategories.get(0).getId();
        categoryRepository.deleteCategory(categoryIdToDelete);
    }

    private static void testCurrencyValueCrudOperations() {
        CurrencyValueCrudOperations currencyValueRepository = new CurrencyValueRepository();

       // Test d'ajout d'une valeur de devise
       CurrencyValue newCurrencyValue = new CurrencyValue(1, "USD", "EUR", 1.2, LocalDateTime.now());
       currencyValueRepository.addCurrencyValue(newCurrencyValue);

        // Test de récupération de toutes les valeurs de devise
        List<CurrencyValue> allCurrencyValues = currencyValueRepository.getAllCurrencyValues();
        System.out.println("All Currency Values:");
        allCurrencyValues.forEach(System.out::println);

        // Test de suppression d'une valeur de devise
        int currencyValueIdToDelete = allCurrencyValues.get(0).getId();
        currencyValueRepository.deleteCurrencyValue(currencyValueIdToDelete);
    }

    private static void testTransferCrudOperations() {
        TransferCrudOperations transferRepository = new TransferRepository();

        // Test d'ajout d'un transfert
        Transfer newTransfer = new Transfer("1", "2", "3", 500.0, Timestamp.valueOf(LocalDateTime.now()));
        transferRepository.addTransfer(newTransfer);

        // Test de récupération de tous les transferts
        List<Transfer> allTransfers = transferRepository.getAllTransfers();
        System.out.println("All Transfers:");
        allTransfers.forEach(System.out::println);

        // Test de mise à jour d'un transfert
        Transfer transferToUpdate = allTransfers.get(0);
        transferToUpdate.setAmount(700.0);
        transferRepository.updateTransfer(transferToUpdate);

        // Test de suppression d'un transfert
        String transferIdToDelete = allTransfers.get(1).getId();
        transferRepository.deleteTransfer(transferIdToDelete);
    }
}
