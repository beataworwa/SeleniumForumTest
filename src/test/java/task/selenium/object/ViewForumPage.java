package task.selenium.object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ViewForumPage extends AbstractPage {

    public ViewForumPage(final WebDriver webDriver) {
        this.driver = webDriver;
    }

    private By newTopicButton = By.xpath("//span[contains(text(),'New Topic')]");

    public ViewForumPage createTopic() {
        getWebElement(newTopicButton).click();
        return new ViewForumPage(driver);
    }
}
