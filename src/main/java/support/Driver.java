package support;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.edge.EdgeDriver;

/**
 * Provide a web driver.
 */
public class Driver {

    public static WebDriver driver;

    /**
     * Set Driver by type.
     * @param type driver type
     */
    public static void setDriver(String type) {
        if (type.equals("chrome")) {
            driver = new ChromeDriver();
        } else if (type.equals("firefox")) {
            driver = new FirefoxDriver();
        } else if (type.equals("ie")) {
            driver = new InternetExplorerDriver();
        } else if (type.equals("edge")) {
            driver = new EdgeDriver();
        } else {
            System.out.println("Unknown driver type (" + type + ").");
            System.exit(1);
        }
    }
}
