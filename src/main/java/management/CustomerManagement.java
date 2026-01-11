package management;

import model.Customer;
import model.ElectricFillingStationNetwork;

public class CustomerManagement {

    private final ElectricFillingStationNetwork network;

    public CustomerManagement(ElectricFillingStationNetwork network) {
        this.network = network;
    }

    public void registerCustomer(String name, String email) {
        network.addCustomer(new Customer(name, email));
    }

    public Customer findCustomer(String name) {
        return network.findCustomerByName(name);
    }

    public void updateCustomerEmail(String name, String newEmail) {
        Customer c = findCustomer(name);
        if (c != null) c.setEmail(newEmail);
    }

    public void deleteCustomer(String name) {
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
