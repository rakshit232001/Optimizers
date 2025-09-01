package base;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.HomePage;
import pages.LoginPage;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

import org.apache.log4j.Logger;

import org.openqa.selenium.support.FindBy;
import org.apache.log4j.Category;


public class BasePage {
	protected WebDriver driver;
	protected Logger log;
	Actions action;
	protected WebDriverWait wait;
	Pattern pattern;
	Matcher matcher;
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.log = Logger.getLogger(this.getClass());
		this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(5), Duration.ofSeconds(10));
		this.action = new Actions(driver); 
		PageFactory.initElements(driver, this);
		
	}
//-----------------------Common Locators-------------------------- 	
	@FindBy(xpath="//button[text()='Open Menu']")
	protected WebElement openSideBarElement;
	
	@FindBy(xpath="//button[text()='Close Menu']")
	protected WebElement closeSideBarElement;
	
	@FindBy(xpath="//a[text()='All Items']")
	protected WebElement allItemsButtonElement;
	
	@FindBy(xpath="//a[text()='Reset App State']")
	protected WebElement resetAppStateButtonElement;
	
	@FindBy(xpath="//a[text()='About']")
	protected WebElement aboutButtonElement;
	
	@FindBy(xpath="//a[text()='Logout']")
	protected WebElement logOutButtonElement;
	
	protected By logOutBy = By.xpath("//a[text()='Logout']");
//-----------------------Common Locators--------------------------

	// Common reusable action with logging
	protected void click(WebElement element, String elementName) {
		try {
			element.click();
			log.info("Clicked on: " + elementName);
		} catch (Exception e) {
			log.error("Failed to click on: " + elementName, e);
			throw e;
		}
	}

	protected void type(WebElement element, String value, String elementName) {
		try {
			element.clear();
			element.sendKeys(value);
			log.info("Entered '" + value + "' into: " + elementName);
		} catch (Exception e) {
			log.error("Failed to enter text into: " + elementName, e);
			throw e;
		}

	}
	
	protected void waitForElementToBePresent(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
    }
	
	public HomePage openHomePage()throws Exception {
		click(openSideBarElement, "Click on Open Side Bar");
		Thread.sleep(2000);
		click(allItemsButtonElement, "Click on All items button");
		return new HomePage(driver);
	}
	
	public LoginPage logOutAccount() {
		
		click(wait.until(ExpectedConditions.elementToBeClickable(openSideBarElement)),"Open side bar");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(logOutBy));
		click(wait.until(ExpectedConditions.elementToBeClickable(logOutButtonElement)),"Log out button");
		
		
		return new LoginPage(driver);
	}
}

