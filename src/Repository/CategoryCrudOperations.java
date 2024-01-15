package Repository;
import Models.Category;
import Models.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface CategoryCrudOperations {
    // Méthode pour ajouter une catégorie
    default void addCategory(Category category) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO Category (category_name) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, category.getName());
                preparedStatement.executeUpdate();
                System.out.println("Category added successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to add category: " + e.getMessage());
        }
    }

    // Méthode pour récupérer toutes les catégories
    default List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM Category";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("category_id");
                    String name = resultSet.getString("category_name");
                    Category category = new Category(id, name);
                    categories.add(category);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get categories: " + e.getMessage());
        }
        return categories;
    }

    // Méthode pour supprimer une catégorie
    default void deleteCategory(int categoryId) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "DELETE FROM Category WHERE category_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, categoryId);
                preparedStatement.executeUpdate();
                System.out.println("Category deleted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to delete category: " + e.getMessage());
        }
    }

    // Méthode pour mettre à jour une catégorie
    default void updateCategory(Category category) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "UPDATE Category SET category_name = ? WHERE category_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, category.getName());
                preparedStatement.setInt(2, category.getId());
                preparedStatement.executeUpdate();
                System.out.println("Category updated successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update category: " + e.getMessage());
        }
    }

    // Méthode pour récupérer les catégories pour un compte spécifique dans une plage de dates
    default List<Category> getCategoriesForAccount(int accountId, Timestamp startDate, Timestamp endDate) {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT DISTINCT c.* FROM Category c " +
                    "JOIN Transaction tr ON tr.category_id = c.category_id " +
                    "WHERE (tr.account_source_id = ? OR tr.account_destination_id = ?) " +
                    "AND tr.transaction_date BETWEEN ? AND ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, accountId);
                preparedStatement.setInt(2, accountId);
                preparedStatement.setTimestamp(3, startDate);
                preparedStatement.setTimestamp(4, endDate);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("category_id");
                    String name = resultSet.getString("category_name");
                    Category category = new Category(id, name);
                    categories.add(category);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get categories: " + e.getMessage());
        }
        return categories;
    }
}
