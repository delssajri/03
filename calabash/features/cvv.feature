Feature: Abbility to enter CVV

  Scenario: As a user I can enter correct card and automatically switch to next view. Then I can enter 4 digits for date and CVV. I will see last 4 card number digits, date and cvv. Prev button have to become enabled
    When I enter "4042692503977148"
    When I enter "0417"
    When I enter "123"
    Then I see "7148"
    Then I see "04/17"
    Then I see "123"
    Then I check button Prev enabled
    Then I check button Next disabled
    Then I check button Done enabled

  


