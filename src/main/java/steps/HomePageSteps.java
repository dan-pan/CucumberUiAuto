package steps;

import factories.PageFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebElement;
import pages.HomePage;

/**
 * The steps on home page.
 */
public class HomePageSteps {

    /**
     * The corresponding page in steps.
     */
    HomePage page = PageFactory.home;

    @Given("^user visit homepage$")
    public void launchHomePage() {
        page.launch();
    }

    @Then("^input '(.*)' in search bar$")
    public void inputKeywordsInSearchBar(String keywords) {
        page.inputKeywords(keywords);
    }

    @Then("^click search btn$")
    public void clickSearchBtn() {
        page.clickSearchBtn();
    }

    @Then("^search results contains '(.*)'$")
    public void checkSearchResults(String keywords) {
        page.waitUntil(
            () -> page.getSearchResults().size() > 1,
            3000,
            "search results are not displayed"
        );
        for(WebElement item : page.getSearchResults()) {
            assert item.getText().toLowerCase().contains(keywords.toLowerCase());
        }
    }
}
