package utility;

//Import Java standard library
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;

//Import user's custom package
import project.*;
import user.User;

public class Utility {
    //Static method for clearing terminal 
    public static void clearConsole() {
        try {

            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                new ProcessBuilder("cmd", "/c", "clear").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ex) {
        }
    }
    
    //Static method for repeating a string
    public static String repeat(String str, int times) {
        String result = "";
        while (times > 0) {
            result = result.concat(str);
            times--;
        }
        return result;
    }
    
    //Static method for parsing a Date to String
    public static String parseDate(Date date) throws IllegalArgumentException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
    
    //Static method for parsing a String to Date (including exception handling)
    public static Date parseToDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        return formatter.parse(date);
    
    }
    
    //Static method for searching a task by name
    public static Task searchTaskByName(ArrayList<Task> listTask, String taskName) {
        for (Task task : listTask) {
            if (task.getName().equalsIgnoreCase(taskName)) {
                return task;
            }
        }
        return null;
    }
    
    //Static method for creating .txt file (If file exist, this method will NOT overwrite the existing files)
    public static void createFile(String filePath) {
        File file = new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    //Static method for reading data from tasks.txt
    public static ArrayList<Task> readTasks() {
        /*Variables declaration*/
        ArrayList<Task> listTasks = new ArrayList<>();
        String[] taskDatas;
        Task task = null;
        Scanner scannerTask = null;
        
        //Try reading file tasks.txt
        try {
            scannerTask = new Scanner(new File("data/tasks.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        
        //Read data from tasks.txt
        while (scannerTask.hasNextLine()) {
            //Split the line read from tasks.txt into an array of String
            taskDatas = scannerTask.nextLine().split("\\|");
            
            //Create task or budget task based on the array length
            try {
                if (taskDatas.length == 5) {
                    task = new Task(taskDatas[0], taskDatas[1], parseToDate(taskDatas[2]), parseToDate(taskDatas[3]), 
                                    taskDatas[4].equals("true"));
                } else {
                    task = new BudgetTask(taskDatas[0], taskDatas[1], parseToDate(taskDatas[2]), parseToDate(taskDatas[3]), 
                                        taskDatas[4].equals("true"), Double.parseDouble(taskDatas[5]));
                }
            } catch (ParseException e) {
                System.out.println("Error reading tasks.txt!");
                scannerTask.close();
                throw new RuntimeException(e);
            } 
            
            //Add task to the list task
            listTasks.add(task);
        }
        
        //Closing the scanner to prevent data leak
        scannerTask.close();
        
        //Return list task
        return listTasks;
    }
    
    //Static method for reading data from projects.txt file
    public static ArrayList<Project> readProjects(User user) {
        /*Variable declaration*/
        ArrayList<Project> listProjects = new ArrayList<>();
        ArrayList<Task> listTasks = readTasks();
        String[] projectDatas;
        Project project = null;
        Task task = null;
        Scanner scannerProject = null;
        
        //Try reading from projects.txt
        try {
            scannerProject = new Scanner(new File("data/projects.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        
        //Read data from projects.txt
        while (scannerProject.hasNextLine()) {
            //Split the line read from projects.txt into an array of String
            projectDatas = scannerProject.nextLine().split("\\|");
            
            //Check if project belong to that user
            if (user.getUserName() != projectDatas[0]) {
                continue;
            }

            //Create project's meta data
            try {
                project = new Project(projectDatas[1], projectDatas[2], projectDatas[3], parseToDate(projectDatas[4]), parseToDate(projectDatas[5]), new ArrayList<>());
                project.setOwner(projectDatas[0]);
            } catch (ParseException e) {
                System.out.println("Error reading projects.txt");
                scannerProject.close();
                throw new RuntimeException(e);
            }
            
            //Add tasks to project
            for (int i = 7; i < projectDatas.length; i++) {
                task = searchTaskByName(listTasks, projectDatas[i]);
                if (task != null) {
                    project.addTask(task);
                }
            }
            
            //Add project to list of projects
            listProjects.add(project);
        }
        
        //Close file pointer to prevent data leak
        scannerProject.close();
        
        return listProjects;
    }
    
    //Static method for writing data to projects.txt
    public static void writeFile(Project project, boolean isAppending, String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath, isAppending);
           
            //clear all data in projects.txt by passing a null object
            if (project == null) {
                writer.write("");
                writer.close();
                return;
            }
            
            /*If project != null, then write the data into projects.txt based on mode (append or overwrite)*/
            
            //Add project's meta data to result string
            String result = String.format("%s|%s|%s|%s|%s|%s|", project.getOwner(), project.getName(), project.getDescription(), 
                                                                   project.getCategory(), parseDate(project.getStartDate()), parseDate(project.getEndDate()));
            
            //Add project's task into result string
            for (Task task : project.getListTasks()) {
                result = result.concat(String.format("%s|", task.getName()));
            }
            
            result = result.concat("\n");
            
            //Write data to file
            writer.write(result); 
            
            //Close file writer
            writer.close();
        } catch (IOException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
    
    //Static method for writing data to tasks.txt
    public static void writeFile(Task task, boolean isAppending, String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath, isAppending);
            
            //Clear all data in tasks.txt by passing a null tasks
            if (task == null) {
                writer.write("");
                writer.close();
                return;
            }
            
            /*If task != null, then write the data into projects.txt based on mode (append or overwrite)*/
            
            //Add task's meta data to result string
            String result = String.format("%s|%s|%s|%s|%s|", task.getName(), task.getDescription(), parseDate(task.getStartDate()), 
                                                                    parseDate(task.getEndDate()), task.getIsComplete());
            
            //If task is a budget task, add cost to result string
            if (task instanceof BudgetTask) {
                String cost = Double.toString(((BudgetTask) task).getMoney());
                result = result.concat(String.format("%s|", cost));
            }
            
            result = result.concat("\n");
            
            //Write data to file
            writer.write(result);
            
            //Close file writer
            writer.close();
        } catch (IOException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
                
    }
    
}
