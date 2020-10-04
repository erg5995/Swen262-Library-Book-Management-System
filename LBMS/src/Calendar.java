import java.time.LocalDateTime;

public class Calendar {
    private LocalDateTime dateTime;
//    private Manager manager;

    public Calendar() {
        dateTime = LocalDateTime.now();
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

    }

    public void close() {

    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
