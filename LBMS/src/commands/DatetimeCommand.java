package commands;

import system.Calendar;

import java.time.LocalDateTime;

/**
 * Description: Displays the current date and time in the simulation. This should include any days that have
 * been added to the calendar using the command to advance time.
 * Request: datetime;
 * Response: datetime, date (YYYY/MM/DD),time (HH:MM:SS)
 */
public class DatetimeCommand implements Command{
    Calendar calendar;

    public DatetimeCommand(Calendar calendar) {
        this.calendar = calendar;
    }

    /**
     * Executes the command
     *
     * @return command output
     */
    public String execute(){
        if (calendar == null) {
            return "";
        }

        LocalDateTime currentTime = calendar.getCurrentTime();

        return "datetime," + currentTime.getYear() + "/" + currentTime.getMonthValue() + "/" + currentTime.getDayOfMonth()
                + "," + currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond();
    }
}