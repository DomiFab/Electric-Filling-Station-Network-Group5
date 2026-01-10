Feature: Vehicle Charging
  As a customer
  I want to start charging my vehicle
  So that I can charge when a station is available and I have enough balance

  Background:
    Given an electric filling station network exists
    And a location named "FH Technikum Wien" exists with address "Hochstädtplatz"
    And pricing for location "FH Technikum Wien" and charging mode "AC" is 0.30
    And a charging station with id "CS-1" exists at location "FH Technikum Wien" with charging mode "AC"
    And charging station "CS-1" has operating status "AVAILABLE"

  Scenario: Start charging successfully
    Given a customer "C-1" exists with balance 10.00
    When customer "C-1" starts charging at station "CS-1"
    Then charging station "CS-1" has operating status "OCCUPIED"
    And an active charging session exists for station "CS-1"

  Scenario: Charging is rejected when balance is insufficient
    Given a customer "C-2" exists with balance 0.00
    When customer "C-2" starts charging at station "CS-1"
    Then charging station "CS-1" has operating status "AVAILABLE"
    And no active charging session exists for station "CS-1"
