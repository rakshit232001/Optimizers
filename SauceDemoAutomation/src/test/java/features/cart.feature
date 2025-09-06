Feature: SauceDemo Cart
  To verify the Cart functionality of SauceDemo application
  Users should be able to view, remove, continue shopping, and checkout products from cart

  Background:
    Given User is logged in with username "standard_user" and password "secret_sauce"

  Scenario: Open Cart Page
    When User navigates to the cart page
    Then Cart page should be displayed

  Scenario: Verify Cart is empty initially
    When User navigates to the cart page
    Then Cart should be empty

  Scenario: Add products and verify in cart
    When User adds products to cart:
      | Sauce Labs Backpack |
      | Sauce Labs Bolt T-Shirt |
    And User navigates to the cart page
    Then Cart should contain products:
      | Sauce Labs Backpack |
      | Sauce Labs Bolt T-Shirt |

  Scenario: Remove product from cart
    Given Products are added to cart:
      | Sauce Labs Backpack |
      | Sauce Labs Bolt T-Shirt |
    When User removes product "Sauce Labs Bolt T-Shirt"
    Then Cart should not contain product "Sauce Labs Bolt T-Shirt"

  Scenario: Continue shopping from cart page
    Given Cart has products:
      | Sauce Labs Backpack |
    When User clicks Continue Shopping
    Then User should be on Home Page

  Scenario: Checkout button disabled when cart is empty
    Given Cart is empty
    Then Checkout button should be disabled

  Scenario: Checkout products in cart
    Given Cart is empty
    When User adds products to cart:
      | Sauce Labs Backpack |
      | Sauce Labs Bolt T-Shirt |
    And User navigates to the cart page
    Then Checkout button should be enabled
    When User clicks Checkout
    Then Checkout page should open
