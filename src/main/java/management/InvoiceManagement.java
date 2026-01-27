package management;

import model.ElectricFillingStationNetwork;
import model.Invoice;
import model.InvoiceStatus;
import model.TopUpEntry;

import java.time.Instant;

import java.util.List;

public class InvoiceManagement {

    private final ElectricFillingStationNetwork network;
    private int invoiceCounter = 1;

    public InvoiceManagement(ElectricFillingStationNetwork network) {
        this.network = network;
    }

    public void addInvoiceForCustomer(String customerIdOrName, Invoice invoice) {
        network.addInvoice(customerIdOrName, invoice);
    }

    public Invoice ensureOpenInvoice(String customerId) {
        List<Invoice> existing = getInvoicesForCustomer(customerId);
        for (int i = existing.size() - 1; i >= 0; i--) {
            Invoice inv = existing.get(i);
            if (inv.getStatus() == InvoiceStatus.OPEN) {
                return inv;
            }
        }

        String newId = "INV-" + invoiceCounter++;
        Invoice created = new Invoice(newId, customerId, InvoiceStatus.OPEN);
        network.addInvoice(customerId, created);
        return created;
    }

    public void recordTopUp(String customerId, double amount, double resultingBalance) {
        Invoice inv = ensureOpenInvoice(customerId);
        inv.addTopUp(new TopUpEntry(Instant.now(), amount));
        inv.setOutstandingBalance(resultingBalance);
    }

    public void addInvoiceItem(String invoiceId, Invoice.InvoiceItem item, double resultingBalance) {
        Invoice inv = findInvoice(invoiceId);
        if (inv == null) {
            throw new IllegalArgumentException("Invoice \"" + invoiceId + "\" does not exist");
        }
        inv.addItem(item);
        inv.setOutstandingBalance(resultingBalance);
    }

    public List<Invoice> getInvoicesForCustomer(String customerIdOrName) {
        return network.getInvoicesForCustomer(customerIdOrName);
    }

    public void updateInvoiceStatus(String invoiceId, String status) {
        Invoice inv = network.findInvoice(invoiceId);
        if (inv == null) {
            throw new IllegalArgumentException("Invoice \"" + invoiceId + "\" does not exist");
        }
        inv.setStatus(InvoiceStatus.valueOf(status));
    }

    public Invoice findInvoice(String invoiceId) {
        return network.findInvoice(invoiceId);
    }
}
