import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

class OpenLibraryTimeTask extends TimerTask {

    public void run() {

    }
}

class CloseLibraryTimeTask extends TimerTask {

    public void run() {

    }
}

public class Calendar {
    private LocalDateTime dateTime;
    private Timer timer;
    private final LocalDateTime openingTime;
    private final LocalDateTime closingTime;
    private final long MILLISECOND_DAY = 86400 * 1000;
    private boolean state = false;

    public Calendar() {
        dateTime = LocalDateTime.now();
        timer = new Timer();
        // the date is just to start the recurring timer
        openingTime = LocalDateTime.of(2020, Month.OCTOBER, 4, 8, 0, 0);
        closingTime = LocalDateTime.of(2020, Month.OCTOBER, 4, 11, 51, 0);
        // start scheduled tasks
        open();
        close();
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean newState) {
        state = newState;
    }

    public void advanceDay(long day) {
        dateTime = dateTime.plusDays(day);
    }

    public void advanceHour(long hour) {
        dateTime = dateTime.plusHours(hour);
    }

    public void open() {
            timer.schedule(new OpenLibraryTimeTask(), Date.from(Instant.from(openingTime.atZone(ZoneId.systemDefault()))),
                    MILLISECOND_DAY);
    }

    public void close() {
            timer.schedule(new CloseLibraryTimeTask(), Date.from(Instant.from(closingTime.atZone(ZoneId.systemDefault()))),
                    MILLISECOND_DAY);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getOpeningTime() {
        return openingTime;
    }

    public LocalDateTime getClosingTime() {
        return closingTime;
    }
}
