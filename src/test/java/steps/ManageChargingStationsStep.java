package steps;

import io.cucumber.java.en.*;
import model.Owner;
import model.Location;
import model.ChargingStation;
import model.OperatingStatus;

import static org.junit.jupiter.api.Assertions.*;
import static steps.DuplicateSteps.owner;

public class ManageChargingStationsStep {

    /* ---------- Background ---------- */

    @Given("an electric filling station network exists")
    public void an_electric_filling_station_network_exists() {
        owner = new Owner();
    }

    @Given("a location named {string} exists with address {string}")
    public void a_location_named_exists_with_address(String name, String address) {
        owner.createLocation(name, address);
    }

    /* ---------- Create ---------- */

    @When("the owner adds a charging station with id {string} to location {string}")
    public void the_owner_adds_a_charging_station(String stationId, String locationName) {
        owner.addChargingStation(locationName, stationId);
    }

    @Given("a charging station with id {string} exists at location {string}")
    public void a_charging_station_exists_at_location(String stationId, String locationName) {
        owner.addChargingStation(locationName, stationId);
    }

    /* ---------- Read ---------- */

    @Then("location {string} has {int} charging stations")
    public void location_has_charging_stations(String locationName, Integer expected) {
        Location loc = owner.findLocation(locationName);
        assertNotNull(loc);
        assertEquals(expected.intValue(), loc.getChargingStations().size());
    }

    @Then("charging station {string} at location {string} has operating status {string}")
    public void charging_station_has_operating_status(
            String stationId, String locationName, String status) {

        ChargingStation station = findStation(locationName, stationId);
        assertNotNull(station);
        assertEquals(OperatingStatus.valueOf(status), station.getStatus());
    }

    /* ---------- Update ---------- */

    @When("the owner sets the operating status of charging station {string} to {string}")
    public void the_owner_sets_operating_status(String stationId, String status) {
        owner.setChargingStationStatus(
                findLocationOfStation(stationId),
                stationId,
                OperatingStatus.valueOf(status)
        );
    }

    /* ---------- Delete ---------- */

    @When("the owner removes the charging station {string} from location {string}")
    public void the_owner_removes_the_charging_station(String stationId, String locationName) {
        owner.removeChargingStation(locationName, stationId);
    }

    /* ---------- Helpers ---------- */

    private ChargingStation findStation(String locationName, String stationId) {
        Location loc = owner.findLocation(locationName);
        assertNotNull(loc);

        return loc.getChargingStations().stream()
                .filter(s -> s.getStationId().equals(stationId))
                .findFirst()
                .orElse(null);
    }

    private String findLocationOfStation(String stationId) {
        return owner.getLocations().stream()
                .filter(l -> l.getChargingStations().stream()
                        .anyMatch(s -> s.getStationId().equals(stationId)))
                .map(Location::getName)
                .findFirst()
                .orElseThrow();
    }
}
