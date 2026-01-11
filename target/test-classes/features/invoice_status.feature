Feature: Invoice status
  As a customer
  I want to see all my invoices and their status
  So that I can track what I need to pay

  Background:
    Given an electric filling station network exists
    And a customer "C-1" exists

  Scenario: Customer can see all invoices
    Given the owner creates an invoice "INV-1" for customer "C-1" with amount 12.50 and status "OPEN"
    And the owner creates an invoice "INV-2" for customer "C-1" with amount 7.00 and status "PAID"
    When the customer "C-1" requests all invoices
    Then the customer "C-1" sees 2 invoices

  Scenario: Owner updates invoice status and customer sees the change
    Given the owner creates an invoice "INV-3" for customer "C-1" with amount 20.00 and status "OPEN"
    When the owner updates invoice "INV-3" status to "PAID"
    And the customer "C-1" requests all invoices
    Then invoice "INV-3" has status "PAID"
