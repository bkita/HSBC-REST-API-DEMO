Feature: Read latest foreign exchange rates

  Scenario Outline: Reading latest fx rates should return status code <statusCode>
    Given User specifies an endpoint "latest"
    When User sends a GET request
    Then User verifies status code is <statusCode>
    Examples:
      | statusCode |
      | 200        |

  Scenario: Reading latest fx rates should return correct data
    Given User specifies an endpoint "latest"
    When User sends a GET request
    Then User verifies response contains "current date"
    And User verifies response contains base "EUR"
    And User verifies response contains positive rates

  Scenario Outline: Reading latest fx rates with symbols <symbols> and base <base> should return status code <statusCode>
    Given User specifies an endpoint "latest"
    When User sends a GET request with query params
      | symbols   | base   |
      | <symbols> | <base> |
    Then User verifies status code is <statusCode>
    Examples:
      | statusCode | symbols | base |
      | 200        | GBP     | USD  |
      | 200        | USD     | PLN  |
      | 200        |         | USD  |
      | 200        | GBP     |      |
      | 200        |         |      |
      | 400        | USD     | XXX  |
      | 400        | XXX     | EUR  |
      | 400        | XXX     | XXX  |

  Scenario Outline: Reading latest fx rates with base <base> should return correct data
    Given User specifies an endpoint "latest"
    When User sends a GET request with query params
      | base   |
      | <base> |
    Then User verifies status code is <statusCode>
    And User verifies response contains base "<base>"
    And User verifies response contains positive rates
    Examples:
      | statusCode | base |
      | 200        | GBP  |
      | 200        | USD  |

  Scenario Outline: Reading latest fx rates with symbols <symbols> should return correct data
    Given User specifies an endpoint "latest"
    When User sends a GET request with query params
      | symbols   |
      | <symbols> |
    Then User verifies status code is <statusCode>
    And User verifies response contains base "EUR"
    And User verifies response contains <numberOfRates> rates
    And User verifies response contains positive rates
    Examples:
      | statusCode | symbols | numberOfRates |
      | 200        | GBP     | 1             |
      | 200        | USD     | 1             |
      | 200        | USD,PLN | 2             |