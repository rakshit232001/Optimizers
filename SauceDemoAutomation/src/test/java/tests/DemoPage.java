package tests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.testng.annotations.*;

import base.*;

import pages.*;

public class DemoPage extends BaseTest {
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

		confirmationPage = checkoutPage.clickFinish();
		boolean isCompleteDisplayed = confirmationPage.isOrderConfirmed();

		Assert.assertTrue("❌ Checkout Completion message not displayed. Expected: 'Checkout: Complete!'",
				isCompleteDisplayed);

	}

	@Test
	public void verifyOrderConfirmationMessage() {
		log.info("Verifying order confirmation message is displayed.");
		Assert.assertTrue("❌ Expected 'Thank you' message was not displayed.", confirmationPage.isOrderConfirmed());
	}

	@Test(priority = 1)
	public void verifyBackHomeNavigation() {
		log.info("Clicking Back Home button.");
		confirmationPage.clickBackHome();

		// You can add an assertion to verify navigation, e.g.:
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue("❌ Back Home button did not navigate to inventory page.",
				currentUrl.contains("inventory.html"));
	}

}
