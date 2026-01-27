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
        if (loc == null) {
            throw new IllegalArgumentException("Location \"" + locationName + "\" does not exist");
        }
        if (chargingMode == null || chargingMode.isBlank()) {
            throw new IllegalArgumentException("Charging mode must not be empty");
        }
        if (price < 0.0) {
            throw new IllegalArgumentException("Price must be >= 0.0");
        }

        loc.setPricePerKwh(chargingMode, price);
    }

    public double getPricePerKwh(String locationName, String chargingMode) {
        Location loc = network.findLocationByName(locationName);
        if (loc == null) {
            throw new IllegalArgumentException("Location \"" + locationName + "\" does not exist");
        }

        return loc.getPricePerKwh(chargingMode);
    }
}
