package management;

public class Owner {

    private final CustomerManagement customerManagement;
    private final LocationManagement locationManagement;
    private final ChargingStationManagement chargingStationManagement;
    private final InvoiceManagement invoiceManagement;
    private final NetworkStatusManagement networkStatusManagement;
    private final LocationPricingManagement locationPricingManagement;
    private final VehicleChargingManagement vehicleChargingManagement;

    public Owner(
            CustomerManagement customerManagement,
            LocationManagement locationManagement,
            ChargingStationManagement chargingStationManagement,
            InvoiceManagement invoiceManagement,
            NetworkStatusManagement networkStatusManagement,
            LocationPricingManagement locationPricingManagement,
            VehicleChargingManagement vehicleChargingManagement
    ) {
        this.customerManagement = customerManagement;
        this.locationManagement = locationManagement;
        this.chargingStationManagement = chargingStationManagement;
        this.invoiceManagement = invoiceManagement;
        this.networkStatusManagement = networkStatusManagement;
        this.locationPricingManagement = locationPricingManagement;
        this.vehicleChargingManagement = vehicleChargingManagement;
    }

    public CustomerManagement customers() {
        return customerManagement;
    }

    public LocationManagement locations() {
        return locationManagement;
    }

    public ChargingStationManagement stations() {
        return chargingStationManagement;
    }

    public InvoiceManagement invoices() {
        return invoiceManagement;
    }

    public NetworkStatusManagement networkStatus() {
        return networkStatusManagement;
    }

    public LocationPricingManagement pricing() {
        return locationPricingManagement;
    }

    public VehicleChargingManagement vehicleCharging() {
        return vehicleChargingManagement;
    }

    public void createLocation(String name, String address) {
        locationManagement.createLocation(name, address);
    }

    public void addChargingStation(String locationName, String stationId, String chargingMode) {
        chargingStationManagement.addChargingStation(locationName, stationId, chargingMode);
    }

    public void setPricePerKwh(String locationName, String mode, double price) {
        locationPricingManagement.setPricePerKwh(locationName, mode, price);
    }

    public void registerCustomer(String customerId, String email) {
        customerManagement.registerCustomer(customerId, email);
    }
}
