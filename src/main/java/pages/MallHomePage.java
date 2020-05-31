package pages;

import org.openqa.selenium.WebElement;

import java.util.List;

public class MallHomePage extends Page {

    /**
     * MallHomePage constructor to set up base url.
     */
    public MallHomePage() {
        super("mallBaseUrl");
    }

    /**
     * Add product to cart by product name.
     * @param productName product name
     */
    public void addProductToCart(String productName) {
        List<WebElement> items = getElements("productItems");
        for (WebElement item: items) {
            if (item.findElement(getBy("productName")).getText().equals(productName)) {
                getAction().moveToElement(item).perform();
                waitUntil(() -> item.findElement(getBy("addToCartBtn")).isDisplayed(),
                        3000,
                        "add to cart button is not displayed");
                item.findElement(getBy("addToCartBtn")).click();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                closeCartModel();
            }
        }
    }

    /**
     * Close cart model by js.
     */
    public void closeCartModel() {
        excuteJs("document.querySelector('.cross').click()");
    }

    /**
     * Move to shopping cart then click checkout button.
     */
    public void clickCheckoutBtn() {
        getAction().moveToElement(getElement("shoppingCart")).perform();
        waitUntil(() -> getElement("checkoutBtn").isDisplayed(),
                3000,
                "check out button is not displayed");
        getElement("checkoutBtn").click();
    }

    /**
     * Login with email and password.
     * @param email email
     * @param pwd password
     */
    public void login(String email, String pwd) {
        getElement("email").sendKeys(email);
        getElement("password").sendKeys(pwd);
        getElement("loginBtn").click();
    }
}
