package user;

//Import java standard library
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//Import user's custom package
import menu.Menu;

public class User {
    private final Scanner sc = new Scanner(System.in);
    private String userName, userPass;
    
    // Get userName
    public String getUserName() {
        return userName;
    }

    //Print menu for user to sign in/create account/exit
    public boolean userWelcome() {
        Menu welcomeMenu = new Menu();
        welcomeMenu.addOption("Sign in");
        welcomeMenu.addOption("Sign up (New here)");
        welcomeMenu.addOption("Exit");
        welcomeMenu.display("WELCOME PAGE");
        
        // Get option from user
        int option;
        while (true) {
            try {
                System.out.printf("Enter your option [1, %d]: ", welcomeMenu.getMenuSize());
                option = sc.nextInt();
                if (option < 1 || option > welcomeMenu.getMenuSize())
                    System.out.printf("*Error: option must be in range [1, %d]!\n", welcomeMenu.getMenuSize());
                else {
                    sc.nextLine();
                    break;
                }
            } catch (InputMismatchException ime) {
                System.out.println("*Error: option is a number!\n");
                sc.nextLine();
            }
        }

        //A boolean to check if user want to exit or not
        boolean exit = true;
        
        switch (option) {
            case 1: {
                this.userName = userSignIn();
                exit = false;
                break;
            }
            case 2: {
                this.userName = userSignUp();
                exit = false;
                break;
            } 
            case 3: {
                System.out.println("Thanks for using our service!");
                exit = true;
                break;
            }
        }
        
        return exit;
    }
    
    // Sign in
    private String userSignIn() {
        Menu signInMenu = new Menu();
        signInMenu.display("SIGN-IN PAGE");

        while (true) {
            // Get userName
            while (true) {
                System.out.print("Enter username: ");
                userName = sc.nextLine().trim();
                // userName: least 3 charactes; least one letter
                if (!userName.matches("(?=.*[a-zA-z]).{3,}"))
                    System.out.println("*Error: username must have at least 3 characters and least one letter!\n");
                else
                    break;
            }
            
            // Get userPass
            while (true) {
                System.out.print("Enter password: ");
                userPass = sc.nextLine().trim();
                // userPass: no whitespace; least 5 characters
                if (userPass.matches("(?=\\S+$).{5,}")) {
                    break;
                } else {
                    System.out.println(
                            "*Error: password must have at least 5 characters and no whitespace!\n");
                }
            }
            
            // Check userName && userPass correct
            boolean check = false;

            try {
                File file = new File("data/users.txt");
                Scanner fileRead = new Scanner(file);
                int line = 1;
                while (fileRead.hasNextLine()) {
                    String lineText = fileRead.nextLine();
                    if (line % 2 != 0 && lineText.equals(userName)) {
                        line++;
                        lineText = fileRead.nextLine();
                        if (lineText.equals(md5Hash(userPass))) {
                            System.out.println("\n=> Sign-in successfully!");
                            System.out.print("Press ENTER to continue...");
                            sc.nextLine();
                            check = true;
                            break;
                        }
                    }
                    line++;
                }
                fileRead.close();
            } catch (FileNotFoundException fnfe) {
                System.out.println("*Error: no file data for checking!\n");
            }
            
            if (!check) {
                System.out.println("*Error: username or password go wrong!\n");
            } else {
                return userName;
            }
        }
    }
    
    //Sign up
    private String userSignUp() {
        Menu signUpMenu = new Menu();
        signUpMenu.display("SIGN-UP PAGE");

        // Get userName
        while (true) {
            System.out.print("Enter username: ");
            userName = sc.nextLine().trim();
            // userName: least 3 charactes; least one letter
            if (!userName.matches("(?=.*[a-zA-z]).{3,}"))
                System.out.println("*Error: username must have at least 3 characters and least one letter!\n");
            else {
                // Check userName existed in data
                boolean check = true;
                try {
                    File file = new File("data/users.txt");
                    Scanner fileRead = new Scanner(file);
                    int line = 1;
                    while (fileRead.hasNextLine()) {
                        String lineText = fileRead.nextLine();
                        if (line % 2 != 0 && lineText.equals(userName)) {
                            System.out.println("*Error: username already existed in database!\n");
                            check = false;
                            break;
                        }
                        line++;
                    }
                    fileRead.close();
                } catch (FileNotFoundException e) {
                    System.out.println("*Error: no file data for checking!\n" + e);
                    continue;
                }
                if (check)
                    break;
            }
        }

        // Get userPass
        while (true) {
            System.out.print("Enter password: ");
            userPass = sc.nextLine().trim();
            // userPass: no whitespace; least 5 characters
            if (userPass.matches("(?=\\S+$).{5,}")) {
                break;
            } else {
                System.out.println(
                        "*Error: password must have at least 5 characters and no whitespace!\n");
            }
        }

        // Hash userPass
        userPass = md5Hash(userPass);

        // Add user to File
        while (true) {
            try {
                FileWriter users = new FileWriter("data/users.txt", true);
                users.write(userName + '\n');
                users.write(userPass + '\n');
                users.close();
                System.out.println("\n=> Sign-up successfully!");
                System.out.print("Press ENTER to continue...");
                sc.nextLine();
                return userName;
            } catch (IOException ioe) {
                System.out.println("*Error: not find data file to write!\n");
            }
        }
    }
    
    // MD5 hash String
    private String md5Hash(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(text.getBytes());
            BigInteger textHash = new BigInteger(1, messageDigest);
            return textHash.toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
