package model;

public class ChargingStation {

    private final String stationId;
    private final String chargingMode;
    private OperatingStatus status = OperatingStatus.AVAILABLE;


    public ChargingStation(String stationId, String chargingMode) {
        this.stationId = stationId;
        this.chargingMode = chargingMode;
    }


    public String getStationId() { return stationId; }


    public OperatingStatus getStatus() { return status; }
    public void setStatus(OperatingStatus status) {
        this.status = status;
    }

    public String getChargingMode() {
        return chargingMode;
    }

    @Override
    public String toString() {
        return "ChargingStation{id='" + stationId + "', mode='" + chargingMode + "', status=" + status + "}";
    }
}



