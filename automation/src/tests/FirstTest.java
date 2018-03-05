package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import page.BasePage;
import page.DeleteCategory;

public class FirstTest extends BaseTest {
	private BasePage bpage;
	private DeleteCategory dcatpage;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		bpage = new BasePage(driver);
		dcatpage = new DeleteCategory(driver);
	}
	
	@After
	public void tearDown() throws Exception {
//		super.tearDown();
	}

	@Test
	public void test1() {
		String taskname = "Task 1";
		bpage.Load();
		bpage.CreateNewTask(taskname);
		//Confirming the new task name appears in the task grid
		String preText = driver.findElement(By.cssSelector("pre")).getText();
		assertTrue(preText.contains(taskname+ " (None)"));
	}

	
	// test2 can be ran for both scenarios, "Test case 2" and "Test case 2.1"
	@Test
	public void test2() {
		String day = "5";
		String month = "Jan";
		String year = "2018";
		String taskName = "Task 2";
		bpage.Load();
		String monthValue = bpage.SelectDueDate(day, month, year);
		bpage.CreateNewTask(taskName);
		
		
		// adding "0" to the day, if the day string contains only one character
		if (day.length() == 1) {
			day = "0" + day;
		}
		
		// cutting year string to get only two last characters
		String yearValue = year.substring(year.length() - 2);
		
		//duedate String as it should be displayed in the task grid follow the task name
		String duedate = "(" + day + "/" + monthValue + "/" + yearValue + ")";
		
		//taskduedate - due date formatted for the further comparison with the current date
		String taskduedate = day + "/" + monthValue + "/" + yearValue;
		
		//Parsing the taskduedate from the String to Date object
        DateFormat df = new SimpleDateFormat("dd/MM/yy"); 
        Date taskDate = null;

        try 
        {
           taskDate = df.parse(taskduedate);

        } catch (ParseException e) 
        {
            e.printStackTrace();
        }
        
        //Current date
        Date date = new Date();
        
		String preText = driver.findElement(By.cssSelector("pre")).getText();
		
		//Checking if the task is Overdue. If yes, than the task name should be displayed in red color
		if (taskDate.before(date)) {
			String style = driver.findElement(By.xpath("//span[.='" + taskName + " " + duedate + "']")).getAttribute("style");
//			System.out.println(style);
			String expectedStyle = "color: red;";
			assertTrue(style.equals(expectedStyle));
		} else {
		
		//If the task is NOT Overdue, regular confirming the new task name appears in the task grid
		assertTrue(preText.contains(taskName + " " + duedate));
		}
		
	}
	
	// test3 can be ran for both scenarios, "Test case 3" and "Test case 3.1"
	@Test
	public void test3() {
		
		String day = "5";
		String month = "May";
		String year = "2018";
		String category = "College";
		String taskName = "Task 3";
		bpage.Load();
		String monthValue = bpage.SelectDueDate(day, month, year);
		bpage.SelectCategory(category);
		bpage.CreateNewTask(taskName);
		String categoryColor = bpage.GetCategoryColor(category);
		
		// adding "0" to the day, if the day string contains only one character
		if (day.length() == 1) {
			day = "0" + day;
		}
		
		// cutting year string to get only two last characters
		String yearValue = year.substring(year.length() - 2);
		
		//duedate String as it should be displayed in the task grid follow the task name
		String duedate = "(" + day + "/" + monthValue + "/" + yearValue + ")";
		
		//taskduedate - due date formatted for the further comparison with the current date
		String taskduedate = day + "/" + monthValue + "/" + yearValue;
		
		//Parsing the taskduedate from the String to Date object
        DateFormat df = new SimpleDateFormat("dd/MM/yy"); 
        Date taskDate = null;

        try 
        {
           taskDate = df.parse(taskduedate);

        } catch (ParseException e) 
        {
            e.printStackTrace();
        }
        
        //Current date
        Date date = new Date();
        		
		//Checking if the task is Overdue. If yes, than the task name should be displayed in red color
		//following by the category name displayed in the category color
		if (taskDate.before(date)) {
			String style = driver.findElement(By.xpath("//span[.='" + taskName + " " + duedate + "']")).getAttribute("style");
			String expectedStyle = "color: red;";
			assertTrue(style.equals(expectedStyle));
			String catstyle = driver.findElement(By.xpath("//span[.='" + taskName + " " + duedate + "']//following-sibling::span[.='" + category + "']")).getAttribute("style");
			int index1 = catstyle.indexOf("(");
			int index2 = catstyle.indexOf(")");
			String styleColor = catstyle.substring((index1 + 1), index2);
			assertTrue(styleColor.equals(categoryColor));
		} else {
		
		//If the task is NOT Overdue,task name should be displayed in the category color
		//due date will not be displayed in the same span with task name
			String TaskNameStyle = driver.findElement(By.xpath("//span[.='" + taskName + "']")).getAttribute("style");
			int index1 = TaskNameStyle.indexOf("(");
			int index2 = TaskNameStyle.indexOf(")");
			String styleColor = TaskNameStyle.substring((index1 + 1), index2);
			assertTrue(styleColor.equals(categoryColor));
		}

	}
	
	//Mark task as completed
	@Test
	public void test4() {
		String taskName = "Task 1";
		bpage.Load();
		bpage.MarkCompleted(taskName);
		assertTrue(driver.findElements(By.xpath("//strike[.='" + taskName + "']")).size() != 0); 
	}
	
	//Remove task
	@Test
	public void test5() {
		String taskName = "Task 2";
		bpage.Load();
		bpage.RemoveTaks(taskName);
		assertTrue(driver.findElements(By.xpath("//strike[.='" + taskName + "']")).size() == 0); 
	}
	
	//Edit task
	@Test
	public void test6() {
		String taskName = "Task 6";
		String newName = "Task 6 edited";
		String newCategory = "Work";
		String newDay = "22";
		String newMonth = "Apr";
		String newYear = "2019";
		bpage.Load();	
		String monthValue = bpage.EditTask(taskName, newName, newCategory, newDay, newMonth, newYear);
		
		
		String categoryColor = bpage.GetCategoryColor(newCategory);
		
		// adding "0" to the day, if the day string contains only one character
		if (newDay.length() == 1) {
			newDay = "0" + newDay;
		}
		
		// cutting year string to get only two last characters
		String yearValue = newYear.substring(newYear.length() - 2);
		
		//duedate String as it should be displayed in the task grid follow the task name
		String duedate = "(" + newDay + "/" + monthValue + "/" + yearValue + ")";
		
		//taskduedate - due date formatted for the further comparison with the current date
		String taskduedate = newDay + "/" + monthValue + "/" + yearValue;
		
		//Parsing the taskduedate from the String to Date object
        DateFormat df = new SimpleDateFormat("dd/MM/yy"); 
        Date taskDate = null;

        try 
        {
           taskDate = df.parse(taskduedate);

        } catch (ParseException e) 
        {
            e.printStackTrace();
        }
        
        //Current date
        Date date = new Date();
        		
		//Checking if the task is Overdue. If yes, than the task name should be displayed in red color
		//following by the category name displayed in the category color
		if (taskDate.before(date)) {
			String style = driver.findElement(By.xpath("//span[.='" + newName + " " + duedate + "']")).getAttribute("style");
			String expectedStyle = "color: red;";
			assertTrue(style.equals(expectedStyle));
			String catstyle = driver.findElement(By.xpath("//span[.='" + newName + " " + duedate + "']//following-sibling::span[.='" + newCategory + "']")).getAttribute("style");
			int index1 = catstyle.indexOf("(");
			int index2 = catstyle.indexOf(")");
			String styleColor = catstyle.substring((index1 + 1), index2);
			assertTrue(styleColor.equals(categoryColor));
		} else {
		
		//If the task is NOT Overdue,task name should be displayed in the category color
		//due date will not be displayed in the same span
			String TaskNameStyle = driver.findElement(By.xpath("//span[.='" + newName + "']")).getAttribute("style");
			int index1 = TaskNameStyle.indexOf("(");
			int index2 = TaskNameStyle.indexOf(")");
			String styleColor = TaskNameStyle.substring((index1 + 1), index2);
			assertTrue(styleColor.equals(categoryColor));
		}
	}

	@Test
	public void test7() {
		String categoryName = "TestCategory";
		bpage.Load();
		bpage.CreateNewCategory(categoryName);
		//Need to implement assertion for category color, to verify it is black
		assertTrue(driver.findElements(By.xpath("//select[@name='category']/option[.='" + categoryName + "']")).size() != 0);
		assertTrue(driver.findElements(By.xpath("//a[@title='Remove this category']/span[.='" + categoryName + "']")).size() != 0);
	}
	
	@Test
	public void test8() {
		String categoryName = "TestCategory";
		String color = "Cyan";
		bpage.Load();
		String catColor = bpage.SelectCategoryColor(color);
		bpage.CreateNewCategory(categoryName);
		//Need to implement assertion for category color, to verify it is the same as catColor variable
		assertTrue(driver.findElements(By.xpath("//select[@name='category']/option[.='" + categoryName + "']")).size() != 0);
		assertTrue(driver.findElements(By.xpath("//a[@title='Remove this category']/span[.='" + categoryName + "']")).size() != 0);
	}
	
	@Test
	public void test9() {
		String categoryName = "Test 1";
		bpage.Load();
		bpage.RemoveCategory(categoryName);
		dcatpage.Nevermind();
		assertTrue(driver.findElements(By.xpath("//select[@name='category']/option[.='" + categoryName + "']")).size() == 0);
		assertTrue(driver.findElements(By.xpath("//a[@title='Remove this category']/span[.='" + categoryName + "']")).size() == 0);
	}
	
	@Test
	public void test10() {
		String categoryName = "Test 1";
		bpage.Load();
		bpage.RemoveCategory(categoryName);
		dcatpage.Yes();
		assertTrue(driver.findElements(By.xpath("//select[@name='category']/option[.='" + categoryName + "']")).size() != 0);
		assertTrue(driver.findElements(By.xpath("//a[@title='Remove this category']/span[.='" + categoryName + "']")).size() != 0);
	}
	
	@Test
	public void test11() {
		bpage.Load();
		bpage.ToggleAll();
		List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
		for(WebElement el : checkboxes) {
			Boolean isChecked;
			isChecked = el.isSelected();
			assertTrue(isChecked == true);
		}
	}
	
	//Test case confirms advanced options/fields are hidden when "Advanced" option selected
	@Test
	public void test12() {
		bpage.Load();
		bpage.Advanced();
		String style = driver.findElement(By.id("extra")).getAttribute("style");
		String hidden = "visibility: hidden";
		assertTrue(style.contains(hidden));
	}
	
	//Test case confirms advanced options/fields are visible when "Advanced" option selected is selected again
	@Test
	public void test13() {
		bpage.Load();
		bpage.Advanced();
		String style = driver.findElement(By.id("extra")).getAttribute("style");
		String hidden = "visibility: hidden";
		assertTrue(style.contains(hidden));
		bpage.Advanced();
		String style2 = driver.findElement(By.id("extra")).getAttribute("style");
		String visible = "visibility: visible";
		assertTrue(style2.contains(visible));
	}
}
