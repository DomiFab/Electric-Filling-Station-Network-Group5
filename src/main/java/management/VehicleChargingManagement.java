package management;

import model.*;

public class VehicleChargingManagement {

    private final ElectricFillingStationNetwork network;

    public VehicleChargingManagement(ElectricFillingStationNetwork network) {
        this.network = network;
    }

    public void startCharging(String customerId, String stationId) {
        Location foundLocation = null;
        ChargingStation station = null;

        for (Location loc : network.getLocations()) {
            ChargingStation s = loc.findChargingStationById(stationId);
            if (s != null) {
                foundLocation = loc;
                station = s;
                break;
            }
        }

        if (foundLocation == null || station == null) return;

        if (station.getStatus() != OperatingStatus.AVAILABLE) return;

        Customer customer = network.findCustomerByName(customerId);
        if (customer == null) return;

        if (customer.getAccount().getBalance() <= 0.0) return;

        double price = foundLocation.getPricePerKwh(station.getChargingMode());

        ChargingSession session = new ChargingSession(stationId, customerId, price);
        network.addActiveSession(session);
        station.setStatus(OperatingStatus.OCCUPIED);
    }

    public boolean hasActiveSession(String stationId) {
        return network.hasActiveSession(stationId);
    }

    public ChargingSession getActiveSession(String stationId) {
        return network.getActiveSession(stationId);
    }
    public double getActiveSessionPricePerKwh(String stationId) {
        ChargingSession session = network.getActiveSession(stationId);
        if (session == null) {
            throw new IllegalStateException("No active session for station " + stationId);
        }
        return session.getLockedPricePerKwh();
    }

}
