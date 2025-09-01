package tests.CartPage;

import java.util.List;

import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;

public class Test_CartItem extends BaseTest {
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
	
	@Test(priority = 3,description = "Verify that the products added to the cart are displayed correctly in the cart page")
	public void verifyAddedProductsAreInCart() throws Exception {
		 if (!homePage.isHomePageOpen()) {
		        log.info("⚠️ HomePage not open. Navigating back.");
		        homePage = cartPage.clickContinueShopping(); // create a method for navigation
		    }
		
		List<String> productsToAdd = List.of("Sauce Labs Backpack", "Sauce Labs Bolt T-Shirt");
		log.info("Currently product in cart: "+ homePage.getCartProductCount());
		homePage.addProduct(productsToAdd);
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
}
