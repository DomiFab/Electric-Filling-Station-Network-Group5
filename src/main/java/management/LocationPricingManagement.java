package management;

import model.ElectricFillingStationNetwork;
import model.Location;

public class LocationPricingManagement {

    private final ElectricFillingStationNetwork network;

    public LocationPricingManagement(ElectricFillingStationNetwork network) {
        this.network = network;
    }

    public void setPricePerKwh(String locationName, String chargingMode, double price) {
        Location loc = network.findLocationByName(locationName);
        if (loc == null) return;

        loc.setPricePerKwh(chargingMode, price);
    }

    public double getPricePerKwh(String locationName, String chargingMode) {
        Location loc = network.findLocationByName(locationName);
        if (loc == null) return 0.0;

        return loc.getPricePerKwh(chargingMode);
    }
}
