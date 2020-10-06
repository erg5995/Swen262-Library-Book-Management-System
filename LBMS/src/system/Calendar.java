package system;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

class OpenLibraryTimeTask extends TimerTask {

    private Manager manager;

    public OpenLibraryTimeTask(Manager manager) {
        this.manager = manager;
    }

    public void run() {
        manager.setState(0); // open
    }

}

class CloseLibraryTimeTask extends TimerTask {

    private Manager manager;

    public CloseLibraryTimeTask(Manager manager) {
        this.manager = manager;
    }

    public void run() {
        manager.setState(1); // closed
    }
}

public class Calendar {
    private LocalDateTime currentTime;
    private Timer timer;
    private final LocalDateTime openingTime;
    private final LocalDateTime closingTime;
    private Manager manager;
    private final long MILLISECOND_DAY = 86400 * 1000;

    public Calendar() {
        this.currentTime = LocalDateTime.now();
        this.timer = new Timer();
        // the date is just to start the recurring timer
        this.openingTime = LocalDateTime.of(2020, Month.OCTOBER, 4, 8, 0, 0);
        this.closingTime = LocalDateTime.of(2020, Month.OCTOBER, 4, 19, 0, 0);
    }

    public void setManager(Manager manager) {
        this.manager = manager;
        startScheduledTasks();
    }

    private void startScheduledTasks() {
        // check if the hour is within closing time
        if (currentTime.getHour() >= closingTime.getHour() || currentTime.getHour() < openingTime.getHour()) {
            open();
            close();
        } else {
            close();
            open();
        }
    }

    public void advanceDay(long day) {
        currentTime = currentTime.plusDays(day);
    }

    public void advanceHour(long hour) {
        currentTime = currentTime.plusHours(hour);
    }

    public void open() {
        timer.schedule(new OpenLibraryTimeTask(manager), Date.from(Instant.from(openingTime.atZone(ZoneId.systemDefault()))),
                MILLISECOND_DAY);
    }

    public void close() {
        timer.schedule(new CloseLibraryTimeTask(manager), Date.from(Instant.from(closingTime.atZone(ZoneId.systemDefault()))),
                MILLISECOND_DAY);
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }
}
