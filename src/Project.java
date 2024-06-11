import java.util.Date;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class Project {
	//Class attribute
	private String ID;
	private String name;
	private String description;
	private String category;
	private Date startDate;
	private Date endDate;
	private ArrayList<Task> listTasks;

	//Default constructor
	public Project () {
		this.ID = "";
		this.name = "";
		this.description = "";
		this.category = "";
		this.startDate = new Date();
		this.endDate = new Date();
		this.listTasks = new ArrayList<>();
	}
	
	//Parametric constructor
	public Project (String ID, String name, String description, String category, Date startDate, Date endDate, ArrayList<Task> listTasks) {
		this.ID = ID;
		this.name = name;
		this.description = description;
		this.category = category;
		this.startDate = new Date(startDate.getTime());
		this.endDate = new Date(endDate.getTime());
	}

	/*Getter methods*/
	public String getID () {
		return this.ID;
	}

	public String getName () {
		return this.name;
	}

	public String getDescription () {
		return this.description;
	}

	public String getCategory () {
		return this.category;
	}

	public Date getStartDate () {
		return this.startDate;
	}

	public Date getEndDate () {
		return this.endDate;
	}

	public ArrayList<Task> getListTasks () {
		return this.listTasks;
	}

	/*Setter methods*/
	public void setID (String ID) {
		this.ID = ID;
	}

	public void setName (String name) {
		this.name = name;
	}

	public void setDescription (String description) {
		this.description = description;
	}

	public void setCategory (String category) {
		this.category = category;
	}

	public void setStartDate (Date startDate) {
		this.startDate = new Date(startDate.getTime());
	}

	public void setEndDate (Date endDate) {
		this.endDate = new Date(endDate.getTime());
	}

	public void setListTasks (ArrayList<Task> listTasks) {
		this.listTasks = new ArrayList<>(listTasks);
	}

	/*Private methods (helper methods)*/
	private String repeat (String s, int times) {
		String str = "";
		while (times > 0) {
			str = str.concat(s);
			times--;
		}
		return str;
	}

	/*Public methods*/
	//parse date to string method
	public String parseDate (Date date) throws IllegalArgumentException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(date);
	}

	//Add task to list of tasks
	public void addTask (Task task) {
		this.listTasks.add(task);
	}

	//Add budget task to list of tasks
	public void addTask (BudgetTask task) {
		this.listTasks.add(task);
	}

	//update task
	public void updateTask (String ID, Task newTask) {
		int index = 0;
		for (Task task : listTasks) {
			if (task.getID().equals(ID)) {
				listTasks.set(index, newTask);
				break;
			} else {
				index++;
			}
		}
	}

	//delete task
	public void deleteTask (String ID) {
		int index = 0;
		for (Task task : listTasks) {
			if (task.getID().equals(ID)) {
				this.listTasks.remove(index);
				return;
			}
			index++;
		}
	}
	
	//Calculate project's progress
	public double calculateProgress () {
		double count = 0;
		for (Task task : listTasks) {
			if (task.getIsComplete()) {
				count++;
			}
		}
		return (count * 100) / listTasks.size();
	}

	//Display project
	public void displayProject (int index) {
		System.out.println(repeat("-", 20));
		System.out.printf("Project %d: %.2f%%\n", index, calculateProgress());
		System.out.printf(">>> ID: %s\n", ID);
		System.out.printf(">>> Name: %s\n", name);
		System.out.printf(">>> Description: %s\n", description);
		System.out.printf(">>> Category: %s\n", category);
		System.out.printf(">>> Start date: %s\n", parseDate(startDate));
		System.out.printf(">>> End date: %s\n", parseDate(endDate));
		System.out.printf("+%s+%s+%s+%s+%s+%s+%s+\n", repeat("-", 7), repeat("-", 32), repeat("-", 52), repeat("-", 12), repeat("-", 12), repeat("-", 12), repeat("-", 12));
		System.out.printf("| %-5s | %-30s | %-50s | %10s | %10s | %10s | %10s |\n", "ID", "Name", "Description", "Start date", "End date", "Complete?", "Budget");
		System.out.printf("+%s+%s+%s+%s+%s+%s+%s+\n", repeat("-", 7), repeat("-", 32), repeat("-", 52), repeat("-", 12), repeat("-", 12), repeat("-", 12), repeat("-", 12));
		for (Task task : listTasks) {
			task.displayTask();
        }
	}
}


