Feature: Manage Customers
  As an owner
  I want to create, view (read), update and delete a customer
  So that I can manage who uses the charging network.

  Scenario: CRUD operations on Customers
    Given a customer registry
    When a customer registers with name "Dea" and email "Dea@FhTechnikum.com"
    Then a customer with name "Dea" exists
    And the customer "Dea" has a unique customer id
    And the customer "Dea" has a customer account with balance 0.00.

    When the owner updates the email of customer "Dea" to "Dea@FhTechnikum-wien.at"
    Then the customer "Dea" has email "Dea@FhTechnikum-wien.at".

    When the owner deletes the customer "Dea"
    Then no customers exist.