package menu;

//Import Java standard library 
import java.util.ArrayList;
//Import user's custom package

public class Menu {
    //Class attribute
    private ArrayList<String> menu;

    //Constructor
    public Menu () {
        this.menu = new ArrayList<>();
    }

    //Public methods
    public int getMenuSize() {
        return this.menu.size();
    }

    public void addOption(String option) {
        this.menu.add(option);
    }

    public void display (String header) {
        int index = 1;

        System.out.printf("\t\t\t\tPROJECT MANAGEMENT SYSTEM\n");
        System.out.printf("\t\t\t\t\t## %s ##\n", header);
        
        for (String option : menu) {
            System.out.printf("[%d] %s.\n", index, option);
            index++;
        }        
    }

    public void display (String header, String username) {
        int index = 1;

        System.out.printf("\t\t\t\t----- PROJECT MANAGEMENT SYSTEM -----\n");
        System.out.printf("\t\t\t\t\t ## %s ##\n", header);
        System.out.printf("\tWelcome, %s\n\n", username);
        
        for (String option : menu) {
            System.out.printf("[%d] %s.\n", index, option);
            index++;
        }
    }
}
