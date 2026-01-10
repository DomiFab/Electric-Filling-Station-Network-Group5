package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Owner {

    private final List<Location> locations = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final Map<String, ChargingSession> activeSessions = new HashMap<>();


    public void createLocation(String name, String address) {
        locations.add(new Location(name, address));
    }

    public Location findLocation(String name) {
        return locations.stream()
                .filter(l -> l.getName().equals(name))
                .findFirst()
                .orElse(null);
    }


    public void setLocationPrice(String locationName, String mode, double pricePerKwh) {
        Location loc = findLocation(locationName);
        if (loc != null) loc.setPrice(mode, pricePerKwh);
    }

    public double getLocationPrice(String locationName, String mode) {
        Location loc = findLocation(locationName);
        if (loc == null) return 0.0;
        return loc.getPrice(mode);
    }


    public void addChargingStation(String locationName, String stationId, String mode) {
        Location loc = findLocation(locationName);
        if (loc != null) {
            loc.addChargingStation(new ChargingStation(stationId, mode));
        }
    }

    public void updateChargingStationStatus(String stationId, String status) {
        ChargingStation station = findChargingStation(stationId);
        if (station != null) {
            station.setStatus(OperatingStatus.valueOf(status));
        }
    }

    public String getChargingStationStatus(String stationId) {
        ChargingStation station = findChargingStation(stationId);
        if (station == null) return "UNKNOWN";
        return station.getStatus().name();
    }

    private ChargingStation findChargingStation(String stationId) {
        for (Location loc : locations) {
            ChargingStation s = loc.findChargingStation(stationId);
            if (s != null) return s;
        }
        return null;
    }



    public void createCustomer(String name, double balance) {
        // Create customer (email is irrelevant for this MVP test)
        Customer c = new Customer(name, "dummy@local");

        customers.add(c);
    }

    public Customer findCustomer(String name) {
        return customers.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    // -------------------------
    // Vehicle Charging
    // -------------------------

    public void startCharging(String customerName, String stationId) {
        Customer customer = findCustomer(customerName);
        ChargingStation station = findChargingStation(stationId);

        if (customer == null || station == null) return;

        if (station.getStatus() != OperatingStatus.AVAILABLE) return;

        // Balance check (via Customer.getBalance() which returns account.getBalance())
        if (customer.getBalance() <= 0.0) return;

        activeSessions.put(stationId, new ChargingSession(stationId, customerName));
        station.setStatus(OperatingStatus.OCCUPIED);
    }

    public boolean hasActiveSession(String stationId) {
        return activeSessions.containsKey(stationId);
    }
}
