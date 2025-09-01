package tests.CheckoutPage;

import java.util.List;

import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;

public class Test_verifyForm extends BaseTest{
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

		List<String> products = List.of("Sauce Labs Backpack");
		homePage.addProduct(products);
		cartPage = homePage.openCart();
		Assert.assertTrue("❌ Cart page did not open properly.", cartPage.isCartPageOpen());

		// Go to checkout page
		checkoutPage = cartPage.clickCheckout();
		Assert.assertTrue("❌ Checkout page did not open properly.", checkoutPage.isCheckoutPageOpen());
	}

	@AfterMethod
	public void afterMethod() throws Exception {
		homePage = checkoutPage.openHomePage();
		Assert.assertTrue("❌ Expected to return to HomePage after cancel.", homePage.isHomePageOpen());

		cartPage = homePage.openCart();
		checkoutPage = cartPage.clickCheckout();
		Assert.assertTrue("❌ Checkout page did not open properly for next test data.",
				checkoutPage.isCheckoutPageOpen());

	}

	@Test(dataProvider = "checkoutData", description = "Verify checkout with valid and invalid data")
	public void testCheckoutProcess(String firstName, String lastName, String postalCode, boolean shouldProceed) {

		checkoutPage.enterCheckoutInfo(firstName, lastName, postalCode);
		boolean result = checkoutPage.clickContinue();

		if (shouldProceed) {
			Assert.assertTrue("❌ Expected to proceed to Checkout Overview, but failed.", result);

		} else {
			Assert.assertFalse("❌ Expected error but proceeded to Checkout Overview.", result);

			Assert.assertTrue("❌ Expected error message not displayed.", checkoutPage.isErrorDisplayed());
		}

	}
}
