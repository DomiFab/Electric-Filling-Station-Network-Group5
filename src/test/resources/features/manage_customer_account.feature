Feature: Customer account (prepaid)
  As a customer
  I want to top up my prepaid account and have costs deducted
  So that I can pay for charging sessions in a prepaid system

  Background:
    Given an electric filling station network exists
    And a location named "FH Technikum Wien" exists with address "Hochstädtplatz"
    And pricing for location "FH Technikum Wien" and charging mode "AC" is 0.30
    And a charging station with id "CS-1" exists at location "FH Technikum Wien" with charging mode "AC"
    And charging station "CS-1" has operating status "AVAILABLE"
    And a customer "C-1" exists with balance 0.00

  Scenario: Customer can top up the prepaid account
    When customer "C-1" tops up their account by 10.00
    Then customer "C-1" balance is 10.00
    And the latest invoice for customer "C-1" contains a top-up of 10.00

  Scenario: Charging deducts money and creates an invoice item
    Given customer "C-1" tops up their account by 10.00
    When customer "C-1" starts charging at station "CS-1"
    And the customer stops charging at station "CS-1" with duration 5 minutes and energy 2.0 kWh
    Then customer "C-1" balance is 9.40
    And the latest invoice for customer "C-1" contains an item for station "CS-1" with energy 2.0 kWh

  # Error case: top-up must be positive
  Scenario: Negative top-up is rejected
    When customer "C-1" tries to top up their account by -5.00
    Then an error indicates that the top-up amount must be positive
