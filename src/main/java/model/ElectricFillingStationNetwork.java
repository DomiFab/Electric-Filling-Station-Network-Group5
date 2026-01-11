package model;

import java.util.*;

public class ElectricFillingStationNetwork {

    private final Map<String, Customer> customersByName = new HashMap<>();
    private final Map<String, Location> locationsByName = new HashMap<>();

    public void addCustomer(Customer customer) {
        customersByName.put(customer.getName(), customer);
    }

    public Customer findCustomerByName(String name) {
        return customersByName.get(name);
    }

    public void removeCustomerByName(String name) {
        customersByName.remove(name);
    }

    public Collection<Customer> getCustomers() {
        return customersByName.values();
    }

    public void addLocation(Location location) {
        locationsByName.put(location.getName(), location);
    }

    public Location findLocationByName(String name) {
        return locationsByName.get(name);
    }

    public void removeLocationByName(String name) {
        locationsByName.remove(name);
    }

    public Collection<Location> getLocations() {
        return locationsByName.values();
    }
    private final Map<String, List<Invoice>> invoicesByCustomer = new HashMap<>();
    private final Map<String, Invoice> invoicesById = new HashMap<>();

    public void addInvoice(String customerKey, Invoice invoice) {
        invoicesById.put(invoice.getInvoiceId(), invoice);
        invoicesByCustomer.computeIfAbsent(customerKey, k -> new ArrayList<>()).add(invoice);
    }

    public List<Invoice> getInvoicesForCustomer(String customerKey) {
        return invoicesByCustomer.getOrDefault(customerKey, List.of());
    }

    public Invoice findInvoice(String invoiceId) {
        return invoicesById.get(invoiceId);
    }
    private final java.util.Map<String, ChargingSession> activeSessionsByStationId = new java.util.HashMap<>();

    public void addActiveSession(ChargingSession session) {
        activeSessionsByStationId.put(session.getStationId(), session);
    }

    public ChargingSession getActiveSession(String stationId) {
        return activeSessionsByStationId.get(stationId);
    }

    public boolean hasActiveSession(String stationId) {
        return activeSessionsByStationId.containsKey(stationId);
    }

    public void removeActiveSession(String stationId) {
        activeSessionsByStationId.remove(stationId);
    }

}
