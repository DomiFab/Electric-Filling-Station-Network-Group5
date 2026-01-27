package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Invoice;
import model.InvoiceItem;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static steps.DuplicateSteps.ctx;

public class ManageCustomerAccountStep {

    @When("customer {string} tops up their account by {double}")
    public void customer_tops_up(String customerId, double amount) {
        ctx.customerManagement.topUp(customerId, amount);
        ctx.invoiceManagement.recordTopUp(customerId, amount, ctx.customerManagement.getBalance(customerId));
    }

    @When("customer {string} tries to top up their account by {double}")
    public void customer_tries_to_top_up(String customerId, double amount) {
        try {
            ctx.customerManagement.topUp(customerId, amount);
            ctx.invoiceManagement.recordTopUp(customerId, amount, ctx.customerManagement.getBalance(customerId));
        } catch (Exception e) {
            ctx.errorMsg = e.getMessage();
        }
    }

    @Then("customer {string} balance is {double}")
    public void customer_balance_is(String customerId, double expected) {
        assertEquals(expected, ctx.customerManagement.getBalance(customerId), 1e-9);
    }

    @Then("the latest invoice for customer {string} contains a top-up of {double}")
    public void latest_invoice_contains_topup(String customerId, double amount) {
        List<Invoice> invoices = ctx.invoiceManagement.getInvoicesForCustomer(customerId);
        assertFalse(invoices.isEmpty(), "No invoices found for " + customerId);
        Invoice latest = invoices.get(invoices.size() - 1);
        assertTrue(latest.getTopUps().stream().anyMatch(t -> Math.abs(t.getAmount() - amount) < 1e-9));
    }

    @Then("the latest invoice for customer {string} contains an item for station {string} with energy {double} kWh")
    public void latest_invoice_contains_item(String customerId, String stationId, double energyKwh) {
        List<Invoice> invoices = ctx.invoiceManagement.getInvoicesForCustomer(customerId);
        assertFalse(invoices.isEmpty(), "No invoices found for " + customerId);
        Invoice latest = invoices.get(invoices.size() - 1);
        assertTrue(
                latest.getItems().stream().anyMatch(i ->
                        i.getStationId().equals(stationId) && Math.abs(i.getEnergyKwh() - energyKwh) < 1e-9
                ),
                "No matching invoice item found"
        );
    }

    @Then("an error indicates that the top-up amount must be positive")
    public void error_topup_positive() {
        assertEquals("Top-up amount must be positive", ctx.errorMsg);
    }
}
