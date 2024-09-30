package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;
    Wrappers actionWrapper;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in
     * instructions
     */

    /*
     * Do not change the provided methods unless necessary, they will help in
     * automation and assessment
     */
    @BeforeTest
    public void startBrowser()throws InterruptedException {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        actionWrapper = new Wrappers(driver);
        
       
    }

    @Test
    public void testCase01() throws InterruptedException {
        // Step 1: Go to www.flipkart.com and search for "Washing Machine"
        driver.get("https://www.flipkart.com");
        Thread.sleep(5000);
         actionWrapper.closeLoginPopup();
        actionWrapper.searchItem("Washing Machine");

        // Step 2: Sort by popularity and get count of items with rating <= 4 stars
        actionWrapper.waitFor(5000);
        actionWrapper.clickElement(By.xpath("//div[@class='sHCOk2']/div[2]"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("popularity"));
        Thread.sleep(5000);

        List<WebElement> ratings = actionWrapper.getElements(By.xpath("//div[@class='XQDdHH']"));
        int count = 0;
        for (WebElement rating : ratings) {
            String ratingValue = rating.getText();
            if (!ratingValue.isEmpty() && Float.parseFloat(ratingValue) <= 4.0) {
                count++;
            }
        }
        System.out.println("Count of Washing Machines with rating <= 4 stars: " + count);
    }

    @Test
    public void testCase02() throws InterruptedException {
        // Step 1: Search for "iPhone"
        driver.get("https://www.flipkart.com");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains(".com"));
        Thread.sleep(5000);
        actionWrapper.closeLoginPopup();

        actionWrapper.searchItem("iPhone");

        // Step 2: Print Titles and discount % of items with more than 17% discount
        actionWrapper.waitFor(3000);
        List<WebElement> discounts = actionWrapper.getElements(By.xpath("//div[@class='UkUFwK']"));
        List<WebElement> titles = actionWrapper.getElements(By.className("KzDlHZ"));

        System.out.println("iPhone Titles with more than 17% discount:");
        for (int i = 0; i < discounts.size(); i++) {
            String discountText = discounts.get(i).getText();
            if (discountText.contains("%")) {
                int discountValue = Integer.parseInt(discountText.replaceAll("[^0-9]", ""));
                if (discountValue > 17) {
                    System.out.println("Title: " + titles.get(i).getText() + ", Discount: " + discountValue + "%");
                }
            }
        }
    }

    @Test
    public void testCase03() throws InterruptedException {
        driver.get("https://www.flipkart.com");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains(".com"));
        Thread.sleep(5000);
        actionWrapper.closeLoginPopup();
        // Step 1: Search for "Coffee Mug"
        actionWrapper.searchItem("Coffee Mug");

        // Step 2: Select 4 stars and above
        actionWrapper.waitFor(3000);
        actionWrapper.clickElement(By.xpath("(//div[@class='XqNaEv'])[1]"));

        // Step 3: Print Title and image URL of top 5 items with highest number of
        // reviews
        actionWrapper.waitFor(3000);
        List<WebElement> titles = actionWrapper.getElements(By.className("wjcEIp"));
        List<WebElement> images = actionWrapper.getElements(By.xpath("//div/img[@class='DByuf4']"));
        List<WebElement> reviews = actionWrapper.getElements(By.xpath("//div/span[@class='Wphh3N']"));

        System.out.println("Top 5 Coffee Mugs with highest number of reviews:");
        for (int i = 0; i < Math.min(5, titles.size()); i++) {
            System.out.println("Title: " + titles.get(i).getText());
            System.out.println("Image URL: " + images.get(i).getAttribute("src"));
            System.out.println("Number of Reviews: " + reviews.get(i).getText());
        }
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }
}