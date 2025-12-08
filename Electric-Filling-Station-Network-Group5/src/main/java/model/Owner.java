package model;

import java.util.ArrayList;
import java.util.List;

public class Owner {
    private final List<Location> locations = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();

    //Location CRUD Operations

    public void createLocation(String name, String address) {
        locations.add(new Location(name, address));
    }

    public Location findLocation(String name) {
        return locations.stream()
                .filter(l -> l.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void renameLocation(String oldName, String newName) {
        Location loc = findLocation(oldName);
        if (loc != null) loc.rename(newName);
    }

    public void deleteLocation(String name) {
        locations.removeIf(l -> l.getName().equals(name));
    }

    public List<Location> getLocations() {
        return locations;
    }

    //Charging Station CRUD Operations

    public void addChargingStation(String locationName, String stationId) {
        Location loc = findLocation(locationName);
        if (loc != null) {
            loc.addChargingStation(new ChargingStation(stationId));
        }
    }

    public void removeChargingStation(String locationName, String stationId) {
        Location loc = findLocation(locationName);
        if (loc != null) {
            loc.removeChargingStation(stationId);
        }
    }

    //Customer CRUD Operations

    public void registerCustomer(String name, String email) {
        customers.add(new Customer(name, email));
    }

    public Customer findCustomer(String name) {
        return customers.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void updateCustomerEmail(String name, String newEmail) {
        Customer c = findCustomer(name);
        if (c != null) c.updateEmail(newEmail);
    }

    public void deleteCustomer(String name) {
        customers.removeIf(c -> c.getName().equals(name));
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
