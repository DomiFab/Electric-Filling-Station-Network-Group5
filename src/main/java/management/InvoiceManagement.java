package management;

import model.ElectricFillingStationNetwork;
import model.Invoice;
import model.InvoiceStatus;

import java.util.List;

public class InvoiceManagement {

    private final ElectricFillingStationNetwork network;

    public InvoiceManagement(ElectricFillingStationNetwork network) {
        this.network = network;
    }

    public void addInvoiceForCustomer(String customerIdOrName, Invoice invoice) {
        network.addInvoice(customerIdOrName, invoice);
    }

    public List<Invoice> getInvoicesForCustomer(String customerIdOrName) {
        return network.getInvoicesForCustomer(customerIdOrName);
    }

    public void updateInvoiceStatus(String invoiceId, String status) {
        Invoice inv = network.findInvoice(invoiceId);
        if (inv != null) inv.setStatus(InvoiceStatus.valueOf(status));
    }

    public Invoice findInvoice(String invoiceId) {
        return network.findInvoice(invoiceId);
    }
}
