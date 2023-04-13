import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumEseoTest {

    private static WebDriver webDriver;
    private static final String TITLE_ENGLISH = "Engineering studies in France-electronics and computer engineering school | ESEO";

    private static final String TITLE_FRENCH = "ESEO - Grande Ecole d'Ingénieurs Généralistes à Angers, Paris, Dijon";

    @BeforeAll
    public static void beforeTest(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        SeleniumEseoTest.webDriver = new ChromeDriver(options);

    }

    @Test
    @Order(1)
    public void testHomePage(){
        SeleniumEseoTest.webDriver.get("https://eseo.fr/en/");
        Assertions.assertEquals(SeleniumEseoTest.TITLE_ENGLISH, SeleniumEseoTest.webDriver.getTitle(), "web page title");
        try{
            File screenshot = ((TakesScreenshot)
                    SeleniumEseoTest.webDriver).getScreenshotAs(OutputType.FILE);
            ImageIO.write(ImageIO.read(screenshot),"PNG",new File("screenshot.png"));
            }catch(IOException ioe) {
            Assertions.fail("Could not save screenshot.",ioe);
            }

    }

    @Test
    @Order(2)
    public void changeToFrench(){
        WebElement language = SeleniumEseoTest.webDriver.findElement(By.className("langactiv"));
        language.click();
        WebElement french = SeleniumEseoTest.webDriver.findElement(By.className("frenchLang"));
        french.click();
        Assertions.assertEquals(SeleniumEseoTest.TITLE_FRENCH,
        SeleniumEseoTest.webDriver.getTitle(), "Web page title");
        }

    @AfterAll
    public static void afterTest(){
        SeleniumEseoTest.webDriver.close();
    }
}
