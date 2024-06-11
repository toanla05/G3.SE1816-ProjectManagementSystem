import java.io.FileNotFoundException;
import java.text.ParseException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.text.SimpleDateFormat;
import java.io.FileWriter;

public class ProjectManagement {
	// Class attribute
	private final ArrayList<Project> listProjects;
	private Scanner sc = new Scanner(System.in);
	private final User user;

	// Constructor
	public ProjectManagement(User user) {
		this.listProjects = new ArrayList<>();
		this.user = user;
	}

	/* Private methods (Helper methods) start */

	// method for repeating a string (jdk 1.8 does not support repeat() method :) )
	private String repeat(String s, int times) {
		String str = "";
		while (times > 0) {
			str = str.concat(s);
			times--;
		}
		return str;
	}

	// method for clearing terminal (may not works for all IDE's terminals)
	private void clear() {
		String os = System.getProperty("os.name");
		if (os.contains("windows")) {
			try {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} catch (InterruptedException | IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			try {
				new ProcessBuilder("clear").inheritIO().start().waitFor();
			} catch (InterruptedException | IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	// methods to check if the date string is correctly formatted and logically
	// correct
	private Date parseToDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		formatter.setLenient(false);
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	// methods for searching a project by ID
	private boolean searchProjectByID(String ID) {
		File file = new File("../data/projects/projects.txt");
		try {
			Scanner sc = new Scanner(file);
			String projectStr;
			String[] datas;
			while (sc.hasNextLine()) {
				projectStr = sc.nextLine();
				datas = projectStr.split("\\|");
				if (datas[0].equals(ID)) {
					return true;
				}
			}
			return false;
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	// method for searching a task by ID
	private boolean searchTaskById(String ID) {
		File file = new File("../data/tasks/tasks.txt");
		try {
			Scanner sc = new Scanner(file);
			String taskStr;
			String[] datas;
			while (sc.hasNextLine()) {
				taskStr = sc.nextLine();
				datas = taskStr.split("\\|");
				if (datas[0].equals(ID)) {
					return true;
				}
			}
			return false;
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	// method for writing project to file
	private void writeProject(Project project) {
		try {
			FileWriter writer = new FileWriter("../data/projects/projects.txt", true);

			// Create string result to write to file
			String result = project.getID() + "|" + project.getName() + "|" + project.getDescription() + "|"
					+ project.getCategory() + "|" +
					project.parseDate(project.getStartDate()) + "|" + project.parseDate(project.getEndDate()) + "|";
			for (Task task : project.getListTasks()) {
				result = result.concat(task.getID() + "|");
			}

			// write to file
			result = result.concat("\n");
			writer.write(result);
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// method for writing task to file
	private void writeTask(Task task) {
		try {
			FileWriter writer = new FileWriter("../data/tasks/tasks.txt", true);

			// Create string result to write to file
			String result = task.getID() + "|" + task.getName() + "|" + task.getDescription() + "|"
					+ task.parseDate(task.getStartDate()) + "|" +
					task.parseDate(task.getEndDate()) + "|" + task.getIsComplete() + "|";
			if (task instanceof BudgetTask) {
				result = result.concat(Double.toString(((BudgetTask) task).getMoney()));
			}

			// write to file
			result = result.concat("\n");
			writer.write(result);
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/* Private methods (Helper methods) end */

	/* Public methods start */
	public void createProject() {
		// print header
		System.out.println("\t\t\t\t----- PROJECT MANAGEMENT SYSTEM -----");
		System.out.println("\t\t\t\t\t## ADD PROJECT ##");
		System.out.println("\t\t\t\t\t" + repeat("-", 20));

		/* Ask for project's information */
		Project project = new Project();

		// Ask for project's ID
		System.out.print("Please enter project's ID (must be 5 characters): ");
		String ID = sc.next();
		// Handle the case if ID is not exactly 5 characters
		while (ID.length() != 5) {
			System.out.println("The project's ID must be 5 characters in length!!!");
			System.out.print("Please enter project's ID (must be 5 characters): ");
			ID = sc.next();
		}
		// Handle the case where ID already exist
		while (searchProjectByID(ID)) {
			System.out.println("This project's ID is already exist!!! Please try again");
			System.out.print("Please enter project's ID: ");
			ID = sc.next();
		}
		project.setID(ID);

		// Ask for project's name
		sc.nextLine();
		System.out.print("Please enter project's name: ");
		String name = sc.nextLine();
		// Handle error if user enter an empty string (or string that only contains
		// space)
		while (name.trim().isEmpty()) {
			System.out.println("Invalid input!!! Name cannot be an empty/all space string!!!");
			System.out.print("Please enter project's name");
			name = sc.nextLine();
		}
		project.setName(name);

		// Ask for project's description
		System.out.print("Please enter project's description: ");
		String description = sc.nextLine();
		// Handle error if user enter an empty string (or string that only contains
		// space)
		while (name.trim().isEmpty()) {
			System.out.println("Invalid input!!! Description cannot be an empty/all space string!!!");
			System.out.print("Please enter project's description: ");
			description = sc.nextLine();
		}
		project.setDescription(description);

		// Ask for project's category
		System.out.print("Please enter project's category: ");
		String category = sc.nextLine();
		// Handle error if user enter an empty string (or string that only contains
		// space)
		while (name.trim().isEmpty()) {
			System.out.println("Invalid input!!! Category cannot be an empty/all space string!!!");
			System.out.print("Please enter project's category: ");
			category = sc.nextLine();
		}
		project.setCategory(category);

		// Get start and end date (including handling exception)
		String startDate, endDate;
		do {
			// Ask for project's start date
			System.out.print("Please enter project's start date (dd/mm/yyyy): ");
			startDate = sc.next();
			// Check if date is correctly formatted and logically correct
			while (parseToDate(startDate) == null) {
				System.out.println("The date is incorrectly formatted or logically incorrect!!!");
				System.out.print("Please enter project's start date (dd/mm/yyyy): ");
				startDate = sc.next();
			}

			// Ask for project's end date
			System.out.print("Please enter project's end date (dd/mm/yyyy): ");
			endDate = sc.next();
			// Check if date is correctly formatted and logically correct
			while (parseToDate(endDate) == null) {
				System.out.println("The date is incorrectly formatted or logically incorrect!!!");
				System.out.print("Please enter project's end date (dd/mm/yyyy): ");
				endDate = sc.next();
			}

			// If start date later than end date, print error message
			if (!parseToDate(startDate).before(parseToDate(endDate))) {
				System.out.println("The start date cannot be later than end date!!!");
			}
		} while (!parseToDate(startDate).before(parseToDate(endDate)));

		project.setStartDate(parseToDate(startDate));
		project.setEndDate(parseToDate(endDate));

		// Ask for project's tasks
		System.out.print("Please type in the number of tasks for this project: ");
		int numberOfTask = sc.nextInt();
		// handle error if number of tasks is negative integer
		while (numberOfTask < 0) {
			System.out.println("The number of tasks cannot be a negative integer!!!");
			System.out.print("Please type in the number of tasks for this project: ");
			numberOfTask = sc.nextInt();
		}

		// Ask for each task's information
		for (int i = 1; i <= numberOfTask; i++) {
			// print header
			System.out.println("-".repeat(10));
			System.out.println("Task " + i);

			// Ask for task's ID
			System.out.print("Please enter task's ID (strictly 5 characters): ");
			String taskID = sc.next();
			// Check for ID length
			while (taskID.length() != 5) {
				System.out.println("The task's ID must strictly be 5 characters in length!!!");
				System.out.print("Please enter task's ID (strictly 5 characters): ");
				taskID = sc.next();
			}
			// Check if task's ID exist or not
			while (searchTaskById(taskID)) {
				System.out.println("This task's ID already exist!!!");
				System.out.print("Please enter task's ID (strictly 5 characters): ");
				taskID = sc.next();
			}

			// Ask for task's name
			sc.nextLine();
			System.out.print("Please enter task's name: ");
			String taskName = sc.nextLine();
			// Check if task's name is an empty string (or string that contains only space)
			while (taskName.trim().isEmpty()) {
				System.out.println("The task's name cannot be empty!!!");
				System.out.print("Please enter task's name: ");
				taskName = sc.nextLine();
			}
			// Check if task's name exceeds 30 characters
			while (taskName.length() > 30) {
				System.out.println("We only accept task's name up to 30 characters!!!");
				System.out.print("Please enter task's name: ");
				taskName = sc.nextLine();
			}

			// Ask for task's description
			System.out.print("Please enter task's description: ");
			String taskDescription = sc.nextLine();
			// Check if task's description is an empty string (or string that contains only
			// space)
			while (taskDescription.trim().isEmpty()) {
				System.out.println("The task's description cannot be empty!!!");
				System.out.print("Please enter task's description: ");
				taskDescription = sc.nextLine();
			}
			// Check if task's description exceeds 50 characters
			while (taskDescription.length() > 50) {
				System.out.println("We only accept task's description up to 50 characters");
				System.out.print("Please enter task's description: ");
				taskDescription = sc.nextLine();
			}

			// Get task's start and end date (include handling exception)
			String taskStartDate, taskEndDate;
			do {
				do {
					// Ask for task's start date
					System.out.print("Please enter task's start date (dd/mm/yyyy): ");
					taskStartDate = sc.next();
					while (parseToDate(taskStartDate) == null) {
						System.out
								.println("The task's start date is not formatted correctly or logically incorrect!!!");
						System.out.print("Please enter task's start date (dd/mm/yyyy): ");
						taskStartDate = sc.next();
					}

					// Ask for task's end date
					System.out.print("Please enter task's end date (dd/mm/yyyy): ");
					taskEndDate = sc.next();
					while (parseToDate(taskEndDate) == null) {
						System.out.println("The task's end date is not formatted correctly or logically incorrect!!!");
						System.out.print("Please enter task's end date (dd/mm/yyyy): ");
						taskEndDate = sc.next();
					}

					// If start date later than end date, print error message
					if (!parseToDate(taskStartDate).before(parseToDate(taskEndDate))) {
						System.out.println("The task's start date cannot be later than task's end date!!!");
					}
				} while (!parseToDate(taskStartDate).before(parseToDate(taskEndDate)));

				// If the task's duration is outside of project's duration
				if (parseToDate(taskStartDate).before(parseToDate(startDate))
						|| parseToDate(taskEndDate).after(parseToDate(endDate))) {
					System.out.println("The task's duration must be between in project's duration");
				}
			} while (parseToDate(taskStartDate).before(parseToDate(startDate))
					|| parseToDate(taskEndDate).after(parseToDate(endDate)));

			// Task isComplete is false by default, so we don't ask for task's status here

			// Ask whether this task is a budget or non-budget
			System.out.print("Is this task has a budget or not (Y/N): ");
			String option = sc.next();
			// Handle exception
			while (!option.equals("Y") && !option.equals("N")) {
				System.out.println("Invalid value, we only accept Y or N!!!");
				System.out.print("Is this task has a budget or not (Y/N): ");
				option = sc.next();
			}
			// Handle each case
			if (option.equals("N")) {
				// create normal task
				Task task = new Task(taskID, taskName, taskDescription, parseToDate(taskStartDate), parseToDate(taskEndDate), false);
				// add task into project's listTasks
				project.addTask(task);
				// write to task file
				writeTask(task);
			} else {
				// Ask for task's budget
				System.out.print("Please enter task's budget: ");
				double cost = sc.nextDouble();
				// Check if cost is valid
				while (cost <= 0) {
					System.out.println("The budget of this task must be positive!!!");
					System.out.print("Please enter task's budget: ");
					cost = sc.nextDouble();
				}
				// Create budget task
				BudgetTask budgetTask = new BudgetTask(taskID, taskName, taskDescription, parseToDate(taskStartDate), parseToDate(taskEndDate), false, cost);
				// add task to project's listTasks
				project.addTask(budgetTask);
				// write to task file
				writeTask(budgetTask);
			}
		}

		// Add project into listProjects
		listProjects.add(project);

		// Write to file database
		writeProject(project);

		// Print message to user
		System.out.println(">>> Project has created and added to database successfully!");
	}

	/* Public methods end */
}
