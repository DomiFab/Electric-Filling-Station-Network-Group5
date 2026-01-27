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
            // silent behavior for non-validated call
        }
    }

    /**
     * Same as {@link #startCharging(String, String)} but throws an exception instead of silently returning.
     * Used for BDD error-case scenarios.
     */
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

        // Prepaid: reserve the cost for 1 kWh immediately.
        double reservation = price;
        if (customer.getAccount().getBalance() < reservation) {
            throw new IllegalArgumentException("Insufficient balance for customer \"" + customerId + "\"");
        }

        // System invoice creation & linking
        InvoiceManagement invoiceManagement = new InvoiceManagement(network);
        String invoiceId = invoiceManagement.ensureOpenInvoice(customerId).getInvoiceId();

        // Deduct reservation immediately
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

    /**
     * Finish a charging session and create an invoice item (billing by kWh; duration is recorded).
     * Any additional amount beyond the reserved amount is deducted.
     */
    public void stopChargingValidated(String stationId, int durationMinutes, double energyKwh) {
        ChargingSession session = network.getActiveSession(stationId);
        if (session == null) {
            throw new IllegalArgumentException("No active charging session for station \"" + stationId + "\"");
        }

        if (durationMinutes < 0 || energyKwh < 0) {
            throw new IllegalArgumentException("Duration and energy must be non-negative");
        }

        // Find station and location
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

        double total = energyKwh * session.getLockedPricePerKwh();
        double reserved = session.getReservedAmount();
        double additional = Math.max(0.0, total - reserved);
        if (customer.getAccount().getBalance() < additional) {
            throw new IllegalArgumentException("Insufficient balance for customer \"" + customer.getName() + "\"");
        }
        if (additional > 0.0) {
            customer.getAccount().deduct(additional);
        }

        // Create invoice line item
        InvoiceManagement invoiceManagement = new InvoiceManagement(network);
        int itemNo = invoiceManagement.findInvoice(session.getInvoiceId()).getItems().size() + 1;
        InvoiceItem item = new InvoiceItem(
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
