package pages;

import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

/**
 * Represents the SauceDemo Home (Products) Page.
 * Provides element references, user actions, and validations
 * for interacting with products, sorting, and cart features.
 * 
 * @Author: Rakshit Bhadoria 
 * @version: Sept 2025
 */
public class HomePage extends BasePage {

    // ==============================
    // 🔹 Constructor
    // ==============================

    /**
     * Initializes the HomePage object.
     *
     * @param driver WebDriver instance used for this page
     */
    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // ==============================
    // 🔹 Dynamic Locators
    // ==============================

    /** Locator for Add to Cart button of a specific product */
    private static final String ADD_TO_CART_BTN_BY_PRODUCT =
            "//div[@class='inventory_item' and .//div[@class='inventory_item_name ' and text()='%s']]//button[contains(@class,'btn_inventory')]";

    /** Locator for Remove button of a specific product */
    private static final String REMOVE_FROM_CART_BTN_BY_PRODUCT =
            "//div[@class='inventory_item' and .//div[@class='inventory_item_name ' and text()='%s']]//button[text()='Remove']";

    /** Locator for Home Page title */
    private final By hpTitle = By.xpath("//span[@data-test='title' and text()='Products']");

    // ==============================
    // 🔹 Page Elements
    // ==============================

    @FindBy(xpath = "//span[@data-test='title' and text()='Products']")
    private WebElement homePageTitle;

    @FindBy(xpath = "//button[text()='Add to cart']")
    private List<WebElement> addToCartButtons;

    @FindBy(xpath = "//button[text()='Remove']")
    private List<WebElement> removeFromCartButtons;

    @FindBy(xpath = "//div[contains(@class,'inventory_item_name ')]")
    private List<WebElement> productTitleElements;

    @FindBy(xpath = "//select[contains(@class,'sort')]")
    private WebElement sortFilterDropdown;

    @FindBy(xpath = "//a[contains(@class,'shopping_cart')]")
    private WebElement cartButton;

    @FindBy(xpath = "//a[contains(@class,'shopping_cart')]/span")
    private List<WebElement> cartQuantityBadge;

    @FindBy(xpath = "//div[@class='inventory_item']//div[@class='inventory_item_name']")
    private List<WebElement> productNameElements;

    @FindBy(xpath = "//div[@class='inventory_item']//div[@class='inventory_item_price']")
    private List<WebElement> productPriceElements;

    // ==============================
    // 🔹 Getters
    // ==============================

    /**
     * Fetches all product names displayed on the Home Page.
     *
     * @return List of product names as strings
     */
    public List<String> getAllProductNames() {
        return productNameElements.stream().map(WebElement::getText).toList();
    }

    /**
     * Fetches all product prices displayed on the Home Page.
     *
     * @return List of product prices as doubles
     */
    public List<Double> getAllProductPrices() {
        return productPriceElements.stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .toList();
    }

    /**
     * Fetches the current product count in the cart.
     *
     * @return number of products currently in cart (0 if empty)
     */
    public int getCartProductCount() {
        try {
            if (!cartQuantityBadge.isEmpty()) {
                return Integer.parseInt(cartQuantityBadge.get(0).getText().trim());
            }
            return 0;
        } catch (Exception e) {
            log.error("❌ Failed to get cart product count", e);
            return 0;
        }
    }

    // ==============================
    // 🔹 Actions
    // ==============================

    /**
     * Adds the given products to the cart by their names.
     *
     * @param productNames list of product names to be added
     * @return current HomePage instance for method chaining
     */
    public HomePage addProducts(List<String> productNames) {
        for (String product : productNames) {
            try {
                By addBtn = By.xpath(String.format(ADD_TO_CART_BTN_BY_PRODUCT, product));
                WebElement button = driver.findElement(addBtn);
                click(button, "Add product: " + product);
                log.info("✅ Added product: " + product);
            } catch (Exception e) {
                log.error("❌ Failed to add product: " + product, e);
                Assert.fail("Could not add product: " + product);
            }
        }
        return this;
    }

    /**
     * Removes the given products from the cart by their names.
     *
     * @param productNames list of product names to be removed
     * @return current HomePage instance for method chaining
     */
    public HomePage removeProducts(List<String> productNames) {
        for (String product : productNames) {
            try {
                By removeBtn = By.xpath(String.format(REMOVE_FROM_CART_BTN_BY_PRODUCT, product));
                WebElement button = driver.findElement(removeBtn);
                click(button, "Remove product: " + product);
                log.info("✅ Removed product: " + product);
            } catch (Exception e) {
                log.error("❌ Failed to remove product: " + product, e);
                Assert.fail("Could not remove product: " + product);
            }
        }
        return this;
    }

    /**
     * Sorts products by selecting the given option from the dropdown.
     *
     * @param sortOption option text to be selected (e.g., "Price (low to high)")
     * @return current HomePage instance for method chaining
     */
    public HomePage sortProducts(String sortOption) {
        try {
            new Select(sortFilterDropdown).selectByVisibleText(sortOption);
            log.info("✅ Sorted products by option: " + sortOption);
        } catch (Exception e) {
            log.error("❌ Failed to sort products with option: " + sortOption, e);
            Assert.fail("Sorting failed with option: " + sortOption);
        }
        return this;
    }

    /**
     * Navigates to the Cart Page by clicking the cart button.
     *
     * @return CartPage object
     */
    public CartPage openCart() {
        click(cartButton, "Cart Button");
        return new CartPage(driver);
    }

    // ==============================
    // 🔹 Validations
    // ==============================

    /**
     * Verifies if the Home Page is loaded by checking the title.
     *
     * @return true if Home Page is successfully loaded, false otherwise
     */
    public boolean isHomePageOpen() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement element = shortWait.until(ExpectedConditions.visibilityOfElementLocated(hpTitle));
            String text = element.getText();
            if (text.contains("Products")) {
                log.info("✅ Home Page loaded successfully. Title: " + text);
                return true;
            } else {
                log.error("❌ Home Page title mismatch. Found: " + text);
                return false;
            }
        } catch (Exception e) {
            log.error("❌ Home Page did not load within expected time.", e);
            return false;
        }
    }
}
