package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;
import static steps.DuplicateSteps.ctx;

public class ManageVehicleChargingStep {

    @When("customer {string} starts charging at station {string}")
    public void customer_starts_charging(String customerId, String stationId) {
        ctx.vehicleChargingManagement.startCharging(customerId, stationId);
    }

    @When("customer {string} tries to start charging at station {string}")
    public void customer_tries_to_start_charging(String customerId, String stationId) {
        try {
            ctx.vehicleChargingManagement.startChargingValidated(customerId, stationId);
        } catch (Exception e) {
            ctx.errorMsg = e.getMessage();
        }
    }

    @Then("an error indicates that charging station {string} is not available")
    public void an_error_indicates_station_not_available(String stationId) {
        assertNotNull(ctx.errorMsg);
        assertTrue(ctx.errorMsg.startsWith("ChargingStation \"" + stationId + "\" is not available"));
    }

    //@Then("charging station {string} has operating status {string}")
    public void station_status_should_be(String stationId, String expectedStatus) {
        assertEquals(expectedStatus,
                ctx.chargingStationManagement.getOperatingStatusByStationId(stationId).name());
    }

    @Then("an active charging session exists for station {string}")
    public void active_session_exists(String stationId) {
        assertTrue(ctx.vehicleChargingManagement.hasActiveSession(stationId));
    }

    @Then("no active charging session exists for station {string}")
    public void no_active_session_exists(String stationId) {
        assertFalse(ctx.vehicleChargingManagement.hasActiveSession(stationId));
    }
}
