package base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import pages.HomePage;
import pages.LoginPage;

import java.time.Duration;
import java.util.regex.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * BasePage class provides common functionality for all page objects.
 * 
 * @Author: Rakshit Bhadoria 
 * @version: Sept 2025
 */
public class BasePage {

    // ==============================
    // 🔹 Instance Variables
    // ==============================
    protected WebDriver driver;
    protected Logger log;
    protected Actions action;
    protected WebDriverWait wait;

    // Regex utilities (if needed in subclasses)
    protected Pattern pattern;
    protected Matcher matcher;

    // ==============================
    // 🔹 Constructor
    // ==============================
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.log = LogManager.getLogger(this.getClass());
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(5), Duration.ofSeconds(10));
        this.action = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    // ==============================
    // 🔹 Common Locators
    // ==============================
    @FindBy(xpath = "//button[text()='Open Menu']")
    protected WebElement openSideBarElement;

    @FindBy(xpath = "//button[text()='Close Menu']")
    protected WebElement closeSideBarElement;

    @FindBy(xpath = "//a[text()='All Items']")
    protected WebElement allItemsButtonElement;

    @FindBy(xpath = "//a[text()='Reset App State']")
    protected WebElement resetAppStateButtonElement;

    @FindBy(xpath = "//a[text()='About']")
    protected WebElement aboutButtonElement;

    @FindBy(xpath = "//a[text()='Logout']")
    protected WebElement logOutButtonElement;

    protected By logOutBy = By.xpath("//a[text()='Logout']");

    // ==============================
    // 🔹 Common Page Actions
    // ==============================
    /**
     * Click on a WebElement with logging.
     * @param element WebElement to click
     * @param elementName Friendly name for logging
     */
    protected void click(WebElement element, String elementName) {
        try {
            element.click();
            log.info("Clicked on: " + elementName);
        } catch (Exception e) {
            log.error("Failed to click on: " + elementName, e);
            throw e;
        }
    }

    /**
     * Type text into a WebElement with logging.
     * @param element WebElement to type into
     * @param value Text value to enter
     * @param elementName Friendly name for logging
     */
    protected void type(WebElement element, String value, String elementName) {
        try {
            element.clear();
            element.sendKeys(value);
            log.info("Entered '" + value + "' into: " + elementName);
        } catch (Exception e) {
            log.error("Failed to enter text into: " + elementName, e);
            throw e;
        }
    }

    /**
     * Wait until a WebElement is visible.
     * @param element WebElement to wait for
     */
    protected void waitForElementToBePresent(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    // ==============================
    // 🔹 Page Navigation / Validations
    // ==============================
    /**
     * Open the home page via sidebar navigation.
     * @return HomePage object
     * @throws Exception if any click fails
     */
    public HomePage openHomePage() throws Exception {
        wait.until(ExpectedConditions.visibilityOf(openSideBarElement));
        click(openSideBarElement, "Open Side Bar");

        wait.until(ExpectedConditions.visibilityOf(allItemsButtonElement));
        click(allItemsButtonElement, "All Items Button");

        return new HomePage(driver);
    }

    /**
     * Log out from the application.
     * @return LoginPage object
     */
    public LoginPage logOutAccount() {
        click(wait.until(ExpectedConditions.elementToBeClickable(openSideBarElement)), "Open Side Bar");
        wait.until(ExpectedConditions.visibilityOfElementLocated(logOutBy));
        click(wait.until(ExpectedConditions.elementToBeClickable(logOutButtonElement)), "Log Out Button");
        return new LoginPage(driver);
    }
}
