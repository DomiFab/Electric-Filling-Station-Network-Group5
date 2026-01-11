package steps;

import io.cucumber.java.en.Given;
import model.OperatingStatus;

import static steps.DuplicateSteps.ctx;

public class CommonSteps {

    @Given("a customer registry")
    public void a_customer_registry() {
        ctx.reset();
    }

    @Given("an existing electric filling station network")
    public void an_existing_network() {
        ctx.reset();
    }

    @Given("an electric filling station network exists")
    public void an_electric_filling_station_network_exists() {
        ctx.reset();
    }

    @Given("a location named {string} exists with address {string}")
    public void a_location_exists(String name, String address) {
        ctx.locationManagement.createLocation(name, address);
    }

    @Given("a charging station with id {string} exists at location {string} with charging mode {string}")
    public void station_exists(String stationId, String locationName, String mode) {
        ctx.chargingStationManagement.addChargingStation(locationName, stationId, mode);
    }

    @Given("pricing for location {string} and charging mode {string} is {double}")
    public void pricing_exists(String locationName, String mode, Double price) {
        ctx.locationPricingManagement.setPricePerKwh(locationName, mode, price);
    }

    @Given("charging station {string} has operating status {string}")
    public void station_has_status(String stationId, String status) {
        ctx.chargingStationManagement.setOperatingStatusByStationId(
                stationId, OperatingStatus.valueOf(status)
        );
    }

    @Given("a customer {string} exists")
    public void a_customer_exists(String customerName) {
        ctx.customerManagement.registerCustomer(customerName, customerName + "@example.com");
    }

    @Given("a customer {string} exists with balance {double}")
    public void a_customer_exists_with_balance(String customerId, Double balance) {
        ctx.customerManagement.registerCustomer(customerId, customerId + "@example.com");
        ctx.customerManagement.setBalance(customerId, balance);
    }
}
