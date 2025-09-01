package pages;

import java.nio.channels.SelectableChannel;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.*;

public class HomePage extends BasePage {
	public HomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//span[@data-test='title' and text()='Products']")
	WebElement homePageTitle;
	
	By hpTitle = By.xpath("//span[@data-test='title' and text()='Products']");
	

	@FindBy(xpath = "//button[text()='Add to cart']")
	List<WebElement> addToCartButton;

	@FindBy(xpath = "//button[text()='Remove']")
	List<WebElement> removeToCartButton;

	@FindBy(xpath = "//div[contains(@class,'inventory_item_name ')]")
	List<WebElement> productsTitle;

	@FindBy(xpath = "//a[div[@class='inventory_item_name ']]")
	List<WebElement> productDetailLink;

	@FindBy(xpath = "//select[contains(@class,'sort')]")
	WebElement sortFilterElement;

	@FindBy(xpath = "//a[contains(@class,'shopping_cart')]")
	WebElement cartButtonElement;

	@FindBy(xpath = "//a[contains(@class,'shopping_cart')]/span")
	List<WebElement> cartQuantityElement;
	
	private String addToCartBtnByProductName = 
		    "//div[@class='inventory_item' and .//div[@class='inventory_item_name ' and text()='%s']]//button[contains(@class,'btn_inventory')]";
	
	private final String removeFromCartBtnByProductName = 
	        "//div[@class='inventory_item' and .//div[@class='inventory_item_name ' and text()='%s']]//button[text()='Remove']";
	
	
	
	@FindBy(xpath = "//div[@class='inventory_item']//div[@class='inventory_item_name']")
	List<WebElement> productNameElements;

	@FindBy(xpath = "//div[@class='inventory_item']//div[@class='inventory_item_price']")
	List<WebElement> productPriceElements;
	
	public List<String> getAllProductNames() {
	    return productNameElements.stream()
	            .map(WebElement::getText)
	            .toList();
	}

	public List<Double> getAllProductPrices() {
	    return productPriceElements.stream()
	            .map(e -> Double.parseDouble(e.getText().replace("$", "")))
	            .toList();
	}
	
	
	public HomePage addProduct(List<String> productName) {

		 for (String product : productName) {
		        try {
		            // Create dynamic XPath for this product
		            By productAddBtn = By.xpath(String.format(addToCartBtnByProductName, product));
		            
		            WebElement button = driver.findElement(productAddBtn);
		            click(button, "Add " + product + " to cart");
		        } catch (Exception e) {
		            log.error("Failed to add product: " + product, e);
		            Assert.fail("Could not add product: " + product);
		        }
		    }

		return this;
	}
	
	public HomePage removeProduct(List<String> productNames) {
	    for (String product : productNames) {
	        try {
	            By productRemoveBtn = By.xpath(String.format(removeFromCartBtnByProductName, product));
	            WebElement button = driver.findElement(productRemoveBtn);
	            click(button, "Remove " + product + " from cart");
	        } catch (Exception e) {
	            log.error("Failed to remove product: " + product, e);
	            Assert.fail("Could not remove product: " + product);
	        }
	    }
	    return this;
	}


	public HomePage checkHomePageOpen() {
		try {
			WebDriverWait shortWait = new WebDriverWait(driver, java.time.Duration.ofSeconds(3));

			WebElement element = shortWait.until(ExpectedConditions.visibilityOfElementLocated(hpTitle));

			String Text = element.getText();
			if (Text.contains("Products")) {
				log.error("Home Page is open : " + Text);
			}
		} catch (org.openqa.selenium.TimeoutException e) {
			log.error("❌ Home Page did not load within expected time.");
	        Assert.fail("Home Page not loaded.");
		}
		return this;
	}

	public HomePage sortProducts(String sortOption) {
	    try {
	        Select sortDropdown = new Select(sortFilterElement);
	        sortDropdown.selectByContainsVisibleText(sortOption);
	        log.info("Sorted products by option: " + sortOption);
	    } catch (Exception e) {
	        log.error("❌ Failed to sort products with option: " + sortOption, e);
	        Assert.fail("Sorting failed with option: " + sortOption);
	    }
	    return this;
	}
	
	public int getCartProductCount() {
	    try {
	        if (cartQuantityElement.size() > 0) {
	            // Element exists, get the text
	            String countText = cartQuantityElement.get(0).getText().trim();
	            return Integer.parseInt(countText);
	        } else {
	            // Element not present then cart is empty
	            return 0;
	        }
	    } catch (Exception e) {
	        log.error("❌ Failed to get cart product count", e);
	        return 0;
	    }
	}
	
	public CartPage openCart() {
		click(cartButtonElement, "Cart Button");
		
		return new CartPage(driver);	
	}
	
	public boolean isHomePageOpen() {
	    try {
	        WebDriverWait shortWait = new WebDriverWait(driver, java.time.Duration.ofSeconds(3));
	        WebElement element = shortWait.until(ExpectedConditions.visibilityOfElementLocated(hpTitle));

	        String text = element.getText();
	        if (text.contains("Products")) {
	            log.info("✅ Home Page is open. Title: " + text);
	            return true;
	        } else {
	            log.error("❌ Home Page title mismatch. Found: " + text);
	            return false;
	        }
	    } catch (org.openqa.selenium.TimeoutException e) {
	        log.error("❌ Home Page did not load within expected time.");
	        return false;
	    }
	}
}
