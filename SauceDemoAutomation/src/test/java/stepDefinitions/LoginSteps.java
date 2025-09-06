package stepDefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.LoginPage;

public class LoginSteps {

    private LoginPage loginPage;

    @Given("User is on the SauceDemo login page")
    public void user_is_on_the_saucedemo_login_page() {
        loginPage = new LoginPage(Hooks.driver);
        Hooks.driver.get("https://www.saucedemo.com/");
    }

    @When("User enters username {string} and password {string}")
    public void user_enters_credentials(String username, String password) {
        loginPage.fillLoginPage(username, password);
    }

    @When("User enters a very long username and password {string}")
    public void user_enters_long_username(String password) {
        String longUsername = "a".repeat(1000);
        loginPage.fillLoginPage(longUsername, password);
    }

    @When("Clicks on the login button")
    public void clicks_on_the_login_button() throws Exception {
        loginPage.clickLoginButton();
    }

    @Then("User should be redirected to the inventory page")
    public void user_should_be_redirected_to_inventory_page() {
        Assert.assertTrue(Hooks.driver.getCurrentUrl().contains("inventory.html"));
    }

    @Then("Error message containing {string} should be displayed")
    public void error_message_should_be_displayed(String expected) {
        String actual = loginPage.getLoginErrorMessage();
        Assert.assertNotNull(actual);
        Assert.assertTrue(actual.contains(expected));
    }

    @Then("Error message should be displayed")
    public void error_message_should_be_displayed_generic() {
        String actual = loginPage.getLoginErrorMessage();
        Assert.assertNotNull(actual);
    }
}
