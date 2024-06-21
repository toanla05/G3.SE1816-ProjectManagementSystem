package project;

//Import Java standard library
import java.util.ArrayList;

//Import user's custom package
import user.User;
import utility.Utility;

public class ProjectManagement {
    //Class attribute
    private final ProjectCreator projectCreator;

    //Default constructor
    public ProjectManagement(User user) {
        ArrayList<Project> listProjects = new ArrayList<>(Utility.readProjects());
        this.projectCreator = new ProjectCreator(listProjects, user);
    }

    /*public methods*/
    public void createProject() {
        projectCreator.addProject();
    }

    public void showProject() {
        //Thuan's code will go here
        System.out.println("Thuan's code will go here");
    }
    
    public void updateProject() {
        //Thanh's code will go here
        System.out.println("Thanh's code will go here");
    }
    
    public void deleteProject() {
        //Thanh's code will go here
        System.out.println("Thanh's code will go here");
    }
}
