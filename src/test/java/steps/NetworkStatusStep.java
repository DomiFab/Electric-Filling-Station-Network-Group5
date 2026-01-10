package steps;

import io.cucumber.java.en.*;
import model.Owner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NetworkStatusStep {

    private Owner owner;
    private String lastNetworkStatus;

    @Given("an electric filling station network exists")
    public void network_exists() {
        owner = new Owner();
    }

    @Given("a location named {string} exists with address {string}")
    public void location_exists(String name, String address) {
        owner.createLocation(name, address);
    }

    @Given("a charging station with id {string} exists at location {string} with charging mode {string}")
    public void station_exists(String stationId, String locationName, String mode) {
        owner.addChargingStation(locationName, stationId, mode);
    }

    @Given("charging station {string} has operating status {string}")
    public void station_has_status(String stationId, String status) {
        owner.updateChargingStationStatus(stationId, status);
    }

    @When("the customer requests the network status")
    public void customer_requests_network_status() {
        lastNetworkStatus = owner.getNetworkStatus();
    }

    @Then("the network status is {string}")
    public void network_status_is(String expected) {
        assertEquals(expected, lastNetworkStatus);
    }

    @When("charging station {string} has operating status {string}")
    public void change_station_status(String stationId, String status) {
        owner.updateChargingStationStatus(stationId, status);
    }
}
