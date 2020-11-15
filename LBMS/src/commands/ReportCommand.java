package commands;

import system.Calendar;
import system.DataStorage;

public class ReportCommand implements Command{

    private int numDays;
    private DataStorage dataStorage;
    private Calendar calendar;

    public ReportCommand(DataStorage db, Calendar cal) {
        this(0, db, cal);
    }

    public ReportCommand(int days, DataStorage db, Calendar cal){
        this.numDays = days;
        dataStorage = db;
        calendar = cal;
    }

    public String execute(){
        StringBuilder output = new StringBuilder();

        if (calendar == null) {
            return "Error: Calendar null in ReportCommand";
        }

        if (dataStorage == null) {
            return "Error: Database null in ReportCommand";
        }

        // if the user did not pass a number of days, generate a report for all days
        if (numDays == 0) {
            output.append("report,").append(calendar.toString()).append(",\n");
            output.append(dataStorage.generateReport().toString());
        } else {
            output.append("report,").append(calendar.toString()).append(",\n");
            output.append(dataStorage.generateReport(numDays,
                    calendar.getCurrentTime().toLocalDate()).toString());
        }

        return output.toString();
    }
}