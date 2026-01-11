package model;

public class ChargingSession {

    private final String stationId;
    private final String customerId;
    private final double lockedPricePerKwh;

    public ChargingSession(String stationId, String customerId, double lockedPricePerKwh) {
        this.stationId = stationId;
        this.customerId = customerId;
        this.lockedPricePerKwh = lockedPricePerKwh;
    }

    public String getStationId() {
        return stationId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getLockedPricePerKwh() {
        return lockedPricePerKwh;
    }
}
