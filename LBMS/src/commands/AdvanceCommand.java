package commands;

import system.Calendar;

/**
 * Description: For simulation purposes. This method will advance the simulated date of
 * the library ahead by a specified number of days and/or hours. The total number of
 * days/hours advanced must be tracked by the system and added to the current date as
 * appropriate.
 * Request: advance, number-of-days (0-7), [number-of-hours (0-23)];
 *
 */
public class AdvanceCommand implements Command {

    private int numDays;
    private int numHours;
    private Calendar calendar;

    public AdvanceCommand(int day, Calendar calend) {
        this.numDays = day;
        calendar = calend;
    }

    public AdvanceCommand(int day, int hour, Calendar calend){
        this.numHours = hour;
        this.numDays = day;
        calendar = calend;
    }

    private boolean checkValidDay() {
        return numDays >= 0 && numDays <= 7;
    }

    private boolean checkValidHour() {
        return numHours >= 0 && numHours <= 23;
    }

    public String execute(){
        StringBuilder output = new StringBuilder();
        output.append("advance,");

        if (!checkValidDay()) {
            output.append("invalid-number-of-days,");
            output.append(this.numDays);
            return output.toString();
        }
        if (!checkValidHour()) {
            output.append("invalid-number-of-hours,");
            output.append(this.numHours);
            return output.toString();
        }

        output.append("success");

        calendar.advanceDay(numDays);
        calendar.advanceHour(numHours);
        calendar.startScheduledTasks();
        return output.toString();
    }
}