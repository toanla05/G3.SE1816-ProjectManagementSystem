package report;

//Import java standard library
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.awt.Desktop;

//Import user's defined package
import user.User;
import project.Project;
import utility.Utility;

public class Report {
    private String result = "";
    private ArrayList<Project> listProjects;
    private User user;
    private Scanner sc = new Scanner(System.in);    

    //Constructor
    public Report (ArrayList<Project> listProjects, User user) {
        this.listProjects = listProjects;
        this.user = user;
        this.result = "";
    }

    //public method
    public void createReport() {
        //Add header to result
        result += String.format("\t\t\t\t\t%s\n", "REPORT");

        /*Add general information to result*/
        result += String.format("Username: %s\n", user.getUserName());
        result += String.format("%s%s%s\n", Utility.repeat("-", 10), "GENERAL INFORMATION", Utility.repeat("-", 10));
        result += String.format("Number of projects: %d\n", listProjects.size());

        //Get the number of completed projects
        int completedProject = 0;
        for (Project project : listProjects) {
            if (project.calculateProgress() == 100) {
                completedProject++;
            }
        }
        result += String.format("Number of completed projects: %d\n", completedProject);

        //Get the number of completed projects that does not meet the deadline 
        int notCompletedProjectDueDate = 0;
        Date currentDay = new Date();
        for (Project project : listProjects) {
            if (project.calculateProgress() < 100 && !currentDay.after(project.getEndDate())) {
                notCompletedProjectDueDate++;
            }
        }
        result += String.format("Number of uncompleted projects that is late: %d\n", notCompletedProjectDueDate);

        //Get the number of projects that does not go over the budget
        int projectNotOverBudget = 0;
        for (Project project : listProjects) {
            if (project.calculateTotalCost() <= project.getInitialBudget()) {
                projectNotOverBudget++;
            }
        }
        result += String.format("Number of project that does not go over budget: %d\n", projectNotOverBudget);

        /*Add detail information to result*/
        result += String.format("%s%s%s\n", Utility.repeat("-", 10), "DETAIL INFORMATION", Utility.repeat("-", 10));
        
        //Add each project's progress to result
        result += String.format("%s\n", "* Project progress");
        for (Project project : listProjects) {
            result += String.format("%s: %.2f%%\n", project.getName(), project.calculateProgress());
        }

        //Add each project's status and date compare to deadline
        result += String.format("%s\n", "* Project deadline");
        for (Project project : listProjects) {
            if (project.calculateProgress() == 100) {
                result += String.format("%s: completed\n", project.getName());
            }

            if (project.calculateProgress() < 100 && currentDay.after(project.getEndDate())) {
                result += String.format("%s: late %d days\n", project.getName(),
                (currentDay.getTime() - project.getEndDate().getTime()) / (1000 * 60 * 60 * 24));
            }

            if (project.calculateProgress() < 100 && !currentDay.after(project.getEndDate())) {
                result += String.format("%s: %d days before deadline\n", project.getName(),
                (project.getEndDate().getTime() - currentDay.getTime()) / (1000 * 60 * 60 *24));
            }
        }

        //Add project's total cost compare to project's initial budget
        result += String.format("%s\n", "* Project total cost");
        for (Project project : listProjects) {
            if (project.calculateTotalCost() < project.getInitialBudget()) {
                result += String.format("%s: under budget\n", project.getName());
            } else {
                result += String.format("%s: went over budget for %.2f VND\n", project.getName(), 
                project.calculateTotalCost() - project.getInitialBudget());
            }
        }

        /*Print to terminal*/
        System.out.println(result);

        /*Ask user if they want to create a report.txt file*/
        boolean isValid;
        String option;
        do {
            System.out.print("Do you want to export the report into a file (Y/N)?");
            option = sc.nextLine().trim();
            
            //Check for option validity
            isValid = option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N");
            if (!isValid) {
                System.out.println("Invalid option! We only accept Y/N or y/n");
                continue;
            }

            //Handle each case
            if (option.equalsIgnoreCase("Y")) {
                //Create report.txt
                Utility.createFile("data/report.txt");

                //Write data to file
                try {
                    FileWriter writer = new FileWriter(new File("data/report.txt"), false);
                    writer.write(result);
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Cannot find report.txt");
                }
                
                //Automatically opening report.txt file
                try {
                    Desktop.getDesktop().edit(new File("data/report.txt"));
                } catch (IOException e) {
                    System.out.println("Problem opening report.txt!");
                    e.printStackTrace();
                }

                System.out.printf("You can also find report file at: %s/data\n", System.getProperty("user.dir"));
            }
        } while (!isValid);
    }
}
