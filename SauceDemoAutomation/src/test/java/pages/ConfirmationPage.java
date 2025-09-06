package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;

/**
 * ConfirmationPage represents the final checkout confirmation page in the
 * SauceDemo application. 
 * @Author: Rakshit Bhadoria 
 * @version: Sept 2025
 */
public class ConfirmationPage extends BasePage {

	// =============================
	// Constructor
	// =============================
	public ConfirmationPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// =============================
	// Page Locators
	// =============================

	/** Title of the confirmation page (e.g., "Checkout: Complete!") */
	@FindBy(xpath = "//span[@class='title' and contains(text(),'Checkout: Complete')]")
	private WebElement confirmationTitle;

	/** Thank you message displayed after successful order placement */
	@FindBy(xpath = "//h2[contains(text(),'Thank you')]")
	private WebElement thankYouMessage;

	/** "Back Home" button to return to the home page */
	@FindBy(xpath = "//button[text()='Back Home']")
	private WebElement backHomeButton;

	// =============================
	// Page Actions / Methods
	// =============================

	/**
	 * Check if the order confirmation message is displayed.
	 *
	 * @return true if the "Thank you" message is visible, false otherwise
	 */
	public boolean isOrderConfirmed() {
		log.info("Verifying if order confirmation message is displayed.");
		return thankYouMessage.isDisplayed();
	}

	/**
	 * Get the title text of the confirmation page.
	 *
	 * @return the confirmation page title as String
	 */
	public String getConfirmationTitle() {
		log.info("Fetching confirmation page title.");
		return confirmationTitle.getText();
	}

	/**
	 * Click on the "Back Home" button to return to the Home page.
	 *
	 * @return HomePage object
	 */
	public HomePage clickBackHome() {
		log.info("Clicking on 'Back Home' button.");
		click(backHomeButton, "Back Home button");
		return new HomePage(driver);
	}
}
