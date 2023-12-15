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
                // Récupération des variables d'environnement
                String url = System.getenv("DB_URL");
                String user = System.getenv("DB_USER");
                String password = System.getenv("DB_PASSWORD");

                // Vérification que les variables d'environnement ne sont pas nulles
                if (url != null && user != null && password != null) {
                    // Établissement de la connexion
                    connection = DriverManager.getConnection(url, user, password);
                    System.out.println("Connected to the database.");
                } else {
                    System.err.println("Environment variables for database connection are not set.");
                }
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
