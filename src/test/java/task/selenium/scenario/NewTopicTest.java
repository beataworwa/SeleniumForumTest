package task.selenium.scenario;


import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import task.selenium.driver.DriverManager;
import task.selenium.object.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

import static task.selenium.config.TestProperties.*;

public class NewTopicTest {

    private static final String NEW_SUBJECT = "Test subject";
    private static final String NEW_MESSAGE = "Test message" + System.lineSeparator() + "second row";
    private WebDriver driver = DriverManager.getDriver();

    @BeforeTest
    public void openPage() {
        driver.get(PAGE_ADDRESS);
    }

    @Test
    public void createTopic() {
        //given
        String currentTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String newSubject = NEW_SUBJECT + currentTimestamp;
        String newMessage = NEW_MESSAGE + currentTimestamp;
        login();
        //when
        MainPage mainPage = new MainPage(driver);
        mainPage.goToForum();
        ViewForumPage viewForumPage = new ViewForumPage(driver);
        viewForumPage.createTopic();
        PostingPage postingPage = new PostingPage(driver);

        postingPage.setSubject(newSubject);
        postingPage.setMessage(newMessage);
        postingPage.submitNewTopic();
        //then
        verifyResult(newSubject, newMessage);
    }

    private void login() {
        LoginPage loginPage = new LoginPage(driver);

        if (!loginPage.isLoginPossible()) {
            loginPage.clickLoggedUserName();
            loginPage.logoutUser();
        }
        loginPage.setUsername(USERNAME);
        loginPage.setPassword(PASSWORD);
        loginPage.loginToPage();
    }

    @AfterClass
    public void cleanUp() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLoggedUserName();
        loginPage.logoutUser();
        driver.close();
        driver.quit();
    }

    private void verifyResult(final String newSubject, final String newMessage) {
        TopicPage topicPage = new TopicPage(driver);
        verifyMessage(newMessage, topicPage.getMessage(newSubject));
        Assert.assertTrue(topicPage.isSubjectTopicDisable(newSubject));

    }

    private void verifyMessage(final String expectedMessage, final String resultMessage) {
        String[] resultTopicMessage = resultMessage.replaceAll("\n", System.lineSeparator()).split(System.lineSeparator());
        String[] expectedTopicMessage = expectedMessage.split(System.lineSeparator());
        IntStream.range(0, expectedTopicMessage.length).forEach(i ->
                Assert.assertEquals(expectedTopicMessage[i], resultTopicMessage[i]));
    }


}
