Feature: Payment system image selection

  Scenario: As a user of Visa payment system I shall see Visa Icon
    When I press key 4
    Then I see "4"

  Scenario: As a user of Mastercard payment system I shall see Mastercard Icon
    When I press key 5
    Then I see "5"

  Scenario: As a user of unknown payment system I shall see Unknown Icon
    When I press key 6
    Then I see "6"


