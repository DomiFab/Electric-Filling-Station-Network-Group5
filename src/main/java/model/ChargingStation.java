package model;

public class ChargingStation {
    private final String stationId;
    private OperatingStatus status = OperatingStatus.AVAILABLE;

    public ChargingStation(String stationId) {
        this.stationId = stationId;
    }

    public String getStationId() { return stationId; }
    public OperatingStatus getStatus() { return status; }

    public void setStatus(OperatingStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ChargingStation{id='" + stationId + "'}";
    }

}
