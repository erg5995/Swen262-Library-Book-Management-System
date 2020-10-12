package system;

import java.time.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

class ChangeLibraryStateTask extends TimerTask {

    private Manager manager;
    private Calendar calendar;

    public ChangeLibraryStateTask(Manager manager, Calendar calendar) {
        this.manager = manager;
        this.calendar = calendar;
    }

    public void run() {
        LocalDateTime currentTime = calendar.getCurrentTime();
        LocalDateTime closingTime = calendar.getClosingTime();
        LocalDateTime openingTime = calendar.getOpeningTime();

        if (currentTime.getHour() >= closingTime.getHour() || currentTime.getHour() < openingTime.getHour()) {
            manager.setState(1); // close
        } else {
            manager.setState(0); // open
        }
    }

}

public class Calendar {
    private Timer timer;
    private final LocalDateTime openingTime;
    private final LocalDateTime closingTime;
    private Manager manager;
    private final long MILLISECOND_DAY = 86400 * 1000;
    private Clock clock;

    public Calendar() {
        this.timer = new Timer();
        clock = Clock.systemDefaultZone();
        // the date is just to start the recurring timer
        this.openingTime = LocalDateTime.of(2020, Month.OCTOBER, 4, 8, 0, 0);
        this.closingTime = LocalDateTime.of(2020, Month.OCTOBER, 4, 19, 0, 0);
    }

    public void setManager(Manager manage) {
        this.manager = manage;
        startScheduledTasks();
    }

    private void startScheduledTasks() {
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

    public void advanceDay(long day) {
        clock = Clock.offset(clock, Duration.ofDays(day));
    }

    public void advanceHour(long hour) {
        clock = Clock.offset(clock, Duration.ofHours(hour));
    }

    public void open() {
        timer.schedule(new ChangeLibraryStateTask(manager, this), Date.from(Instant.from(openingTime.atZone(ZoneId.systemDefault()))),
                MILLISECOND_DAY);
    }

    public void close() {
        timer.schedule(new ChangeLibraryStateTask(manager, this), Date.from(Instant.from(closingTime.atZone(ZoneId.systemDefault()))),
                MILLISECOND_DAY);
    }

    public LocalDateTime getCurrentTime() {
        return clock.instant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public LocalDateTime getOpeningTime() {
        return openingTime;
    }

    public LocalDateTime getClosingTime() {
        return closingTime;
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        LocalDateTime currentTime = getCurrentTime();
        output.append(currentTime.getYear() + ":" + currentTime.getMonthValue() + ":" +
                currentTime.getDayOfMonth());

        return output.toString();
    }
}
