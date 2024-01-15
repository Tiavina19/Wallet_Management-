package Models;

public class TotalAmount {
    private double totalAmount;

    // Constructor without arguments
    public TotalAmount() {
    }

    // Constructor with one argument
    public TotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // Getter and setter
    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "TotalAmount{" +
                "totalAmount=" + totalAmount +
                '}';
    }
}
