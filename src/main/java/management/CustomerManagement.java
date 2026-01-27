package management;

import model.Customer;
import model.ElectricFillingStationNetwork;

public class CustomerManagement {

    private final ElectricFillingStationNetwork network;

    public CustomerManagement(ElectricFillingStationNetwork network) {
        this.network = network;
    }

    public void registerCustomer(String name, String email) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Customer name must not be empty");
        }
        if (network.findCustomerByName(name) != null) {
            throw new IllegalArgumentException("Customer \"" + name + "\" already exists");
        }
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("Customer email \"" + email + "\" is invalid");
        }
        network.addCustomer(new Customer(name, email));
    }

    public Customer findCustomer(String name) {
        return network.findCustomerByName(name);
    }

    public void updateCustomerEmail(String name, String newEmail) {
        Customer c = findCustomer(name);
        if (c == null) {
            throw new IllegalArgumentException("Customer \"" + name + "\" does not exist");
        }
        if (newEmail == null || newEmail.isBlank() || !newEmail.contains("@")) {
            throw new IllegalArgumentException("Customer email \"" + newEmail + "\" is invalid");
        }
        c.setEmail(newEmail);
    }

    public void deleteCustomer(String name) {
        if (network.findCustomerByName(name) == null) {
            throw new IllegalArgumentException("Customer \"" + name + "\" does not exist");
        }
        network.removeCustomerByName(name);
    }

    public boolean hasNoCustomers() {
        return network.getCustomers().isEmpty();
    }

    public void setBalance(String customerName, double balance) {
        Customer c = findCustomer(customerName);
        if (c != null) c.getAccount().setBalance(balance);
    }

}
