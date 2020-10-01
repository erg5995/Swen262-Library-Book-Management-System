package DataClasses;

import java.time.LocalDateTime;

/**
 * Dataclass for visits
 * Author: Thomas Linse
 */
public class Visit
{

    private User visitor;
    private LocalDateTime entryTime, exitTime;

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
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }

    /**
     * The end time is the only thing that will need to be set
     */
    public void setExitTime(LocalDateTime endTime) { exitTime = endTime; }
}
