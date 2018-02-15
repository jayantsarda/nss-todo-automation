package API_Test;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert.*;
import pojos.ToDo;
import pojos.ToDoList;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestAPi {
	URI uri = null;
	ToDo[] toDo = null;
	List<ToDo> toDoList = null;
	Response response = null;

	@Before
	public void setUp() throws URISyntaxException {
		uri = new URI("http://localhost:80/fake-api-call.php");
	}

	@Test
	public void test() throws URISyntaxException, IOException {

		response = given().accept(ContentType.JSON).when().get(uri).then().assertThat().statusCode(200).extract()
				.response();

		String body = response.asString();
		Gson gson = new GsonBuilder().create();

		toDo = gson.fromJson(body, ToDo[].class);

		toDoList = new ArrayList<ToDo>(Arrays.asList(toDo));
		
		// Find how many tasks do not have "category" assigned
		
		int counter = 0;
		for (int i = 0; i < toDoList.size(); i++) {
			if (toDoList.get(i).getCategory().isEmpty()) {
				counter++;
			}
		}
		System.out.println("Number of tasks that do not have any category assigned is " + counter);
		
		// Aggregate and print only "task names"
		System.out.println("Printing only task names");
		for (int i = 0; i < toDoList.size(); i++) {
			System.out.print(toDoList.get(i).getTaskname() + ", ");
		}
		//Read API response and print tasks in descending due date order
		System.out.println("Read API response and print tasks in descending due date order");

		for (int i = 0; i < toDoList.size(); i++) {
			System.out.println("Date is " + toDoList.get(i).getDate() + " task is " + toDoList.get(i).getTaskname());
		}
	}
}