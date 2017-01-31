package task.selenium.object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import task.selenium.driver.DriverManager;

public class MainPage extends AbstractPage {

    private By usernameField = By.className("forumtitle");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public MainPage goToForum() {
        getWebElement(usernameField).click();
        return new MainPage(DriverManager.getDriver());
    }
}
