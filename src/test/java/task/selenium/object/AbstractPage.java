package task.selenium.object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import task.selenium.driver.DriverManager;

public abstract class AbstractPage {

    protected WebDriver driver;

    protected WebElement getWebElement(By byElement) {
        return driver.findElement(byElement);

    }
}
