package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import utilities.Driver;

public class HomePage {

	public HomePage() {
		PageFactory.initElements(Driver.getDriver(), this);
	}

	@FindBy(xpath = "//input[@type='checkbox']")
	private List<WebElement> checkboxes;
	@FindBy(name = "category")
	private WebElement category_dropdown;

	@FindBy(name = "due_day")
	private WebElement dueday;
	@FindBy(name = "due_month")
	private WebElement due_month;
	@FindBy(name = "due_year")
	private WebElement due_year;
	@FindBy(name = "data")
	public WebElement data;
	@FindBy(xpath = "//input[@type='submit'][2]")
	public WebElement submit;

	public void selectDueDate(String day, String month) {
		Select dayD = new Select(dueday);
		dayD.selectByVisibleText(day);
		Select monthD = new Select(due_month);
		monthD.selectByVisibleText(month);
		//Select yearD = new Select(due_year);
		//monthD.selectByVisibleText(year);

	}

	public void selectTask(int index) {
		checkboxes.get(index).click();
	}

	public void selectCategory(String category) {
		Select catdrop = new Select(category_dropdown);
		catdrop.selectByVisibleText(category);
	}
}
//
