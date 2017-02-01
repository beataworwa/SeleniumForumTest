package task.selenium.config;

import java.util.Properties;

public class TestProperties {
    public static final String PAGE_ADDRESS;
    public static final String USERNAME;
    public static final String PASSWORD;
    public static final String BROWSER;
    public static final String DRIVER_PATH;
    public static final String SCREENSHOT_PATH;

    static {
        Properties prop = new PropertiesLoader().loadPropertiesFromFile();
        PAGE_ADDRESS = prop.getProperty("page");
        USERNAME = prop.getProperty("username");
        PASSWORD = prop.getProperty("password");
        BROWSER = prop.getProperty("browser");
        DRIVER_PATH = prop.getProperty("driverPath");
        SCREENSHOT_PATH = prop.getProperty("screenshotPath");
    }
}
