package task.selenium.object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends AbstractPage {

    private final By user = By.id("username_logged_in");
    private final By logoutButton = By.xpath("//span[text()='Logout']");
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.name("login");


    public LoginPage(final WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage setUsername(final String username) {
        getWebElement(usernameField).click();
        getWebElement(usernameField).sendKeys(username);
        return new LoginPage(driver);
    }

    public LoginPage setPassword(final String password) {
        getWebElement(passwordField).click();
        getWebElement(passwordField).sendKeys(password);
        return new LoginPage(driver);
    }

    public LoginPage loginToPage() {
        getWebElement(loginButton).click();
        return new LoginPage(driver);
    }

    public LoginPage clickLoggedUserName() {
        getWebElement(user).click();
        return new LoginPage(driver);
    }

    public LoginPage logoutUser() {
        getWebElement(logoutButton).click();
        return new LoginPage(driver);
    }

    public boolean isLogoutPossible() {
        return isElementDisplayed(user);
    }

    public boolean isLoginPossible() {
        return isElementDisplayed(usernameField) &&
                isElementDisplayed(passwordField) &&
                isElementDisplayed(loginButton);
    }
}
