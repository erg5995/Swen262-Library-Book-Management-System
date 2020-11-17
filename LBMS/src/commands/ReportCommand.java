package commands;

import system.Calendar;
import system.DataStorage;

/**
 * Response	report,date,<nl> Number of Books: num-books<nl> Number of Visitors: num-visitors<nl> Average Length of Visit: visit-avg<nl> Number of Books Purchased: num-purchased<nl> Fines Collected: fines<nl> Fines Outstanding: outstanding<nl>;
 * date is the date that the report was generated in YYYY/MM/DD format.
 * num-books is the total number of books in the library.
 * num-visitors is the total number of registered library visitors.
 * visit-avg is the average length of a visit in the format hh:mm:ss (hours, minutes, seconds).
 * num-purchased is the number of books purchansed.
 * fines is the amount of fines collected in US dollars.
 * outstanding is the amount of uncollected fines in US dollars.
 */
public class ReportCommand implements Command{

    private int numDays;
    private DataStorage dataStorage;
    private Calendar calendar;

    public ReportCommand(int days, DataStorage db, Calendar cal){
        this.numDays = days;
        dataStorage = db;
        calendar = cal;
    }

    /**
     * Executes the command
     *
     * @return command output
     */
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