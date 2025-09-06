package tests;

import org.testng.Assert;
import org.testng.annotations.*;
import base.BaseTest;
import pages.LoginPage;


/**
 * Test class for SauceDemo Login functionality Covers Positive, Negative, and
 * Edge cases. Organized with TestNG groups (smoke, sanity, regression),
 * priorities, and descriptions for industry-standard reporting.
 */
public class DemoPage extends BaseTest {

	private String urlString;

	@BeforeClass(alwaysRun = true, dependsOnMethods = "base.BaseTest.setUp")
	@Parameters({ "url" })
	public void navigateToUrl(String urlString) {
		this.urlString = urlString;
		driver.get(urlString);
		loginPage = new LoginPage(driver);
	}

	/*** -------------------- POSITIVE TEST CASES -------------------- ***/
	@Test(priority = 1, groups = { "smoke", "sanity",
			"regression" }, description = "Verify that a valid standard user can log in successfully.")
	public void validLogin_standardUser() throws Exception {
		loginPage.fillLoginPage("standard_user", "secret_sauce").clickLoginButton();

		boolean actual = driver.getCurrentUrl().contains("inventory.html");
		Assert.assertTrue(actual, "User should be redirected to inventory page");

		driver.navigate().to(urlString); // reset
	}

	/*** -------------------- NEGATIVE TEST CASES -------------------- ***/
	@Test(priority = 2, groups = {
			"regression" }, description = "Verify login fails when using correct username but wrong password.")
	public void invalidLogin_wrongPassword() throws Exception {
		loginPage.fillLoginPage("standard_user", "wrong_pass").clickLoginButton();
		String error = loginPage.getLoginErrorMessage();

		Assert.assertNotNull(error, "Error message should appear");
		Assert.assertTrue(error.contains("Epic sadface"), "Proper error message should be shown");

		driver.navigate().to(urlString);
	}

	@Test(priority = 3, groups = {
			"regression" }, description = "Verify login fails when using invalid username with correct password.")
	public void invalidLogin_wrongUsername() throws Exception {
		loginPage.fillLoginPage("wrong_user", "secret_sauce").clickLoginButton();
		String error = loginPage.getLoginErrorMessage();

		Assert.assertNotNull(error, "Error message should appear");
		Assert.assertTrue(error.contains("Epic sadface"), "Proper error message should be shown");

		driver.navigate().to(urlString);
	}

	@Test(priority = 4, groups = { "sanity",
			"regression" }, description = "Verify login fails when both username and password fields are blank.")
	public void invalidLogin_blankFields() throws Exception {
		loginPage.fillLoginPage("", "").clickLoginButton();
		String error = loginPage.getLoginErrorMessage();

		Assert.assertNotNull(error, "Error message should appear for blank fields");
		Assert.assertTrue(error.contains("Epic sadface"), "Proper error message should be shown");

		driver.navigate().to(urlString);
	}

	@Test(priority = 5, groups = { "regression" }, description = "Verify login fails for a locked-out user.")
	public void lockedOutUserLogin() throws Exception {
		loginPage.fillLoginPage("locked_out_user", "secret_sauce").clickLoginButton();
		String error = loginPage.getLoginErrorMessage();

		Assert.assertNotNull(error, "Error message should appear");
		Assert.assertTrue(error.toLowerCase().contains("locked out"), "Error should indicate locked account");

		driver.navigate().to(urlString);
	}

	/*** -------------------- EDGE CASES -------------------- ***/
	@Test(priority = 6, groups = {
			"regression" }, description = "Verify login fails if username contains leading/trailing spaces.")
	public void login_withSpacesInUsername() throws Exception {
		loginPage.fillLoginPage(" standard_user ", "secret_sauce").clickLoginButton();
		String error = loginPage.getLoginErrorMessage();

		Assert.assertNotNull(error, "Error should be shown for username with spaces");

		driver.navigate().to(urlString);
	}

	@Test(priority = 7, groups = {
			"regression" }, description = "Verify login fails if username is entered with wrong case (case-sensitive check).")
	public void login_withCaseSensitiveUsername() throws Exception {
		loginPage.fillLoginPage("Standard_User", "secret_sauce").clickLoginButton();
		String error = loginPage.getLoginErrorMessage();

		Assert.assertNotNull(error, "Error should be shown for wrong case username");

		driver.navigate().to(urlString);
	}

	@Test(priority = 8, groups = {
			"regression" }, description = "Verify login fails when SQL injection string is used as username.")
	public void login_withSQLInjectionString() throws Exception {
		loginPage.fillLoginPage("' OR '1'='1", "secret_sauce").clickLoginButton();
		String error = loginPage.getLoginErrorMessage();

		Assert.assertNotNull(error, "Error should be shown for SQL injection attempt");

		driver.navigate().to(urlString);
	}

	@Test(priority = 9, groups = {
			"regression" }, description = "Verify login fails when username exceeds max length (long string).")
	public void login_withLongUsername() throws Exception {
		String longUsername = "a".repeat(1000);
		loginPage.fillLoginPage(longUsername, "secret_sauce").clickLoginButton();
		String error = loginPage.getLoginErrorMessage();

		Assert.assertNotNull(error, "Error should be shown for long username input");

		driver.navigate().to(urlString);
	}
}
