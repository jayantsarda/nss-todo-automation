
/* JSON field value serialized */

package pojos;

import com.google.gson.annotations.SerializedName;

public class ToDo {

	@SerializedName("id")
	private String id;
	@SerializedName("status")
	private String status;
	@SerializedName("task name")
	private String taskname;
	@SerializedName("category")
	private String category;
	@SerializedName("due date")
	private String duedate;

	public ToDo(String id, String status, String taskname, String category, String duedate) {
		this.id = id;
		this.status = status;
		this.taskname = taskname;
		this.category = category;
		this.duedate = duedate;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDate() {
		return duedate;
	}

	public void setDate(String duedate) {
		this.duedate = duedate;
	}

}
