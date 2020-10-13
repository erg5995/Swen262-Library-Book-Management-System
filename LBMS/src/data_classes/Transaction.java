package data_classes;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Dataclass for transactions
 * Author: Thomas Linse
 */
public class Transaction implements Serializable
{
    private Book book;
    private User user;
    private LocalDate dueDate, dateChecked;
    private double fine;

    /**
     * constructor can take in LocalDate or LocalDateTime for dueDate,
     * but always save it as LocalDate since it doesn't matter what
     * time it was checked out or returned
     */
    public Transaction(Book book, User user, LocalDate dateChecked, LocalDate dueDate)
    {
        this.book = book;
        this.user = user;
        this.dueDate = dueDate;
        this.dateChecked = dateChecked;
        fine = 0;
    }
    public Transaction(Book book, User user, LocalDateTime dateChecked, LocalDateTime dueDateTime)
    {
        this(book, user, dateChecked.toLocalDate(), dueDateTime.toLocalDate());
    }

    /**
     * Getter methods
     */
    public Book getBook() { return book; }
    public User getUser() { return user; }
    public LocalDate dueDate() { return dueDate; }
    public LocalDate getDateChecked() { return dateChecked; }
    public double getFine() { return fine; }
    public boolean isOverdue() { return fine > 0; }

    /**
     * Methods with inputted dates
     */
    public void update(LocalDate date)
    {
        int daysPastDue = TimeBetween.numDays(dueDate, date);
        if (daysPastDue > 0) {
            fine = 10;
            if (daysPastDue > 70)
                fine += 20;
            else
                fine += 2 * (daysPastDue - 1) / 7;
        }
    }
    public void close(LocalDate date) { dateChecked = date; }
}
