package tests;

import org.testng.annotations.*;

import base.BaseTest;
import pages.LoginPage;

/**
 * Test class to verify Logout functionality of the application.
 * 
 * Workflow:
 * 1. Log in using provided credentials
 * 2. Perform logout action
 * 
 * Uses TestNG annotations:
 * - @BeforeClass: setup login before running test
 * - @Test: execute logout verification
 * - @Parameters: pass URL and user credentials from testng.xml
 */
public class Test_LogOut extends BaseTest {

    // ==============================
    // 🔹 Test Data
    // ==============================
    String urlString, userString, passwordString;

    // ==============================
    // 🔹 Test Setup
    // ==============================
    /**
     * Login before executing logout test.
     * Initializes LoginPage and HomePage objects.
     * 
     * @param urlString    URL of the application
     * @param userString   Username for login
     * @param passwordString Password for login
     * @throws Exception
     */
    @BeforeClass
    @Parameters({ "url", "user", "password" })
    public void beforeClass(String urlString, String userString, String passwordString) throws Exception {
        this.urlString = urlString;
        this.userString = userString;
        this.passwordString = passwordString;

        // Navigate to application
        driver.get(urlString);

        // Initialize LoginPage and perform login
        loginPage = new LoginPage(driver);
        loginPage.fillLoginPage(userString, passwordString);
        homePage = loginPage.clickLoginButton();
    }

    // ==============================
    // 🔹 Test Cases
    // ==============================
    /**
     * Test to verify that a logged-in user can successfully log out.
     * Invokes logOutAccount method from HomePage.
     */
    @Test
    public void testLogOut() {
        homePage.logOutAccount();
    }
}
