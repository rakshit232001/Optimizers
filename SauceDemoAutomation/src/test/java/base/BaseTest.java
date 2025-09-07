package base;

import pages.*;
import utils.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

/**
 * BaseTest class for all test classes.
 * Responsibilities:
 * - Initialize WebDriver for different browsers
 * - Provide test setup and teardown
 * - Capture screenshots on test failure
 * - Provide common DataProviders
 * 
 * @Author: Rakshit Bhadoria 
 * @version: Sept 2025
 */
public class BaseTest {

    // ==============================
    // 🔹 Logger & WebDriver
    // ==============================
    protected Logger log = LogManager.getLogger(BaseTest.class);
    protected WebDriver driver;

    // ==============================
    // 🔹 Page Objects
    // ==============================
    protected HomePage homePage;
    protected LoginPage loginPage;
    protected CartPage cartPage;
    protected CheckoutPage checkoutPage;
    protected ConfirmationPage confirmationPage;

    // ==============================
    // 🔹 Test Setup / Teardown
    // ==============================

    /**
     * Setup WebDriver before any test class execution.
     * @param browserString Browser name from testng.xml
     */
    @Parameters({ "browser" })
    @BeforeClass(alwaysRun = true)
    public void setUp(String browserString) {
        LogConfig.initLogs();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);

        log.info("=========================================");
        log.info("======== Test Execution Started =========");
        log.info("=========================================");
        log.info("Selected Browser: " + browserString);

        switch (browserString.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("prefs", prefs);
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("signon.rememberSignons", false);
                profile.setPreference("signon.autofillForms", false);
                profile.setPreference("signon.generation.enabled", false);
                FirefoxOptions options = new FirefoxOptions();
                options.setProfile(profile);
                driver = new FirefoxDriver(options);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setExperimentalOption("prefs", prefs);
                driver = new EdgeDriver(edgeOptions);
                break;
            default:
                throw new IllegalArgumentException("Unsupported Browser: " + browserString);
        }
    }

    /**
     * Quit WebDriver and log test execution end.
     */
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            log.info("Browser closed.");
        }
        log.info("===== Test Execution Finished =====\n");
    }

    /**
     * Capture screenshot on test failure.
     */
    @AfterMethod(alwaysRun = true)
    public void captureFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            log.error("❌ Test Failed: " + result.getName());
            Screenshot.captureScreenshot(driver, "Failure_" + result.getName());
        }
    }

    // ==============================
    // 🔹 DataProviders
    // ==============================

    @DataProvider(name = "sortOptions")
    public Object[][] getSortOption() {
        return new Object[][] { { "Highest to lowest" }, { "Lowest to highest" } };
    }

    @DataProvider(name = "productOptions")
    public Object[][] getProductOption() {
        return new Object[][] { { "iPhone 12" }, { "iPhone 11" } };
    }

    @DataProvider(name = "userAccounts")
    public Object[][] userDetails() {
        return new Object[][] { { "demouser", "testingisfun99" }, { "bb", "testingisfun99" } };
    }

    @DataProvider(name = "checkoutData")
    public Object[][] getCheckoutData() {
        return new Object[][] {
            { "John", "Doe", "12345", true },       // valid data
            { "", "Doe", "12345", false },          // missing first name
            //{ "John", "Doe", "ABCD", false },       // Invalid postal code
            { "John", "", "12345", false },         // missing last name
            { "John", "Doe", "", false },           // missing postal code
            { "", "", "", false }                    // all blank
        };
    }
}
