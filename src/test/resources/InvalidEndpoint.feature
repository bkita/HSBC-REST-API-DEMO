Feature: Negative scenarios

  Scenario: Sending request to invalid endpoint should return 400
    Given User specifies an endpoint "invalid-endpoint"
    When User sends a GET request
    Then User verifies status code is 400