package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;

import base.BasePage;

public class LoginPage extends BasePage {
	
	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="user-name")
	WebElement userNameFieldElement;
	
	@FindBy(id="password")
	WebElement passwordFieldElement;
	
	@FindBy(id="login-button")
	WebElement loginButtonElement;
	
	@FindBy(xpath = "//h3[@data-test='error']")
	WebElement errorNotificationBy;
	
	By errorLBy = By.xpath("//h3[@data-test='error']");
	
	public LoginPage fillLoginPage(String username, String password){
        type(userNameFieldElement, username, "UserName Field");
        type(passwordFieldElement, password,"PasswordField");
        return this;
    }
    
    public HomePage clickLoginButton() throws Exception{
        click(loginButtonElement,"Login Button");
        Thread.sleep(2000);
		try {
			WebDriverWait shortWait = new WebDriverWait(driver, java.time.Duration.ofSeconds(3));

			WebElement element = shortWait.until(ExpectedConditions.visibilityOfElementLocated(errorLBy));

			String errorText = element.getText();
			if (errorText.contains("Username and password do not match")) {
				log.error("Login failed with error: " + errorText);
				Assert.fail("Login failed with error: " + errorText); // or throw exception, depending on your design
			}
			else if (errorText.contains("locked out")) {
				log.error("Login failed with error: " + errorText);
				Assert.fail("Login failed with error: " + errorText); // or throw exception, depending on your design
			}
		} catch (org.openqa.selenium.TimeoutException e) {

		}

		log.info("Login submitted, navigating to Home Page.");
		Thread.sleep(3000);
		
        return new HomePage(driver);
    }
//    public void verifyFailedLogin(String expectedText){
//        Assert.assertEquals(readTextFromElement(errorNotificationBy), expectedText);
//        
//    }
//    public void verifyLogout(String expectedText){
//        Assert.assertEquals(readAttributeValueAsText(loginButtonElement, "value"), expectedText);
//    }

}
