package API_Test;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pages.HomePage;
import utilities.Driver;

public class UiTest {
	private WebDriver driver;
	HomePage homePage = null;

	@Before
	public void setUp() {
		driver = Driver.getDriver();
		homePage = new HomePage();
	}

	@Test
	public void test() throws InterruptedException {

		String url = "http://localhost:80/index.php";
		driver.get(url);
		Thread.sleep(5);
		homePage.selectTask(0);
		homePage.selectCategory("Work");
		homePage.selectDueDate("1", "Dec");
		homePage.submit.click();
		boolean warning = driver.findElement(By.xpath("//b[1]")).isDisplayed();
		if (warning) {
			System.out.println("Test is failed, permission denied");
		}

	}

	@After
	public void tearDown() {
		driver.quit();
	}

}