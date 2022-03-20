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
}
