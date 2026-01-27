package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Invoice created by the system for a customer.
 * Contains charging session items (sorted by start time) and top-up information.
 */
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
        this.outstandingBalance = 0.0;
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
        this.outstandingBalance = outstandingBalance;
    }
}
