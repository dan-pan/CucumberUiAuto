package support;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Hooks class will be triggered by cucumber running.
 */
public class Hooks {

    @Before
    /**
     * Initialize webDriver.
     * Inject web driver type by system property.
     * The default web driver is chrome.
     */
    public void init() {
        String driverType = System.getProperty("driverType");
        Driver.setDriver(driverType == null ? "chrome" : driverType);
    }


    @After
    /**
     * Take a screenshot in test report if test is marked as failed.
     * Quit driver in the end.
     */
    public void takeScreenshot(Scenario scenario) {
        WebDriver driver = Driver.driver;
        if(scenario.isFailed()) {
            try {
                scenario.write("Current Page URL is " + driver.getCurrentUrl());
                byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
                try {
                    Files.write(Paths.get(scenario.getName().toLowerCase().replace(" ","_")
                            + "_" + new Date().getTime() + ".png"), screenshot);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (WebDriverException somePlatformsDontSupportScreenshots) {
                System.err.println(somePlatformsDontSupportScreenshots.getMessage());
            }
        }
        driver.quit();
    }
}