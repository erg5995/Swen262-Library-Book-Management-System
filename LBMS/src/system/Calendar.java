package system;

import java.time.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

class ChangeLibraryStateTask extends TimerTask {

    private RequestManager requestManager;
    private Calendar calendar;

    public ChangeLibraryStateTask(RequestManager requestManager, Calendar calendar) {
        this.requestManager = requestManager;
        this.calendar = calendar;
    }

    /**
     * Opens or closes the library at a scheduled time
     */
    public void run() {
        LocalDateTime currentTime = calendar.getCurrentTime();
        LocalTime closingTime = calendar.getClosingTime();
        LocalTime openingTime = calendar.getOpeningTime();

        if (currentTime.getHour() >= closingTime.getHour() || currentTime.getHour() < openingTime.getHour()) {
            requestManager.setState(1); // close
        } else {
            requestManager.setState(0); // open
        }
    }

}

public class Calendar {
    private Timer timer;
    private final LocalTime openingTime;
    private final LocalTime closingTime;
    private RequestManager requestManager;
    private final long MILLISECOND_DAY = 86400 * 1000;
    private Clock clock;

    public Calendar() {
        this.timer = new Timer();
        clock = Clock.systemDefaultZone();
        // the date is just to start the recurring timer
        this.openingTime = LocalTime.of(8, 0, 0);
        this.closingTime = LocalTime.of(19, 0, 0);
    }

    /**
     * Set the manager for this class. This method gets called in the Manager class after Calendar is initialized
     * to avoid timing issue.
     *
     * @param manage Manager
     */
    public void setRequestManager(RequestManager manage) {
        this.requestManager = manage;
        startScheduledTasks(false);
    }

    /**
     * Starts an opening and closing scheduled tasks depending on the time of the day the system first begins.
     *
     * @param isDayDiff is it a new day
     */
    public void startScheduledTasks(boolean isDayDiff) {
        // if it is a new day, close the library
        if (isDayDiff) {
            requestManager.setState(1);
        }

        // check if the hour is within closing time
        int hour = LocalTime.now().getHour();
        if (hour >= closingTime.getHour() || hour < openingTime.getHour()) {
            open();
            close();
        } else {
            close();
            open();
        }
    }

    /**
     * Advance the date by the amount of days.
     *
     * @param day amount of days
     */
    public void advanceDay(long day) {
        clock = Clock.offset(clock, Duration.ofDays(day));
    }

    /**
     * Advance the time by the number of hours.
     *
     * @param hour number of hours
     */
    public void advanceHour(long hour) {
        clock = Clock.offset(clock, Duration.ofHours(hour));
    }

    /**
     * Starts a scheduled task to open the library.
     */
    public void open() {
        timer.schedule(new ChangeLibraryStateTask(requestManager, this), new Date(), MILLISECOND_DAY);
    }

    /**
     * Starts a scheduled task to close the library.
     */
    public void close() {
        timer.schedule(new ChangeLibraryStateTask(requestManager, this), new Date(), MILLISECOND_DAY);
    }

    /**
     * Gets the current time in the system. The initial time is based on the system timezone.
     *
     * @return current time
     */
    public LocalDateTime getCurrentTime() {
        return clock.instant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Gets the library opening time
     *
     * @return opening time
     */
    public LocalTime getOpeningTime() {
        return openingTime;
    }

    /**
     * Gets the library closing time
     *
     * @return closing time
     */
    public LocalTime getClosingTime() {
        return closingTime;
    }

    /**
     * Pretty prints the current time in the system
     *
     * @return current time
     */
    public String toString() {
        StringBuilder output = new StringBuilder();
        LocalDateTime currentTime = getCurrentTime();
        output.append(currentTime.getYear()).append(":").append(currentTime.getMonthValue()).append(":")
                .append(currentTime.getDayOfMonth());
        return output.toString();
    }
}
