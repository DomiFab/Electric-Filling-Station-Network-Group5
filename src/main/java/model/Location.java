package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Location {
    private String name;
    private String address;
    private final List<ChargingStation> chargingStations = new ArrayList<>();
    private final Map<String, Double> pricing = new HashMap<>();


    public Location(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void setPrice(String mode, double pricePerKwh) {
        pricing.put(mode.trim(), pricePerKwh);
    }

    public double getPrice(String mode) {
        return pricing.getOrDefault(mode.trim(), 0.0);
    }

    public String getName() { return name; }
    public String getAddress() { return address; }

    public void rename(String newName) {
        this.name = newName;
    }

    public void updateAddress(String newAddress) {
        this.address = newAddress;
    }

    public void addChargingStation(ChargingStation station) {
        chargingStations.add(station);
    }

    public void removeChargingStation(String stationId) {
        chargingStations.removeIf(s -> s.getStationId().equals(stationId));
    }

    public List<ChargingStation> getChargingStations() {
        return chargingStations;
    }


    @Override
    public String toString() {
        return "Location{name='" + name + "', address='" + address +
                "', chargingStations=" + chargingStations.size() + "}";
    }

}
