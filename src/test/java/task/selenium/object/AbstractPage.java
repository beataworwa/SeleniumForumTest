package task.selenium.object;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class AbstractPage {

    protected WebDriver driver;

    WebElement getWebElement(final By element) {
        return driver.findElement(element);
    }

    public boolean isElementDisplayed(final By element) {
        try {
            return getWebElement(element).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
