package Repository;

import Models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface AccountCrudOperations {
    // Méthode pour ajouter un compte
    default void addAccount(Account account) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO Account (account_id, user_name, balance, last_update, type_id, currency_id) VALUES (?, ?, ?, ?, (SELECT type_id FROM AccountType WHERE type_name = ?), (SELECT currency_id FROM Currency WHERE currency_code = ?))";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, account.getId());
                preparedStatement.setString(2, account.getName());
                preparedStatement.setDouble(3, account.getBalance());
                preparedStatement.setTimestamp(4, new Timestamp(account.getLastUpdate().getTime()));
                preparedStatement.setString(5, account.getType().name());
                preparedStatement.setString(6, account.getCurrency().getCode());
                preparedStatement.executeUpdate();
                System.out.println("Account added successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to add account: " + e.getMessage());
        }
    }

    default List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT a.*, c.currency_name, c.currency_code, t.type_name FROM Account a JOIN Currency c ON a.currency_id = c.currency_id JOIN AccountType t ON a.type_id = t.type_id";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String id = resultSet.getString("account_id");
                    String name = resultSet.getString("user_name");
                    double balance = resultSet.getDouble("balance");
                    Timestamp lastUpdate = resultSet.getTimestamp("last_update");
                    String currencyName = resultSet.getString("currency_name");
                    String currencyCode = resultSet.getString("currency_code");
                    String typeName = resultSet.getString("type_name");
                    Account account = new Account(id, name, balance, lastUpdate, new Currency(String.valueOf(resultSet.getInt("currency_id")), currencyName, currencyCode), AccountType.valueOf(typeName));
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
            String query = "DELETE FROM Account WHERE account_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, accountId);
                preparedStatement.executeUpdate();
                System.out.println("Account deleted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to delete account: " + e.getMessage());
        }
    }

    default void updateAccount(Account account) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "UPDATE Account SET user_name = ?, balance = ?, last_update = ?, currency_id = (SELECT currency_id FROM Currency WHERE currency_code = ?), type_id = (SELECT type_id FROM AccountType WHERE type_name = ?) WHERE account_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, account.getName());
                preparedStatement.setDouble(2, account.getBalance());
                preparedStatement.setTimestamp(3, new Timestamp(account.getLastUpdate().getTime()));
                preparedStatement.setString(4, account.getCurrency().getCode());
                preparedStatement.setString(5, account.getType().name());
                preparedStatement.setString(6, account.getId());
                preparedStatement.executeUpdate();
                System.out.println("Account updated successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update account: " + e.getMessage());
        }
    }
}
