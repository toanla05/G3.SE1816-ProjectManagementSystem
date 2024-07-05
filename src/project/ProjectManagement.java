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
    private final ProjectEditing projectEditing;

    //Default constructor
    public ProjectManagement(User user) {
        //Filter the list of projects based on user
        ArrayList<Project> listProjects = Utility.readProjects(user.getUserName());

        //create component for each functionailiy
        this.projectCreator = new ProjectCreator(listProjects, user);
        this.projectDisplayer = new ProjectDisplayer(listProjects, user);
        this.report = new Report(listProjects, user);
        this.projectEditing = new ProjectEditing(listProjects, user);
    }

    /*public methods*/
    public void createProject() {
        Utility.clearConsole();
        projectCreator.addProject();
    }

    public void displayProject() {
        Utility.clearConsole();
        projectDisplayer.display();
    }
    
    public void editProject() {
        Utility.clearConsole();
        projectEditing.updateProject();
    }

    public void updateProjectProgress() {
        Utility.clearConsole();
        projectEditing.updateProject();
    }
    
    public void deleteProject() {
        Utility.clearConsole();
        projectEditing.deleteProject();
    }

    public void makeReport() {
        Utility.clearConsole();
        report.createReport();
    }
}

