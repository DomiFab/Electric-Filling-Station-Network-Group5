package model;

public class CustomerAccount {
    private double balance = 0.00;

    public double getBalance() { return balance; }

    public void topUp(double amount) { balance += amount; }

    public void deduct(double amount) { balance -= amount; }
}
