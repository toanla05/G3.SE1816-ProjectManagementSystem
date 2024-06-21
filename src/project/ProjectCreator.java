package project;

//import Java standard library
import java.util.Scanner;
import java.util.ArrayList;

//import user's custom package
import utility.Utility;
import user.User;

public class ProjectCreator {
    //Class attribute
    private final ArrayList<Project> listProjects;
    private final User user;
    private final Scanner sc = new Scanner(System.in);

    //Constructor
    public ProjectCreator(ArrayList<Project> listProjects, User user) {
        this.listProjects = listProjects;
        this.user = user;
    }

    /*Helper methods start*/

    //methods for searching a project by ID
    private boolean searchProjectByID(String ID) {
        for (Project project : listProjects) {
            if (project.getID().equals(ID))
                return true;
        }
        return false;
    }

    //method for searching a task by ID
    private boolean searchTaskById(String ID) {
        for (Project project : listProjects) {
            for (Task task : project.getListTasks()) {
                if (task.getID().equals(ID)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*Helper methods end*/

    /*Public methods start*/

    public void addNewTask(Project project) {
        boolean isValid = false;
        //Ask for project's tasks
        int numberOfTasks;
        do {
            System.out.print("Please type in the number of tasks for this project: ");
            numberOfTasks = sc.nextInt();

            //Print error message if user enter a non-positive integer
            if (numberOfTasks <= 0) {
                System.out.println("The number of tasks cannot be a negative integer!!!");
            }
        } while (numberOfTasks <= 0);

        //Ask for each task's information
        for (int i = 1; i <= numberOfTasks; i++) {
            //Print header
            System.out.println(Utility.repeat("-", 10));
            System.out.println("Task " + i);

            //Ask user whether this task is a budget or non-budget task
            String option;
            do {
                System.out.print("Is this task a budget task or non-budget task (Y/N): ");
                option = sc.next();

                //Handle if user enter invalid option
                if (!option.equalsIgnoreCase("Y") && !option.equalsIgnoreCase("N")) {
                    System.out.println("Invalid option!! We only accept 'Y/N' or 'y/n' as valid options");
                    isValid = false;
                    continue;
                }

                isValid = true;
            } while (!isValid);

            //Create budget task or non-budget task based on user's option
            Task task = null;
            if (option.equalsIgnoreCase("Y")) {
                task = new BudgetTask();
            } else if (option.equalsIgnoreCase("N")) {
                task = new Task();
            }

            //Ask for task's ID
            do {
                try {
                    System.out.print("Please enter task's ID: ");
                    task.setID(sc.next());
                } catch (IllegalArgumentException e) {
                    System.out.println("The task's ID must start with 'NT' or 'BT' and follows by 3 digits");
                    isValid = false;
                    continue;
                }

                if (searchTaskById(task.getID())) {
                    System.out.println("This task's ID is already exist!!! Please try again");
                    isValid = false;
                    continue;
                }

                isValid = true;
            } while (!isValid);

            //Ask for task's name
            sc.nextLine(); //Swallow the '\n' leaves by sc.next() in ID
            do {
                try {
                    System.out.print("Please enter task's name: ");
                    task.setName(sc.nextLine());
                    isValid = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("The task's name cannot be an empty string or exceeding 30 characters!!!");
                    isValid = false;
                }
            } while (!isValid);

            //Ask for task's description
            do {
                try {
                    System.out.print("Please enter task's description: ");
                    task.setDescription(sc.nextLine());
                    isValid = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("The task's description cannot be an empty string or exceeding 50 characters!!!");
                    isValid = false;
                }
            } while (!isValid);

            //Ask for task's start and end date (include handling exception)
            String taskStartDate, taskEndDate;
            do {
                //Get task's start date
                do {
                    System.out.print("Please enter task's start date (dd/mm/yyyy): ");
                    taskStartDate = sc.next();

                    if (Utility.parseToDate(taskStartDate) == null) {
                        System.out.println("The task's start date is incorrectly formatted or logically incorrect!!!");
                    }
                } while (Utility.parseToDate(taskStartDate) == null);

                //Ask for task's end date
                do {
                    System.out.print("Please enter task's end date (dd/mm/yyyy): ");
                    taskEndDate = sc.next();
                    if (Utility.parseToDate(taskEndDate) == null) {
                        System.out.println("The task's end date is incorrectly formatted or logically incorrect!!!");
                    }
                } while (Utility.parseToDate(taskEndDate) == null);

                //Check if task's start date is before task's end date
                isValid = Utility.parseToDate(taskStartDate).before(Utility.parseToDate(taskEndDate));

                //Print error message to user if task's start date is later than task's end date
                if (!isValid) {
                    System.out.println("The task's start date cannot be later than task's end date!!!");
                    continue;
                }

                //Check if task's start and end date is between project's start and end date
                isValid = Utility.parseToDate(taskStartDate).after(project.getStartDate()) || Utility.parseToDate(taskEndDate).before(project.getEndDate());

                //Print error message to user if task's start and end date is not between project's start and end date
                if (!isValid) {
                    System.out.println("The task's start and end date must be between in project's start and end date!!!");
                }
            } while (!isValid);

            task.setStartDate(Utility.parseToDate(taskStartDate));
            task.setEndDate(Utility.parseToDate(taskEndDate));

            //Task isComplete is false by default, so we don't ask for task's status here

            //Ask for task's cost (if this is a budget task)
            if (task instanceof BudgetTask) {
                do {
                    try {
                        System.out.print("Please enter budget task's cost: ");
                        ((BudgetTask) task).setMoney(sc.nextDouble());
                        isValid = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("The task's cost cannot be a non-positive number!!!");
                        isValid = false;
                    }
                } while (!isValid);
            }

            project.addTask(task);
            Utility.writeFile(task, true, "data/tasks.txt");
        }
    }

    public void addProject() {
        //print header
        System.out.println("\t\t\t\t----- PROJECT MANAGEMENT SYSTEM -----");
        System.out.println("\t\t\t\t\t## ADD PROJECT ##");
        System.out.println("\t\t\t\t\t" + Utility.repeat("-", 20));

        /*Ask for project's information*/

        Project project = new Project();
        boolean isValid = false;

        //Set project's owner to the current user
        project.setOwner(user.getUserName());

        //Ask for project's ID
        do {
            try {
                System.out.print("Please enter project's ID (must be 5 characters): ");
                project.setID(sc.next());
            } catch (IllegalArgumentException e) {
                System.out.println("The project's ID must start with 'P' and follows by 4 digits");
                continue;
            }

            if (searchProjectByID(project.getID())) {
                System.out.println("This project's ID is already exist!!! Please try again");
                continue;
            }

            isValid = true;
        } while (!isValid);

        //Ask for project's name
        sc.nextLine(); //Swallow the '\n' leaves by sc.next() in ID
        do {
            try {
                System.out.print("Please enter project's name: ");
                project.setName(sc.nextLine());
                isValid = true;
            } catch (IllegalArgumentException e) {
                System.out.println("The project's name cannot be empty!!!");
                isValid = false;
            }
        } while (!isValid);

        //Ask for project's description
        do {
            try {
                System.out.print("Please enter project's description: ");
                project.setDescription(sc.nextLine());
                isValid = true;
            } catch (IllegalArgumentException e) {
                System.out.println("The project's description cannot be empty");
                isValid = false;
            }
        } while (!isValid);

        //Ask for project's category
        do {
            try {
                System.out.print("Please enter project's category: ");
                project.setCategory(sc.nextLine());
                isValid = true;
            } catch (IllegalArgumentException e) {
                System.out.println("The project's category cannot be empty");
                isValid = false;
            }
        } while (!isValid);

        //Ask for project's start and end dates (including exception handling)
        String startDate, endDate;
        do {
            //Get project's start date
            do {
                System.out.print("Please enter project's start date (dd/mm/yyyy): ");
                startDate = sc.next();

                if (Utility.parseToDate(startDate) == null) {
                    System.out.println("The start date is incorrectly formatted or logically incorrect!!!");
                }
            } while (Utility.parseToDate(startDate) == null);

            //Get project's end date
            do {
                System.out.print("Please enter project's end date (dd/mm/yyyy): ");
                endDate = sc.next();
            } while (Utility.parseToDate(endDate) == null);

            //Check if start date is sooner than end date or not
            isValid = Utility.parseToDate(startDate).before(Utility.parseToDate(endDate));

            //Print error message to user
            if (!isValid) {
                System.out.println("The start date cannot be later than end date!!!");
            }
        } while (!isValid);

        project.setStartDate(Utility.parseToDate(startDate));
        project.setEndDate(Utility.parseToDate(endDate));

        //Get project's tasks
        addNewTask(project);

        /*Update data to database*/
        listProjects.add(project);
        Utility.writeFile(project, true, "data/projects.txt");

        //Print message to user
        System.out.println(">>> Project has created and added to database successfully!");

        /*Ask whether user want to continue to add project or not*/
        String option;
        do {
            System.out.print("Do you want to continue creating project (Y/N): ");
            option = sc.next();

            if (!option.equalsIgnoreCase("Y") && !option.equalsIgnoreCase("N")) {
                System.out.println("Invalid option!! We only accept 'Y/N' or 'y/n' as valid option");
                isValid = false;
                continue;
            }

            isValid = true;
        } while (!isValid);

        //Handle each case
        if (option.equalsIgnoreCase("Y")) {
            Utility.clearConsole();
            addProject();
        } else if (option.equalsIgnoreCase("N")) {
            return;
        }
    }

    /*Public methods end*/
}