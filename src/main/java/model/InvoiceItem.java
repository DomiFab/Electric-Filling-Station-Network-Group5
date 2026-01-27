package model;

import java.time.Instant;

/**
 * Single line item on an invoice (a completed charging session).
 */
public class InvoiceItem {

    private final int itemNo;
    private final Instant startTime;
    private final String locationName;
    private final String stationId;
    private final String chargingMode;
    private final int durationMinutes;
    private final double energyKwh;
    private final double pricePerKwh;
    private final double totalPrice;

    public InvoiceItem(int itemNo,
                       Instant startTime,
                       String locationName,
                       String stationId,
                       String chargingMode,
                       int durationMinutes,
                       double energyKwh,
                       double pricePerKwh,
                       double totalPrice) {
        this.itemNo = itemNo;
        this.startTime = startTime;
        this.locationName = locationName;
        this.stationId = stationId;
        this.chargingMode = chargingMode;
        this.durationMinutes = durationMinutes;
        this.energyKwh = energyKwh;
        this.pricePerKwh = pricePerKwh;
        this.totalPrice = totalPrice;
    }

    public int getItemNo() {
        return itemNo;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getStationId() {
        return stationId;
    }

    public String getChargingMode() {
        return chargingMode;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public double getEnergyKwh() {
        return energyKwh;
    }

    public double getPricePerKwh() {
        return pricePerKwh;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
