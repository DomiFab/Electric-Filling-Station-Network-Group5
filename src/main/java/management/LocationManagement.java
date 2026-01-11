package management;

import model.ElectricFillingStationNetwork;
import model.Location;

public class LocationManagement {

    private final ElectricFillingStationNetwork network;

    public LocationManagement(ElectricFillingStationNetwork network) {
        this.network = network;
    }

    public void createLocation(String name, String address) {
        network.addLocation(new Location(name, address));
    }

    public Location findLocation(String name) {
        return network.findLocationByName(name);
    }

    public void renameLocation(String oldName, String newName) {
        Location loc = findLocation(oldName);
        if (loc == null) return;

        network.removeLocationByName(oldName);
        loc.setName(newName);
        network.addLocation(loc);
    }

    public void deleteLocation(String name) {
        network.removeLocationByName(name);
    }

    public boolean hasNoLocations() {
        return network.getLocations().isEmpty();
    }
}
