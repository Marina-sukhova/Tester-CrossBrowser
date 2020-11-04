import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class MouseMoove {
    WebDriver driver;
    final String SITE_URL1 = "http://the-internet.herokuapp.com/dropdown";

    @BeforeTest
    public void initialization() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        //options.setHeadless(true);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get(SITE_URL1);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void Check(){
        Select select = new Select(driver.findElement(By.id("dropdown")));
        WebElement option = select.getFirstSelectedOption();
        Assert.assertEquals("Please select an option", option.getText());
    }
    @Test
    public void checkSelectByVisibleText(){
        Select select = new Select(driver.findElement(By.id("dropdown")));
        select.selectByVisibleText("Option 1");
        WebElement option = select.getFirstSelectedOption();
        Assert.assertEquals("Option 1", option.getText());
    }

    @Test
    public void checkSelectByIndex(){
        Select select = new Select(driver.findElement(By.id("dropdown")));
        select.selectByIndex(2);
        WebElement option = select.getFirstSelectedOption();
        Assert.assertEquals("Option 2", option.getText());
    }
}
