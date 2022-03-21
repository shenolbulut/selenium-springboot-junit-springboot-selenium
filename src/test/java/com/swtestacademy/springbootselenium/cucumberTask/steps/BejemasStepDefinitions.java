package com.swtestacademy.springbootselenium.cucumberTask.steps;

import com.swtestacademy.springbootselenium.annotations.LazyAutowired;
import com.swtestacademy.springbootselenium.cucumberTask.BejemasPages.BejemasHomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Value;

public class BejemasStepDefinitions {
    @Value("${browser}")
    private String browser;

    @LazyAutowired
    private BejemasHomePage homePage;

    @Given("user open home page")
    public void userOpenHomePage() {
        homePage
                .goToHomePage();
    }

    @Then("user should see photo of the day picture inside items")
    public void userShouldSeePhotoOfTheDayPictureInsideItems() {
        homePage
                .checkPhotoOfTheDay();
    }

    @When("user adds first item to basket on Home Page")
    public void userAddsFirstItemToBasketOnHomePage() {
        homePage
                .hoverFirstItem()
                .clickAddToCardButton();
    }

    @Then("user should see selected item in the basket")
    public void userShouldSeeSelectedItemInTheBasket() {
        homePage.checkBasketForItem();
    }

    @When("user select one category as {string} on Home Page")
    public void userSelectOneCategoryAsOnHomePage(String category) {
        homePage.selectOneCategory(category);
    }

    @Then("user should see all item is related correct category as {string}")
    public void userShouldSeeAllItemIsRelatedCorrectCategoryAs(String categoryName) {
        homePage.checkAllTheItemName(categoryName);
    }

    @When("user select sortType PRICEorALPHABETIC {string} on home Page")
    public void userSelectSortTypePRICEorALPHABETICOnHomePage(String priceOrAlphabetic) {
        homePage.selectSortingType(priceOrAlphabetic);
    }

    @Then("user should see PRICEorALPHABETIC {string} correct sorting")
    public void userShouldSeePRICEorALPHABETICCorrectSorting(String priceOrAlphabetic) {
        homePage.checkSortingType(priceOrAlphabetic);
    }
}
