package task.selenium.test;

import com.google.common.base.Throwables;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import task.selenium.driver.DriverManager;
import task.selenium.object.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

import static task.selenium.config.TestProperties.*;

public class NewTopicTest {

    private static final String NEW_SUBJECT = "Test subject";
    private static final String NEW_MESSAGE = "Test message" + System.lineSeparator() + "second row";
    private WebDriver driver = DriverManager.getDriver();
    private String currentTimestamp;

    @BeforeMethod
    public void openPage() {
        currentTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        driver.get(PAGE_ADDRESS);
    }

    @Test
    public void createTopic() {
        //given
        String newSubject = NEW_SUBJECT + currentTimestamp;
        String newMessage = NEW_MESSAGE + currentTimestamp;
        login();
        //when
        MainPage mainPage = new MainPage(driver);
        Assert.assertTrue(mainPage.isForumNameDisplayed(), "Forum name displayed");
        mainPage.goToForum();
        ViewForumPage viewForumPage = new ViewForumPage(driver);
        viewForumPage.createTopic();
        fillTopicData(newSubject, newMessage);
        //then
        verifyResult(newSubject, newMessage);
    }

    @AfterMethod
    public void captureScreenshotOnFailure(ITestResult testResult) {
        if (!testResult.isSuccess()) {
            takeScreenshot();
        }
        cleanUp();
    }

    private void fillTopicData(final String newSubject, final String newMessage) {
        PostingPage postingPage = new PostingPage(driver);
        postingPage.setSubject(newSubject);
        postingPage.setMessage(newMessage);
        postingPage.submitNewTopic();
    }

    private void login() {
        LoginPage loginPage = new LoginPage(driver);
        if (!loginPage.isLoginPossible() && loginPage.isLogoutPossible()) {
            loginPage.clickLoggedUserName();
            loginPage.logoutUser();
        }
        loginPage.setUsername(USERNAME);
        loginPage.setPassword(PASSWORD);
        loginPage.loginToPage();
    }

    private void takeScreenshot() {
        String filename = Paths.get(SCREENSHOT_PATH, currentTimestamp + ".jpg").toString();
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(filename));
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    private void cleanUp() {
        LoginPage loginPage = new LoginPage(driver);
        if (loginPage.isLogoutPossible()) {
            loginPage.clickLoggedUserName();
            loginPage.logoutUser();
        }
        DriverManager.closeDriver();
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
