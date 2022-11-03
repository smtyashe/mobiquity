Feature: Validate email addresses for user posts

  Background: Gather required input
    Given "Delphine" is provided as the username

  Scenario: Email addresses found in the comments for a user can be validated
    When I call the api service
    Given A user with username "Delphine" is found
    And One or more posts can be found for the user found
    And Posts have one or more comments
    And A comment has an email address
    And check email addresses are valid
    Then There should be no invalid email address
