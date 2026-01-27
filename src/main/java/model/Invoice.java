package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static model.CustomerAccount.roundMoney;


public class Invoice {

    private final String invoiceId;
    private final String customerId;
    private InvoiceStatus status;

    private final List<InvoiceItem> items = new ArrayList<>();
    private final List<TopUpEntry> topUps = new ArrayList<>();
    private double outstandingBalance;

    public Invoice(String invoiceId, String customerId, InvoiceStatus status) {
        this.invoiceId = invoiceId;
        this.customerId = customerId;
        this.status = status;
        this.outstandingBalance = 0.00;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public List<InvoiceItem> getItems() {
        items.sort(Comparator.comparing(InvoiceItem::getStartTime));
        return items;
    }

    public List<TopUpEntry> getTopUps() {
        return topUps;
    }

    public void addItem(InvoiceItem item) {
        items.add(item);
    }

    public void addTopUp(TopUpEntry entry) {
        topUps.add(entry);
    }

    public double getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(double outstandingBalance) {
        this.outstandingBalance = roundMoney(outstandingBalance);
    }


    public static class InvoiceItem {

        private final int itemNo;
        private final java.time.Instant startTime;
        private final String locationName;
        private final String stationId;
        private final String chargingMode;
        private final int durationMinutes;
        private final double energyKwh;
        private final double pricePerKwh;
        private final double totalPrice;

        public InvoiceItem(int itemNo,
                           java.time.Instant startTime,
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

        public java.time.Instant getStartTime() {
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
}
