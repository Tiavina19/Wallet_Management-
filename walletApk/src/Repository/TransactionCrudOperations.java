package Repository;

import Models.DBConnection;
import Models.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionCrudOperations {
    private final Connection connection;

    public TransactionCrudOperations() {
        // Obtenez la connexion depuis la classe DBConnection
        this.connection = DBConnection.getConnection();
    }

    // Méthode pour ajouter une transaction
    public void addTransaction(Transaction transaction) {
        String query = "INSERT INTO Transaction (amount, account_source_id, account_destination_id) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, transaction.getAmount());
            preparedStatement.setInt(2, transaction.getAccountSourceId());
            preparedStatement.setInt(3, transaction.getAccountDestinationId());
            preparedStatement.executeUpdate();
            System.out.println("Transaction added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour récupérer toutes les transactions
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM Transaction";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int transactionId = resultSet.getInt("transaction_id");
                double amount = resultSet.getDouble("amount");
                int accountSourceId = resultSet.getInt("account_source_id");
                int accountDestinationId = resultSet.getInt("account_destination_id");
                Transaction transaction = new Transaction(transactionId, amount, accountSourceId, accountDestinationId);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    // Méthode pour mettre à jour une transaction
    public void updateTransaction(Transaction transaction) {
        String query = "UPDATE Transaction SET amount=?, account_source_id=?, account_destination_id=? WHERE transaction_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, transaction.getAmount());
            preparedStatement.setInt(2, transaction.getAccountSourceId());
            preparedStatement.setInt(3, transaction.getAccountDestinationId());
            preparedStatement.setInt(4, transaction.getTransactionId());
            preparedStatement.executeUpdate();
            System.out.println("Transaction updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour supprimer une transaction
    public void deleteTransaction(int transactionId) {
        String query = "DELETE FROM Transaction WHERE transaction_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, transactionId);
            preparedStatement.executeUpdate();
            System.out.println("Transaction deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour fermer la connexion (à appeler à la fin)
    public void closeConnection() {
        DBConnection.closeConnection();
    }
}
