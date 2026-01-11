Feature: Manage Locations and Charging Stations
  As an owner
  I want to create, view (read), update and delete locations and charging stations
  So that my Electric Filling Station Network is correctly represented in the system.

  Background:
    Given an existing electric filling station network
    And the owner creates a location "FH Technikum" with address "Hochstädtplatz"
    And the owner adds a charging station with id "CS-1" and charging mode "AC" to location "FH Technikum"
    And the owner adds a charging station with id "CS-2" and charging mode "DC" to location "FH Technikum"

  Scenario: Create and view location with charging stations
    Then the network contains a location named "FH Technikum"
    And location "FH Technikum" has 2 charging stations.

  Scenario: Rename location
    When the owner renames the location "FH Technikum" to "FH Technikum Wien"
    Then the network contains a location named "FH Technikum Wien"
    And the network does not contain a location named "FH Technikum".

  Scenario: Remove charging station from location
    When the owner renames the location "FH Technikum" to "FH Technikum Wien"
    And the owner removes the charging station "CS-2" from location "FH Technikum Wien"
    Then location "FH Technikum Wien" has 1 charging station.

  Scenario: Delete location
    When the owner renames the location "FH Technikum" to "FH Technikum Wien"
    And the owner deletes the location "FH Technikum Wien"
    Then the network contains no locations.
