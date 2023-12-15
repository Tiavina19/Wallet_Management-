package Repository;
import Models.Category;
import Models.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface CategoryCrudOperations {
    default void addCategory(Category category) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO Category (id, name) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, category.getId());
                preparedStatement.setString(2, category.getName());
                preparedStatement.executeUpdate();
                System.out.println("Category added successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to add category: " + e.getMessage());
        }
    }

    default List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM Category";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Category category = new Category(id, name);
                    categories.add(category);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get categories: " + e.getMessage());
        }
        return categories;
    }

    default void deleteCategory(int categoryId) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "DELETE FROM Category WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, categoryId);
                preparedStatement.executeUpdate();
                System.out.println("Category deleted successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to delete category: " + e.getMessage());
        }
    }

    default void updateCategory(Category category) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "UPDATE Category SET name = ? WHERE id = ?";
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
}

