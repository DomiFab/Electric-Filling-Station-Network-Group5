package steps;

import io.cucumber.java.en.*;
import model.Invoice;
import model.Owner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceStatusStep {

    private Owner owner;
    private List<Invoice> lastInvoices;

    @Given("an electric filling station network exists")
    public void network_exists() {
        owner = new Owner();
    }

    @Given("a customer {string} exists")
    public void customer_exists(String customerName) {
        owner.createCustomer(customerName);
    }

    @Given("the owner creates an invoice {string} for customer {string} with amount {double} and status {string}")
    public void owner_creates_invoice(String invoiceId, String customerName, Double amount, String status) {
        owner.createInvoice(customerName, invoiceId, amount, status);
    }

    @When("the customer {string} requests all invoices")
    public void customer_requests_all_invoices(String customerName) {
        lastInvoices = owner.getInvoicesForCustomer(customerName);
    }

    @Then("the customer {string} sees {int} invoices")
    public void customer_sees_invoices(String customerName, Integer expected) {
        assertNotNull(lastInvoices);
        assertEquals(expected.intValue(), lastInvoices.size());
    }

    @When("the owner updates invoice {string} status to {string}")
    public void owner_updates_invoice_status(String invoiceId, String status) {
        owner.updateInvoiceStatus(invoiceId, status);
    }

    @Then("invoice {string} has status {string}")
    public void invoice_has_status(String invoiceId, String expected) {
        Invoice inv = owner.findInvoice(invoiceId);
        assertNotNull(inv);
        assertEquals(expected, inv.getStatus().name());
    }
}
