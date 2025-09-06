package stepDefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.CartPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.LoginPage;

import java.util.List;

public class CartSteps {

	private LoginPage loginPage;
	private HomePage homePage;
	private CartPage cartPage;
	private CheckoutPage checkoutPage;

	@Given("User is logged in with username {string} and password {string}")
	public void user_is_logged_in(String username, String password) throws Exception {
		loginPage = new LoginPage(Hooks.driver);
		Hooks.driver.get("https://www.saucedemo.com/");
		homePage = loginPage.fillLoginPage(username, password).clickLoginButton();
		Assert.assertTrue(homePage.isHomePageOpen());
	}

	@When("User navigates to the cart page")
	public void user_navigates_to_cart_page() {
		cartPage = homePage.openCart();
	}

	@Then("Cart page should be displayed")
	public void cart_page_should_be_displayed() {
		Assert.assertTrue(cartPage.isCartPageOpen());
	}

	@Then("Cart should be empty")
	public void cart_should_be_empty() {
		Assert.assertTrue(cartPage.isCartEmpty());
	}

	@When("User adds products to cart:")
	public void user_adds_products_to_cart(List<String> products) throws Exception {

		homePage.addProducts(products);

	}

	@Then("Cart should contain products:")
	public void cart_should_contain_products(List<String> expectedProducts) {
		List<String> cartProducts = cartPage.getCartProductNames();
		Assert.assertEquals(expectedProducts, cartProducts);
	}

	@Given("Products are added to cart:")
	public void products_are_added_to_cart(List<String> products) throws Exception {
		user_adds_products_to_cart(products);
		cartPage = homePage.openCart();
	}

	@Given("Cart has products:")
	public void cart_has_products(List<String> products) throws Exception {
		// Ensure user is on HomePage
		if (homePage == null || !homePage.isHomePageOpen()) {
			loginPage = new LoginPage(Hooks.driver);
			Hooks.driver.get("https://www.saucedemo.com/");
			homePage = loginPage.fillLoginPage("standard_user", "secret_sauce").clickLoginButton();
		}

		// Clear cart first to avoid duplicates
		cartPage = homePage.openCart();
		if (!cartPage.isCartEmpty()) {
			for (String product : cartPage.getCartProductNames()) {
				cartPage.removeProduct(product);
			}
		}

		// Add products
		
			homePage.addProducts(products);
		

		// Navigate to CartPage
		cartPage = homePage.openCart();
	}

	@When("User removes product {string}")
	public void user_removes_product(String productName) {
		cartPage.removeProduct(productName);
	}

	@Then("Cart should not contain product {string}")
	public void cart_should_not_contain_product(String productName) {
		Assert.assertFalse(cartPage.isProductInCart(productName));
	}

	@When("User clicks Continue Shopping")
	public void user_clicks_continue_shopping() {
		homePage = cartPage.clickContinueShopping();
	}

	@Then("User should be on Home Page")
	public void user_should_be_on_home_page() {
		Assert.assertTrue(homePage.isHomePageOpen());
	}

	@Given("Cart is empty")
	public void cart_is_empty() {
		cartPage = homePage.openCart();
		if (!cartPage.isCartEmpty()) {
			for (String product : cartPage.getCartProductNames()) {
				cartPage.removeProduct(product);
			}
		}
		Assert.assertTrue(cartPage.isCartEmpty());
	}

	@Then("Checkout button should be disabled")
	public void checkout_button_should_be_disabled() {
		Assert.assertFalse(cartPage.isCheckoutButtonEnabled());
	}

	@Then("Checkout button should be enabled")
	public void checkout_button_should_be_enabled() {
		Assert.assertTrue(cartPage.isCheckoutButtonEnabled());
	}

	@When("User clicks Checkout")
	public void user_clicks_checkout() {
		checkoutPage = cartPage.clickCheckout();
	}

	@Then("Checkout page should open")
	public void checkout_page_should_open() {
		Assert.assertTrue(checkoutPage.isCheckoutPageOpen());
	}
}
