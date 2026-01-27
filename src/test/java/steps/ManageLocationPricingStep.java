package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static steps.DuplicateSteps.ctx;

public class ManageLocationPricingStep {

    @When("the owner defines pricing for location {string} as:")
    public void the_owner_defines_pricing(String locationName, DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            String mode = row.get("chargingMode");
            double price = Double.parseDouble(row.get("pricePerKwh"));
            ctx.locationPricingManagement.setPricePerKwh(locationName, mode, price);
        }
    }

    @Then("the price for location {string} and charging mode {string} is {double}")
    public void the_price_is(String locationName, String mode, Double expected) {
        double actual = ctx.locationPricingManagement.getPricePerKwh(locationName, mode);
        assertEquals(expected, actual, 0.0001);
    }

    @When("the owner updates the price for location {string} and charging mode {string} to {double}")
    public void update_price(String locationName, String mode, Double newPrice) {
        ctx.locationPricingManagement.setPricePerKwh(locationName, mode, newPrice);
    }

    @When("the owner tries to set the price for location {string} and charging mode {string} to {double}")
    public void the_owner_tries_to_set_negative_price(String locationName, String mode, Double newPrice) {
        try {
            ctx.locationPricingManagement.setPricePerKwh(locationName, mode, newPrice);
        } catch (Exception e) {
            ctx.errorMsg = e.getMessage();
        }
    }

    @Then("an error indicates that the price must be non-negative")
    public void an_error_indicates_price_must_be_non_negative() {
        assertEquals("Price must be >= 0.0", ctx.errorMsg);
    }

    @Then("the active charging session at station {string} uses price {double}")
    public void the_active_charging_session_at_station_uses_price(String stationId, Double expectedPrice) {

        assertTrue(
                ctx.vehicleChargingManagement.hasActiveSession(stationId),
                "Expected an active charging session for station " + stationId
        );

        double actualPrice =
                ctx.vehicleChargingManagement.getActiveSessionPricePerKwh(stationId);

        assertEquals(expectedPrice, actualPrice, 0.0001);
    }

}
