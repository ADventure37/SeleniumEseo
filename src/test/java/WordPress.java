import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WordPress {

    private static WebDriver webDriver;

    @BeforeAll
    public static void beforeTest(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        WordPress.webDriver = new ChromeDriver(options);
    }


    @AfterAll
    public static void afterTest(){
        WordPress.webDriver.close();
    }
}
