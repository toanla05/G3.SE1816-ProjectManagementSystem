package project;

//Import Java standard library
import java.util.Date;

//Import user's custom package
import utility.Utility;

public class BudgetTask extends Task {
    //Class attribute
    private double money;

    //Default constructor
    public BudgetTask() {
        super();
        this.money = 0.0;
    }

    //Parametric constructor
    public BudgetTask(String name, String description, Date startDate, Date endDate, boolean isComplete, double money) {
        super(name, description, startDate, endDate, isComplete);
        this.money = money;
    }

    /*Getter methods*/
    public double getMoney() {
        return this.money;
    }

    /*Setter methods*/
    public void setMoney(double money) throws IllegalArgumentException {
        if (money <= 0) {
            throw new IllegalArgumentException();
        }
        this.money = money;
    }

    /*public methods*/
    @Override
    public void displayTask() {
        String format = "| %-30s | %-50s | %10s | %10s | %10s | %10s |\n";
        System.out.printf(format, name, description, parseDate(startDate), parseDate(endDate), isComplete, money);
        System.out.printf("+%s+%s+%s+%s+%s+%s+\n", Utility.repeat("-", 32), Utility.repeat("-", 52), Utility.repeat("-", 12), Utility.repeat("-", 12), Utility.repeat("-", 12), Utility.repeat("-", 12));
    }

}