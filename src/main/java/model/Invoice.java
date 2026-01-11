package model;

public class Invoice {

    private final String invoiceId;
    private final String customerId;
    private final double amount;
    private InvoiceStatus status;

    public Invoice(String invoiceId, String customerId, double amount, InvoiceStatus status) {
        this.invoiceId = invoiceId;
        this.customerId = customerId;
        this.amount = amount;
        this.status = status;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getAmount() {
        return amount;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }
}
