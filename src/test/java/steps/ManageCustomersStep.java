package steps;

import io.cucumber.java.en.*;
import model.Customer;

import static org.junit.jupiter.api.Assertions.*;
import static steps.DuplicateSteps.ctx;

public class ManageCustomersStep {

    @When("a customer registers with name {string} and email {string}")
    public void register_customer(String name, String email) {
        ctx.customerManagement.registerCustomer(name, email);
    }

    @When("a customer tries to register with name {string} and invalid email {string}")
    public void customer_tries_to_register_invalid_email(String name, String email) {
        try {
            ctx.customerManagement.registerCustomer(name, email);
        } catch (Exception e) {
            ctx.errorMsg = e.getMessage();
        }
    }

    @Then("an error indicates that the customer email {string} is invalid")
    public void an_error_indicates_invalid_email(String email) {
        assertEquals("Customer email \"" + email + "\" is invalid", ctx.errorMsg);
    }

    @Then("a customer with name {string} exists")
    public void customer_exists(String name) {
        assertNotNull(ctx.customerManagement.findCustomer(name));
    }

    @Then("the customer {string} has a unique customer id")
    public void the_customer_has_unique_id(String name) {
        Customer c = ctx.customerManagement.findCustomer(name);
        assertNotNull(c);
        assertNotNull(c.getCustomerId());
        assertFalse(c.getCustomerId().isBlank());
    }

    @Then("the customer {string} has a customer account with balance {double}.")
    public void customer_has_balance(String name, double expected) {
        Customer c = ctx.customerManagement.findCustomer(name);
        assertNotNull(c);
        assertEquals(expected, c.getAccount().getBalance(), 0.0001);
    }

    @When("the owner updates the email of customer {string} to {string}")
    public void update_email(String name, String newEmail) {
        ctx.customerManagement.updateCustomerEmail(name, newEmail);
    }

    @Then("the customer {string} has email {string}.")
    public void check_email(String name, String email) {
        assertEquals(email,
                ctx.customerManagement.findCustomer(name).getEmail());
    }

    @When("the owner deletes the customer {string}")
    public void delete_customer(String name) {
        ctx.customerManagement.deleteCustomer(name);
    }

    @Then("no customers exist.")
    public void no_customers_exist() {
        assertTrue(ctx.customerManagement.hasNoCustomers());
    }
}
