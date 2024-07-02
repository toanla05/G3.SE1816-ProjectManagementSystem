package project;

//Import Java standard library
import java.util.ArrayList;

import report.Report;
//Import user's custom package
import user.User;
import utility.Utility;

public class ProjectManagement {
    //Class attribute
    private final ProjectCreator projectCreator;
    private final ProjectDisplayer projectDisplayer;
    private final Report report;

    //Default constructor
    public ProjectManagement(User user) {
        //Filter the list of projects based on user
        ArrayList<Project> listProjects = Utility.readProjects(user.getUserName());

        //create component for each functionailiy
        this.projectCreator = new ProjectCreator(listProjects, user);
        this.projectDisplayer = new ProjectDisplayer(listProjects, user);
        this.report = new Report(listProjects, user);
    }

    /*public methods*/
    public void createProject() {
        projectCreator.addProject();
    }

    public void displayProject() {
        projectDisplayer.display();
    }
    
    public void editProject() {
        //Thanh's code will go here
        System.out.println("Thanh's code will go here");
    }

    public void updateProjectProgress() {
        
    }
    
    public void deleteProject() {
        //Thanh's code will go here
        System.out.println("Thanh's code will go here");
    }

    public void makeReport() {
        report.createReport();
    }
}

