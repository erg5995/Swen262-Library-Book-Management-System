package commands;

import system.Calendar;

import java.time.LocalDateTime;

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

    /**
     * Checks if the number of days is between 0-7
     *
     * @return true if the number is valid, false if not
     */
    private boolean checkValidDay() {
        return numDays >= 0 && numDays <= 7;
    }

    /**
     * Checks that the number of hours is between 0-23
     *
     * @return true if the number is valid, false if not
     */
    private boolean checkValidHour() {
        return numHours >= 0 && numHours <= 23;
    }

    /**
     * Executes the command
     *
     * @return command output
     */
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

        LocalDateTime oldDate = calendar.getCurrentTime();
        calendar.advanceDay(numDays);
        calendar.advanceHour(numHours);
        LocalDateTime newDate = calendar.getCurrentTime();

        if (newDate.getDayOfMonth() != oldDate.getDayOfMonth()) {
            calendar.startScheduledTasks(true);
        } else {
            calendar.startScheduledTasks(false);
        }

        return output.toString();
    }
}