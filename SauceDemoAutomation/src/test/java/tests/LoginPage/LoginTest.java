package tests.LoginPage;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utils.ExcelUtil;

/**
 * Test class for SauceDemo Login functionality
 * Covers Positive, Negative, and Edge cases.
 * Organized with TestNG groups (smoke, sanity, regression),
 * priorities, and descriptions for industry-standard reporting.
 */
public class LoginTest extends BaseTest{
	private String urlString, groupString;

    @BeforeClass(alwaysRun = true)
    @Parameters({ "url", "group"})
    public void setUp(String urlString, String groupString) {
        this.urlString = urlString;
        this.groupString = groupString;
        driver.get(urlString);
        loginPage = new LoginPage(driver);
    }
    
    @DataProvider(name = "LoginData")
    public Object[][] getLoginData() {
        return ExcelUtil.getTestData("src/test/resources/LoginTestData.xlsx", "Sheet1",groupString);
    }

    @Test(dataProvider = "LoginData")
    public void loginTest(String testCaseId, String username, String password,
                          String expected, String testType, String groups, String desc) throws Exception {

        log.info("------------------------------------------------------------");
        log.info("▶ Test Case: {}", testCaseId);
        log.info("   ➤ Desc     : {}", desc);
        log.info("   ➤ Type     : {}", testType);
        log.info("   ➤ Groups   : {}", groups);
        log.info("   ➤ Username : {}", username);
        log.info("   ➤ Expected : {}", expected);
        log.info("------------------------------------------------------------");
        
        loginPage.fillLoginPage(username, password).clickLoginButton();

        boolean onInventoryPage = driver.getCurrentUrl().contains("inventory.html");
        String errorMsg = loginPage.getLoginErrorMessage();

        if (expected.equalsIgnoreCase("success")) {
            Assert.assertTrue(onInventoryPage, "Expected login success but failed");
            log.info(testCaseId + " ✅ PASSED: User logged in successfully.");
        } else {
            Assert.assertNotNull(errorMsg, "Expected error but no error message appeared");
            log.info(testCaseId + " ✅ PASSED: Correct error displayed -> " + errorMsg);
        }

        driver.navigate().to(urlString); // reset for next test
    }
}
