package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeleteCategory {
	protected WebDriver driver;
	protected WebDriverWait wait;
	
	public DeleteCategory(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 5);
	}
	
	public void Yes() {
		driver.findElement(By.xpath("//a[.='Yes']")).click();
	}
	
	public void Nevermind() {
		driver.findElement(By.xpath("//a[.='Nevermind']")).click();
	}

}
