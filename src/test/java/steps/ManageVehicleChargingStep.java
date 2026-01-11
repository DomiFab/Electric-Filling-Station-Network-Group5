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
