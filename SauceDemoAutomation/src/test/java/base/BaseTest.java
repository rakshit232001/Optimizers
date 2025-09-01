package base;


import org.openqa.selenium.support.events.EventFiringDecorator;

import utils.WebDriverEventListener;
import org.testng.annotations.Test;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.ITestResult;

import java.awt.Checkbox;
import java.lang.runtime.SwitchBootstraps;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import pages.*;
import utils.*;
import com.google.common.base.CaseFormat;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class BaseTest {
	protected Logger log = Logger.getLogger(BaseTest.class);
	protected WebDriver driver;
	protected HomePage homePage;
	protected LoginPage loginPage;
	protected CartPage cartPage;
	protected CheckoutPage checkoutPage;
	protected ConfirmationPage confirmationPage;
	

	@AfterMethod
	public void captureFailure(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			log.error("❌ Test Failed: " + result.getName());
			Screenshot.captureScreenshot(driver, "Failure_" + result.getName());
		}
		
	
	}

	@Parameters({ "browser" })
	@BeforeClass
	public void setUp(String browserString) {
		LogConfig.initLogs();
		Map<String, Object> prefs = new HashMap<>();

		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		prefs.put("profile.password_manager_leak_detection", false);
		
		browserString.toLowerCase();
		log.info("=========================================");
		log.info("======== Test Execution Started =========");
		log.info("=========================================");
		log.info("Selected Browser: " + browserString);
		switch (browserString) {
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

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
			log.info("Browser closed.");
		}

		log.info("===== Test Execution Finished =====");
	}
	
	@DataProvider(name="sortOptions")
	public Object[][] getSortOption(){
		return new Object[][] {
			{"Highest to lowest"},
            {"Lowest to highest"}
		};
	}
	
	@DataProvider(name="productOptions")
	public Object[][] getProductOption(){
		return new Object[][] {
			{"iPhone 12"},
            {"iPhone 11"}
		};
	}
	
	
	@DataProvider(name = "userAccounts")
    public Object[][] userDetails() {
        return new Object[][]{
                {"demouser","testingisfun99"},
                {"bb","testingisfun99"}
        };
    }
	
	
	  @DataProvider(name = "checkoutData")
	    public Object[][] getCheckoutData() {
	        return new Object[][] {
	            {"John", "Doe", "12345", true},    // valid data
	            {"", "Doe", "12345", false},       // missing first name
	            {"John", "Doe", "ABCD", false},    // Invalid postal code
	            {"John", "", "12345", false},      // missing last name
	            {"John", "Doe", "", false},        // missing postal code
	            {"", "", "", false}                // all blank
	        };
	    }
	

}
