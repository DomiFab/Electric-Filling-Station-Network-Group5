package steps;

import io.cucumber.java.en.*;
import model.Invoice;
import model.InvoiceStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static steps.DuplicateSteps.ctx;

public class ManageInvoiceStatusStep {

    private List<Invoice> lastInvoices;

    @Given("the system creates an invoice {string} for customer {string} with status {string}")
    public void system_creates_invoice(String invoiceId, String customerId, String status) {
        ctx.invoiceManagement.addInvoiceForCustomer(customerId,
                new Invoice(invoiceId, customerId, InvoiceStatus.valueOf(status))
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

    @When("the owner tries to update invoice {string} status to {string}")
    public void owner_tries_to_update_non_existing_invoice(String invoiceId, String status) {
        try {
            ctx.invoiceManagement.updateInvoiceStatus(invoiceId, status);
        } catch (Exception e) {
            ctx.errorMsg = e.getMessage();
        }
    }

    @Then("an error indicates that invoice {string} does not exist")
    public void an_error_indicates_invoice_does_not_exist(String invoiceId) {
        assertEquals("Invoice \"" + invoiceId + "\" does not exist", ctx.errorMsg);
    }

    @Then("invoice {string} has status {string}")
    public void invoice_has_status(String invoiceId, String expectedStatus) {
        Invoice inv = ctx.invoiceManagement.findInvoice(invoiceId);
        assertNotNull(inv, "Invoice not found: " + invoiceId);

        InvoiceStatus expected = InvoiceStatus.valueOf(expectedStatus);
        assertEquals(expected, inv.getStatus());
    }
}
