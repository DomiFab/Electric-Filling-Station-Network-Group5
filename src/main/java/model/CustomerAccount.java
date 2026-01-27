package model;

public class CustomerAccount {
    private double balance = 0.00;


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void topUp(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Top-up amount must be positive");
        }
        balance += amount;
    }

    public void deduct(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Deduction amount must be non-negative");
        }
        if (balance < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        balance -= amount;
    }

    @Override
    public String toString() {
        return "Account{balance=" + balance + "}";
    }


}
