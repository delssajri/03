Feature: Abbility to enter date

  Scenario: As a user I can enter correct card and automatically switch to next view. Then I can enter 4 digits for date. I will see last 4 card number digits and date and hint cvv. Prev button have to become enabled
    When I enter "4042692503977148"
    When I enter "0417"
    Then I see "7148"
    Then I see "04/17"
    Then I see "CVV"
    Then I check button Prev enabled
    Then I check button Done disabled


  


