import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

class LibraryStateTimeTask extends TimerTask {
//    private Manger manger;

    public void run() {

    }
}

public class Calendar {
    private LocalDateTime dateTime;
    private Timer timer;
    private final LocalDateTime openingTime;
    private final LocalDateTime closingTime;

    public Calendar() {
        dateTime = LocalDateTime.now();
        timer = new Timer();
        openingTime = LocalDateTime.now();
        closingTime = LocalDateTime.now();
    }

    public void advanceDay(long day) {
        dateTime = dateTime.plusDays(day);
    }

    public void advanceHour(long hour) {
        dateTime = dateTime.plusHours(hour);
    }

    public LocalDateTime record(LocalDateTime localDateTime) {
        return null;
    }

    public void open() {
        timer.schedule(new LibraryStateTimeTask(), Date.from(Instant.from(openingTime.atZone(ZoneId.systemDefault()))),
                86400); // there are 86400 seconds in a day
    }

    public void close() {
        timer.schedule(new LibraryStateTimeTask(), Date.from(Instant.from(closingTime.atZone(ZoneId.systemDefault()))),
                86400); // there are 86400 seconds in a day
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
