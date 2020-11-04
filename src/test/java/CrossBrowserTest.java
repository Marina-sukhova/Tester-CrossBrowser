import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

public class CrossBrowserTest {


    WebDriver driver;
    final String SITE_URL = "https://testingcup.pgs-soft.com/";
    final String USER_PROFILE_LINK = "https://testingcup.pgs-soft.com/task_6/logged";
    Set<Cookie> cookies;

    @BeforeClass
    @Parameters("browser")
    public void initialization(String browser) {
        if (browser.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.adge.driver",".\\msedgedriver.exe");
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            driver = new EdgeDriver(options);
        } else if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.setHeadless(true);
            driver = new ChromeDriver(options);
        }

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(SITE_URL);
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                driver.manage().addCookie(cookie);
            }
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }


    @Test(description = "Login and see logout link", priority = 1)
    public void testOne() {
        driver.findElement(By.linkText("Zadanie 6")).click();
        driver.findElement(By.id("LoginForm__username")).sendKeys("tester");
        driver.findElement(By.id("LoginForm__password")).sendKeys("123-xyz");
        driver.findElement(By.id("LoginForm_save")).click();
        assertTrue(driver.findElement(By.id("logout")).isDisplayed());
        cookies = driver.manage().getCookies();
    }

    @Test(description = "Use cookies from previous test and go to profile. Check if logout link is displayed",
            priority = 2)
    public void loginWithoutLogin() {
        driver.get(USER_PROFILE_LINK);
        assertTrue(driver.findElement(By.id("logout")).isDisplayed());
        driver.findElement(By.id("logout")).click();

    }

    @Test(description = "Use cookies from first test to go to profile and logout. Check if login link is displayed.",
            priority = 3)
    public void loginSecondAfterLogout() {
        driver.get(USER_PROFILE_LINK);
        assertTrue(driver.findElements(By.id("logout")).isEmpty());
    }
}
