package project;

import java.util.Date;
import java.text.SimpleDateFormat;
import utility.Utility;

public class Task {
    //Class attribute
    protected String ID;
    protected String name;
    protected String description;
    protected Date startDate;
    protected Date endDate;
    protected boolean isComplete;

    //Default constructor
    public Task() {
        this.ID = "";
        this.name = "";
        this.description = "";
        this.startDate = new Date();
        this.endDate = new Date();
        this.isComplete = false;
    }

    //Parametric constructor
    public Task (String ID, String name, String description, Date startDate, Date endDate, boolean isComplete) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.startDate = new Date(startDate.getTime());
        this.endDate = new Date(endDate.getTime());
        this.isComplete = isComplete;
    }

    /*Getter methods*/
    public String getID() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public boolean getIsComplete() {
        return this.isComplete;
    }

    /*Setter methods*/
    public void setID(String ID) throws IllegalArgumentException {
        if (!ID.matches("NT\\d{3}")) {
            throw new IllegalArgumentException();
        }

        this.ID = ID;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (name.contains("|")) {
            throw new IllegalArgumentException();
        }

        if (name.length() > 30) {
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

        if (description.length() > 50) {
            throw new IllegalArgumentException();
        }

        this.description = description;
    }

    public void setStartDate(Date startDate) {
        this.startDate = new Date(startDate.getTime());
    }

    public void setEndDate(Date endDate) {
        this.endDate = new Date(endDate.getTime());
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    /*Public methods*/
    public String parseDate(Date date) throws IllegalArgumentException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
    
    //Method for toggling between complete and incomplete
    public void markTask() {
        this.isComplete = !this.isComplete;
    }

    //method for display task in tabular format
    public void displayTask() {
        String format = "| %-5s | %-30s | %-50s | %10s | %10s | %10s | %10s |\n";
        System.out.printf(format, ID, name, description, parseDate(startDate), parseDate(endDate), isComplete, "_");
        System.out.printf("+%s+%s+%s+%s+%s+%s+%s+\n", Utility.repeat("-", 7), Utility.repeat("-", 32), Utility.repeat("-", 52), Utility.repeat("-", 12), Utility.repeat("-", 12), Utility.repeat("-", 12), Utility.repeat("-", 12));
    }
}