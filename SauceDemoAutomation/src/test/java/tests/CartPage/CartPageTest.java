package tests.CartPage;

import java.util.List;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;

public class CartPageTest extends BaseTest{
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
	
	@Test(priority = 1, description = "Verify that the cart page opens when clicking on the cart button")
	public void verifyCartPageOpens() throws Exception {
		cartPage= homePage.openCart();
		Assert.assertTrue("❌ Cart page did not open properly.",cartPage.isCartPageOpen());
	
		Thread.sleep(10000);
	}
	
	@Test(priority = 2, description = "Verify that Cart Page is empty when no products are added")
    public void verifyCartIsEmptyInitially() {
        Assert.assertTrue("❌ Cart is not empty initially!",cartPage.isCartEmpty());
    }
	
	@Test(priority = 3,description = "Verify that the products added to the cart are displayed correctly in the cart page")
	public void verifyAddedProductsAreInCart() throws Exception {
		 if (!homePage.isHomePageOpen()) {
		        log.info("⚠️ HomePage not open. Navigating back.");
		        homePage = cartPage.clickContinueShopping(); // create a method for navigation
		    }
		
		List<String> productsToAdd = List.of("Sauce Labs Backpack", "Sauce Labs Bolt T-Shirt");
		log.info("Currently product in cart: "+ homePage.getCartProductCount());
		homePage.addProducts(productsToAdd);
		log.info("Currently product in cart: "+ homePage.getCartProductCount());
		
		cartPage= homePage.openCart();
		Assert.assertTrue("❌ Cart page did not open properly.",cartPage.isCartPageOpen());
		
		List<String> cartProducts = cartPage.getCartProductNames();
	    log.info("Products inside cart: " + cartProducts);
	    
	    Assert.assertEquals("❌ Cart products do not match added products",cartProducts, productsToAdd);
        log.info("Cart products match with added products.");
		
	}
	
	@Test(priority = 4,description = "Verify that user can remove product from cart")
    public void verifyRemoveProductFromCart() {
//        // Add product
//		List<String> productsToAdd = List.of("Sauce Labs Backpack", "Sauce Labs Bolt T-Shirt");
//		log.info("Currently product in cart: "+ homePage.getCartProductCount());
//		homePage.addProduct(productsToAdd);
//		log.info("Currently product in cart: "+ homePage.getCartProductCount());
//		
//        cartPage = homePage.openCart();
        
        // Remove product
        cartPage.removeProduct("Sauce Labs Bolt T-Shirt");
        
        Assert.assertFalse("❌ Product was not removed from cart!",cartPage.isProductInCart("Sauce Labs Bolt T-Shirt"));
    }
	
	@Test(priority = 5, description = "Verify that user can continue shopping from cart page")
    public void verifyContinueShoppingButton() {
		homePage= cartPage.clickContinueShopping();
        Assert.assertTrue("❌ Did not navigate back to Home Page!",homePage.isHomePageOpen());
    }
	
	@Test(priority = 6, description = "Verify that Checkout button is disabled when cart is empty")
	public void verifyCheckoutButtonBehavior() {
	    // Step 1: Ensure cart is empty
	    cartPage = homePage.openCart();
	    if (!cartPage.isCartEmpty()) {
	        log.info("Cart not empty, clearing cart first...");
	        List<String> existingProducts = cartPage.getCartProductNames();
	        for (String product : existingProducts) {
	            cartPage.removeProduct(product);
	        }
	    }

	    // Step 2: Verify checkout is disabled
	    Assert.assertFalse("❌ Checkout button should be disabled when cart is empty!", cartPage.isCheckoutButtonEnabled());
	    log.info("✅ Checkout button is disabled as expected when cart is empty.");
	}
	
	@Test(priority = 7, description = "Verify if we can checkout product(s)")
	public void verifyCheckout() {
//		cartPage = homePage.openCart();
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
		homePage.addProducts(productsToAdd);

		cartPage = homePage.openCart();

		Assert.assertTrue("❌ Checkout button should be enabled after adding products!",
				cartPage.isCheckoutButtonEnabled());
		log.info("Checkout button enabled after adding products.");

		// Proceed to checkout
		checkoutPage = cartPage.clickCheckout();
		Assert.assertTrue("❌ Checkout page did not open!", checkoutPage.isCheckoutPageOpen());
	}
	
}
