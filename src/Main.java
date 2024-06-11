import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        // Get user account -> userName
        User user = new User();
        user.userWelcome();
	String userName = user.getUserName();
        clearConsole();

        // Menu
        System.out.println("            PROJECT MANAGEMENT SYSTEM");
        System.out.println("               ## MENU PAGE ##\n");
        System.out.println("Welcome, " + userName + "!\n");
        System.out.println("  [1] Add project\n  [2] Update project\n  [3] Remove project\n  [4] Show project\n");

        // Get user option
        Scanner sc = new Scanner(System.in);
        int option;

	/*
        while (true) {
            try {
                System.out.print("Enter your option [1, 4]: ");
                option = sc.nextInt();
                if (option < 1 || option > 4)
                    System.out.println("*Error: option must be in range [1, 4]!\n");
                else {
                    sc.nextLine();
                    sc.close();
                    break;
                }
            } catch (InputMismatchException ime) {
                System.out.println("*Error: option is a number!\n");
                sc.nextLine();
            }
        }

        // Welcome page -> project option
	// Append method here...
        clearConsole();
	*/

	ProjectManagement manager = new ProjectManagement(user);
	manager.createProject();

	/*
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
		break;
	    }
        }
	*/
    }

    public static void clearConsole() {
        try {

            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c",
                        "cls").inheritIO().start().waitFor();
            else
                new ProcessBuilder("cmd", "/c",
                        "clear").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ex) {
        }
    }
}
