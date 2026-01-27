package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static steps.DuplicateSteps.ctx;

public class ManageNetworkStatusStep {

    private String lastNetworkStatus;
    private String lastReport;

    @When("the customer requests the network status")
    public void customer_requests_network_status() {
        lastNetworkStatus = ctx.networkStatusManagement.getNetworkStatus().name();
    }

    @When("the customer requests the network status report")
    public void customer_requests_network_status_report() {
        lastReport = ctx.networkStatusManagement.getNetworkStatusReport();
    }

    @Then("the network status report contains {string}")
    public void report_contains(String expectedFragment) {
        org.junit.jupiter.api.Assertions.assertNotNull(lastReport);
        org.junit.jupiter.api.Assertions.assertTrue(
                lastReport.contains(expectedFragment),
                "Report did not contain: " + expectedFragment + "\nReport was:\n" + lastReport
        );
    }

    @Then("the network status is {string}")
    public void network_status_is(String expected) {
        assertEquals(expected, lastNetworkStatus);
    }
}
