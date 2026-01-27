Feature: Invoice status
  As a customer
  I want to see all my invoices and their status
  So that I can track what I need to pay

  Background:
    Given an electric filling station network exists
    And a customer "C-1" exists

  Scenario: Customer can see all invoices
    Given the system creates an invoice "INV-1" for customer "C-1" with status "OPEN"
    And the system creates an invoice "INV-2" for customer "C-1" with status "PAID"
    When the customer "C-1" requests all invoices
    Then the customer "C-1" sees 2 invoices

  Scenario: Owner updates invoice status and customer sees the change
    Given the system creates an invoice "INV-3" for customer "C-1" with status "OPEN"
    When the owner updates invoice "INV-3" status to "PAID"
    And the customer "C-1" requests all invoices
    Then invoice "INV-3" has status "PAID"

  # Error case: updating a non-existing invoice should fail
  Scenario: Updating a non-existing invoice is rejected
    When the owner tries to update invoice "INV-404" status to "PAID"
    Then an error indicates that invoice "INV-404" does not exist
