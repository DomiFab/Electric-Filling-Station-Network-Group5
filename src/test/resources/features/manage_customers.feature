Feature: Manage Customers
  As an owner
  I want to manage customers of the charging network
  So that I can manage who uses the charging stations.

  Background:
    Given a customer registry

  Scenario: Create and view a customer
    When a customer registers with name "Dea" and email "Dea@FhTechnikum.com"
    Then a customer with name "Dea" exists
    And the customer "Dea" has a unique customer id
    And the customer "Dea" has a customer account with balance 0.00.

  Scenario: Update customer information
    When a customer registers with name "Dea" and email "Dea@FhTechnikum.com"
    And the owner updates the email of customer "Dea" to "Dea@FhTechnikum-wien.at"
    Then the customer "Dea" has email "Dea@FhTechnikum-wien.at".

  Scenario: Delete a customer
    When a customer registers with name "Dea" and email "Dea@FhTechnikum.com"
    And the owner deletes the customer "Dea"
    Then no customers exist.
