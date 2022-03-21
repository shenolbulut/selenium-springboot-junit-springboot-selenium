Feature: Bejemas Test Scenarios

    Background:
      Given user open home page

    Scenario: Matching Foto of The Day
      Then user should see photo of the day picture inside items

    Scenario: Add To Card
      When user adds first item to basket on Home Page
      Then user should see selected item in the basket

    Scenario: Sort Price and Category
      When user select one category as "pets" on Home Page
      When user select sortType PRICEorALPHABETIC "Price" on home Page
      Then user should see PRICEorALPHABETIC "price" correct sorting
      Then user should see all item is related correct category as "pets"