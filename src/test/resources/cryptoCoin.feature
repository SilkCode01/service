Feature: crypto data is updated on refresh-price
  Scenario: client makes a call to GET /refresh-price
    When the client calls /refresh-price
    Then the client receives status code of 200
    And the client receives response 1

