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

public class ProjectEditing {

    //Class attribute
    private final ArrayList<Project> listProjects;
    private final User user;
    private final Scanner sc = new Scanner(System.in);

    //Constructor
    public ProjectEditing(ArrayList<Project> listProjects, User user) {
        this.listProjects = listProjects;
        this.user = user;
    }

    public void updateProject() {
        //print header
        Menu updateMenu = new Menu();
        updateMenu.display("UPDATE PROJECT", user.getUserName());

        //add function into MENU
        updateMenu.addOption("Check project as complete");
        updateMenu.addOption("Check task as complete");
        updateMenu.addOption("Update project information");

        //Find project need to be update
        boolean isValid;
        String deleteProject = "";
        do {

            try {
                isValid = true;
                //ask for Project's name
                System.out.print("Enter project's name would be update: ");
                deleteProject = sc.nextLine();
            } catch (Exception e) {
                isValid = false;
                System.out.println("Project's name must be non-empty and not containing '|'!");
            }
        } while (!isValid);

        //check if there is projects in list
        boolean isExist = false;
        int index = 0;
        for (Project project : listProjects) {
            if (project.getName().equalsIgnoreCase(deleteProject)) {
                isExist = true;
                break;
            }
            index++;
        }
        //return if project does not exist
        if (!isExist) {
            System.out.println("Project does not exit");
            System.out.print("Press ENTER to continue...");
            sc.nextLine();
        } else {
            //show project to user
            listProjects.get(index).displayProject(index);

            boolean statusLoop;
            int option;
            do {
                statusLoop = false;
                //ask user option
                System.out.println("What you want to do:");
                //show MENU option
                updateMenu.display();
                System.out.print("Please Enter your option: ");
                //get user option
                option = Integer.parseInt(sc.nextLine());
                //check valid option
                if (option > 3 || option < 1) {
                    statusLoop = true;
                    continue;
                }
            } while (statusLoop);
            //do user's option
            switch (option) {
                case 1:
                    listProjects.get(index).checkProject();
                    System.out.println("=> Check project complete successfully");
                    System.out.print("Press ENTER to continue...");
                    sc.nextLine();
                    break;
                case 2:
                    System.out.print("What task would like to be check is complete: ");
                    String completeTask = sc.nextLine();
                    listProjects.get(index).checkTask(completeTask);
                    System.out.println("=> Check task complete successfully");
                    System.out.print("Press ENTER to continue...");
                    sc.nextLine();
                    break;
                case 3:
            }
        }
    }

    public void deleteProject() {
        //print header
        Menu deleteMenu = new Menu();
        deleteMenu.display("DELETE PROJECT", user.getUserName());

        deleteMenu.addOption("Delete whole project");
        deleteMenu.addOption("Delete task");
        deleteMenu.addOption("Exit");

        boolean isValid;
        String deleteProject = "";
        do {

            try {
                isValid = true;
                System.out.print("Enter project's name would be delete: ");
                deleteProject = sc.nextLine();
            } catch (Exception e) {
                isValid = false;
                System.out.println("Project's name must be non-empty and not containing '|'!");
            }
        } while (!isValid);

        boolean isExist = false;
        int index = 0;
        for (Project project : listProjects) {
            if (project.getName().equalsIgnoreCase(deleteProject)) {
                isExist = true;
                break;
            }
            index++;
        }
        if (!isExist) {
            System.out.println("Project does not exit");
            System.out.print("Press ENTER to continue...");
            sc.nextLine();
        } else {
            listProjects.get(index).displayProject(index);

            isValid = false;
            int option = 0;
            //Ask for user's option
            do {
                try {
                    System.out.println("What you want to do about this project:");
                    deleteMenu.display();
                    System.out.printf("Enter one option [1, %d]: ", deleteMenu.getMenuSize());
                    option = Integer.parseInt(sc.nextLine().trim());
                    isValid = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid option!");
                    isValid = false;
                }
            } while (!isValid);

            switch (option) {
                case 1: {
                    listProjects.remove(index);
                    System.out.println("=> Delete project successfully");
                    System.out.print("Press ENTER to continue...");
                    sc.nextLine();
                    break;
                }
                case 2: {
                    boolean isContinue = false;
                    do {
                        //Ask for task's name
                        String deleteTaskName = "";
                        do {
                            try {
                                System.out.print("Please enter task's name: ");
                                deleteTaskName = sc.nextLine();
                                isValid = true;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Task's name must be non-empty, not containing '|' and 30 length at most!");
                                isValid = false;
                            }
                        } while (!isValid);
                        listProjects.get(index).deleteTask(deleteTaskName);
                        System.out.println("=> Delete task successfully");
                        do {
                            isValid = true;
                            String YoN;
                            System.out.print("Do you want to continue delete task (Y/N): ");
                            YoN = sc.nextLine().trim();
                            if (YoN.equalsIgnoreCase("Y")) {
                                isContinue = true;
                            } else if (YoN.equalsIgnoreCase("N")) {
                                isContinue = false;
                            } else {
                                isValid = false;
                            }
                            if (!isValid) {
                                System.out.println("Invalid option!! We only accept 'Y/N' or 'y/n' as valid option");
                            }
                        } while (!isValid);
                    } while (isContinue);
                    break;
                }

            }

            //update into file data
            if (listProjects.size() == 0) {
                Project project = null;
                Utility.writeFile(project, false, String.format("data/%s/projects.txt", user.getUserName()));
            } else {
                for (Project project : listProjects) {
                    Utility.writeFile(project, false, String.format("data/%s/projects.txt", user.getUserName()));
                }

            }

        }

    }
}
