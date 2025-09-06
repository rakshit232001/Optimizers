package pages;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.BasePage;

/**
 * Page Object representing the Cart Page in SauceDemo.
 * Contains methods to interact with cart elements and perform validations.
 * 
 * @Author: Rakshit Bhadoria 
 * @version: Sept 2025
 */
public class CartPage extends BasePage {

    // ==============================
    // 🔹 Constructor
    // ==============================
    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // ==============================
    // 🔹 Dynamic Locators
    // ==============================
    private static final String REMOVE_BTN_BY_PRODUCT_NAME =
            "//div[@class='cart_item' and .//div[@class='inventory_item_name' and text()='%s']]//button[text()='Remove']";

    private static final String PRODUCT_TITLE_BY_NAME =
            "//div[@class='cart_item']//div[@class='inventory_item_name' and text()='%s']";

    // ==============================
    // 🔹 Web Elements
    // ==============================
    @FindBy(xpath = "//button[text()='Remove']")
    private List<WebElement> removeButtonElements;

    @FindBy(xpath = "//div[@class='inventory_item_name']")
    private List<WebElement> productTitleElements;

    @FindBy(xpath = "//button[text()='Continue Shopping']")
    private WebElement continueButtonElement;

    @FindBy(xpath = "//button[text()='Checkout']")
    private WebElement checkoutButtonElement;

    @FindBy(xpath = "//span[@class='title' and text()='Your Cart']")
    private WebElement cartTitle;

    // ==============================
    // 🔹 Page Validations
    // ==============================

    /**
     * Verify if Cart Page is open.
     *
     * @return true if cart page is open, false otherwise
     */
    public boolean isCartPageOpen() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOf(cartTitle));
            String title = element.getText();
            if (title.equalsIgnoreCase("Your Cart")) {
                log.info("✅ Cart Page opened successfully.");
                return true;
            } else {
                log.warn("❌ Cart Page title mismatch. Found: " + title);
                return false;
            }
        } catch (Exception e) {
            log.error("❌ Cart Page not opened.", e);
            return false;
        }
    }

    /**
     * Check if cart is empty.
     *
     * @return true if cart is empty, false otherwise
     */
    public boolean isCartEmpty() {
        int productCount = productTitleElements.size();
        log.info("Cart contains " + productCount + " product(s).");
        return productCount == 0;
    }

    /**
     * Check if Checkout button is enabled.
     *
     * @return true if enabled, false otherwise
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

    /**
     * Check if a specific product exists in the cart.
     *
     * @param productName product name to search
     * @return true if present, false otherwise
     */
    public boolean isProductInCart(String productName) {
        By productLocator = By.xpath(String.format(PRODUCT_TITLE_BY_NAME, productName));
        boolean exists = !driver.findElements(productLocator).isEmpty();
        log.info("Product '{}' presence in cart: {}", productName, exists);
        return exists;
    }

    // ==============================
    // 🔹 Getters
    // ==============================

    /**
     * Get names of all products currently in the cart.
     *
     * @return list of product names
     */
    public List<String> getCartProductNames() {
        return productTitleElements.stream().map(WebElement::getText).toList();
    }

    /**
     * Get total count of products currently in cart.
     *
     * @return product count
     */
    public int getCartItemCount() {
        int count = productTitleElements.size();
        log.info("Total products in cart: " + count);
        return count;
    }

    // ==============================
    // 🔹 Actions
    // ==============================

    /**
     * Remove a specific product from the cart.
     *
     * @param productName name of product to remove
     */
    public void removeProduct(String productName) {
        By productRemoveBtn = By.xpath(String.format(REMOVE_BTN_BY_PRODUCT_NAME, productName));
        List<WebElement> buttons = driver.findElements(productRemoveBtn);

        if (buttons.isEmpty()) {
            log.error("❌ Product '{}' not found in cart to remove.", productName);
            Assert.fail("Product '" + productName + "' not present in cart to remove!");
        } else {
            click(buttons.get(0), "Remove " + productName + " from cart");
            log.info("✅ Removed product from cart: " + productName);
        }
    }

    /**
     * Click on "Continue Shopping" button.
     *
     * @return HomePage object
     */
    public HomePage clickContinueShopping() {
        click(continueButtonElement, "Continue Shopping");
        log.info("✅ Clicked Continue Shopping button.");
        return new HomePage(driver);
    }

    /**
     * Click on "Checkout" button.
     *
     * @return CheckoutPage object if button is enabled, else null
     */
    public CheckoutPage clickCheckout() {
        if (checkoutButtonElement.isEnabled()) {
            click(checkoutButtonElement, "Checkout Button");
            log.info("✅ Navigating to CheckoutPage.");
            return new CheckoutPage(driver);
        } else {
            log.warn("⚠️ Checkout button is disabled, staying on CartPage.");
            return null;
        }
    }
}
