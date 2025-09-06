package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.BasePage;

/**
 * Page Object representing the Checkout Page in SauceDemo.
 * Covers steps for entering user details, continuing, canceling,
 * and finishing the checkout process.
 * @Author: Rakshit Bhadoria 
 * @version: Sept 2025
 */
public class CheckoutPage extends BasePage {

    // ==============================
    // 🔹 Constructor
    // ==============================
    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // ==============================
    // 🔹 Web Elements
    // ==============================
    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(xpath = "//button[text()='Cancel']")
    private WebElement cancelButton;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(xpath = "//span[@class='title' and contains(text(),'Checkout')]")
    private WebElement checkoutTitle;

    @FindBy(xpath = "//h3[@data-test='error']")
    private WebElement errorLabel;

    @FindBy(xpath = "//span[@class='title' and contains(text(),'Checkout: Overview')]")
    private WebElement checkoutOverviewTitle;

    @FindBy(xpath = "//button[text()='Finish']")
    private WebElement finishButton;

    // ==============================
    // 🔹 Page Validations
    // ==============================

    /**
     * Verify if Checkout Information Page is open.
     *
     * @return true if page is open, false otherwise
     */
    public boolean isCheckoutPageOpen() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOf(checkoutTitle));
            String title = element.getText();
            if (title.equalsIgnoreCase("Checkout: Your Information")) {
                log.info("✅ Checkout Page opened successfully.");
                return true;
            } else {
                log.warn("❌ Checkout Page title mismatch. Found: " + title);
                return false;
            }
        } catch (Exception e) {
            log.error("❌ Checkout Page did not open.", e);
            return false;
        }
    }

    /**
     * Verify if Checkout Overview Page is open.
     *
     * @return true if overview page is visible
     */
    public boolean isCheckoutOverviewPageOpen() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOf(checkoutOverviewTitle));
            String title = element.getText();
            if (title.equalsIgnoreCase("Checkout: Overview")) {
                log.info("✅ Checkout Overview Page opened successfully.");
                return true;
            } else {
                log.warn("⚠️ Found Checkout title but mismatch: " + title);
            }
        } catch (Exception e) {
            log.debug("Checkout Overview Page not open yet.");
        }
        return false;
    }

    /**
     * Verify if an error message is displayed on Checkout Page.
     *
     * @return true if error is visible
     */
    public boolean isErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorLabel));
            String errorText = errorLabel.getText();
            log.warn("⚠️ Checkout Error displayed: " + errorText);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ==============================
    // 🔹 Actions
    // ==============================

    /**
     * Fill checkout form with user information.
     *
     * @param firstName user's first name
     * @param lastName user's last name
     * @param postalCode user's postal code
     */
    public void enterCheckoutInfo(String firstName, String lastName, String postalCode) {
        try {
            type(firstNameField, firstName, "First Name");
            type(lastNameField, lastName, "Last Name");
            type(postalCodeField, postalCode, "Postal Code");
            log.info("✅ Entered checkout info: {} {} | {}", firstName, lastName, postalCode);
        } catch (Exception e) {
            log.error("❌ Failed to enter checkout info", e);
            Assert.fail("Could not enter checkout info");
        }
    }

    /**
     * Click Continue button and proceed to next step.
     */
    public void clickContinueButton() {
        try {
            click(continueButton, "Continue Button");
            log.info("✅ Clicked Continue button.");
        } catch (Exception e) {
            log.error("❌ Failed to click Continue", e);
            Assert.fail("Continue button click failed!");
        }
    }

    /**
     * Click Continue and return status based on navigation result.
     *
     * @return true if navigated to overview, false if error
     */
    public boolean clickContinue() {
        try {
            click(continueButton, "Continue Button");
            log.info("✅ Clicked Continue button.");
            if (isCheckoutOverviewPageOpen()) {
                return true;
            } else if (isErrorDisplayed()) {
                return false;
            }
        } catch (Exception e) {
            log.error("❌ Failed to click Continue button", e);
        }
        return false;
    }

    /**
     * Cancel checkout and navigate back to Home Page.
     *
     * @return HomePage object
     */
    public HomePage clickCancelToHome() {
        try {
            click(cancelButton, "Cancel Button");
            log.info("✅ Clicked Cancel button, returning to HomePage.");
            return new HomePage(driver);
        } catch (Exception e) {
            log.error("❌ Failed to click Cancel button", e);
            Assert.fail("Cancel button click failed!");
            return null;
        }
    }

    /**
     * Finish checkout process and navigate to Confirmation Page.
     *
     * @return ConfirmationPage object
     */
    public ConfirmationPage clickFinish() {
        try {
            click(finishButton, "Finish Button");
            log.info("✅ Clicked Finish button.");
            return new ConfirmationPage(driver);
        } catch (Exception e) {
            log.error("❌ Failed to click Finish button", e);
            return null;
        }
    }
}
