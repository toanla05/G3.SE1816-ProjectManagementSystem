package project;

//import Java standard library
import java.util.Scanner;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

//import user's custom package
import utility.Utility;
import user.User;
import menu.Menu;

public class ProjectEditing {

    //Class attribute
    private final ArrayList<Project> listProjects;
    private final User user;
    private final Scanner sc = new Scanner(System.in);

    //Constructor
    public ProjectEditing(ArrayList<Project> listProjects, User user) {
        this.listProjects = listProjects;
        this.user = user;
    }

    //Utilizable Method
    private boolean searchProjectByName(String name) {
        for (Project project : listProjects) {
            if (project.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void updateProject() {
        //print header
        Menu updateMenu = new Menu();
        updateMenu.display("UPDATE PROJECT", user.getUserName());

        //add function into MENU
        updateMenu.addOption("Check whole project is completed");
        updateMenu.addOption("Check task is completed");
        updateMenu.addOption("Check whole project is uncompleted");
        updateMenu.addOption("Check task is uncompleted");
        updateMenu.addOption("Update project information");

        //Find project need to be update
        boolean isValid;
        String deleteProject = "";
        do {

            try {
                isValid = true;
                //ask for Project's name
                System.out.print("Enter project's name would be update: ");
                deleteProject = sc.nextLine();
            } catch (Exception e) {
                isValid = false;
                System.out.println("Project's name must be non-empty and not containing '|'!");
            }
        } while (!isValid);

        //check if there is projects in list
        boolean isExist = false;
        int index = 0;
        for (Project project : listProjects) {
            if (project.getName().equalsIgnoreCase(deleteProject)) {
                isExist = true;
                break;
            }
            index++;
        }
        //return if project does not exist
        if (!isExist) {
            System.out.println("Project does not exit");
            System.out.print("Press ENTER to continue...");
            sc.nextLine();
        } else {
            //show project to user
            listProjects.get(index).displayProject(index);

            int option = 0;
            //Ask for user's option
            do {
                try {
                    System.out.println("What you want to do about this project:");
                    updateMenu.display();
                    System.out.printf("Enter one option [1, %d]: ", updateMenu.getMenuSize());
                    option = Integer.parseInt(sc.nextLine().trim());
                    isValid = true;
                    if (option > updateMenu.getMenuSize() || option < 1) {
                        System.out.printf("Option must be from 1 to %d\n", updateMenu.getMenuSize());
                        isValid = false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid option!");
                    isValid = false;
                }
            } while (!isValid);

            switch (option) {
                case 1: {
                    //check project is completed
                    listProjects.get(index).checkProject(true);
                    System.out.println("=> Check project is completed successfully");
                    System.out.print("Press ENTER to continue...");
                    sc.nextLine();
                    break;
                }
                case 2: {
                    boolean isContinue = false;
                    do {
                        //Ask for task's name
                        String completedTaskName = "";
                        do {
                            try {
                                System.out.print("Please enter task's name: ");
                                completedTaskName = sc.nextLine();
                                isValid = true;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Task's name must be non-empty, not containing '|' and 30 length at most!");
                                isValid = false;
                            }
                        } while (!isValid);
                        listProjects.get(index).checkTask(completedTaskName, true);
                        System.out.println("=> Check task is completed successfully");
                        do {
                            isValid = true;
                            String YoN;
                            System.out.print("Do you want to continue check task (Y/N): ");
                            YoN = sc.nextLine().trim();
                            if (YoN.equalsIgnoreCase("Y")) {
                                isContinue = true;
                            } else if (YoN.equalsIgnoreCase("N")) {
                                isContinue = false;
                            } else {
                                isValid = false;
                            }
                            if (!isValid) {
                                System.out.println("Invalid option!! We only accept 'Y/N' or 'y/n' as valid option");
                            }
                        } while (!isValid);
                    } while (isContinue);
                    break;
                }
                case 3: {
                    //check project is completed
                    listProjects.get(index).checkProject(false);
                    System.out.println("=> Check project is uncompleted successfully");
                    System.out.print("Press ENTER to continue...");
                    sc.nextLine();
                    break;
                }
                case 4: {
                    boolean isContinue = false;
                    do {
                        //Ask for task's name
                        String completedTaskName = "";
                        do {
                            try {
                                System.out.print("Please enter task's name: ");
                                completedTaskName = sc.nextLine();
                                isValid = true;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Task's name must be non-empty, not containing '|' and 30 length at most!");
                                isValid = false;
                            }
                        } while (!isValid);
                        listProjects.get(index).checkTask(completedTaskName, false);
                        System.out.println("=> Check task is uncompleted successfully");
                        do {
                            isValid = true;
                            String YoN;
                            System.out.print("Do you want to continue check task (Y/N): ");
                            YoN = sc.nextLine().trim();
                            if (YoN.equalsIgnoreCase("Y")) {
                                isContinue = true;
                            } else if (YoN.equalsIgnoreCase("N")) {
                                isContinue = false;
                            } else {
                                isValid = false;
                            }
                            if (!isValid) {
                                System.out.println("Invalid option!! We only accept 'Y/N' or 'y/n' as valid option");
                            }
                        } while (!isValid);
                    } while (isContinue);
                    break;
                }
                case 5: {
                    //Make a Menu for refactor func
                    Menu refactorMenu = new Menu();
                    refactorMenu.addOption("Renaming Project");
                    refactorMenu.addOption("Changing Project's description");
                    refactorMenu.addOption("Changing Project's Category");
                    refactorMenu.addOption("Updating Project's Start date and End date");
                    refactorMenu.addOption("Updating Project's initial budget");

                    option = 0;
                    //Ask for user's option
                    do {
                        try {
                            System.out.println("What would be updated:");
                            refactorMenu.display();
                            System.out.printf("Enter one option [1, %d]: ", refactorMenu.getMenuSize());
                            option = Integer.parseInt(sc.nextLine().trim());
                            isValid = true;
                            if (option > refactorMenu.getMenuSize() || option < 1) {
                                System.out.printf("Option must be from 1 to %d\n", refactorMenu.getMenuSize());
                                isValid = false;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid option!");
                            isValid = false;
                        }
                    } while (!isValid);

                    switch (option) {
                        case 1: {
                            do {
                                try {

                                    System.out.print("Enter project's new name: ");
                                    String newName = sc.nextLine();
                                    isValid = !searchProjectByName(newName);
                                    if (!isValid) {
                                        System.out.println("Project's name already exist! Try other name");
                                    } else {
                                        listProjects.get(index).setName(newName);
                                    }
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Project's name must be non-empty and not containing '|'!");
                                    isValid = false;
                                }

                            } while (!isValid);
                            System.out.println("=> Rename project successfully");
                            System.out.print("Press ENTER to continue...");
                            sc.nextLine();
                            break;
                        }
                        case 2: {
                            do {
                                try {
                                    System.out.print("Enter project's new description: ");
                                    listProjects.get(index).setDescription(sc.nextLine());
                                    isValid = true;
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Project's description must be non-empty and not containing '|'!");
                                    isValid = false;
                                }
                            } while (!isValid);
                            System.out.println("=> Change project's description successfully");
                            System.out.print("Press ENTER to continue...");
                            sc.nextLine();
                            break;
                        }
                        case 3: {
                            do {
                                try {
                                    System.out.print("Enter new project's category: ");
                                    listProjects.get(index).setCategory(sc.nextLine());
                                    isValid = true;
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Project's category must be non-empty and not containing '|'");
                                    isValid = false;
                                }
                            } while (!isValid);
                            System.out.println("=> Change project's category successfully");
                            System.out.print("Press ENTER to continue...");
                            sc.nextLine();
                            break;
                        }
                        case 4: {
                            Date startDate = null, endDate = null;
                            do {
                                //Get project's start date
                                do {
                                    System.out.print("Enter new project's start date (dd/mm/yyyy): ");
                                    try {
                                        startDate = Utility.parseToDate(sc.nextLine().trim());
                                        isValid = true;
                                    } catch (ParseException e) {
                                        System.out.println("Project's new start date is invalid!");
                                        isValid = false;
                                    }
                                } while (!isValid);

                                //Get project's end date
                                do {
                                    System.out.print("Enter project's new end date (dd/mm/yyyy): ");
                                    try {
                                        endDate = Utility.parseToDate(sc.nextLine().trim());
                                        isValid = true;
                                    } catch (ParseException e) {
                                        System.out.println("Project's new end date is invalid!");
                                        isValid = false;
                                    }
                                } while (!isValid);

                                //Check if start date is sooner than end date or not
                                isValid = startDate.before(endDate);
                                if (!isValid) {
                                    System.out.println("The start date cannot be later than end date!!!");
                                    continue;
                                }

                                listProjects.get(index).setStartDate(startDate);
                                listProjects.get(index).setEndDate(endDate);
                            } while (!isValid);
                            System.out.println("=> Change project's date successfully");
                            System.out.print("Press ENTER to continue...");
                            sc.nextLine();
                            break;
                        }
                        case 5: {
                            double initialBudget;
                            do {
                                try {
                                    System.out.print("Please enter new project's initial budget: ");
                                    initialBudget = Double.parseDouble(sc.nextLine());
                                    listProjects.get(index).setInitialBudget(initialBudget);
                                    isValid = true;
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Project's initial budget must be a non-negative number!");
                                    isValid = false;
                                }
                            } while (!isValid);
                            System.out.println("=> Change project's initial budget successfully");
                            System.out.print("Press ENTER to continue...");
                            sc.nextLine();
                            break;
                        }
                    }

                }

            }

            //update into file data
            //reset file
            Project projectNull = null;
            Task taskNull = null;
            Utility.writeFile(projectNull, false, String.format("data/%s/projects.txt", user.getUserName()));
            Utility.writeFile(taskNull, false, String.format("data/%s/tasks.txt", user.getUserName()));

            //write new data
            for (Project project : listProjects) {
                Utility.writeFile(project, true, String.format("data/%s/projects.txt", user.getUserName()));
                for (Task task : project.getListTasks()) {
                    Utility.writeFile(task, true, String.format("data/%s/tasks.txt", user.getUserName()));
                }
            }

        }

    }

    public void deleteProject() {
        //print header
        Menu deleteMenu = new Menu();
        deleteMenu.display("DELETE PROJECT", user.getUserName());

        deleteMenu.addOption("Delete whole project");
        deleteMenu.addOption("Delete task");
        deleteMenu.addOption("Exit");

        boolean isValid;
        String deleteProject = "";
        do {

            try {
                isValid = true;
                System.out.print("Enter project's name would be delete: ");
                deleteProject = sc.nextLine();
            } catch (Exception e) {
                isValid = false;
                System.out.println("Project's name must be non-empty and not containing '|'!");
            }
        } while (!isValid);

        boolean isExist = false;
        int index = 0;
        for (Project project : listProjects) {
            if (project.getName().equalsIgnoreCase(deleteProject)) {
                isExist = true;
                break;
            }
            index++;
        }
        if (!isExist) {
            System.out.println("Project does not exit");
            System.out.print("Press ENTER to continue...");
            sc.nextLine();
        } else {
            listProjects.get(index).displayProject(index);

            int option = 0;
            //Ask for user's option
            do {
                try {
                    System.out.println("What you want to do about this project:");
                    deleteMenu.display();
                    System.out.printf("Enter one option [1, %d]: ", deleteMenu.getMenuSize());
                    option = Integer.parseInt(sc.nextLine().trim());
                    isValid = true;
                    if (option > deleteMenu.getMenuSize() || option < 1) {
                        System.out.printf("Option must be from 1 to %d\n", deleteMenu.getMenuSize());
                        isValid = false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid option!");
                    isValid = false;
                }
            } while (!isValid);

            switch (option) {
                case 1: {
                    listProjects.remove(index);
                    System.out.println("=> Delete project successfully");
                    System.out.print("Press ENTER to continue...");
                    sc.nextLine();
                    break;
                }
                case 2: {
                    boolean isContinue = false;
                    do {
                        //Ask for task's name
                        String deleteTaskName = "";
                        do {
                            try {
                                System.out.print("Please enter task's name: ");
                                deleteTaskName = sc.nextLine();
                                isValid = true;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Task's name must be non-empty, not containing '|' and 30 length at most!");
                                isValid = false;
                            }
                        } while (!isValid);
                        listProjects.get(index).deleteTask(deleteTaskName);
                        System.out.println("=> Delete task successfully");
                        do {
                            isValid = true;
                            String YoN;
                            System.out.print("Do you want to continue delete task (Y/N): ");
                            YoN = sc.nextLine().trim();
                            if (YoN.equalsIgnoreCase("Y")) {
                                isContinue = true;
                            } else if (YoN.equalsIgnoreCase("N")) {
                                isContinue = false;
                            } else {
                                isValid = false;
                            }
                            if (!isValid) {
                                System.out.println("Invalid option!! We only accept 'Y/N' or 'y/n' as valid option");
                            }
                        } while (!isValid);
                    } while (isContinue);
                    break;
                }

            }

            //update into file data
            //reset file
            Project projectNull = null;
            Task taskNull = null;
            Utility.writeFile(projectNull, false, String.format("data/%s/projects.txt", user.getUserName()));
            Utility.writeFile(taskNull, false, String.format("data/%s/tasks.txt", user.getUserName()));

            //write new data
            for (Project project : listProjects) {
                Utility.writeFile(project, true, String.format("data/%s/projects.txt", user.getUserName()));
                for (Task task : project.getListTasks()) {
                    Utility.writeFile(task, true, String.format("data/%s/tasks.txt", user.getUserName()));
                }
            }

        }

    }

}
