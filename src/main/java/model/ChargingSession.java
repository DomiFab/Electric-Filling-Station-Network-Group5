package model;

import java.time.Instant;

/**
 * Represents an active charging session.
 * <p>
 * Pricing is locked at the start of the session.
 * A prepaid system is used: at session start a small reservation is deducted.
 */
public class ChargingSession {

    private final String stationId;
    private final String customerId;
    private final String invoiceId;
    private final double lockedPricePerKwh;
    private final Instant startTime;
    private final double reservedAmount;

    public ChargingSession(String stationId,
                           String customerId,
                           String invoiceId,
                           double lockedPricePerKwh,
                           Instant startTime,
                           double reservedAmount) {
        this.stationId = stationId;
        this.customerId = customerId;
        this.invoiceId = invoiceId;
        this.lockedPricePerKwh = lockedPricePerKwh;
        this.startTime = startTime;
        this.reservedAmount = reservedAmount;
    }

    public String getStationId() {
        return stationId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public double getLockedPricePerKwh() {
        return lockedPricePerKwh;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public double getReservedAmount() {
        return reservedAmount;
    }
}
