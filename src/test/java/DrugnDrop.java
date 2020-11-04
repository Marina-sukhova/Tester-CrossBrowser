import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class DrugnDrop {
    WebDriver driver;
    final String SITE_URL = "https://testingcup.pgs-soft.com/task_7";

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
            //options.setHeadless(true);
            driver = new ChromeDriver(options);
        }

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get(SITE_URL);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void addProductWithDragAndDrop() {
        WebElement fieldProductNumber = driver.findElement(By.xpath("//h4[text()='Aparat']/following-sibling::div/input"));
        fieldProductNumber.clear();
        fieldProductNumber.sendKeys("2");
        WebElement productImage = driver.findElement(By.xpath("//div[h4='Aparat']/preceding-sibling::div/img"));
        WebElement basket = driver.findElement(By.cssSelector(".panel-body"));
        Actions action = new Actions(driver);
        action.dragAndDrop(productImage, basket).perform();
        WebElement basketAmountForProduct = driver.findElement(By.xpath("//span[@data-quantity-for='Aparat']"));
       Assert.assertEquals(basketAmountForProduct.getText(), "2");
    }

}
