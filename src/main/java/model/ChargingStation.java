package model;

public class ChargingStation {
    private final String stationId;
    private OperatingStatus status = OperatingStatus.AVAILABLE;
    private final String mode;

    public ChargingStation(String stationId, String mode) {
        this.stationId = stationId;
        this.mode = mode;
    }

    public String getStationId() { return stationId; }

    public String getMode()  { return mode; }

    public OperatingStatus getStatus() { return status; }

    public void setStatus(OperatingStatus status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "ChargingStation{id='" + stationId + "'}";
    }

}
