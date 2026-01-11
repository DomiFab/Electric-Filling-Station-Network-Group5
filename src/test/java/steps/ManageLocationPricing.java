package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import model.Owner;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ManageLocationPricing {

    private Owner owner;

    @Given("an electric filling station network exists")
    public void an_existing_network() {
        owner = new Owner();
    }

    @Given("a location named {string} exists with address {string}")
    public void a_location_exists(String name, String address) {
        owner.createLocation(name, address);
    }

    @When("the owner defines pricing for location {string} as:")
    public void the_owner_defines_pricing(String locationName, DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            String mode = row.get("chargingMode");
            double price = Double.parseDouble(row.get("pricePerKwh"));
            owner.setLocationPrice(locationName, mode, price);
        }
    }

    @Then("the price for location {string} and charging mode {string} is {double}")
    public void the_price_is(String locationName, String mode, Double expected) {
        double actual = owner.getLocationPrice(locationName, mode);
        assertEquals(expected.doubleValue(), actual);
    }

    @Given("pricing for location {string} and charging mode {string} is {double}")
    public void pricing_exists(String locationName, String mode, Double price) {
        owner.setLocationPrice(locationName, mode, price.doubleValue());
    }

    @When("the owner updates the price for location {string} and charging mode {string} to {double}")
    public void update_price(String locationName, String mode, Double newPrice) {
        owner.setLocationPrice(locationName, mode, newPrice.doubleValue());
    }
}

