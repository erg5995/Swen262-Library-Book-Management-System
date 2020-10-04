import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

class OpenLibraryTimeTask extends TimerTask {

    public void run() {
        System.out.println("Open");
    }

}

class CloseLibraryTimeTask extends TimerTask {

    public void run() {
        System.out.println("Close");
    }
}

public class Calendar {
    private LocalDateTime currentTime;
    private Timer timer;
    private final LocalDateTime openingTime;
    private final LocalDateTime closingTime;
    private final long MILLISECOND_DAY = 86400 * 1000;

    public Calendar() {
        currentTime = LocalDateTime.now();
        timer = new Timer();
        // the date is just to start the recurring timer
        openingTime = LocalDateTime.of(2020, Month.OCTOBER, 4, 8, 0, 0);
        closingTime = LocalDateTime.of(2020, Month.OCTOBER, 4, 19, 0, 0);

        startScheduledTasks();
    }

    public void startScheduledTasks() {
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
        timer.schedule(new OpenLibraryTimeTask(), Date.from(Instant.from(openingTime.atZone(ZoneId.systemDefault()))),
                MILLISECOND_DAY);
    }

    public void close() {
        timer.schedule(new CloseLibraryTimeTask(), Date.from(Instant.from(closingTime.atZone(ZoneId.systemDefault()))),
                MILLISECOND_DAY);
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }
}
