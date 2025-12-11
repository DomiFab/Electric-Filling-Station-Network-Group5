Feature: Manage Locations and Charging Stations
  As an owner
  I want to create, view (read), update and delete locations and charging stations
  So that my Electric Filling Station Network is correctly represented in the system.

    Scenario: CRUD operations on Locations and Charging Stations
      Given an existing electric filling station network
      When the owner creates a location "FH Technikum" with address "Hochstädtplatz"
      And the owner adds a charging station with id "CS-1" to location "FH Technikum"
      And the owner adds a charging station with id "CS-2" to location "FH Technikum"
      Then the network contains a location named "FH Technikum"
      And location "FH Technikum" has 2 charging stations.

      When the owner renames the location "FH Technikum" to "FH Technikum Wien"
      Then the network contains a location named "FH Technikum Wien"
      And the network does not contain a location named "FH Technikum".

      When the owner removes the charging station "CS-2" from location "FH Technikum Wien"
      Then location "FH Technikum Wien" has 1 charging station.

      When the owner deletes the location "FH Technikum Wien"
      Then the network contains no locations.