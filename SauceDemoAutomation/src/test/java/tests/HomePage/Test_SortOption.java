package tests.HomePage;

import java.util.List;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;

public class Test_SortOption extends BaseTest {
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
	
	@Test(priority = 1)
    public void testSortByNameAToZ() {
        homePage.sortProducts("Name (A to Z)");

        List<String> productNames = homePage.getAllProductNames();
        List<String> sortedNames = productNames.stream().sorted().toList();
        
        Assert.assertEquals("❌ Products are not sorted A → Z", productNames, sortedNames);
    }

    @Test(priority = 2)
    public void testSortByNameZToA() {
        homePage.sortProducts("Name (Z to A)");

        List<String> productNames = homePage.getAllProductNames();
        List<String> sortedNames = productNames.stream().sorted((a, b) -> b.compareTo(a)).toList();

        Assert.assertEquals("❌ Products are not sorted Z → A",productNames, sortedNames);
    }

    @Test(priority = 3)
    public void testSortByPriceLowToHigh() {
        homePage.sortProducts("Price (low to high)");

        List<Double> productPrices = homePage.getAllProductPrices();
        List<Double> sortedPrices = productPrices.stream().sorted().toList();

        Assert.assertEquals("❌ Products are not sorted Low → High",productPrices, sortedPrices);
    }

    @Test(priority = 4)
    public void testSortByPriceHighToLow() {
        homePage.sortProducts("Price (high to low)");

        List<Double> productPrices = homePage.getAllProductPrices();
        List<Double> sortedPrices = productPrices.stream().sorted((a, b) -> Double.compare(b, a)).toList();

        Assert.assertEquals("❌ Products are not sorted High → Low",productPrices, sortedPrices);
    }
	
}
