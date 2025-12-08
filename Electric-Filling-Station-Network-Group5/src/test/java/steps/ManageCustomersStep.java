package steps;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;
import model.Customer;
import model.Owner;
public class ManageCustomersStep {

    private Owner owner;

    @Given("a customer registry")
    public void a_customer_registry() {
        owner = new Owner();
    }

    @When("a customer registers with name {string} and email {string}")
    public void register_customer(String name, String email) {
        owner.registerCustomer(name, email);
    }

    @Then("a customer with name {string} exists")
    public void customer_exists(String name) {
        assertNotNull(owner.findCustomer(name));
    }

    @Then("the customer {string} has a unique customer id")
    public void the_customer_has_unique_id(String name) {
        Customer c = owner.findCustomer(name);
        assertNotNull(c);
        assertNotNull(c.getCustomerId());
        assertFalse(c.getCustomerId().isBlank());
    }

    @Then("the customer {string} has a customer account with balance {double}.")
    public void customer_has_balance(String name, double expected) {
        Customer c = owner.findCustomer(name);
        assertNotNull(c);
        assertEquals(expected, c.getAccount().getBalance(), 0.0001);
    }

    @When("the owner updates the email of customer {string} to {string}")
    public void update_email(String name, String newEmail) {
        owner.updateCustomerEmail(name, newEmail);
    }

    @Then("the customer {string} has email {string}.")
    public void check_email(String name, String email) {
        assertEquals(email, owner.findCustomer(name).getEmail());
    }

    @When("the owner deletes the customer {string}")
    public void delete_customer(String name) {
        owner.deleteCustomer(name);
    }

    @Then("no customers exist.")
    public void no_customers_exist() {
        assertTrue(owner.getCustomers().isEmpty());
    }
}