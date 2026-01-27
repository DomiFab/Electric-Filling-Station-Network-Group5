import management.*;
import model.*;

/**
 * Simple demo program that showcases the business logic independently of the Cucumber step definitions.
 */
public class MainApp {
    public static void main(String[] args) {
        // Main program mirrors the management composition used in TestContext
        ElectricFillingStationNetwork network = new ElectricFillingStationNetwork();
        CustomerManagement customerManagement = new CustomerManagement(network);
        LocationManagement locationManagement = new LocationManagement(network);
        ChargingStationManagement chargingStationManagement = new ChargingStationManagement(network);
        InvoiceManagement invoiceManagement = new InvoiceManagement(network);
        NetworkStatusManagement networkStatusManagement = new NetworkStatusManagement(network);
        LocationPricingManagement locationPricingManagement = new LocationPricingManagement(network);
        VehicleChargingManagement vehicleChargingManagement = new VehicleChargingManagement(network);
        Owner owner = new Owner(
                customerManagement,
                locationManagement,
                chargingStationManagement,
                invoiceManagement,
                networkStatusManagement,
                locationPricingManagement,
                vehicleChargingManagement
        );

        System.out.println("=== Electric Filling Station Network (Final Demo) ===");

        // 1) Static setup
        owner.createLocation("FH Technikum Wien", "Hochstädtplatz");
        owner.addChargingStation("FH Technikum Wien", "CS-1", "AC");
        owner.addChargingStation("FH Technikum Wien", "CS-2", "DC");
        owner.setPricePerKwh("FH Technikum Wien", "AC", 0.30);
        owner.setPricePerKwh("FH Technikum Wien", "DC", 0.45);

        owner.registerCustomer("C-1", "c-1@example.com");
        // 2) Prepaid top-up (recorded on the customer invoice)
        customerManagement.topUp("C-1", 10.00);
        invoiceManagement.recordTopUp("C-1", 10.00, customerManagement.getBalance("C-1"));

        System.out.println("Network status: " + networkStatusManagement.getNetworkStatusAsString());
        System.out.println("--- Network Status Report ---\n" + networkStatusManagement.getNetworkStatusReport());
        System.out.println("Customer C-1 balance after top-up: " + customerManagement.getBalance("C-1"));

        // 3) Charging workflow (prepaid reservation deducted immediately)
        vehicleChargingManagement.startChargingValidated("C-1", "CS-1");
        System.out.println("CS-1 status after start: " + chargingStationManagement.getOperatingStatusByStationId("CS-1"));
        System.out.println("Locked price (CS-1): " + vehicleChargingManagement.getActiveSessionPricePerKwh("CS-1"));
        System.out.println("Customer C-1 balance after reservation: " + customerManagement.getBalance("C-1"));

        // Finish charging -> invoice item is created by the system
        vehicleChargingManagement.stopChargingValidated("CS-1", 5, 2.0);
        System.out.println("CS-1 status after stop: " + chargingStationManagement.getOperatingStatusByStationId("CS-1"));

        Invoice openInvoice = invoiceManagement.ensureOpenInvoice("C-1");
        System.out.println("Open invoice: " + openInvoice.getInvoiceId());
        System.out.println("Invoice items: " + openInvoice.getItems().size());
        System.out.println("Top-ups recorded: " + openInvoice.getTopUps().size());
        System.out.println("Outstanding balance: " + openInvoice.getOutstandingBalance());

        System.out.println("=== Demo finished ===");
    }
}
