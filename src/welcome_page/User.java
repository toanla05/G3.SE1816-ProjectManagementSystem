import java.util.*;

// ** How to use **
// -> User.userWelcome (you will get userName: String) (don't need make object)

public class User {

    static Scanner sc = new Scanner(System.in);

    public static void userWelcome() {
        System.out.println("            PROJECT MANAGEMENT SYSTEM");
        System.out.println("               ## WELCOME PAGE ##\n");
        System.out.println("    [1] Sign in");
        System.out.println("    [2] Sign up (New here)\n");

        // Get option from user
        int option;
        while (true) {
            try {
                System.out.print("Enter your option [1, 2]: ");
                option = sc.nextInt();
                if (option < 1 || option > 2)
                    System.out.println("*Error: option must be in range [1, 2]!\n");
                else
                    break;
            } catch (Exception e) {
                System.out.println("*Error: option is a number!\n");
                sc.nextLine();
            }
        }

        // Option -> SignIn OR SignUp
        switch (option) {
            case 1:

                break;

            case 2:
                userSignUp();
                break;
        }
    }

    // Sign Up
    public static void userSignUp() {
        System.out.println("            PROJECT MANAGEMENT SYSTEM");
        System.out.println("               ## SIGN-UP PAGE ##\n");

        String userName, userPass;
        while (true) {
            System.out.print("Enter username: ");
            userName = sc.nextLine().trim();
            // userName: least 3 charactes; least one letter
            if (userName.matches("(?=.*[a-zA-z]).{3,}")) {
                continue;
            } else {
                System.out.println("*Error: username must have at least 3 characters and least one letter!\n");
            }

            System.out.print("Enter password: ");
            userPass = sc.nextLine().trim();
            // userPass: no whitespace; least 5 characters; least one upperCase, one
            // lowerCase, one number
            if (userPass.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{5,}")) {
                break;
            } else {
                System.out.println(
                        "*Error: password must have at least 5 characters, no whitespace, least one upperCase and lowerCase letter, least one number!\n");
            }
        }

        System.out.println("\n\nYour account: " + userName);
        System.out.println("Your password: " + userPass);
    }
}
