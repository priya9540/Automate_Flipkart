package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    private WebDriver driver;

    public Wrappers(WebDriver driver) {
        this.driver = driver;
    }

    // Method to perform search operation
    public void searchItem(String searchText) {
        WebElement searchBox = driver.findElement(By.xpath("//input[@class='Pke_EE']"));
        searchBox.clear();
        searchBox.sendKeys(searchText);
        driver.findElement(By.xpath("//button[@class='_2iLD__']")).click();
    }

    // Method to click a specified element
    public void clickElement(By locator) {
        driver.findElement(locator).click();
    }

    // Method to wait for some time (explicit waits can be added here)
    public void waitFor(int milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
    }

    // Method to close the login popup if displayed
    public void closeLoginPopup() {
        try {
            WebElement closeButton = driver.findElement(By.xpath("//div/span[@class='_30XB9F']"));
            closeButton.click();
        } catch (Exception e) {
            System.out.println("Login popup not displayed.");
        }
    }

    // Method to get a list of elements based on the locator
    public List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }
}
