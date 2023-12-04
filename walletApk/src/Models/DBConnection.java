package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;

    // Constructeur privé pour empêcher l'instanciation
    private DBConnection() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // URL de connexion à la base de données PostgreSQL
                String url = "jdbc:postgresql://localhost:5432/wallet_management";
                // Nom d'utilisateur de la base de données
                String user = "wallet_admin";
                // Mot de passe de la base de données
                String password = "123456";

                // Établissement de la connexion
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to the database.");
            } catch (SQLException e) {
                // Log de l'exception (à remplacer par un logger approprié)
                System.err.println("Connection failed: " + e.getMessage());
            }
        }
        return connection;
    }

    // Méthode pour fermer la connexion
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                // Log de l'exception (à remplacer par un logger approprié)
                System.err.println("Failed to close the connection: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
}
