package runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

 
/**
 * Test Runner for Login Feature.
 * Configured to run with TestNG + Cucumber.
 * Generates pretty, HTML, and JSON reports.
 * 
 * @Author: Rakshit Bhadoria
 * @version: Sept 2025
 */


@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/java/features",
    glue = "stepDefinitions",
    plugin = {"pretty", 
    		"json:target/cucumber-reports/Cucumber.json",
    		"html:target/cucumber-reports.html"},
    monochrome = true
)
public class TestRunner {

}

