package project;

//Import Java standard library
import java.util.ArrayList;

import report.Report;
//Import user's custom package
import user.User;
import utility.Utility;

public class ProjectManagement {
    //Class attribute
    private final ArrayList<Project> listProjects = new ArrayList<>();
    private final ProjectCreator projectCreator;
    private final Report report;

    //Default constructor
    public ProjectManagement(User user) {
        for (Project project : Utility.readProjects()) {
            if (project.getOwner().equals(user.getUserName())) {
                this.listProjects.add(project);
            }
        }
        this.projectCreator = new ProjectCreator(listProjects, user);
        this.report = new Report(listProjects, user);
    }

    /*public methods*/
    public void createProject() {
        projectCreator.addProject();
    }

    public void showProject() {
        //Thuan's code will go here
        System.out.println("Thuan's code will go here");
        //ArrayList<Project> listProjects = new ArrayList<>(Utility.readProjects(user));
        for (Project project : listProjects) {
            project.displayProject(1);
        }
    }
    
    public void updateProject() {
        //Thanh's code will go here
        System.out.println("Thanh's code will go here");
    }
    
    public void deleteProject() {
        //Thanh's code will go here
        System.out.println("Thanh's code will go here");
    }

    public void makeReport() {
        report.createReport();
    }
}

