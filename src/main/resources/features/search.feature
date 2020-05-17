Feature: Search
  As a user
  I want to get result with my keywords
  Scenario: Search a keywords
    Given user visit homepage
    And input 'music' in search bar
    And click search btn
    Then search results contains 'music'