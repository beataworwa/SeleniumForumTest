package task.selenium.object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PostingPage extends AbstractPage {

    private By subjectField = By.id("subject");
    private By messageTextArea = By.id("message");
    private By submitButton = By.xpath("//input[@value='Submit']");


    public PostingPage(final WebDriver driver) {
        this.driver = driver;
    }

    public PostingPage setSubject(final String subject) {
        getWebElement(subjectField).click();
        getWebElement(subjectField).sendKeys(subject);
        return new PostingPage(driver);
    }

    public PostingPage setMessage(final String message) {
        getWebElement(messageTextArea).click();
        getWebElement(messageTextArea).sendKeys(message);
        return new PostingPage(driver);
    }

    public PostingPage submitNewTopic() {
        getWebElement(submitButton).click();
        return new PostingPage(driver);
    }

}


