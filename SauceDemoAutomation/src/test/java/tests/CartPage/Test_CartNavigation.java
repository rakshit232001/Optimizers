package tests.CartPage;

import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;

public class Test_CartNavigation extends BaseTest{
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
	
	@Test(priority = 3, description = "Verify that user can continue shopping from cart page")
    public void verifyContinueShoppingButton() {
		homePage= cartPage.clickContinueShopping();
        Assert.assertTrue("❌ Did not navigate back to Home Page!",homePage.isHomePageOpen());
    }
}
