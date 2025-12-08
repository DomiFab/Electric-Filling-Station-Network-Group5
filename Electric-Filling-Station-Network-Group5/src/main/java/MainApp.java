import model.Owner;

public class MainApp {
    public static void main(String[] args) {

        System.out.println("Electric Filling Station Network Demo");

        Owner owner = new Owner();

        //Manage Locations
        owner.createLocation("FH Technikum", "Hochstädtplatz");
        owner.addChargingStation("FH Technikum", "CS-1");
        owner.addChargingStation("FH Technikum", "CS-2");

        System.out.println("\nLocations after creation:");
        owner.getLocations().forEach(System.out::println);

        //Rename location
        owner.renameLocation("FH Technikum", "FH Technikum Wien");

        System.out.println("\nLocations after renaming:");
        owner.getLocations().forEach(System.out::println);

        //Remove charging station
        owner.removeChargingStation("FH Technikum Wien", "CS-2");

        System.out.println("\nAfter removing CS-2:");
        owner.getLocations().forEach(System.out::println);

        //Delete location
        owner.deleteLocation("FH Technikum Wien");

        System.out.println("\nAfter deleting FH Technikum Wien:");
        System.out.println("Locations: " + owner.getLocations().size());

        //Manage Customers
        owner.registerCustomer("Dea", "Dea@FhTechnikum.com");

        System.out.println("\nCustomers after registration:");
        owner.getCustomers().forEach(System.out::println);

        owner.updateCustomerEmail("Dea", "Dea@FhTechnikum-wien.at");

        System.out.println("\nCustomers after email update:");
        owner.getCustomers().forEach(System.out::println);

        owner.deleteCustomer("Dea");

        System.out.println("\nAfter deleting customer Dea:");
        System.out.println("Customers: " + owner.getCustomers().size());

        System.out.println("\nDemo End");
    }
}

