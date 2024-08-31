Feature: Launch the application, Add vegetables to cart and validate the total price
  Scenario: Add vegetable to cart and validate Total price
    Given User launches application
    When User adds vegetables to cart
    Then Cart is updated with Items, quantity and price
