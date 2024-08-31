package org.example1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;

import static org.example1.AddToCart.isElementPresent;

public class CheckOut extends BaseClass{


    static String actualTotalAmtLocator = "//span[@class='totAmt']";
    static int actualTotalAmt;
    static String placeOrder = "//button[text()='Place Order']";
    static String selDropDown = "//label[text()='Choose Country']/following::select";
    static String agreeChkBox = "//input[@class='chkAgree']";
    static String proceed = "//button[text()='Proceed']";
    static String finalmsg = "//span[text()='Thank you, your order has been placed successfully']";


    public static void ValidateOverallTotalAmount() throws IOException {

        wait = new WebDriverWait(driver,10);
        actualTotalAmt = Integer.parseInt(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(actualTotalAmtLocator))).getText());

        System.out.println("Total Overall Amount validation");
        Assert.assertEquals(actualTotalAmt,totalExpectedAmt,"Overall Amount does not match");
        captureScreenshots();
    }


    public static void placeOrder() throws IOException {
        wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(placeOrder))).click();
        WebElement selectDrop = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selDropDown)));

        Select sel = new Select(selectDrop);
        sel.selectByValue("India");
        driver.findElement(By.xpath(agreeChkBox)).click();
        captureScreenshots();
        driver.findElement(By.xpath(proceed)).click();
        captureScreenshots();
        /*if(isElementPresent(finalmsg)){
            String msg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(finalmsg))).getText();
            System.out.println(msg);
        }*/

    }
}
