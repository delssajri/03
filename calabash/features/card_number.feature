Feature: Abbility to enter card number

  Scenario: As a user I can enter 15 first card number digits and see them separated in 3 groups by 4 digits each
    When I enter "423456789012345"
    Then I see "4234 5678 9012 345"

  Scenario: As a user I can enter correct card and automatically switch to next view. I will see last 4 card number digits and hint for date and cvv. Prev button have to become enabled
    When I enter "4042692503977148"
    Then I see "7148"
    Then I see "MM/YY"
    Then I see "CVV"
    Then I check button Prev enabled
    Then I check button Done disabled

  Scenario: As a user I can enter incorrect card number and stay on card number view
    When I enter "4042692503977149"
    Then I see "4042 6925 0397 7149"
    Then I check button Done disabled

Scenario: As a user I can enter correct card and return back to card number view by pressing PREV button
    When I enter "4042692503977148"
    When I touch Prev
    Then I see "4042 6925 0397 7148"
    Then I check button Prev disabled
    Then I check button Next enabled

