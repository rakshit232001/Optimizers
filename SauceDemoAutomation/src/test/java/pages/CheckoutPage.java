package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.BasePage;

public class CheckoutPage extends BasePage {

	public CheckoutPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@id='first-name']")
	WebElement firstNameField;

	@FindBy(xpath = "//input[@id='last-name']")
	WebElement lastNameField;

	@FindBy(xpath = "//input[@id='postal-code']")
	WebElement postalCodeField;

	@FindBy(xpath = "//button[text()='Cancel']")
	WebElement cancelButton;

	@FindBy(xpath = "//input[@id='continue']")
	WebElement continueButton;

	@FindBy(xpath = "//span[@class='title' and contains(text(),'Checkout')]")
	WebElement checkoutTitle;

	@FindBy(xpath = "//h3[@data-test ='error']")
	WebElement errorLabel;

	@FindBy(xpath = "//span[@class='title' and contains(text(),'Checkout: Overview')]")
	WebElement checkout2Title;

	@FindBy(xpath = "//button[text()='Finish']")
	WebElement finishButton;

	public boolean isCheckoutPageOpen() {
		try {
			WebElement element = wait.until(ExpectedConditions.visibilityOf(checkoutTitle));
			String title = element.getText();
			if (title.equalsIgnoreCase("Checkout: Your Information")) {
				log.info("Checkout Page opened successfully. Title: " + title);
				return true;
			} else {
				log.error("❌ Checkout Page title mismatch. Found: " + title);
				return false;
			}
		} catch (Exception e) {
			log.error("❌ Checkout Page did not open.", e);
			return false;
		}
	}

	// Fill checkout form
	public void enterCheckoutInfo(String firstName, String lastName, String postalCode) {
		try {
			type(firstNameField, firstName, "First Name");
			Thread.sleep(5000);
			type(lastNameField, lastName, "Last Name");
			type(postalCodeField, postalCode, "Postal Code");
			log.info("Entered checkout info: " + firstName + " " + lastName + " | " + postalCode);
		} catch (Exception e) {
			log.error("❌ Failed to enter checkout info", e);
			Assert.fail("Could not enter checkout info");
		}
	}

	// Click Continue
	public void clickContinueButton() {
		try {
			click(continueButton, "Continue Button");
			log.info("Clicked Continue button.");
		} catch (Exception e) {
			log.error("❌ Failed to click Continue", e);
			Assert.fail("Continue button click failed!");
		}
	}
	
	/**
     * Click Continue button
     */
    public boolean clickContinue() {
        try {
            click(continueButton, "Continue Button");
            log.info("Clicked Continue button.");

            // Wait for either Overview or Error
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
     * Check if error message is displayed
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

    /**
     * Check if Checkout Overview Page is open
     */
    public boolean isCheckoutOverviewPageOpen() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOf(checkout2Title));
            String title = element.getText();
            if (title.equalsIgnoreCase("Checkout: Overview")) {
                log.info("✅ Checkout Overview Page opened successfully.");
                return true;
            } else {
                log.warn("⚠️ Found Checkout title but text mismatch: " + title);
            }
        } catch (Exception e) {
            log.debug("Checkout Overview Page not open yet (might be error case).");
        }
        return false;
    }
    
    /**
     * Click Cancel button from Checkout Overview and go back to HomePage
     */
    public HomePage clickCancelToHome() {
        try {
            click(cancelButton, "Cancel Button");
            log.info("Clicked Cancel button, navigating back to HomePage.");
            return new HomePage(driver);
        } catch (Exception e) {
            log.error("❌ Failed to click Cancel button", e);
            Assert.fail("Cancel button click failed!");
            return null;
        }
    }
    
    
    /**
     * Click Finish button on Checkout Overview
     * Navigates to Checkout Complete page
     */
    public ConfirmationPage clickFinish() {
        try {
            click(finishButton, "Finish Button");
            log.info("✅ Clicked Finish button.");
            
        } catch (Exception e) {
            log.error("❌ Failed to click Finish button or Complete page not loaded", e);
            return null;
        }
        return new ConfirmationPage(driver);
    }

    
    
}
