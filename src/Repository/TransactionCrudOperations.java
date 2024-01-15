package Repository;

import Models.DBConnection;
import Models.Transaction;
import Models.TransactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface TransactionCrudOperations {
    // Méthode pour ajouter une transaction
    default void addTransaction(Transaction transaction) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO Transaction (transaction_label, amount, transaction_date, type_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, transaction.getLabel());
                preparedStatement.setDouble(2, transaction.getAmount());
                preparedStatement.setTimestamp(3, new Timestamp(transaction.getDate().getTime()));
                preparedStatement.setInt(4, transaction.getType().ordinal() + 1); // Assuming enum values start from 1
                preparedStatement.executeUpdate();
                System.out.println("Transaction added successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to add transaction: " + e.getMessage());
        }
    }

    // Méthode pour récupérer toutes les transactions
    default List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM Transaction";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String id = String.valueOf(resultSet.getInt("transaction_id"));
                    String label = resultSet.getString("transaction_label");
                    double amount = resultSet.getDouble("amount");
                    Timestamp date = resultSet.getTimestamp("transaction_date");
                    int typeId = resultSet.getInt("type_id");
                    Transaction transaction = new Transaction(id, label, amount, TransactionType.values()[typeId - 1]);
                    transaction.setDate(date);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get transactions: " + e.getMessage());
        }
        return transactions;
    }

    // Méthode pour supprimer une transaction
    default void deleteTransaction(String transactionId) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "DELETE FROM Transaction WHERE transaction_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, Integer.parseInt(transactionId));
                preparedStatement.executeUpdate();
                System.out.println("Transaction deleted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to delete transaction: " + e.getMessage());
        }
    }

    // Méthode pour récupérer les transactions pour un compte spécifique dans une plage de dates
    default List<Transaction> getTransactionsForAccount(int accountId, Timestamp startDate, Timestamp endDate) {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM Transaction WHERE (account_source_id = ? OR account_destination_id = ?) AND transaction_date BETWEEN ? AND ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, accountId);
                preparedStatement.setInt(2, accountId);
                preparedStatement.setTimestamp(3, startDate);
                preparedStatement.setTimestamp(4, endDate);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String id = String.valueOf(resultSet.getInt("transaction_id"));
                    String label = resultSet.getString("transaction_label");
                    double amount = resultSet.getDouble("amount");
                    Timestamp date = resultSet.getTimestamp("transaction_date");
                    int typeId = resultSet.getInt("type_id");
                    Transaction transaction = new Transaction(id, label, amount, TransactionType.values()[typeId - 1]);
                    transaction.setDate(date);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get transactions: " + e.getMessage());
        }
        return transactions;
    }
}