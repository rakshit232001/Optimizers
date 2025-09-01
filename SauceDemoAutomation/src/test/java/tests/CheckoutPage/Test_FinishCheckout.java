package tests.CheckoutPage;

import java.util.List;

import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;

public class Test_FinishCheckout extends BaseTest{
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
		checkoutPage.enterCheckoutInfo("Rakshit", "Bhadoria", "123456");
		checkoutPage.clickContinue();
	}


	@Test(description = "Verify completing checkout process with Finish button")
	public void testFinishCheckout() {
		
		confirmationPage = checkoutPage.clickFinish();
		   boolean isCompleteDisplayed = confirmationPage.isOrderConfirmed();

		    Assert.assertTrue("❌ Checkout Completion message not displayed. Expected: 'Checkout: Complete!'",isCompleteDisplayed);
		    
		    log.info("✅ Checkout Completion test passed, confirmation displayed.");
		

	}
}
