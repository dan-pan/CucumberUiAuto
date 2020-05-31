Feature: Add product to cart
  As a user
  I want to add a product to cart
  Scenario: Add product to cart without login
    Given user visit mall homepage
    And add product 'Printed Summer Dress' to cart
    Then check cart quantity is 2
    And click checkout button in shopping cart banner
    Then check current step is 'Summary'
    And click proceed checkout button in shopping cart
    Then check current step is 'Sign in'
    And login with 'daaan.pan@163.com' '12345678a' in Sign in page
    Then check current step is 'Address'