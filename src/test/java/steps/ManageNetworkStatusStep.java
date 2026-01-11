package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static steps.DuplicateSteps.ctx;

public class ManageNetworkStatusStep {

    private String lastNetworkStatus;

    @When("the customer requests the network status")
    public void customer_requests_network_status() {
        lastNetworkStatus = ctx.networkStatusManagement.getNetworkStatus().name();
    }

    @Then("the network status is {string}")
    public void network_status_is(String expected) {
        assertEquals(expected, lastNetworkStatus);
    }
}
