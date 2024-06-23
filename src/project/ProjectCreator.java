package project;

//import Java standard library
import java.util.Scanner;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

//import user's custom package
import utility.Utility;
import user.User;
import menu.Menu;

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
    private boolean searchProjectByName(String name) {
        for (Project project : listProjects) {
            if (project.getName().equals(name))
                return true;
        }
        return false;
    }

    private boolean searchTaskByName(String name) {
        for (Project project : listProjects) {
            for (Task task : project.getListTasks()) {
                if (task.getName().equals(name)) {
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

        //Ask for number of tasks
        int numberOfTasks = 0;
        do {
            System.out.print("Enter number of tasks: ");
            try {
                numberOfTasks = Integer.parseInt(sc.nextLine());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid option!");
                isValid = false;
                continue;
            }

            //Check if number of tasks is valid
            isValid = numberOfTasks > 0;
            if (!isValid) {
                System.out.println("The number of tasks cannot be a negative integer!!!");
            }
        } while (!isValid);

        //Ask for each task's information
        for (int i = 1; i <= numberOfTasks; i++) {
            //Print header
            System.out.println(Utility.repeat("-", 10));
            System.out.println("Task " + i);

            //Ask user whether this task is a budget or non-budget task
            String option;
            do {
                System.out.print("Is this task a budget task or non-budget task (Y/N): ");
                option = sc.nextLine().trim();

                //Check if option is valid
                isValid = option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N");
                if (!isValid) {
                    System.out.println("Invalid option!!");
                }
            } while (!isValid);

            //Create budget task or non-budget task based on user's option
            Task task = null;
            if (option.equalsIgnoreCase("Y")) {
                task = new BudgetTask();
            } else if (option.equalsIgnoreCase("N")) {
                task = new Task();
            }

            //Ask for task's name
            do {
                try {
                    System.out.print("Please enter task's name: ");
                    task.setName(sc.nextLine());
                    isValid = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Task's name must be non-empty, not containing '|' and 30 length at most!");
                    isValid = false;
                    continue;
                }

                //Check if task's name already exist
                isValid = !searchTaskByName(task.getName());
                if (!isValid) {
                    System.out.println("Task's name already exist!");
                }
            } while (!isValid);

            //Ask for task's description
            do {
                try {
                    System.out.print("Enter task's description: ");
                    task.setDescription(sc.nextLine());
                    isValid = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Task's description must be non-empty, not containing '|' and 50 length at most!");
                    isValid = false;
                }
            } while (!isValid);

            //Ask for task's start and end date (include handling exception)
            Date startDate = null, endDate = null;
            do {
                //Get task's start date
                do {
                    System.out.print("Enter task's start date (dd/mm/yyyy): ");
                    try {
                        startDate = Utility.parseToDate(sc.nextLine().trim());
                        isValid = true;
                    } catch (ParseException e) {
                        System.out.println("Task's start date is incorrectly formatted or logically incorrect!!!");
                        isValid = false;
                    }
                } while (!isValid);

                //Ask for task's end date
                do {
                    System.out.print("Enter task's end date (dd/mm/yyyy): ");
                    try {
                        endDate = Utility.parseToDate(sc.nextLine().trim());
                        isValid = true;
                    } catch (ParseException e) {
                        System.out.println("Task's end date is incorrectly formatted or logically incorrect!!!");
                        isValid = false;
                    }
                } while (!isValid);

                //Check if task's start date is before task's end date
                isValid = startDate.before(endDate);

                //Print error message to user if task's start date is later than task's end date
                if (!isValid) {
                    System.out.println("Task's start date cannot be later than task's end date!!!");
                    continue;
                }

                //Check if task's start and end date is between project's start and end date
                isValid = (startDate.after(project.getStartDate())  || startDate.equals(project.getStartDate())) &&
                          (endDate.before(project.getEndDate())     || endDate.equals(project.getEndDate()));

                //Print error message to user if task's start and end date is not between project's start and end date
                if (!isValid) {
                    System.out.println("Task's start and end date must be between in project's start and end date!");
                    continue;
                }

                task.setStartDate(startDate);
                task.setEndDate(endDate);
            } while (!isValid);

            //Task isComplete is false by default, so we don't ask for task's status here

            //Ask for task's cost (if this is a budget task)
            if (task instanceof BudgetTask) {
                do {
                    try {
                        System.out.print("Enter budget task's cost: ");
                        ((BudgetTask) task).setMoney(Double.parseDouble(sc.nextLine().trim()));
                        isValid = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Task's cost cannot be a non-positive number!!!");
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
        Menu addProjectMenu = new Menu();
        addProjectMenu.display("ADD PROJECT", user.getUserName());

        /*Ask for project's information*/

        Project project = new Project();
        boolean isValid = false;

        //Set project's owner to the current user
        project.setOwner(user.getUserName());

        //Ask for project's name
        do {
            try {
                System.out.print("Enter project's name: ");
                project.setName(sc.nextLine());
                isValid = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Project's name must be non-empty and not containing '|'!");
                isValid = false;
                continue;
            }

            //Check if project already exist
            isValid = !searchProjectByName(project.getName());
            if (!isValid) {
                System.out.println("Project's name already exist!");
            }
        } while (!isValid);

        //Ask for project's description
        do {
            try {
                System.out.print("Enter project's description: ");
                project.setDescription(sc.nextLine());
                isValid = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Project's description must be non-empty and not containing '|'!");
                isValid = false;
            }
        } while (!isValid);

        //Ask for project's category
        do {
            try {
                System.out.print("Enter project's category: ");
                project.setCategory(sc.nextLine());
                isValid = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Project's category must be non-empty and not containing '|'");
                isValid = false;
            }
        } while (!isValid);

        //Ask for project's start and end dates (including exception handling)
        Date startDate = null, endDate = null;
        do {
            //Get project's start date
            do {
                System.out.print("Enter project's start date (dd/mm/yyyy): ");
                try {
                    startDate = Utility.parseToDate(sc.nextLine().trim());
                    isValid = true;
                } catch (ParseException e) {
                    System.out.println("Project's start date is invalid!");
                    isValid = false;
                }
            } while (!isValid);

            //Get project's end date
            do {
                System.out.print("Enter project's end date (dd/mm/yyyy): ");
                try {
                    endDate = Utility.parseToDate(sc.nextLine().trim());
                    isValid = true;
                } catch (ParseException e) {
                    System.out.println("Project's end date is invalid!");
                    isValid = false;
                }
            } while (!isValid);

            //Check if start date is sooner than end date or not
            isValid = startDate.before(endDate);
            if (!isValid) {
                System.out.println("The start date cannot be later than end date!!!");
                continue;
            }

            project.setStartDate(startDate);
            project.setEndDate(endDate);
        } while (!isValid);

        //Get project's tasks
        addNewTask(project);

        /*Update data to database*/
        listProjects.add(project);
        Utility.writeFile(project, true, "data/projects.txt");

        //Print message to user
        System.out.println("=> Project created and added to database successfully!");

        /*Ask whether user want to continue to add project or not*/
        String option;
        do {
            System.out.print("Do you want to continue creating project (Y/N): ");
            option = sc.nextLine().trim();

            isValid = option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N");
            if (!isValid) {
                System.out.println("Invalid option!! We only accept 'Y/N' or 'y/n' as valid option");
            }
        } while (!isValid);

        //Handle each case
        if (option.equalsIgnoreCase("Y")) {
            addProject();
        } else if (option.equalsIgnoreCase("N")) {
            return;
        }
    }
    /*Public methods end*/
}