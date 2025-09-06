Feature: SauceDemo Login
  To verify the login functionality of SauceDemo application
  Users should be able to login with valid credentials and see proper error messages for invalid inputs

  # Positive Test Case
  Scenario: Valid login for standard user
    Given User is on the SauceDemo login page
    When User enters username "standard_user" and password "secret_sauce"
    And Clicks on the login button
    Then User should be redirected to the inventory page

  # Negative Test Cases
  Scenario: Invalid login with wrong password
    Given User is on the SauceDemo login page
    When User enters username "standard_user" and password "wrong_pass"
    And Clicks on the login button
    Then Error message containing "Epic sadface" should be displayed

  Scenario: Invalid login with wrong username
    Given User is on the SauceDemo login page
    When User enters username "wrong_user" and password "secret_sauce"
    And Clicks on the login button
    Then Error message containing "Epic sadface" should be displayed

  Scenario: Blank username and password
    Given User is on the SauceDemo login page
    When User enters username "" and password ""
    And Clicks on the login button
    Then Error message containing "Epic sadface" should be displayed

  Scenario: Locked out user login
    Given User is on the SauceDemo login page
    When User enters username "locked_out_user" and password "secret_sauce"
    And Clicks on the login button
    Then Error message containing "locked out" should be displayed

  # Edge Cases
  Scenario: Username with leading/trailing spaces
    Given User is on the SauceDemo login page
    When User enters username " standard_user " and password "secret_sauce"
    And Clicks on the login button
    Then Error message should be displayed

  Scenario: Username case-sensitivity check
    Given User is on the SauceDemo login page
    When User enters username "Standard_User" and password "secret_sauce"
    And Clicks on the login button
    Then Error message should be displayed

  Scenario: SQL injection attempt
    Given User is on the SauceDemo login page
    When User enters username "' OR '1'='1" and password "secret_sauce"
    And Clicks on the login button
    Then Error message should be displayed

  Scenario: Long username input
    Given User is on the SauceDemo login page
    When User enters a very long username and password "secret_sauce"
    And Clicks on the login button
    Then Error message should be displayed
