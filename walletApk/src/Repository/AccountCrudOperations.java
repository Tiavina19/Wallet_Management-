package Repository;

import Models.Account;
import Models.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountCrudOperations {
    private final Connection connection;

    public AccountCrudOperations() {
        // Obtenez la connexion depuis la classe DBConnection
        this.connection = DBConnection.getConnection();
    }

    // Méthode pour ajouter un compte
    public void addAccount(Account account) {
        String query = "INSERT INTO Account (user_name, balance, currency_id) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, account.getUserName());
            preparedStatement.setDouble(2, account.getBalance());
            preparedStatement.setInt(3, account.getCurrencyId());
            preparedStatement.executeUpdate();
            System.out.println("Account added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour récupérer tous les comptes
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM Account";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int accountId = resultSet.getInt("account_id");
                String userName = resultSet.getString("user_name");
                double balance = resultSet.getDouble("balance");
                int currencyId = resultSet.getInt("currency_id");
                Account account = new Account(accountId, userName, balance, currencyId);
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    // Méthode pour mettre à jour un compte
    public void updateAccount(Account account) {
        String query = "UPDATE Account SET user_name=?, balance=?, currency_id=? WHERE account_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, account.getUserName());
            preparedStatement.setDouble(2, account.getBalance());
            preparedStatement.setInt(3, account.getCurrencyId());
            preparedStatement.setInt(4, account.getAccountId());
            preparedStatement.executeUpdate();
            System.out.println("Account updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour supprimer un compte
    public void deleteAccount(int accountId) {
        String query = "DELETE FROM Account WHERE account_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.executeUpdate();
            System.out.println("Account deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour fermer la connexion (à appeler à la fin)
    public void closeConnection() {
        DBConnection.closeConnection();
    }
}
