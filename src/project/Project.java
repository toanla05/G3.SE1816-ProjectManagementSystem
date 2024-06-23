package project;

//Import Java standard library
import java.util.Date;
import java.util.ArrayList;

//Import user's custom package
import utility.Utility;

public class Project {
    //Class attribute
    private String owner;
    private String name;
    private String description;
    private String category;
    private Date startDate;
    private Date endDate;
    private final ArrayList<Task> listTasks;

    //Default constructor
    public Project() {
        this.name = "";
        this.description = "";
        this.category = "";
        this.startDate = new Date();
        this.endDate = new Date();
        this.listTasks = new ArrayList<>();
    }

    //Parametric constructor
    public Project(String name, String description, String category, Date startDate, Date endDate, ArrayList<Task> listTasks) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.startDate = new Date(startDate.getTime());
        this.endDate = new Date(endDate.getTime());
        this.listTasks = new ArrayList<>(listTasks);
    }

    /*Getter methods*/
    public String getOwner() {
        return this.owner;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCategory() {
        return this.category;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public ArrayList<Task> getListTasks() {
        return this.listTasks;
    }

    /*Setter methods*/        
    public void setOwner(String username) {
        this.owner = username;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (name.contains("|")) {
            throw new IllegalArgumentException();
        }

        this.name = name;
    }

    public void setDescription(String description) throws IllegalArgumentException {
        if (description.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (description.contains("|")) {
            throw new IllegalArgumentException();
        }

        this.description = description;
    }

    public void setCategory(String category) throws IllegalArgumentException {
        if (category.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (category.contains("|")) {
            throw new IllegalArgumentException();
        }

        this.category = category;
    }

    public void setStartDate(Date startDate) {
        this.startDate = new Date(startDate.getTime());
    }

    public void setEndDate(Date endDate) {
        this.endDate = new Date(endDate.getTime());
    }

    /*Private methods (helper methods)*/

    /*Public methods*/
    public void addTask(Task task) {
        this.listTasks.add(task);
    }

    public void addTask(BudgetTask task) {
        this.listTasks.add(task);
    }

    public void deleteTask(String name) {
        int index = 0;
        for (Task task : listTasks) {
            if (task.getName().equalsIgnoreCase(name)) {
                this.listTasks.remove(index);
                return;
            }
            index++;
        }
    }

    public double calculateProgress() {
        double count = 0;
        for (Task task : listTasks) {
            if (task.getIsComplete()) {
                count++;
            }
        }
        return (count * 100) / listTasks.size();
    }

    public void displayProject(int index) {
        System.out.println(Utility.repeat("-", 20));
        System.out.printf("Project %d: %.2f%%\n", index, calculateProgress());
        System.out.printf(">>> Name: %s\n", name);
        System.out.printf(">>> Description: %s\n", description);
        System.out.printf(">>> Category: %s\n", category);
        System.out.printf(">>> Start date: %s\n", Utility.parseDate(startDate));
        System.out.printf(">>> End date: %s\n", Utility.parseDate(endDate));
        System.out.printf("+%s+%s+%s+%s+%s+%s+\n", Utility.repeat("-", 32), Utility.repeat("-", 52), Utility.repeat("-", 12), Utility.repeat("-", 12), Utility.repeat("-", 12), Utility.repeat("-", 12));
        System.out.printf("| %-30s | %-50s | %10s | %10s | %10s | %10s |\n", "Name", "Description", "Start date", "End date", "Complete?", "Budget");
        System.out.printf("+%s+%s+%s+%s+%s+%s+\n", Utility.repeat("-", 32), Utility.repeat("-", 52), Utility.repeat("-", 12), Utility.repeat("-", 12), Utility.repeat("-", 12), Utility.repeat("-", 12));
        for (Task task : listTasks) {
            task.displayTask();
        }

    }
}
