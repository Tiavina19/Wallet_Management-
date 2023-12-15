package Services;

import Models.CategoryAmount;
import Models.DBConnection;
import Models.TotalAmount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbManager {
    public TotalAmount getTotalAmount(int accountId, Timestamp startDate, Timestamp endDate) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement stmt = conn.prepareCall("{ ? = call get_total_amount(?, ?, ?) }");
            stmt.registerOutParameter(1, Types.DOUBLE);
            stmt.setInt(2, accountId);
            stmt.setTimestamp(3, startDate);
            stmt.setTimestamp(4, endDate);
            stmt.execute();
    
            double amount = stmt.getDouble(1);
            TotalAmount totalAmount = new TotalAmount(amount);
            return totalAmount;
        }
    }
    
    public List<CategoryAmount> getCategoryAmounts(int accountId, Timestamp startDate, Timestamp endDate) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement stmt = conn.prepareCall("{ call get_category_amounts(?, ?, ?) }");
            stmt.setInt(1, accountId);
            stmt.setTimestamp(2, startDate);
            stmt.setTimestamp(3, endDate);

            ResultSet rs = stmt.executeQuery();
            List<CategoryAmount> categoryAmounts = new ArrayList<>();
            while (rs.next()) {
                CategoryAmount categoryAmount = new CategoryAmount();
                categoryAmount.setCategoryName(rs.getString("category_name"));
                categoryAmount.setTotalAmount(rs.getDouble("total_amount"));
                categoryAmounts.add(categoryAmount);
            }
            return categoryAmounts;
        }
    }
}
