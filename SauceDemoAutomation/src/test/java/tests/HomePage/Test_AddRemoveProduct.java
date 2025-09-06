package tests.HomePage;

import java.util.Arrays;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;

public class Test_AddRemoveProduct extends BaseTest {
	String urlString,  userString,  passwordString;
	@BeforeClass
	@Parameters({ "url", "user", "password" })
	public void beforeClass(String urlString, String userString, String passwordString) throws Exception {

		this.urlString = urlString;
		this.userString = userString;
		this.passwordString = passwordString;
		driver.get(urlString);
		loginPage = new LoginPage(driver);
		loginPage.fillLoginPage(userString, passwordString);
		homePage = loginPage.clickLoginButton();

	}

	@Test(description = "Test for add some products then delete them")
	public void addProductAndRemove() throws Exception {
		homePage.addProducts(Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt"));
		Thread.sleep(5000);
		homePage.removeProducts(
				Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt"));
		Thread.sleep(10000);
	}

	@Test(description = "Test for add some products")
	public void addProduct() throws Exception {
		homePage.addProducts(Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt"));
		Thread.sleep(5000);
	}
}
