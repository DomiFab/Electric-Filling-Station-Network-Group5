Feature: Manage Charging Stations
  As an owner
  I want to manage charging stations at a location
  So that the charging network reflects the real operating state

  Background:
    Given an electric filling station network exists
    And a location named "FH Technikum Wien" exists with address "Hochstädtplatz"

  Scenario: Add charging stations to a location
    When the owner adds a charging station with id "CS-1" and charging mode "AC" to location "FH Technikum Wien"
    And the owner adds a charging station with id "CS-2" and charging mode "DC" to location "FH Technikum Wien"
    Then location "FH Technikum Wien" has 2 charging stations
    And charging station "CS-1" at location "FH Technikum Wien" has charging mode "AC"
    And charging station "CS-1" at location "FH Technikum Wien" has operating status "AVAILABLE"

  Scenario: Update operating status of a charging station
    Given a charging station with id "CS-1" and charging mode "AC" exists at location "FH Technikum Wien"
    When the owner sets the operating status of charging station "CS-1" to "OUT_OF_ORDER"
    Then charging station "CS-1" at location "FH Technikum Wien" has operating status "OUT_OF_ORDER"

  Scenario: Remove charging station from a location
    Given a charging station with id "CS-1" and charging mode "AC" exists at location "FH Technikum Wien"
    When the owner removes the charging station "CS-1" from location "FH Technikum Wien"
    Then location "FH Technikum Wien" has 0 charging stations
