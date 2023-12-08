package Repository;

import Models.Currency;
import Models.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface CurrencyCrudOperations {
    // Méthode pour ajouter une devise
    default void addCurrency(Currency currency) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO Currency (id, name, code) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, currency.getId());
                preparedStatement.setString(2, currency.getName());
                preparedStatement.setString(3, currency.getCode());
                preparedStatement.executeUpdate();
                System.out.println("Currency added successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to add currency: " + e.getMessage());
        }
    }

    // Méthode pour récupérer toutes les devises
    default List<Currency> getAllCurrencies() {
        List<Currency> currencies = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM Currency";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    String code = resultSet.getString("code");
                    Currency currency = new Currency(id, name, code);
                    currencies.add(currency);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get currencies: " + e.getMessage());
        }
        return currencies;
    }

    // Méthode pour supprimer une devise
    default void deleteCurrency(String currencyId) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "DELETE FROM Currency WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, currencyId);
                preparedStatement.executeUpdate();
                System.out.println("Currency deleted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to delete currency: " + e.getMessage());
        }
    }

    // Méthode pour mettre à jour une devise
    default void updateCurrency(Currency currency) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "UPDATE Currency SET name = ?, code = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, currency.getName());
                preparedStatement.setString(2, currency.getCode());
                preparedStatement.setString(3, currency.getId());
                preparedStatement.executeUpdate();
                System.out.println("Currency updated successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update currency: " + e.getMessage());
        }
    }
}
