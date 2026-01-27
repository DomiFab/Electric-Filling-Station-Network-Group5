Feature: Network status
  As a customer
  I want to view the current network and location status
  So that I can decide whether charging is possible

  # Edge case: an empty network (no locations/stations) is still operational
  Scenario: Network is OPERATIONAL when there are no stations
    Given an electric filling station network exists
    When the customer requests the network status
    Then the network status is "OPERATIONAL"

  Scenario: Network is OPERATIONAL when no station is out of order
    Given an electric filling station network exists
    And a location named "FH Technikum Wien" exists with address "Hochstädtplatz"
    And a charging station with id "CS-1" exists at location "FH Technikum Wien" with charging mode "AC"
    And a charging station with id "CS-2" exists at location "FH Technikum Wien" with charging mode "DC"
    And charging station "CS-1" has operating status "AVAILABLE"
    And charging station "CS-2" has operating status "AVAILABLE"
    When the customer requests the network status
    Then the network status is "OPERATIONAL"

  Scenario: Network becomes DEGRADED if at least one station is out of order
    Given an electric filling station network exists
    And a location named "FH Technikum Wien" exists with address "Hochstädtplatz"
    And a charging station with id "CS-1" exists at location "FH Technikum Wien" with charging mode "AC"
    And a charging station with id "CS-2" exists at location "FH Technikum Wien" with charging mode "DC"
    And charging station "CS-1" has operating status "AVAILABLE"
    And charging station "CS-2" has operating status "AVAILABLE"
    When charging station "CS-2" has operating status "OUT_OF_ORDER"
    And the customer requests the network status
    Then the network status is "DEGRADED"

  Scenario: Network becomes DOWN if all stations are out of order
    Given an electric filling station network exists
    And a location named "FH Technikum Wien" exists with address "Hochstädtplatz"
    And a charging station with id "CS-1" exists at location "FH Technikum Wien" with charging mode "AC"
    And a charging station with id "CS-2" exists at location "FH Technikum Wien" with charging mode "DC"
    And charging station "CS-1" has operating status "AVAILABLE"
    And charging station "CS-2" has operating status "AVAILABLE"
    When charging station "CS-1" has operating status "OUT_OF_ORDER"
    And charging station "CS-2" has operating status "OUT_OF_ORDER"
    And the customer requests the network status
    Then the network status is "DOWN"
