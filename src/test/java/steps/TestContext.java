package steps;

import management.*;
import model.ElectricFillingStationNetwork;

public class TestContext {
    public ElectricFillingStationNetwork network;
    public Owner owner;

    public CustomerManagement customerManagement;
    public LocationManagement locationManagement;
    public ChargingStationManagement chargingStationManagement;
    public InvoiceManagement invoiceManagement;
    public NetworkStatusManagement networkStatusManagement;
    public LocationPricingManagement locationPricingManagement;
    public VehicleChargingManagement vehicleChargingManagement;


    public void reset() {
        network = new ElectricFillingStationNetwork();
        customerManagement = new CustomerManagement(network);
        locationManagement = new LocationManagement(network);
        chargingStationManagement = new ChargingStationManagement(network);
        invoiceManagement = new InvoiceManagement(network);
        networkStatusManagement = new NetworkStatusManagement(network);
        locationPricingManagement = new LocationPricingManagement(network);
        vehicleChargingManagement = new VehicleChargingManagement(network);

        owner = new Owner(
                customerManagement,
                locationManagement,
                chargingStationManagement,
                invoiceManagement,
                networkStatusManagement,
                locationPricingManagement,
                vehicleChargingManagement
        );
    }
}
