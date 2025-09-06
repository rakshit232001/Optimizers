package stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Hooks {

    public static WebDriver driver;
    public static Logger log = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        log.info("===============================================");
        log.info("Starting Scenario: " + scenario.getName());
        log.info("===============================================");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (driver != null) {
            driver.quit();
        }

        if (scenario.isFailed()) {
            log.error("❌ Scenario Failed: " + scenario.getName());
        } else {
            log.info("✅ Scenario Passed: " + scenario.getName());
        }

        log.info("Scenario Status (after execution): " + scenario.getStatus());
        log.info("===============================================\n");
    }
}
