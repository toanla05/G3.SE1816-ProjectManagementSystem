package main;

//Import Java standard library
import java.util.Scanner;

//Import user's custome package
import user.User;
import project.ProjectManagement;

public class Main {
    public static void main(String[] args) {
        boolean isExit = false;
        boolean isRunning, isValid;
        int option = 0;
        Scanner sc = new Scanner(System.in);

        //Run the program infinitely until user choose to exit
        do {
            //Create user and print welcome page
            User user = new User();
            
            //Check if user want to exit program or not
            isExit = user.userWelcome();
            if (isExit) {
                continue;
            }
            
            //Get user's username
            String userName = user.getUserName();

            //Set the main program to run infinitely until user choose to log out
            isRunning = true;
            
            //Get user's option 
            do {
                // Menu -> Replace this code later with menu object
                System.out.println("            PROJECT MANAGEMENT SYSTEM");
                System.out.println("               ## MENU PAGE ##\n");
                System.out.println("Welcome, " + userName + "!\n");
                System.out.println("  [1] Add project\n  [2] Update project\n  [3] Remove project\n  [4] Show project\n  [5] Log out");

                //Ask for user's option and check validity
                do {
                    try {
                        System.out.print("Please choose one option: ");
                        option = Integer.parseInt(sc.next());
                    } catch (NumberFormatException e) {
                        System.out.println("You must enter a numeric value for option!!!");
                        isValid = false;
                        continue;
                    }

                    //Check if option is in the range
                    isValid = 1 <= option && option <= 5;

                    //Print error message
                    if (!isValid) {
                        System.out.println("Invalid option!!!");
                    }
                } while (!isValid);

                //Run the program based on user's option
                ProjectManagement manager = new ProjectManagement(user);
                
                switch (option) {
                    case 1: {
                        manager.createProject();
                        break;
                    }
                    case 2: {
                        System.out.println("Func 2");
                        break;
                    }
                    case 3: {
                        System.out.println("Func 3");
                        break;
                    }
                    case 4: {
                        System.out.println("Func 4");
                    }
                    case 5: {
                        isRunning = false;
                        break;
                    }
                }
            } while (isRunning);
        } while (!isExit);
    }
}
