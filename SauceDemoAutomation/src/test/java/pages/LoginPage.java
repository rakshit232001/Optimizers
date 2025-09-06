package pages;

import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

/**
 * Page Object Model class for SauceDemo Login Page.
 * 
 * @Author: Rakshit Bhadoria 
 * @version: Sept 2025
 */
public class LoginPage extends BasePage {

    // ==============================
    // 🔹 Constructor
    // ==============================
    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // ==============================
    // 🔹 Web Elements
    // ==============================
    @FindBy(id = "user-name")
    private WebElement userNameFieldElement;

    @FindBy(id = "password")
    private WebElement passwordFieldElement;

    @FindBy(id = "login-button")
    private WebElement loginButtonElement;

    @FindBy(xpath = "//h3[@data-test='error']")
    private WebElement errorNotificationElement;

    // Dynamic locator (for explicit waits when needed)
    private final By errorLocator = By.xpath("//h3[@data-test='error']");

    // ==============================
    // 🔹 Page Actions
    // ==============================

    /**
     * Fill username and password fields.
     * @param username - user name to enter
     * @param password - password to enter
     * @return this LoginPage instance for method chaining
     */
    public LoginPage fillLoginPage(String username, String password) {
        type(userNameFieldElement, username, "UserName Field");
        type(passwordFieldElement, password, "Password Field");
        return this;
    }

    /**
     * Click the login button and navigate to HomePage.
     * @return HomePage object
     * @throws Exception if login fails or button is not clickable
     */
    public HomePage clickLoginButton() throws Exception {
        click(loginButtonElement, "Login Button");
        log.info("Login submitted.");
        return new HomePage(driver);
    }

    // ==============================
    // 🔹 Page Validations / Getters
    // ==============================

    /**
     * Fetch the login error message if visible.
     * @return error message string, or null if not displayed
     */
    public String getLoginErrorMessage() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, java.time.Duration.ofSeconds(3));
            WebElement element = shortWait.until(
                    ExpectedConditions.visibilityOfElementLocated(errorLocator));
            return element.getText();
        } catch (TimeoutException e) {
            return null; // no error appeared
        }
    }
}
