package task.selenium.driver;

import com.google.common.base.Throwables;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static task.selenium.config.TestProperties.BROWSER;
import static task.selenium.config.TestProperties.DRIVER_PATH;

public class DriverManager {

    private static WebDriver driver;

    public synchronized static WebDriver getDriver() {
        if (driver == null) {
            switch (SupportedBrowser.valueOf(BROWSER.toUpperCase())) {
                case CHROME:
                    driver = getChromeDriver();
                    break;
                case FIREFOX:
                    driver = getFirefoxDriver();
                    break;
            }
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void closeDriver() {
        driver.quit();
        driver = null;

    }

    private static WebDriver getFirefoxDriver() {
        ProfilesIni profile = new ProfilesIni();
        FirefoxProfile myprofile = profile.getProfile("default");
        return new FirefoxDriver(myprofile);
    }

    private static WebDriver getChromeDriver() {
        System.setProperty("webdriver.chrome.driver", getDriverAbsolutePath());
        return new ChromeDriver();
    }

    private static String getDriverAbsolutePath() {
        URL url = DriverManager.class.getResource(DRIVER_PATH);
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            Throwables.propagate(e);
        }
        return file.getAbsolutePath();
    }
}
