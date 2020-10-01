package DataClasses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Visit
{
    private User visitor;
    private LocalDateTime entryTime, exitTime;

    public Visit(User visitor, LocalDateTime startTime)
    {
        this.visitor = visitor;
        entryTime = startTime;
    }

    public User getVisitor() { return visitor; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }

    public void setExitTime(LocalDateTime endTime) { exitTime = endTime; }
}
