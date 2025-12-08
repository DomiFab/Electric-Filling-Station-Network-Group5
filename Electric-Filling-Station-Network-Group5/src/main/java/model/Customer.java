package model;

import java.util.UUID;

public class Customer {
    private final String customerId;
    private String name;
    private String email;
    private final CustomerAccount account;

    public Customer(String name, String email) {
        this.customerId = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.account = new CustomerAccount();
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public void updateEmail(String newEmail) {
        this.email = newEmail;
    }

    public CustomerAccount getAccount() {
        return account;
    }
}
