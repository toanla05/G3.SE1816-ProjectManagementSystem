package project;

//Import java standard library
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;

//Import user defined package
import user.User;
import menu.Menu;

public class ProjectDisplayer {
    //Class attribute
    private final Scanner sc = new Scanner(System.in);
    private User user;
    private final ArrayList<Project> listProjects;
    
    //Constructor
    public ProjectDisplayer(ArrayList<Project> listProjects, User user) {
        this.user = user;
        this.listProjects = listProjects;
    }

    /*Public methods*/
    public void display() {
        //Display menu
        Menu displayMenu = new Menu();
        displayMenu.addOption("Display Project");
        displayMenu.addOption("Sort by project's start date");
        displayMenu.addOption("Sort by project's progress");
        displayMenu.addOption("Quit");
        displayMenu.display("DISPLAY PROJECT", user.getUserName());

        
        boolean isValid = false;
        int option = 0;
        boolean isRunning;
        do {
            //Ask for user's option
            do {
                try {
                    System.out.printf("Enter one option [1, %d]: ", displayMenu.getMenuSize());
                    option = Integer.parseInt(sc.nextLine().trim());    
                    isValid = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid option!");
                    isValid = false;
                }
            } while (!isValid);

            //Run the program
            isRunning = true;
            switch (option) {
                case 1: {
                    displayProject();
                    break;
                }
                case 2: {
                    sortByDate();
                    break;
                }
                case 3: {
                    sortByProgress();
                    break;
                }
                case 4: {
                    isRunning = false;
                    break;
                }
            }
        } while (isRunning);
    }

    private void displayProject() {
        int index = 1;
        for (Project project : listProjects) {
            project.displayProject(index);
            index++;
        }
    }

    private void sortByDate() {
        int index = 1;
        for (Project project : listProjects) {
            project.getListTasks().sort(Comparator.comparing(Task::getStartDate));
            project.displayProject(index);
        }
    }

    private void sortByProgress() {
        ArrayList<Project> sortedList = new ArrayList<>(listProjects);
        sortedList.sort(Comparator.comparing(Project::calculateProgress));
        int index = 1;
        for (Project project : sortedList) {
            project.displayProject(index);
            index++;
        }
    }
}
