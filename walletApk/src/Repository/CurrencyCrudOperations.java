package Repository;

import Models.Currency;
import Models.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyCrudOperations {
    private final Connection connection;

    public CurrencyCrudOperations() {
        // Obtenez la connexion depuis la classe DBConnection
        this.connection = DBConnection.getConnection();
    }

    // Méthode pour ajouter une devise
    public void addCurrency(Currency currency) {
        String query = "INSERT INTO Currency (currency_name, exchange_rate) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, currency.getCurrencyName());
            preparedStatement.setDouble(2, currency.getExchangeRate());
            preparedStatement.executeUpdate();
            System.out.println("Currency added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour récupérer toutes les devises
    public List<Currency> getAllCurrencies() {
        List<Currency> currencies = new ArrayList<>();
        String query = "SELECT * FROM Currency";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int currencyId = resultSet.getInt("currency_id");
                String currencyName = resultSet.getString("currency_name");
                double exchangeRate = resultSet.getDouble("exchange_rate");
                Currency currency = new Currency(currencyId, currencyName, exchangeRate);
                currencies.add(currency);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currencies;
    }

    // Méthode pour fermer la connexion (à appeler à la fin)
    public void closeConnection() {
        DBConnection.closeConnection();
    }
}
