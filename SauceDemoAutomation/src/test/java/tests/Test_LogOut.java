package tests;


import org.testng.annotations.*;

import base.BaseTest;
import pages.LoginPage;

public class Test_LogOut extends BaseTest {
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
	public void testLogOut() {
		homePage.logOutAccount();
	}
}
