package steps;

import io.cucumber.java.en.*;
import model.Owner;

import static org.junit.jupiter.api.Assertions.*;

public class ManageVehicleCharging {

    private Owner owner;

    @Given("an electric filling station network exists")
    public void an_existing_network() {
        owner = new Owner();
    }

    @Given("a location named {string} exists with address {string}")
    public void a_location_exists(String name, String address) {
        owner.createLocation(name, address);
    }

    @Given("pricing for location {string} and charging mode {string} is {double}")
    public void pricing_exists(String locationName, String mode, Double price) {
        owner.setLocationPrice(locationName, mode, price.doubleValue());
    }

    @Given("a charging station with id {string} exists at location {string} with charging mode {string}")
    public void station_exists(String stationId, String locationName, String mode) {
        owner.addChargingStation(locationName, stationId, mode);
    }

    @Given("charging station {string} has operating status {string}")
    public void station_has_status(String stationId, String status) {
        owner.updateChargingStationStatus(stationId, status);
    }

    @Given("a customer {string} exists with balance {double}")
    public void customer_exists(String customerId, Double balance) {
        owner.createCustomer(customerId, balance.doubleValue());
    }

    @When("customer {string} starts charging at station {string}")
    public void customer_starts_charging(String customerId, String stationId) {
        owner.startCharging(customerId, stationId);
    }

    @Then("charging station {string} has operating status {string}")
    public void station_status_should_be(String stationId, String expectedStatus) {
        assertEquals(expectedStatus, owner.getChargingStationStatus(stationId));
    }

    @Then("an active charging session exists for station {string}")
    public void active_session_exists(String stationId) {
        assertTrue(owner.hasActiveSession(stationId));
    }

    @Then("no active charging session exists for station {string}")
    public void no_active_session_exists(String stationId) {
        assertFalse(owner.hasActiveSession(stationId));
    }
}

