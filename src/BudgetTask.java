import java.util.Date;

public class BudgetTask extends Task {
	//Class attribute
	private double money;

    	//Default constructor
    	public BudgetTask () {
        	super();
        	this.money = 0.0;
    	}

	//Parametric constructor
    	public BudgetTask (String ID, String name, String description, Date startDate, Date endDate, boolean isComplete, double money) {
        	super(ID, name, description, startDate, endDate, isComplete);
        	this.money = money;
    	}

	/*Getter methods*/
    	public double getMoney () {
        	return this.money;
    	}

    	/*Setter methods*/
    	public void setMoney (double money) {
        	this.money = money;
    	}

    	/*public methods*/
    	@Override
    	public void displayTask () {
        	String format = "| %-5s | %-30s | %-50s | %10s | %10s | %10s | %10s |\n";
        	System.out.printf(format, ID, name, description, parseDate(startDate), parseDate(endDate), isComplete, money);
        	System.out.printf("+%s+%s+%s+%s+%s+%s+%s+\n", repeat("-", 7), repeat("-", 32), repeat("-", 52), repeat("-", 12), repeat("-", 12), repeat("-", 12), repeat("-", 12));
    	}
}
