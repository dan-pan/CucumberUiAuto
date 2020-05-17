package pages;

import org.openqa.selenium.*;
import support.Driver;
import support.Utility;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

/**
 * The parent class for all page class.
 * Define the global variables such as baseUrl, driver, locators.
 * Provide common method such addCookie, launch.
 * Generate web locators according to locator yml file.
 * Generate base url according to system property(platform,locale).
 */
public class Page {

    public String baseUrl;
    public WebDriver driver = Driver.driver;
    public Map<String, Object> locators;
    final private String ELEMENT_PATH = "src/main/resources/webelements/";
    final private String CONFIG_FILE = "src/main/resources/config.yml";

    /**
     * Page constructor to set base url and locators.
     */
    Page() {
        setBaseUrl();
        setLocators();
    }

    /**
     * Open the web page of base url.
     */
    public void launch() {
        driver.get(baseUrl);
    }

    /**
     * Open the web page of base url and endpoint.
     * @param endpoint
     */
    public void launch(String endpoint) {
        driver.get(baseUrl + endpoint);
    }

    /**
     * Set base url according to system property(platform,locale).
     * platform define a environment to run tests like stage or prod.
     * locale define a language to run tests like zh-cn, en-gb...
     * The base url is define in config.yml file.
     */
    private void setBaseUrl() {
        String platform = System.getProperty("platform");
        String locale = System.getProperty("locale");
        Map<String, Object> config = Utility.fetchYmlFile(CONFIG_FILE);
        Map<String, String> base = (Map<String, String>)config.get("baseUrl");
        baseUrl = base.get(platform == null ? "stage" : "prod") + (locale == null ? "" : "/" + locale);
    }

    /**
     * Add cookie
     * @param key key of cookie
     * @param value value of cookie
     */
    public void addCookie(String key, String value) {
        driver.manage().addCookie(new Cookie(key, value));
    }

    /**
     * Set locators base on page class name.
     * All the locators are configure in the corresponding web elements yaml fine.
     */
    private void setLocators() {
        String filePath = ELEMENT_PATH + this.getClass().getName().toLowerCase()
                .replace("pages.", "") + ".yml";
        locators = Utility.fetchYmlFile(filePath);
    }

    /**
     * Get web element by name.
     * @param name element name
     * @return web element
     */
    public WebElement getElement(String name) {
        Map<String, String> locatorValue = (Map<String, String>) locators.get(name);
        WebElement element = null;
        if (locatorValue.containsKey("index")) {
            element = getElements(name).get(Integer.parseInt(locatorValue.get("index")));
        } else {
            element = driver.findElement(getBy(name));
        }
        return element;
    }

    /**
     * Get web elements by name.
     * @param name element name
     * @return list of web elements
     */
    public List<WebElement> getElements(String name) {
        return driver.findElements(getBy(name));
    }

    /**
     * Get By object of element locator.
     * @param name element name
     * @return by
     */
    private By getBy(String name) {
        Map<String, String> locatorValue = (Map<String, String>) locators.get(name);
        String methodName = null;
        String methodValue = null;
        By by = null;

        // Get first key and value
        for (Map.Entry<String, String> entry : locatorValue.entrySet()) {
            methodName = entry.getKey();
            methodValue = entry.getValue();
            break;
        }

        // Invoke selector method of By class.
        try {
            Class byClass = By.class;
            Method method = byClass.getMethod(methodName, String.class);
            by = (By) method.invoke(byClass, methodValue);
        } catch (Exception ex) {
            ex.printStackTrace();
        };
        return by;
    }

    /**
     * Wait until condition is true.
     * @param condition condition
     * @param timeoutMillis wait time out millis
     * @param failedMessage output message if it's failed
     * @throws TimeoutException throw TimeoutException if timeoutMillis is approach.
     */
    public void waitUntil(BooleanSupplier condition, long timeoutMillis, String failedMessage)
            throws TimeoutException{
        long start = System.currentTimeMillis();
        while (!condition.getAsBoolean()){
            if (System.currentTimeMillis() - start > timeoutMillis ){
                throw new TimeoutException(failedMessage + " after " + timeoutMillis + " ms");
            }
        }
    }

    /**
     * Excute javascript.
     * @param js javascript
     */
    public void excuteJs(String js) {
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor)driver).executeScript(js);
        } else {
            throw new IllegalStateException("This driver does not support JavaScript!");
        }
    }
}
