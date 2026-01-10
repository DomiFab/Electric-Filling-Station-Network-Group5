package model;

public class Invoice {
    private final String invoiceId;
    private final String customerName;
    private final double amount;
    private InvoiceStatus status;

    public Invoice(String invoiceId, String customerName, double amount, InvoiceStatus status) {
        this.invoiceId = invoiceId;
        this.customerName = customerName;
        this.amount = amount;
        this.status = status;
    }

    public String getInvoiceId() { return invoiceId; }
    public String getCustomerName() { return customerName; }
    public double getAmount() { return amount; }
    public InvoiceStatus getStatus() { return status; }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }
}
