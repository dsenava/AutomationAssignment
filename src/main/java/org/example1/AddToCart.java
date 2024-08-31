package org.example1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AddToCart extends BaseClass{
    static String veggie = null;
    static String increment = null;
    static String cart_icon = "//a[@class='cart-icon']";
    //static String cart_items = "//ul[@class='cart-items']";
    static String prod_qty;
    static String prod_name;
    static String cartItemCount = "//span[@class='cart-count']";
    static String cartItemCount2 = "//td[text()='Items']/../td[3]/strong";
    static int itemsInCart;
    static String prod_price;
    static String prod_amount;
    static String proceedToCheckout = "//button[text()='PROCEED TO CHECKOUT']";


    public static void vegetables(String veggieName) throws IOException {
        veggie = "//h4[contains(text(),'"+veggieName+"')]";
        if((isElementPresent(veggie))==true) {
            String addToCart = veggie+"/following-sibling::div[@class='product-action']/button";
            addVeggiesToCart(addToCart);
        }
    }

    public static void vegetablesInCart() throws IOException {
        for(int i=1;i<=itemsInCart;i++){
            System.out.println("Iteration : "+i);
            prod_name = "(//p[@class='product-name'])["+i+"]";
            prod_qty = "(//p[@class='quantity'])["+i+"]";
            prod_price = "(//tr/td[4]/p[@class='amount'])["+i+"]";
            prod_amount = "(//tr/td[5]/p[@class='amount'])["+i+"]";
            cartValidation(i-1);
        }
    }

    public static void incrementQty() throws IOException {
        increment = veggie+"/following-sibling::div/a[@class='increment']";
        System.out.println("Inside increment for "+veggieName);
        addVeggiesToCart(increment);
    }

    public static boolean isElementPresent(String locator){
        Boolean bool = driver.findElement(By.xpath(locator)).isDisplayed();
        return bool;
    }

    public static void addVeggiesToCart(String locatorString) throws IOException {
       //Have added Thread.sleep as application seems to be very unstable
         try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locatorString))).click();
        //driver.findElement(By.xpath(locatorString)).click();
        captureScreenshots();

    }

    public static void proceedToCheckout() throws IOException, AWTException {

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_MINUS);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_MINUS);

        wait = new WebDriverWait(driver,15);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(cart_icon))).click();
        captureScreenshots();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(proceedToCheckout))).click();
        vegetablesInCart();
    }


    public static void validateItemCountInCart(){

        //
        itemsInCart = Integer.parseInt(driver.findElement(By.xpath(cartItemCount2)).getText());
        System.out.println(itemsInCart);
        System.out.println(Integer.parseInt(driver.findElement(By.xpath(cartItemCount2)).getText()));
        Assert.assertEquals(itemsInCart,No_of_veggies_to_be_added_to_cart,"Item count is not matching");
    }

    public static void cartValidation(int i) throws IOException {

            wait = new WebDriverWait(driver,15);
            String[] splitName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prod_name))).getText().split(" ");
            String name = splitName[0];
            System.out.println("name : " + name);
            String[] splitQty = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prod_qty))).getText().split(" ");
            String qty = splitQty[0];
            System.out.println("qty : " + qty);

            int actualBasePrice = Integer.parseInt(driver.findElement(By.xpath(prod_price)).getText());
            int actualAmount = Integer.parseInt(driver.findElement(By.xpath(prod_amount)).getText());

            System.out.println("Value of i = "+i);
            System.out.println("Product Name validation");
            System.out.println(i + "=" + veggies_to_be_added_to_cart.get(i));
            Assert.assertEquals(name, veggies_to_be_added_to_cart.get(i));
            System.out.println("Product Qty validation");
            Assert.assertEquals(Integer.parseInt(qty), qty_of_veggies_to_be_added_to_cart.get(i));
            System.out.println("Product Base Price validation");
            Assert.assertEquals(actualBasePrice, base_price_of_veggies_to_be_added_to_cart.get(i));
            System.out.println("Product Total Price validation");
            Assert.assertEquals(actualAmount, total_price_of_veggies_to_be_added_to_cart.get(i));
        captureScreenshots();
    }



}
