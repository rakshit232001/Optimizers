package pages;

import java.util.List;
import java.util.concurrent.TimeoutException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.BasePage;

public class CartPage extends BasePage {

	public CartPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	private final String removeBtnByProductName = "//div[@class='cart_item' and .//div[@class='inventory_item_name' and text()='%s']]//button[text()='Remove']";

	private final String productTitleByName = "//div[@class='cart_item']//div[@class='inventory_item_name' and text()='%s']";

	@FindBy(xpath = "//button[text()='Remove']")
	List<WebElement> removeButtonElements;

	@FindBy(xpath = "//div[@class='inventory_item_name']")
	List<WebElement> productTitleElements;

	@FindBy(xpath = "//button[text()='Continue Shopping']")
	WebElement continueButtonElement;

	@FindBy(xpath = "//button[text()='Checkout']")
	WebElement checkoutButtonElement;

	@FindBy(xpath = "//span[@class='title' and text()='Your Cart']")
	WebElement cartTitle;

	// ✅ Verify Cart Page Open
	public boolean isCartPageOpen() {
		try {
			// WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			WebElement element = wait.until(ExpectedConditions.visibilityOf(cartTitle));
			String title = element.getText();

			if (title.equalsIgnoreCase("Your Cart")) {
				log.info("Cart Page opened successfully. Title: " + title);
				return true;
			} else {
				log.info("❌ Cart Page title mismatch. Found: " + title);
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	// ✅ Get All Products from Cart
	public List<String> getCartProductNames() {
		try {
			return productTitleElements.stream().map(WebElement::getText).toList();
		} catch (Exception e) {
			log.error("❌ Failed to fetch product names from cart", e);
			return List.of();
		}
	}

	/**
	 * Remove a specific product from the cart (by product name)
	 */
	public void removeProduct(String productName) {
        try {
            By productRemoveBtn = By.xpath(String.format(removeBtnByProductName, productName));
            List<WebElement> buttons = driver.findElements(productRemoveBtn);

            if (buttons.isEmpty()) {
                log.error("❌ Product '" + productName + "' not found in cart to remove.");
                Assert.fail("Product '" + productName + "' not present in cart to remove!");
            } else {
                click(buttons.get(0), "Remove " + productName + " from cart");
                log.info("Removed product from cart: " + productName);
            }
        } catch (Exception e) {
            log.error("❌ Failed to remove product: " + productName, e);
            Assert.fail("Failed to remove product: " + productName);
        }
    }

	/**
	 * Check if the cart is empty
	 */
	public boolean isCartEmpty() {
		try {
			int productCount = productTitleElements.size();
			if (productCount == 0) {
				log.info("Cart is empty ✅");
				return true;
			} else {
				log.info("Cart still has " + productCount + " product(s)");
				return false;
			}
		} catch (Exception e) {
			log.error("❌ Failed to check if cart is empty", e);
			return false;
		}
	}

	/**
	 * Get total count of products currently in cart
	 */
	public int getCartItemCount() {
		try {
			int count = productTitleElements.size();
			log.info("Total products in cart: " + count);
			return count;
		} catch (Exception e) {
			log.error("❌ Failed to get cart item count", e);
			return 0;
		}
	}
	
	/**
     * Check if product exists in cart
     */
    public boolean isProductInCart(String productName) {
        try {
            By productLocator = By.xpath(String.format(productTitleByName, productName));
            List<WebElement> product = driver.findElements(productLocator);

            if (!product.isEmpty()) {
                log.info("Product '" + productName + "' found in cart.");
                return true;
            } else {
                log.info("❌ Product '" + productName + "' not found in cart.");
                return false;
            }
        } catch (Exception e) {
            log.error("❌ Failed to check product in cart: " + productName, e);
            return false;
        }
    }
    
    
    /**
     * Click on "Continue Shopping" button
     * Navigates back to HomePage
     */
    public HomePage clickContinueShopping() {
        try {
            click(continueButtonElement, "Continue Shopping");
            log.info("✅ Clicked Continue Shopping button.");
            return new HomePage(driver);
        } catch (Exception e) {
            log.error("❌ Failed to click Continue Shopping button", e);
            Assert.fail("Continue Shopping button click failed!");
            return null;
        }
    }
    
    /**
     * Click on Checkout button
     * Returns CheckoutPage object
     */
    public CheckoutPage clickCheckout() {
        try {
            if (checkoutButtonElement.isEnabled()) {
                click(checkoutButtonElement, "Checkout Button");
                log.info("Clicked Checkout button, navigating to CheckoutPage.");
                return new CheckoutPage(driver); // Navigate to CheckoutPage
            } else {
                log.warn("⚠️ Checkout button is disabled, cannot proceed. Staying on CartPage.");
                return null; // Return current CartPage
            }
        } catch (Exception e) {
            log.error("❌ Failed to click Checkout button", e);
            Assert.fail("Checkout button click failed!");
        }
		return null;
    }
    
    /**
     * Check if Checkout button is enabled
     */
    public boolean isCheckoutButtonEnabled() {
        try {
            boolean enabled = checkoutButtonElement.isEnabled();
            log.info("Checkout button enabled status: " + enabled);
            return enabled;
        } catch (Exception e) {
            log.error("❌ Failed to check Checkout button status", e);
            return false;
        }
    }

    
}
