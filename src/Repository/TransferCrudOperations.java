package Repository;

import Models.DBConnection;
import Models.Transfer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface TransferCrudOperations {
    // Méthode pour ajouter un transfert
    default void addTransfer(Transfer transfer) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO Transfers (debtorId, creditorId, amount, dateTime) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, Integer.parseInt(transfer.getDebtorId()));
                preparedStatement.setInt(2, Integer.parseInt(transfer.getCreditorId()));
                preparedStatement.setDouble(3, transfer.getAmount());
                preparedStatement.setTimestamp(4, transfer.getDateTime());
                preparedStatement.executeUpdate();
                System.out.println("Transfer added successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to add transfer: " + e.getMessage());
        }
    }

    // Méthode pour récupérer tous les transferts
    default List<Transfer> getAllTransfers() {
        List<Transfer> transfers = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM Transfers";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String id = String.valueOf(resultSet.getInt("transfer_id"));
                    String debtorId = String.valueOf(resultSet.getInt("debtorId"));
                    String creditorId = String.valueOf(resultSet.getInt("creditorId"));
                    double amount = resultSet.getDouble("amount");
                    Timestamp dateTime = resultSet.getTimestamp("dateTime");

                    Transfer transfer = new Transfer(id, debtorId, creditorId, amount, dateTime);
                    transfers.add(transfer);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get transfers: " + e.getMessage());
        }
        return transfers;
    }

    // Méthode pour supprimer un transfert
    default void deleteTransfer(String transferId) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "DELETE FROM Transfers WHERE transfer_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, Integer.parseInt(transferId));
                preparedStatement.executeUpdate();
                System.out.println("Transfer deleted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to delete transfer: " + e.getMessage());
        }
    }

    // Méthode pour mettre à jour un transfert
    default void updateTransfer(Transfer transfer) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "UPDATE Transfers SET debtorId = ?, creditorId = ?, amount = ?, dateTime = ? WHERE transfer_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, Integer.parseInt(transfer.getDebtorId()));
                preparedStatement.setInt(2, Integer.parseInt(transfer.getCreditorId()));
                preparedStatement.setDouble(3, transfer.getAmount());
                preparedStatement.setTimestamp(4, transfer.getDateTime());
                preparedStatement.setInt(5, Integer.parseInt(transfer.getId()));
                preparedStatement.executeUpdate();
                System.out.println("Transfer updated successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update transfer: " + e.getMessage());
        }
    }
}