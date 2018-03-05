package page;



import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected String baseURL = "http://amadeus.maclab.org/_demo/nss-todo-1.1/index.php#";
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 5);
	}
	
	public void Load() {
		driver.get(baseURL);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[value='Add']")));
	}
	
	//This method sends Task name string into the New task name input field and clicks Add task button
	public void CreateNewTask(String taskName) {
		driver.findElement(By.cssSelector("input[name='data']")).sendKeys(taskName);
		driver.findElement(By.cssSelector("input[value='Add']")).click();
		
	}
	
	//This method selects task due date using date picker dropdowns, extracts Month's value and returns it as a String
	public String SelectDueDate (String day, String month, String year) {
		driver.findElement(By.cssSelector("select[name='due_day']")).click();
		driver.findElement(By.cssSelector("select[name='due_day']>option[value='" + day + "']")).click();
		
		driver.findElement(By.xpath("//select[@name='due_month']")).click();
		String monthValue = driver.findElement(By.xpath("//select[@name='due_month']/option[.='" + month + "']")).getAttribute("value");
		// adding "0" to the monthValue, if the monthValue string contains only one character
		if (monthValue.length() == 1) {
			monthValue = "0" + monthValue;
		}
		driver.findElement(By.xpath("//select[@name='due_month']/option[.='" + month + "']")).click();
		driver.findElement(By.cssSelector("select[name='due_year']")).click();
		driver.findElement(By.cssSelector("select[name='due_year']>option[value='" + year + "']")).click();
		
		return monthValue;
		
		}
	
	//This method selects category using category dropdown
	public void SelectCategory(String category) {
		driver.findElement(By.xpath("//select[@name='category']")).click();
		driver.findElement(By.xpath("//select[@name='category']/option[.='" + category + "']")).click();

	}
	
	public String GetCategoryColor (String category) {
		String style = driver.findElement(By.xpath("//a[@title='Remove this category']/span[.='" + category + "']")).getAttribute("style");
		int index1 = style.indexOf("(");
		int index2 = style.indexOf(")");
		String categoryColor = style.substring((index1 + 1), index2);
		
		return categoryColor;
	}
	
	public void MarkCompleted (String taskName) {
		try {
		driver.findElement(By.xpath("//*[.='" + taskName + "']//preceding-sibling::input[1]")).click();
		} catch (NoSuchElementException e) {
			driver.findElement(By.xpath("//text()[contains(.,'" + taskName + "')]//preceding-sibling::input[1]")).click();
		}
		driver.findElement(By.cssSelector("input[value='Complete']")).click();
	}
	
	public void RemoveTaks (String taskName) {
		try {
		driver.findElement(By.xpath("//*[.='" + taskName + "']//preceding-sibling::input[1]")).click();
		} catch (NoSuchElementException e) {
			driver.findElement(By.xpath("//text()[contains(.,'" + taskName + "')]//preceding-sibling::input[1]")).click();
		}
		driver.findElement(By.cssSelector("input[value='Remove']")).click();		
	}
	
	public String EditTask (String taskName, String newName, String newCategory, String newDay, String newMonth, String newYear) {
		try {
		driver.findElement(By.xpath("//*[.='" + taskName + "']//preceding-sibling::a[1]")).click();
		} catch (NoSuchElementException e) {
			driver.findElement(By.xpath("//text()[contains(.,'" + taskName + "')]//preceding-sibling::a[1]")).click();
		}		
		driver.findElement(By.cssSelector("input[type='text']")).clear();
		driver.findElement(By.cssSelector("input[type='text']")).sendKeys(newName);
		String monthValue = this.SelectDueDate(newDay, newMonth, newYear);
		this.SelectCategory(newCategory);
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		return monthValue;
	}
	
	//This method eselects category color from the color dropdown
	public String SelectCategoryColor (String color) {
		driver.findElement(By.cssSelector("select[name='colour']")).click();
		String colorValue = driver.findElement(By.xpath("//option[.='" + color + "']")).getAttribute("value");
		driver.findElement(By.xpath("//option[.='" + color + "']")).click();
		return colorValue;
		
	}
	
	//This method sends category name into the new category name inut field and clicks Add category button
	public void CreateNewCategory(String categoryName) {
		driver.findElement(By.cssSelector("input[name='categorydata']")).sendKeys(categoryName);
		driver.findElement(By.cssSelector("input[value='Add category']")).click();		
	}
	
	//This method clicks on the category name in the category list
	public void RemoveCategory (String categoryName) {
		driver.findElement(By.xpath("//a[@title='Remove this category']/span[.='" + categoryName + "']")).click();
		}
	
	//This method selects toggle all option
	public void ToggleAll () {
		driver.findElement(By.cssSelector("input[name='allbox']")).click();
	}
	
	//This method selects Advanced option to hide or display advanced options
	public void Advanced() {
		driver.findElement(By.xpath("//a[.='[Advanced]']")).click();
	}

}
