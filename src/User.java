import java.util.Scanner;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;

// WELCOME PAGE -> return userName 
public class User {
    Scanner sc = new Scanner(System.in);
    private String userName, userPass;

    public String userWelcome() {
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
                else {
                    sc.nextLine();
                    break;
                }
            } catch (Exception e) {
                System.out.println("*Error: option is a number!\n");
                sc.nextLine();
            }
        }

        clearConsole();
        switch (option) {
            case 1:
                return userSignIn();
            case 2:
                return userSignUp();
        }
        return null;
    }

    // Sign in
    public String userSignIn() {
        System.out.println("            PROJECT MANAGEMENT PROJECT");
        System.out.println("                ## SIGN-IN PAGE ##\n");

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
                File file = new File("data/userAccount/users.txt");
                Scanner fileRead = new Scanner(file);
                int line = 1;
                while (fileRead.hasNextLine()) {
                    String lineText = fileRead.nextLine();
                    if (line % 2 != 0 && lineText.equals(userName)) {
                        line++;
                        lineText = fileRead.nextLine();
                        if (lineText.equals(md5Hash(userPass))) {
                            System.out.print("\n=> Sign-in successfully...");
                            sc.nextLine();
                            check = true;
                            break;
                        }
                    }
                    line++;
                }
                fileRead.close();
            } catch (Exception e) {
                System.out.println("*Error: no file data for checking!\n");
            }

            if (!check) {
                System.out.println("*Error: username or password go wrong!\n");
            } else {
                return userName;
            }
        }
    }

    // Sign Up
    public String userSignUp() {
        System.out.println("            PROJECT MANAGEMENT SYSTEM");
        System.out.println("               ## SIGN-UP PAGE ##\n");

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
                    File file = new File("data/userAccount/users.txt");
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
                    System.out.println("*Error: no file data for checking!\n");
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
        try {
            FileWriter users = new FileWriter("data/userAccount/users.txt", true);
            users.write(userName + '\n');
            users.write(userPass + '\n');
            users.close();
            System.out.print("\n=> Account successfully created!");
            sc.nextLine();
            return userName;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c",
                        "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
        }
    }

    private String md5Hash(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(text.getBytes());
            BigInteger textHash = new BigInteger(1, messageDigest);
            return textHash.toString(16);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}