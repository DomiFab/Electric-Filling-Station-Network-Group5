Feature: Manage location pricing
  As an owner
  I want to define and update pricing per location and charging mode
  So that charging prices can vary by location and change dynamically

  Background:
    Given an electric filling station network exists
    And a location named "FH Technikum Wien" exists with address "Hochstädtplatz"

  Scenario: Define pricing for a location
    When the owner defines pricing for location "FH Technikum Wien" as:
      | chargingMode | pricePerKwh |
      | AC           | 0.30        |
      | DC           | 0.45        |
    Then the price for location "FH Technikum Wien" and charging mode "AC" is 0.30
    And the price for location "FH Technikum Wien" and charging mode "DC" is 0.45

  Scenario: Update pricing for a location
    Given pricing for location "FH Technikum Wien" and charging mode "AC" is 0.30
    When the owner updates the price for location "FH Technikum Wien" and charging mode "AC" to 0.35
    Then the price for location "FH Technikum Wien" and charging mode "AC" is 0.35

  Scenario: Pricing is fixed at the start of a charging session
    Given pricing for location "FH Technikum Wien" and charging mode "AC" is 0.30
    And a charging station with id "CS-1" exists at location "FH Technikum Wien" with charging mode "AC"
    And charging station "CS-1" has operating status "AVAILABLE"
    And a customer "C-1" exists with balance 50.00
    When customer "C-1" starts charging at station "CS-1"
    And the owner updates the price for location "FH Technikum Wien" and charging mode "AC" to 0.60
    Then the active charging session at station "CS-1" uses price 0.30
