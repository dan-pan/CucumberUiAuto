package pages;

import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

public class HomePage extends Page {

    public HomePage() {
        super("googleBaseUrl");
    }

    /**
     * Input a keywords in homepage search bar.
     * @param keywords keywords
     */
    public void inputKeywords(String keywords) {
        getElement("searchInput").sendKeys(keywords);
    }

    /**
     * Click search btn on homepage search bar.
     */
    public void clickSearchBtn() {
        // click search btn error with ElementNotVisibleException
        // getElement("searchBtn").click();

        // click search btn with Javascript
        Map<String, String> searchBtnLocator = (Map<String, String>) locators.get("searchBtn");
        excuteJs("document.querySelector(\"" + searchBtnLocator.get("cssSelector") + "\").click()");
    }

    /**
     * Get search results on homepage search bar.
     * @return search results elements
     */
    public List<WebElement> getSearchResults() {
        return getElements("searchResults");
    }
}
