package Repository;

import Models.BalanceHistory;
import Models.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface BalanceHistoryCrudOperations {
    // Méthode pour ajouter un historique de solde
    default void addBalanceHistory(BalanceHistory balanceHistory) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO BalanceHistory (history_id, value, update_date_time, account_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setLong(1, Long.parseLong(balanceHistory.getId()));
                preparedStatement.setDouble(2, balanceHistory.getValue());
                preparedStatement.setTimestamp(3, balanceHistory.getUpdateDateTime());
                preparedStatement.setLong(4, Long.parseLong(balanceHistory.getAccountId()));
                preparedStatement.executeUpdate();
                System.out.println("Balance history added successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to add balance history: " + e.getMessage());
        }
    }

    // Méthode pour récupérer tout l'historique de solde
    default List<BalanceHistory> getAllBalanceHistory() {
        List<BalanceHistory> balanceHistories = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM BalanceHistory";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String id = String.valueOf(resultSet.getLong("history_id"));
                    double value = resultSet.getDouble("value");
                    Timestamp updateDateTime = resultSet.getTimestamp("update_date_time");
                    String accountId = String.valueOf(resultSet.getLong("account_id"));

                    BalanceHistory history = new BalanceHistory();
                    history.setId(id);
                    history.setValue(value);
                    history.setUpdateDateTime(updateDateTime);
                    history.setAccountId(accountId);

                    balanceHistories.add(history);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get balance histories: " + e.getMessage());
        }
        return balanceHistories;
    }

    // Méthode pour supprimer un historique de solde
    default void deleteBalanceHistory(String balanceHistoryId) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "DELETE FROM BalanceHistory WHERE history_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setLong(1, Long.parseLong(balanceHistoryId));
                preparedStatement.executeUpdate();
                System.out.println("Balance history deleted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to delete balance history: " + e.getMessage());
        }
    }

    // Méthode pour mettre à jour un historique de solde
    default void updateBalanceHistory(BalanceHistory balanceHistory) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "UPDATE BalanceHistory SET value = ?, update_date_time = ?, account_id = ? WHERE history_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setDouble(1, balanceHistory.getValue());
                preparedStatement.setTimestamp(2, balanceHistory.getUpdateDateTime());
                preparedStatement.setLong(3, Long.parseLong(balanceHistory.getAccountId()));
                preparedStatement.setLong(4, Long.parseLong(balanceHistory.getId()));
                preparedStatement.executeUpdate();
                System.out.println("Balance history updated successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update balance history: " + e.getMessage());
        }
    }
}