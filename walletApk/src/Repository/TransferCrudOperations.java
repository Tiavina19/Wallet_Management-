package Repository;

import Models.DBConnection;
import Models.Transfer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface TransferCrudOperations {
    default void addTransfer(Transfer transfer) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO Transfers (id, debtorId, creditorId, amount, dateTime) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, transfer.getId());
                preparedStatement.setString(2, transfer.getDebtorId());
                preparedStatement.setString(3, transfer.getCreditorId());
                preparedStatement.setDouble(4, transfer.getAmount());
                preparedStatement.setTimestamp(5, transfer.getDateTime());
                preparedStatement.executeUpdate();
                System.out.println("Transfer added successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to add transfer: " + e.getMessage());
        }
    }

    default List<Transfer> getAllTransfers() {
        List<Transfer> transfers = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM Transfers";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String debtorId = resultSet.getString("debtorId");
                    String creditorId = resultSet.getString("creditorId");
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

    default void deleteTransfer(String transferId) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "DELETE FROM Transfers WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, transferId);
                preparedStatement.executeUpdate();
                System.out.println("Transfer deleted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to delete transfer: " + e.getMessage());
        }
    }

    default void updateTransfer(Transfer transfer) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "UPDATE Transfers SET debtorId = ?, creditorId = ?, amount = ?, dateTime = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, transfer.getDebtorId());
                preparedStatement.setString(2, transfer.getCreditorId());
                preparedStatement.setDouble(3, transfer.getAmount());
                preparedStatement.setTimestamp(4, transfer.getDateTime());
                preparedStatement.setString(5, transfer.getId());
                preparedStatement.executeUpdate();
                System.out.println("Transfer updated successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update transfer: " + e.getMessage());
        }
    }
}
