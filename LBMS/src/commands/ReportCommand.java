package commands;

import system.Calendar;
import system.Database;

public class ReportCommand implements Command{

    private int numDays;
    private Database database;
    private Calendar calendar;

    public ReportCommand(Database db, Calendar cal) {
        this(0, db, cal);
    }

    public ReportCommand(int days, Database db, Calendar cal){
        this.numDays = days;
        database = db;
        calendar = cal;
    }

    public String execute(){
        StringBuilder output = new StringBuilder();

        if (calendar == null) {
            return "Error: Calendar null in ReportCommand";
        }

        // if the user did not pass a number of days, generate a report for all days
        if (numDays == 0) {
            output.append("report," + calendar.toString());
            output.append(database.generateReport().toString());
        } else {
            output.append("report," + calendar.toString());
            output.append(database.generateReport(numDays,
                    calendar.getCurrentTime().toLocalDate()).toString());
        }

        return output.toString();
    }
}