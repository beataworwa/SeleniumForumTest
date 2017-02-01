package task.selenium.object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TopicPage extends AbstractPage {

    private static final String SUBJECT_XPATH = "//div[@class='postbody']/descendant::a[text()='%s']";
    private static final String MESSAGE_PATH = "//a[text()='%s']/ancestor::div[@class='postbody']/descendant::div[@class='content']";

    public TopicPage(final WebDriver driver) {
        this.driver = driver;
    }

    public boolean isSubjectTopicDisable(final String subjectText) {
        return isElementDisplayed(By.xpath(String.format(SUBJECT_XPATH, subjectText)));
    }

    public String getMessage(final String subjectText) {
        return getWebElement(By.xpath(String.format(MESSAGE_PATH, subjectText))).getText();
    }
}
