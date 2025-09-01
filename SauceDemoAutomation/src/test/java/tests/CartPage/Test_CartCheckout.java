package tests.CartPage;

import java.util.List;

import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;

public class Test_CartCheckout extends BaseTest{

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
	
	@Test( description = "Verify if we can checkout product(s)")
	public void verifyCheckout() {
		cartPage = homePage.openCart();
		if (!cartPage.isCartEmpty()) {

			// Clear cart first to ensure test consistency
			List<String> existingProducts = cartPage.getCartProductNames();
			for (String product : existingProducts) {
				cartPage.removeProduct(product);
			}
			Assert.assertTrue(cartPage.isCartEmpty());
			log.info("Cart cleared.");
		}
		
		if (!homePage.isHomePageOpen()) {
	        log.info("⚠️ HomePage not open. Navigating back.");
	        homePage = cartPage.clickContinueShopping(); // create a method for navigation
	    }

		//  Add product and verify checkout
		List<String> productsToAdd = List.of("Sauce Labs Backpack", "Sauce Labs Bolt T-Shirt");
		homePage.addProduct(productsToAdd);

		cartPage = homePage.openCart();

		Assert.assertTrue("❌ Checkout button should be enabled after adding products!",
				cartPage.isCheckoutButtonEnabled());
		log.info("Checkout button enabled after adding products.");

		// Proceed to checkout
		checkoutPage = cartPage.clickCheckout();
		Assert.assertTrue("❌ Checkout page did not open!", checkoutPage.isCheckoutPageOpen());
	}
	
}
