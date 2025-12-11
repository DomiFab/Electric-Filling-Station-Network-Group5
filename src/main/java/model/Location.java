package model;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private String name;
    private String address;
    private final List<ChargingStation> chargingStations = new ArrayList<>();

    public Location(String name, String address) {
        this.name = name;
        this.address = address;
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
