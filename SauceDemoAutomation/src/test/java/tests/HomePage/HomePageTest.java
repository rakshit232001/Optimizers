package tests.HomePage;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;

public class HomePageTest extends BaseTest {
	String urlString, userString, passwordString;

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
		homePage.addProduct(Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt"));
		Thread.sleep(5000);
		homePage.removeProduct(
				Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt"));
		Thread.sleep(10000);
	}

	@Test(description = "Test for add some products")
	public void addProduct() throws Exception {
		homePage.addProduct(Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt"));
		Thread.sleep(5000);
	}

}
