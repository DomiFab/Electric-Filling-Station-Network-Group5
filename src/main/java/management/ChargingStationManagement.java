package management;

import model.*;

public class ChargingStationManagement {

    private final ElectricFillingStationNetwork network;

    public ChargingStationManagement(ElectricFillingStationNetwork network) {
        this.network = network;
    }

    public void addChargingStation(String locationName, String stationId, String chargingMode) {
        Location loc = network.findLocationByName(locationName);
        if (loc == null) {
            throw new IllegalArgumentException("Location \"" + locationName + "\" does not exist");
        }
        if (stationId == null || stationId.isBlank()) {
            throw new IllegalArgumentException("ChargingStation id must not be empty");
        }
        if (loc.findChargingStationById(stationId) != null) {
            throw new IllegalArgumentException(
                    "ChargingStation \"" + stationId + "\" already exists at location \"" + locationName + "\""
            );
        }
        loc.addChargingStation(new ChargingStation(stationId, chargingMode));
    }

    public void removeChargingStation(String locationName, String stationId) {
        Location loc = network.findLocationByName(locationName);
        if (loc == null) {
            throw new IllegalArgumentException("Location \"" + locationName + "\" does not exist");
        }
        if (loc.findChargingStationById(stationId) == null) {
            throw new IllegalArgumentException(
                    "ChargingStation \"" + stationId + "\" does not exist at location \"" + locationName + "\""
            );
        }
        loc.removeChargingStation(stationId);
    }

    public ChargingStation findChargingStation(String locationName, String stationId) {
        Location loc = network.findLocationByName(locationName);
        if (loc == null) return null;

        return loc.findChargingStationById(stationId);
    }

    public void setOperatingStatusByStationId(String stationId, String status) {
        setOperatingStatusByStationId(stationId, model.OperatingStatus.valueOf(status));
    }

    public void setOperatingStatusByStationId(String stationId, model.OperatingStatus status) {
        for (model.Location loc : network.getLocations()) {
            model.ChargingStation station = loc.findChargingStationById(stationId);
            if (station != null) {
                station.setStatus(status);
                return;
            }
        }
    }

    public OperatingStatus getOperatingStatusByStationId(String stationId) {
        for (var loc : network.getLocations()) {
            var s = loc.findChargingStationById(stationId);
            if (s != null) return s.getStatus();
        }
        return null;
    }

}
