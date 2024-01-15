package Models;

public class CategoryAmount {
    private String categoryName;
    private double totalAmount;

    public CategoryAmount(String categoryName) {
        this.categoryName = categoryName;
        this.totalAmount = totalAmount;
    }

    // Constructeur sans arguments
    public CategoryAmount() {
    }

    // Getters and Setters
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "CategoryAmount{" +
                "categoryName='" + categoryName + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
