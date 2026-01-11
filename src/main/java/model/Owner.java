package model;

import java.util.ArrayList;
import java.util.List;

public class Owner {
    private final List<Location> locations = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final java.util.Map<String, java.util.List<Invoice>> invoicesByCustomer = new java.util.HashMap<>(); public void createCustomer(String name) {
        // minimal customer creation for invoice use-case (email irrelevant)
        customers.add(new Customer(name, "dummy@local"));
    }

    public void createInvoice(String customerName, String invoiceId, double amount, String status) {
        Invoice inv = new Invoice(invoiceId, customerName, amount, InvoiceStatus.valueOf(status));
        invoicesByCustomer.computeIfAbsent(customerName, k -> new java.util.ArrayList<>()).add(inv);
    }

    public java.util.List<Invoice> getInvoicesForCustomer(String customerName) {
        return invoicesByCustomer.getOrDefault(customerName, new java.util.ArrayList<>());
    }

    public void updateInvoiceStatus(String invoiceId, String status) {
        Invoice inv = findInvoice(invoiceId);
        if (inv != null) inv.setStatus(InvoiceStatus.valueOf(status));
    }

    public Invoice findInvoice(String invoiceId) {
        for (java.util.List<Invoice> list : invoicesByCustomer.values()) {
            for (Invoice inv : list) {
                if (inv.getInvoiceId().equals(invoiceId)) return inv;
            }
        }
        return null;
    }


    //Location CRUD Operations

    public void createLocation(String name, String address) {
        locations.add(new Location(name, address));
    }

    public Location findLocation(String name) {
        return locations.stream()
                .filter(l -> l.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void renameLocation(String oldName, String newName) {
        Location loc = findLocation(oldName);
        if (loc != null) loc.rename(newName);
    }

    public void deleteLocation(String name) {
        locations.removeIf(l -> l.getName().equals(name));
    }

    public List<Location> getLocations() {
        return locations;
    }

    //Charging Station CRUD Operations

    public void addChargingStation(String locationName, String stationId, String mode) {
        Location loc = findLocation(locationName);
        if (loc != null) {
            loc.addChargingStation(new ChargingStation(stationId,mode));
        }
    }

    public void removeChargingStation(String locationName, String stationId) {
        Location loc = findLocation(locationName);
        if (loc != null) {
            loc.removeChargingStation(stationId);
        }
    }

    //Customer CRUD Operations

    public void registerCustomer(String name, String email) {
        customers.add(new Customer(name, email));
    }

    public Customer findCustomer(String name) {
        return customers.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void updateCustomerEmail(String name, String newEmail) {
        Customer c = findCustomer(name);
        if (c != null) c.updateEmail(newEmail);
    }

    public void deleteCustomer(String name) {
        customers.removeIf(c -> c.getName().equals(name));
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public String getNetworkStatus() {
        int total = 0;
        int outOfOrder = 0;

        for (Location loc : locations) {
            for (ChargingStation cs : loc.getChargingStations()) {
                total++;
                if (cs.getStatus() == OperatingStatus.OUT_OF_ORDER) outOfOrder++;
            }
        }

        if (total == 0) return NetworkStatus.OPERATIONAL.name();
        if (outOfOrder == 0) return NetworkStatus.OPERATIONAL.name();
        if (outOfOrder == total) return NetworkStatus.DOWN.name();
        return NetworkStatus.DEGRADED.name();
    }
}
