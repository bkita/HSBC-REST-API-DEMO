Feature: Read past and future foreign exchange rates

  Scenario Outline: Reading fx rates for given <date> should return status code <statusCode>
    Given User specifies a date "<date>"
    When User sends a GET request
    Then User verifies status code is <statusCode>
    Examples:
      | date       | statusCode |
      | 2020-01-10 | 200        |

  Scenario Outline: Reading fx rates for given <date> should return correct response
    Given User specifies a date "<date>"
    When User sends a GET request
    Then User verifies response contains "<date>"
    Then User verifies response contains base "EUR"
    And User verifies response contains positive rates
    Examples:
      | date       |
      | 2020-01-01 |
      | 2020-01-10 |
      | 2020-04-10 |
      | 2020-04-04 |
      | 2020-04-05 |
      | 2020-04-13 |
      | 2020-05-01 |
      | 2020-12-25 |
      | 2020-12-26 |

  Scenario: Reading fx rates for date in the future should return current date
    Given User specifies a date "future date"
    When User sends a GET request
    Then User verifies response contains "current date"
    And User verifies response contains base "EUR"
    And User verifies response contains positive rates

  Scenario: Reading fx rates for date in the past should return past date
    Given User specifies a date "past date"
    When User sends a GET request
    Then User verifies response contains "past date"
    And User verifies response contains base "EUR"
    And User verifies response contains positive rates

  Scenario: Reading fx rates for the current date  should return current date
    Given User specifies a date "current date"
    When User sends a GET request
    Then User verifies response contains "current date"
    And User verifies response contains base "EUR"
    And User verifies response contains positive rates
