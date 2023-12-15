package Repository;

import Models.CurrencyValue;
import Models.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface CurrencyValueCrudOperations {
    // Méthode pour ajouter une valeur de devise
    default void addCurrencyValue(CurrencyValue currencyValue) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO CurrencyValue (id, currency_source, currency_destination, amount, date_effect) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, currencyValue.getId());
                preparedStatement.setString(2, currencyValue.getCurrencySource());
                preparedStatement.setString(3, currencyValue.getCurrencyDestination());
                preparedStatement.setDouble(4, currencyValue.getAmount());
                preparedStatement.setTimestamp(5, Timestamp.valueOf(currencyValue.getDateEffect()));
                preparedStatement.executeUpdate();
                System.out.println("Currency value added successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to add currency value: " + e.getMessage());
        }
    }

    // Méthode pour récupérer toutes les valeurs de devise
    default List<CurrencyValue> getAllCurrencyValues() {
        List<CurrencyValue> currencyValues = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM CurrencyValue";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String currencySource = resultSet.getString("currency_source");
                    String currencyDestination = resultSet.getString("currency_destination");
                    double amount = resultSet.getDouble("amount");
                    Timestamp dateEffect = resultSet.getTimestamp("date_effect");

                    CurrencyValue currencyValue = new CurrencyValue(id, currencySource, currencyDestination, amount, dateEffect.toLocalDateTime());
                    currencyValues.add(currencyValue);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get currency values: " + e.getMessage());
        }
        return currencyValues;
    }

    // Méthode pour supprimer une valeur de devise
    default void deleteCurrencyValue(int currencyValueId) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "DELETE FROM CurrencyValue WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, currencyValueId);
                preparedStatement.executeUpdate();
                System.out.println("Currency value deleted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to delete currency value: " + e.getMessage());
        }
    }

    // Méthode pour mettre à jour une valeur de devise
    default void updateCurrencyValue(CurrencyValue currencyValue) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "UPDATE CurrencyValue SET currency_source = ?, currency_destination = ?, amount = ?, date_effect = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, currencyValue.getCurrencySource());
                preparedStatement.setString(2, currencyValue.getCurrencyDestination());
                preparedStatement.setDouble(3, currencyValue.getAmount());
                preparedStatement.setTimestamp(4, Timestamp.valueOf(currencyValue.getDateEffect()));
                preparedStatement.setInt(5, currencyValue.getId());
                preparedStatement.executeUpdate();
                System.out.println("Currency value updated successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update currency value: " + e.getMessage());
        }
    }
}
