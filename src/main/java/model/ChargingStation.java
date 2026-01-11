package model;

public class ChargingStation {

    private final String stationId;
    private final String chargingMode; // AC or DC
    private OperatingStatus status = OperatingStatus.AVAILABLE;

    // Bestehender Konstruktor (behalten)
    public ChargingStation(String stationId) {
        this.stationId = stationId;
        this.chargingMode = "AC"; // Default
    }

    // NEU: Konstruktor mit Charging Mode
    public ChargingStation(String stationId, String chargingMode) {
        this.stationId = stationId;
        this.chargingMode = chargingMode;
    }

    public String getStationId() {
        return stationId;
    }

    public String getStationId() { return stationId; }

    public String getMode()  { return mode; }

    public OperatingStatus getStatus() { return status; }
    public String getChargingMode() {
        return chargingMode;
    }

    public OperatingStatus getStatus() {
        return status;
    }

    public void setStatus(OperatingStatus status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "ChargingStation{id='" + stationId + "', mode='" + chargingMode + "'}";
    }
}



