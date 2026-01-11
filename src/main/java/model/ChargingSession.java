package model;

public class ChargingSession {

    private final String stationId;
    private final String customerName;

    public ChargingSession(String stationId, String customerName) {
        this.stationId = stationId;
        this.customerName = customerName;
    }

    public String getStationId() {
        return stationId;
    }

    public String getCustomerName() {
        return customerName;
    }
}

