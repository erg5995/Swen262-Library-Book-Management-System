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

    public DatetimeCommand() {
        this.calendar = new Calendar();
    }

    public String execute(){
        LocalDateTime currentTime = calendar.getCurrentTime();
        StringBuilder output = new StringBuilder();
        output.append("datetime,");
        output.append(currentTime.getYear() + "/" + currentTime.getMonthValue() + "/" + currentTime.getDayOfMonth());
        output.append(",");
        output.append(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());

        return output.toString();
    }
}