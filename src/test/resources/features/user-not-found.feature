Feature: Validate email addresses for user posts

  Background: Gather required input
    Given "Sibulele" is provided as the username

  Scenario: Email addresses found in the comments for a user can be validated
    When I call the api service
    Then No user should be found
    And No posts should be found
    And No comments should be found

