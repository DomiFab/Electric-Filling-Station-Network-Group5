package steps;

import io.cucumber.java.en.*;
import model.Owner;
import model.Location;
import static org.junit.jupiter.api.Assertions.*;

public class ManageLocationsStep {

    private Owner owner;

    @Given("an existing electric filling station network")
    public void an_existing_network() {
        owner = new Owner();
    }

    @When("the owner creates a location {string} with address {string}")
    public void the_owner_creates_location(String name, String address) {
        owner.createLocation(name, address);
    }

    @When("the owner adds a charging station with id {string} to location {string}")
    public void the_owner_adds_station(String stationId, String locationName) {
        owner.addChargingStation(locationName, stationId);
    }

    @Then("the network contains a location named {string}")
    public void the_network_contains_location(String name) {
        assertNotNull(owner.findLocation(name));
    }

    @Then("location {string} has {int} charging stations.")
    public void location_has_charging_stations(String name, Integer expected) {
        Location loc = owner.findLocation(name);
        assertNotNull(loc);
        assertEquals(expected.intValue(), loc.getChargingStations().size());
    }

    @When("the owner renames the location {string} to {string}")
    public void rename_location(String oldName, String newName) {
        owner.renameLocation(oldName, newName);
    }

    @Then("the network does not contain a location named {string}.")
    public void the_network_does_not_contain(String name) {
        assertNull(owner.findLocation(name));
    }

    @Then("location {string} has {int} charging station.")
    public void location_has_one_station(String name, Integer expected) {
        Location loc = owner.findLocation(name);
        assertNotNull(loc);
        assertEquals(expected.intValue(), loc.getChargingStations().size());
    }

    @When("the owner removes the charging station {string} from location {string}")
    public void remove_station(String stationId, String locationName) {
        owner.removeChargingStation(locationName, stationId);
    }

    @When("the owner deletes the location {string}")
    public void delete_location(String name) {
        owner.deleteLocation(name);
    }

    @Then("the network contains no locations.")
    public void the_network_contains_no_locations() {
        assertTrue(owner.getLocations().isEmpty());
    }
}
