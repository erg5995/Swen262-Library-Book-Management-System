package data_classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Dataclass for visits
 * Author: Thomas Linse
 */
public class Visit
{

    private User visitor;
    //entry time holds the date and time
    private LocalDateTime entryTime;
    //exit time just holds the time since it
    //will be the same date as entry time
    private LocalTime exitTime;

    /**
     * Constructor requires User and start time, but
     * not end time because that won't be known until
     * the user leaves
     */
    public Visit(User visitor, LocalDateTime startTime)
    {
        this.visitor = visitor;
        entryTime = startTime;
    }

    /**
     * Getter methods
     */
    public User getVisitor() { return visitor; }
    public LocalDate getEntryDate() { return entryTime.toLocalDate(); }
    public LocalTime getEntryTime() { return entryTime.toLocalTime(); }
    public LocalTime getExitTime() { return exitTime; }

    /**
     * @return time spent at the library in form [hour,minute,second]
     */
    public int[] getTimeSpent() { return TimeBetween.hourMinuteSecond(entryTime.toLocalTime(), exitTime); }

    /**
     * The end time is the only thing that will need to be set
     */
    public void setExitTime(LocalTime endTime) { exitTime = endTime; }
    public void setExitTime(LocalDateTime endTime) { exitTime = endTime.toLocalTime(); }
}
