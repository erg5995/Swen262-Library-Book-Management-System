package commands;

import system.Calendar;
import system.Manager;

public class ReportCommand implements Command{

    private int numDays;
    private Manager manager;
    private Calendar calendar;

    public ReportCommand(Manager manager) {
        this.manager = manager;
        if (manager != null) {
            this.calendar = manager.getCalendar();
        }
    }

    public ReportCommand(int days, Manager manager){
        this.numDays = days;
        this.manager = manager;
        if (manager != null) {
            this.calendar = manager.getCalendar();
        }
    }

    public String execute(){
        StringBuilder output = new StringBuilder();

        if (calendar != null) {
            return "";
        }

        // if the user did not pass a number of days, generate a report for all days
        if (numDays > 0) {
            output.append("report," + calendar.toString());
            output.append(manager.getDatabase().generateReport(
                    calendar.getCurrentTime().toLocalDate()).toString());
        } else {
            output.append("report," + calendar.toString());
            output.append(manager.getDatabase().generateReport(numDays,
                    calendar.getCurrentTime().toLocalDate()).toString());
        }

        return output.toString();
    }
}