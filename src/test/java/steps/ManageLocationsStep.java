package steps;

import io.cucumber.java.en.*;
import model.Location;

import static org.junit.jupiter.api.Assertions.*;
import static steps.DuplicateSteps.ctx;

public class ManageLocationsStep {

    @When("the owner creates a location {string} with address {string}")
    public void the_owner_creates_location(String name, String address) {
        ctx.locationManagement.createLocation(name, address);
    }

    @When("the owner tries to create a location {string} with address {string}")
    public void the_owner_tries_to_create_location(String name, String address) {
        try {
            ctx.locationManagement.createLocation(name, address);
        } catch (Exception e) {
            ctx.errorMsg = e.getMessage();
        }
    }

    @Then("an error indicates that location {string} already exists")
    public void an_error_indicates_duplicate_location(String name) {
        assertEquals("Location \"" + name + "\" already exists", ctx.errorMsg);
    }

    @Then("the network contains a location named {string}")
    public void the_network_contains_location(String name) {
        assertNotNull(ctx.locationManagement.findLocation(name));
    }

    @Then("the network does not contain a location named {string}.")
    public void the_network_does_not_contain(String name) {
        assertNull(ctx.locationManagement.findLocation(name));
    }

    @When("the owner renames the location {string} to {string}")
    public void rename_location(String oldName, String newName) {
        ctx.locationManagement.renameLocation(oldName, newName);
    }

    @When("the owner deletes the location {string}")
    public void delete_location(String name) {
        ctx.locationManagement.deleteLocation(name);
    }

    @Then("location {string} has {int} charging stations.")
    public void location_has_charging_stations(String name, Integer expected) {
        Location loc = ctx.locationManagement.findLocation(name);
        assertNotNull(loc);
        assertEquals(expected.intValue(), loc.getChargingStations().size());
    }

    @Then("location {string} has {int} charging station.")
    public void location_has_charging_station(String name, Integer expected) {
        Location loc = ctx.locationManagement.findLocation(name);
        assertNotNull(loc);
        assertEquals(expected.intValue(), loc.getChargingStations().size());
    }

    @Then("the network contains no locations.")
    public void the_network_contains_no_locations() {
        assertTrue(ctx.locationManagement.hasNoLocations());
    }
}
