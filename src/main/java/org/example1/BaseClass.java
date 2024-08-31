package org.example1;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.EdgeDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.example1.AddToCart.incrementQty;
import static org.example1.AddToCart.vegetables;

public class BaseClass {

    static WebDriver driver;
    static String url="";
    static String browser = "";
    static XSSFWorkbook wb = null;
    static XSSFSheet sheet;
    static Row row;
    static Cell cell;
    static String veggieName;
    static int veggieCount;
    static int noOfCells;
    static int No_of_veggies_to_be_added_to_cart;
    static int basePrice;
    static int totalPrice;
    static ArrayList<String> veggies_to_be_added_to_cart = new ArrayList<String>();
    static ArrayList<Integer> qty_of_veggies_to_be_added_to_cart = new ArrayList<Integer>();
    static int[] price_of_veggies_to_be_added_to_cart;
    static ArrayList<Integer> base_price_of_veggies_to_be_added_to_cart = new ArrayList<Integer>();
    static ArrayList<Integer> total_price_of_veggies_to_be_added_to_cart = new ArrayList<Integer>();
    static int totalExpectedAmt;
    static WebDriverWait wait;

    public static void setUp(){
        readBrowserNameFromExcel("URL");
        if(browser.equalsIgnoreCase("chrome")){
            ChromeOptions cm = new ChromeOptions();
            WebDriverManager.chromedriver().driverVersion("128.0.6613.113").setup();
            driver = new ChromeDriver(cm);
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().driverVersion("128.0.2739.42").setup();
            driver = new EdgeDriver();
        }else {
            System.out.println("Browser "+browser+"does not exists in system");
        }

    }

    public static void tearDown(){
        driver.quit();
    }

    public static void launchURL(String sheetName) throws IOException {
        readUrlFromExcel(sheetName);
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        captureScreenshots();
    }

    public static void readExcel(){
        try {
            wb = new XSSFWorkbook("C:\\Users\\VA338RM\\IdeaProjects\\JPMCTestAssignment\\src\\test\\resources\\TestData\\VegetableList.xlsx");
        } catch (IOException e) {
            System.out.println("Exception thrown inside readUrlFromExcel ");
            throw new RuntimeException(e);
        }
    }

    public static void readUrlFromExcel(String sheetName)  {
        readExcel();
        sheet = wb.getSheet(sheetName);
        row = sheet.getRow(1);
        cell = row.getCell(0);
        url = cell.getStringCellValue();
    }

    public static void readBrowserNameFromExcel(String sheetName) {
        readExcel();
        sheet = wb.getSheet(sheetName);
        row = sheet.getRow(1);
        cell = row.getCell(1);
        browser = cell.getStringCellValue();
    }

    public static void readVeggiesFromExcel(String sheetName) throws IOException {
        readExcel();
        sheet = wb.getSheet(sheetName);
        No_of_veggies_to_be_added_to_cart = sheet.getPhysicalNumberOfRows()-1;
        noOfCells = row.getPhysicalNumberOfCells();
        System.out.println(No_of_veggies_to_be_added_to_cart);
        for(int i=0;i<No_of_veggies_to_be_added_to_cart;i++){
            row = sheet.getRow(i+1);
            cell = row.getCell(0);
            veggieName = cell.getStringCellValue();
            System.out.println(veggieName);

            if(!(veggies_to_be_added_to_cart).contains(veggieName)) {
                veggies_to_be_added_to_cart.add(veggieName);
                System.out.println(veggies_to_be_added_to_cart.get(i));

                cell = row.getCell(1);
                veggieCount = (int) cell.getNumericCellValue();
                qty_of_veggies_to_be_added_to_cart.add(veggieCount);

                cell = row.getCell(2);
                basePrice = (int) cell.getNumericCellValue();
                base_price_of_veggies_to_be_added_to_cart.add(basePrice);

                cell = row.getCell(3);
                totalPrice = (int) cell.getNumericCellValue();
                total_price_of_veggies_to_be_added_to_cart.add(totalPrice);
                totalExpectedAmt += totalPrice;
            }

            System.out.println(veggieName+" count of veggies "+veggieCount);
            for(int j=0;j<veggieCount-1;j++){
                System.out.println("itr "+veggieName);
                incrementQty();
            }
            vegetables(veggieName);

        }
        System.out.println("total="+totalExpectedAmt);
        System.out.println("veggies_to_be_added_to_cart = "+veggies_to_be_added_to_cart);

    }

    public static void captureScreenshots() throws IOException {

        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        File dest = new File("C:\\Users\\VA338RM\\IdeaProjects\\JPMCTestAssignment\\Screenshots\\screenshot_"+formattedDate()+".png");
        FileUtils.copyFile(src,dest);
    }

    public static String formattedDate(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyddmm_hhmmss");
        String formattedDate = sdf.format(d);
        return formattedDate;
    }
}
