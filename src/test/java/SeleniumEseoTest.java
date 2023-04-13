import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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
    }

    @AfterAll
    public static void afterTest(){
        SeleniumEseoTest.webDriver.close();
    }
}
