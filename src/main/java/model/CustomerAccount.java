package model;

public class CustomerAccount {
    private double balance = 0.00;

    public static double roundMoney(double value) {
        return java.math.BigDecimal.valueOf(value)
                .setScale(2, java.math.RoundingMode.HALF_UP)
                .doubleValue();
    }


    public double getBalance() {
        return roundMoney(balance);
    }

    public void setBalance(double balance) {
        this.balance = roundMoney(balance);
    }

    public void topUp(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Top-up amount must be positive");
        }
        balance = roundMoney(balance + amount);
    }

    public void deduct(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Deduction amount must be non-negative");
        }
        if (balance < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        balance = roundMoney(balance - amount);
    }

    @Override
    public String toString() {
        return "Account{balance=" + String.format("%.2f", getBalance()) + "}";
    }


}
