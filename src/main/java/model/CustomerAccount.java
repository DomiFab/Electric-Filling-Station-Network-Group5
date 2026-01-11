package model;

public class CustomerAccount {
    private double balance = 0.00;


    public double getBalance() { return balance; }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void topUp(double amount) { balance += amount; }

    public void deduct(double amount) { balance -= amount; }

    @Override
    public String toString() {
        return "Account{balance=" + balance + "}";
    }


}
