package management;

import model.ElectricFillingStationNetwork;
import model.Location;

public class LocationManagement {

    private final ElectricFillingStationNetwork network;

    public LocationManagement(ElectricFillingStationNetwork network) {
        this.network = network;
    }

    public void createLocation(String name, String address) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Location name must not be empty");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Location address must not be empty");
        }
        if (network.findLocationByName(name) != null) {
            throw new IllegalArgumentException("Location \"" + name + "\" already exists");
        }
        network.addLocation(new Location(name, address));
    }

    public Location findLocation(String name) {
        return network.findLocationByName(name);
    }

    public void renameLocation(String oldName, String newName) {
        Location loc = findLocation(oldName);
        if (loc == null) {
            throw new IllegalArgumentException("Location \"" + oldName + "\" does not exist");
        }
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Location name must not be empty");
        }
        if (network.findLocationByName(newName) != null) {
            throw new IllegalArgumentException("Location \"" + newName + "\" already exists");
        }

        network.removeLocationByName(oldName);
        loc.setName(newName);
        network.addLocation(loc);
    }

    public void deleteLocation(String name) {
        if (network.findLocationByName(name) == null) {
            throw new IllegalArgumentException("Location \"" + name + "\" does not exist");
        }
        network.removeLocationByName(name);
    }

    public boolean hasNoLocations() {
        return network.getLocations().isEmpty();
    }
}
