package steps;

import io.cucumber.java.en.*;
import model.Invoice;
import model.InvoiceStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static steps.DuplicateSteps.ctx;

public class ManageInvoiceStatusStep {

    private List<Invoice> lastInvoices;

    @Given("the owner creates an invoice {string} for customer {string} with amount {double} and status {string}")
    public void owner_creates_invoice(String invoiceId, String customerName, double amount, String status) {
        ctx.invoiceManagement.addInvoiceForCustomer(customerName, new Invoice(invoiceId, customerName, amount, InvoiceStatus.valueOf(status))
        );
    }

    @When("the customer {string} requests all invoices")
    public void customer_requests_all_invoices(String customerName) {
        lastInvoices = ctx.invoiceManagement.getInvoicesForCustomer(customerName);
    }

    @Then("the customer {string} sees {int} invoices")
    public void customer_sees_invoices(String customerName, Integer expected) {
        assertNotNull(lastInvoices);
        assertEquals(expected.intValue(), lastInvoices.size());
    }

    @When("the owner updates invoice {string} status to {string}")
    public void owner_updates_invoice_status(String invoiceId, String status) {
        ctx.invoiceManagement.updateInvoiceStatus(invoiceId, status);
    }

    @Then("invoice {string} has status {string}")
    public void invoice_has_status(String invoiceId, String expectedStatus) {
        Invoice inv = ctx.invoiceManagement.findInvoice(invoiceId);
        assertNotNull(inv, "Invoice not found: " + invoiceId);

        InvoiceStatus expected = InvoiceStatus.valueOf(expectedStatus);
        assertEquals(expected, inv.getStatus());
    }
}
