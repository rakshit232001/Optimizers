package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;

public class ConfirmationPage extends BasePage{
	public ConfirmationPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//span[@class='title' and contains(text(),'Checkout: Complete)]")
	WebElement confirmationTitle;
	
	@FindBy(xpath="//h2[contains(text(),'Thank you')]")
	WebElement thankUTextElement;
	
	@FindBy(xpath="//button[text()='Back Home']")
	WebElement backHomeButton;
	
	
	
	public boolean isOrderConfirmed() {
        log.info("Checking if order confirmation message is displayed.");
        return thankUTextElement.isDisplayed();
    }

    public String getConfirmationTitle() {
        log.info("Fetching confirmation page title.");
        return confirmationTitle.getText();
    }

    public HomePage clickBackHome() {
        log.info("Clicking on 'Back Home' button.");
        click(backHomeButton, "Back to home button");
        return new HomePage(driver);
    }
}
