package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Location {

    private String name;
    private String address;

    private final List<ChargingStation> chargingStations = new ArrayList<>();

    // Pricing: "AC"/"DC" -> price per kWh
    private final Map<String, Double> pricing = new HashMap<>();

    public Location(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void rename(String newName) {
        this.name = newName;
    }

    public void updateAddress(String newAddress) {
        this.address = newAddress;
    }

    // -------------------------
    // Charging Stations
    // -------------------------

    public void addChargingStation(ChargingStation station) {
        chargingStations.add(station);
    }

    public void removeChargingStation(String stationId) {
        chargingStations.removeIf(s -> s.getStationId().equals(stationId));
    }

    public ChargingStation findChargingStation(String stationId) {
        return chargingStations.stream()
                .filter(s -> s.getStationId().equals(stationId))
                .findFirst()
                .orElse(null);
    }

    public List<ChargingStation> getChargingStations() {
        return chargingStations;
    }

    // -------------------------
    // Pricing
    // -------------------------

    public void setPrice(String mode, double pricePerKwh) {
        pricing.put(mode.trim(), pricePerKwh);
    }

    public double getPrice(String mode) {
        return pricing.getOrDefault(mode.trim(), 0.0);
    }
}
