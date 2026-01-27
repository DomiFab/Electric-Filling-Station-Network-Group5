package management;

import model.*;

import java.time.Instant;

public class VehicleChargingManagement {

    private final ElectricFillingStationNetwork network;

    public VehicleChargingManagement(ElectricFillingStationNetwork network) {
        this.network = network;
    }

    public void startCharging(String customerId, String stationId) {
        try {
            startChargingValidated(customerId, stationId);
        } catch (Exception ignored) {
        }
    }

    public void startChargingValidated(String customerId, String stationId) {
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

        if (foundLocation == null || station == null) {
            throw new IllegalArgumentException("ChargingStation \"" + stationId + "\" does not exist");
        }

        if (station.getStatus() != OperatingStatus.AVAILABLE) {
            throw new IllegalArgumentException(
                    "ChargingStation \"" + stationId + "\" is not available (status: " + station.getStatus() + ")"
            );
        }

        Customer customer = network.findCustomerByName(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer \"" + customerId + "\" does not exist");
        }

        double price = foundLocation.getPricePerKwh(station.getChargingMode());
        if (price <= 0.0) {
            throw new IllegalArgumentException(
                    "No valid pricing for location \"" + foundLocation.getName() + "\" and mode \"" + station.getChargingMode() + "\""
            );
        }

        double reservation = price;
        if (customer.getAccount().getBalance() < reservation) {
            throw new IllegalArgumentException("Insufficient balance for customer \"" + customerId + "\"");
        }

        InvoiceManagement invoiceManagement = new InvoiceManagement(network);
        String invoiceId = invoiceManagement.ensureOpenInvoice(customerId).getInvoiceId();

        customer.getAccount().deduct(reservation);

        ChargingSession session = new ChargingSession(
                stationId,
                customerId,
                invoiceId,
                price,
                Instant.now(),
                reservation
        );
        network.addActiveSession(session);
        station.setStatus(OperatingStatus.OCCUPIED);
    }

    public void stopChargingValidated(String stationId, int durationMinutes, double energyKwh) {
        ChargingSession session = network.getActiveSession(stationId);
        if (session == null) {
            throw new IllegalArgumentException("No active charging session for station \"" + stationId + "\"");
        }

        if (durationMinutes < 0 || energyKwh < 0) {
            throw new IllegalArgumentException("Duration and energy must be non-negative");
        }

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
        if (foundLocation == null || station == null) {
            throw new IllegalArgumentException("ChargingStation \"" + stationId + "\" does not exist");
        }

        Customer customer = network.findCustomerByName(session.getCustomerId());
        if (customer == null) {
            throw new IllegalArgumentException("Customer \"" + session.getCustomerId() + "\" does not exist");
        }

        double total = round(energyKwh * session.getLockedPricePerKwh());
        double reserved = session.getReservedAmount();
        double delta = round(total - reserved);

        if (delta > 0.0) {
            if (customer.getAccount().getBalance() < delta) {
                throw new IllegalArgumentException(
                        "Insufficient balance for customer \"" + customer.getName() + "\""
                );
            }
            customer.getAccount().deduct(delta);
        } else if (delta < 0.0) {
            customer.getAccount().topUp(-delta);
        }


        InvoiceManagement invoiceManagement = new InvoiceManagement(network);
        int itemNo = invoiceManagement.findInvoice(session.getInvoiceId()).getItems().size() + 1;
        Invoice.InvoiceItem item = new Invoice.InvoiceItem(
                itemNo,
                session.getStartTime(),
                foundLocation.getName(),
                stationId,
                station.getChargingMode(),
                durationMinutes,
                energyKwh,
                session.getLockedPricePerKwh(),
                total
        );
        invoiceManagement.addInvoiceItem(session.getInvoiceId(), item, customer.getAccount().getBalance());

        // End session
        network.removeActiveSession(stationId);
        station.setStatus(OperatingStatus.AVAILABLE);
    }

    public boolean hasActiveSession(String stationId) {
        return network.hasActiveSession(stationId);
    }

    public double getActiveSessionPricePerKwh(String stationId) {
        ChargingSession session = network.getActiveSession(stationId);
        if (session == null) {
            throw new IllegalStateException("No active session for station " + stationId);
        }
        return session.getLockedPricePerKwh();
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

}
