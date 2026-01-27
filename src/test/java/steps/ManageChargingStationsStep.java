package steps;

import io.cucumber.java.en.*;
import model.OperatingStatus;
import model.ChargingStation;

import static org.junit.jupiter.api.Assertions.*;
import static steps.DuplicateSteps.ctx;

public class ManageChargingStationsStep {

    @When("the owner adds a charging station with id {string} and charging mode {string} to location {string}")
    public void the_owner_adds_a_charging_station_with_mode(String stationId, String chargingMode, String locationName) {
        ctx.chargingStationManagement.addChargingStation(locationName, stationId, chargingMode);
    }

    @When("the owner tries to add a charging station with id {string} and charging mode {string} to location {string}")
    public void the_owner_tries_to_add_a_charging_station(String stationId, String chargingMode, String locationName) {
        try {
            ctx.chargingStationManagement.addChargingStation(locationName, stationId, chargingMode);
        } catch (Exception e) {
            ctx.errorMsg = e.getMessage();
        }
    }

    @Then("an error indicates that charging station {string} already exists at location {string}")
    public void an_error_indicates_duplicate_station(String stationId, String locationName) {
        assertEquals(
                "ChargingStation \"" + stationId + "\" already exists at location \"" + locationName + "\"",
                ctx.errorMsg
        );
    }

    @Given("a charging station with id {string} and charging mode {string} exists at location {string}")
    public void a_charging_station_exists_at_location(String stationId, String chargingMode, String locationName) {
        ctx.chargingStationManagement.addChargingStation(locationName, stationId, chargingMode);
    }

    @Then("location {string} has {int} charging stations")
    public void location_has_charging_stations(String locationName, Integer expected) {
        assertNotNull(ctx.locationManagement.findLocation(locationName));
        assertEquals(expected.intValue(),
                ctx.locationManagement.findLocation(locationName).getChargingStations().size());
    }

    @Then("charging station {string} at location {string} has operating status {string}")
    public void charging_station_has_operating_status(String stationId, String locationName, String status) {
        ChargingStation station = ctx.chargingStationManagement.findChargingStation(locationName, stationId);
        assertNotNull(station);
        assertEquals(OperatingStatus.valueOf(status), station.getStatus());
    }

    @Then("charging station {string} at location {string} has charging mode {string}")
    public void charging_station_has_charging_mode(String stationId, String locationName, String chargingMode) {
        ChargingStation station = ctx.chargingStationManagement.findChargingStation(locationName, stationId);
        assertNotNull(station);
        assertEquals(chargingMode, station.getChargingMode());
    }

    @When("the owner sets the operating status of charging station {string} to {string}")
    public void the_owner_sets_the_operating_status(String stationId, String status) {
        ctx.chargingStationManagement.setOperatingStatusByStationId(stationId, OperatingStatus.valueOf(status));
    }

    @When("the owner removes the charging station {string} from location {string}")
    public void the_owner_removes_the_charging_station_from_location(String stationId, String locationName) {
        ctx.chargingStationManagement.removeChargingStation(locationName, stationId);
    }
}
