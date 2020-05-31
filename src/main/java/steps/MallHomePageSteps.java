package steps;

import factories.PageFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.MallHomePage;

public class MallHomePageSteps {
    /**
     * The corresponding page in steps.
     */
    MallHomePage page = PageFactory.mallHome;

    @Given("^user visit mall homepage$")
    public void launchHomePage() {
        page.launch();
    }

    @And("^add product '(.*)' to cart$")
    public void addProductToCart(String productName) {
        page.addProductToCart(productName);
    }

    @Then("^check cart quantity is (.*)$")
    public void checkCartQuantity(String cartQuantity) {
        assert page.getElement("cartQuantity").getText().equals(cartQuantity);
    }

    @And("^click checkout button in shopping cart banner$")
    public void clickCheckoutBtn() {
        page.clickCheckoutBtn();
    }

    @Then("^click proceed checkout button in shopping cart$")
    public void clickProceedToCheckoutBtn() {
        page.getElement("proceedToCheckoutBtn").click();
    }

    @And("^login with '(.*)' '(.*)' in Sign in page$")
    public void login(String email, String pwd) {
        page.login(email, pwd);
    }

    @Then("^check current step is '(.*)'$")
    public void checkCurrentStep(String step) {
        page.waitUntil(
                () -> page.getElement("currentStep").getText().contains(step),
                3000,
                "current step " + step + " is not displayed"
        );
    }
}
