package Repository;

import Models.Account;
import Models.AccountType;
import Models.Currency;
import Models.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface AccountCrudOperations {
    // Méthode pour ajouter un compte
    default void addAccount(Account account) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO Account (id, name, balance, last_update, currency_id, type_id) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, account.getId());
                preparedStatement.setString(2, account.getName());
                preparedStatement.setDouble(3, account.getBalance());
                preparedStatement.setTimestamp(4, new Timestamp(account.getLastUpdate().getTime()));
                preparedStatement.setString(5, account.getCurrency().getId());
                preparedStatement.setInt(6, account.getType().ordinal() + 1); // Assuming enum values start from 1
                preparedStatement.executeUpdate();
                System.out.println("Account added successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to add account: " + e.getMessage());
        }
    }

    // Méthode pour récupérer tous les comptes
    default List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM Account";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    double balance = resultSet.getDouble("balance");
                    Timestamp lastUpdate = resultSet.getTimestamp("last_update");
                    String currencyId = resultSet.getString("currency_id");
                    int typeId = resultSet.getInt("type_id");
                    Account account = new Account(id, name, balance, lastUpdate, new Currency(currencyId, "", ""), AccountType.values()[typeId - 1]);
                    accounts.add(account);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get accounts: " + e.getMessage());
        }
        return accounts;
    }

    // Méthode pour supprimer un compte
    default void deleteAccount(String accountId) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "DELETE FROM Account WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, accountId);
                preparedStatement.executeUpdate();
                System.out.println("Account deleted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to delete account: " + e.getMessage());
        }
    }

    // Méthode pour mettre à jour un compte
    default void updateAccount(Account account) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "UPDATE Account SET name = ?, balance = ?, last_update = ?, currency_id = ?, type_id = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, account.getName());
                preparedStatement.setDouble(2, account.getBalance());
                preparedStatement.setTimestamp(3, new Timestamp(account.getLastUpdate().getTime()));
                preparedStatement.setString(4, account.getCurrency().getId());
                preparedStatement.setInt(5, account.getType().ordinal() + 1);
                preparedStatement.setString(6, account.getId());
                preparedStatement.executeUpdate();
                System.out.println("Account updated successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update account: " + e.getMessage());
        }
    }
}
