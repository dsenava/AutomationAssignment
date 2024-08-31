package org.example.stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.awt.*;
import java.io.IOException;

import static org.example1.AddToCart.*;
import static org.example1.BaseClass.*;
import static org.example1.CheckOut.*;

public class Validate_Cart {

    @Before
    public void browserSetUp(){
        setUp();
    }

    @After
    public void browserTearDown(){
        tearDown();
    }

    @Given("User launches application")
    public void userLaunchesApplication() throws IOException {
        launchURL("URL");
    }

    @When("User adds vegetables to cart")
    public void userAddsVegetablesToCart() throws IOException {
        readVeggiesFromExcel("Veggies");
    }

    @Then("Cart is updated with Items, quantity and price")
    public void cartIsUpdatedWithItemsQuantityAndPrice() throws IOException, AWTException {
        validateItemCountInCart();
        proceedToCheckout();
        ValidateOverallTotalAmount();
        placeOrder();
    }
}
