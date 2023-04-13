
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WordpressTest {

    private static WebDriver driver;

    @BeforeAll
    public static void beforeTests() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=1920,1080");
        WordpressTest.driver = new ChromeDriver(options);
    }

    @Test
    @Order(1)
    public void openWebsite() {
        driver.get("http://172.24.1.2/wordpress/wp-login.php");
        Assertions.assertEquals("Log In ‹ S8 – Test Blog — WordPress", driver.getTitle());
        try {
            WebElement username = driver.findElement(By.id("user_login"));
            username.clear();
            username.sendKeys("alpha");
        } catch (NoSuchElementException nsee) {
            Assertions.fail("Could not find wordpress_user_login");
        }

        try {
            WebElement password = driver.findElement(By.id("user_pass"));
            password.clear();
            password.sendKeys("network_01");
        } catch (NoSuchElementException nsee) {
            Assertions.fail("Could not find wordpress_user_login");
        }

        try {
            WebElement logon = driver.findElement(By.id("wp-submit"));
            logon.click();
        } catch (NoSuchElementException nsee) {
            Assertions.fail("Could not find wordpress_user_login");
        }


    }

//    @Test
//    @Order(2)
//    public void selectMenuOption() {
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException ex) {
//        }
//        Assertions.assertEquals(WordPress.getString("wordpress_dashboard_title"), driver.getTitle());
//        List<WebElement> menuItems = driver.findElements(By.className("wp-menu-name"));
//        System.out.println(menuItems.size());
//        for (WebElement element : menuItems) {
//            System.out.println(element.getTagName() + " " + element.getAccessibleName() + " " + element.getText());
//        }
//        int indice = 0;
//        boolean found = false;
//        WebElement posts = null;
//        while (!found && indice < menuItems.size()) {
//            System.out.println(menuItems.get(indice).getText());
//            if (menuItems.get(indice).getText().equals("Posts")) {
//                posts = menuItems.get(indice);
//                found = true;
//            }
//            indice++;
//        }
//
//        Assertions.assertTrue(found, "Could not find Posts");
//        Actions actions = new Actions(driver);
//        actions.moveToElement(posts).perform();
//        WebElement addPost = driver.findElement(By.linkText("Add New"));
//        addPost.click();
//        Assertions.assertEquals(WordPress.getString("wordpress_addpost_title"), driver.getTitle());
//
//    }

    @AfterAll
    public static void afterTests() {
        WordpressTest.driver.close();
    }

    @Test
    @Order(3)
    public void tryToRemovePopup() {
        try {
            WebElement closePopup = driver.findElement(By.xpath("/html/body/div[5]/div/div/div[1]/button"));
            if (closePopup.isDisplayed()) {
                closePopup.click();
            }
        } catch (NoSuchElementException nse) {
        }
    }

    @Test
    @Order(3)
    public void tryToPublishNewPost() {
        WebElement postTitle = driver.findElement(By.className("wp-block-post-title"));

        postTitle.clear();
        postTitle.sendKeys("An automated post");

        WebElement postBody = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[1]/div/div[1]/div[1]/div[2]/div[2]/div[3]/div[2]/div/div[2]/div[2]/div/div/p"));
        Actions actions = new Actions(driver);
        actions.moveToElement(postBody);
        actions.click();
        actions.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        actions.sendKeys(Keys.BACK_SPACE);
        actions.sendKeys("Created using Selenium, Chrome and ChromeDriver...");
        actions.build().perform();

        WebElement publish = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[1]/div/div[1]/div[1]/div[1]/div/div[3]/button[2]"));
        publish.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
        }
        publish = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[1]/div/div[1]/div[1]/div[2]/div[4]/div[2]/div/div/div[1]/div[1]/button"));

        publish.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
        }
        WebElement viewPost = driver.findElement(By.linkText("View Post"));
        viewPost.click();
    }


    @Test
    @Order(4)
    public void verifierPost() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
        }
        WebElement entryTitle = driver.findElement(By.className("entry-title"));
        Assertions.assertEquals("An automated post", entryTitle.getText());
        WebElement content = driver.findElement(By.className("entry-content"));
        WebElement contentText = content.findElement(By.tagName("p"));
        Assertions.assertEquals("Created using Selenium, Chrome and ChromeDriver…", contentText.getText());
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
        WebElement postDate = driver.findElement(By.className("entry-date"));
        Assertions.assertEquals(sdf.format(today), postDate.getText());
        WebElement byline = driver.findElement(By.className("byline")).findElement(By.linkText("Andy ALPHA"));
        Assertions.assertNotNull(byline);
    }


    @Test
    @Order(4)
    public void logOut() {
        WebElement displayName = driver.findElement(By.className("display-name"));
        Actions actions = new Actions(driver);
        actions.moveToElement(displayName).perform();
        WebElement logout = driver.findElement(By.linkText("Log Out"));
        logout.click();
        WebElement loggedOut = driver.findElement(By.id("login-message"));
        Assertions.assertEquals("You are now logged out.",loggedOut.getText());



    }
}