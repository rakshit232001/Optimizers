package tests.HomePage;

import java.util.Arrays;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;

public class Test_CheckQuantityInCart extends BaseTest{
	String urlString,  userString,  passwordString;
	@BeforeClass
	@Parameters({"url","user","password"})
	public void beforeClass(String urlString, String userString, String passwordString) throws Exception {
		
		this.urlString = urlString;
		this.userString = userString;
		this.passwordString = passwordString;
		driver.get(urlString);
		loginPage= new LoginPage(driver);
		loginPage.fillLoginPage(userString, passwordString);
		homePage = loginPage.clickLoginButton();
		
	}
	
	@Test
	public void checkQuantity() throws Exception {
		log.info("Currently product in cart: "+ homePage.getCartProductCount());
		homePage.addProducts(Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt"));
		Thread.sleep(5000);
		log.info("Currently product in cart: "+ homePage.getCartProductCount());
		homePage.removeProducts(
				Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt"));
		Thread.sleep(10000);
	}
}
