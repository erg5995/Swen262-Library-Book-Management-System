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
    public Transaction(Book book, User user, LocalDate dueDate)
    {
        this.book = book;
        this.user = user;
        this.dueDate = dueDate;
        fine = 0;
    }
    public Transaction(Book book, User user, LocalDateTime dueDateTime)
    {
        this(book, user, dueDateTime.toLocalDate());
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
            setFine(10);
            if (daysPastDue > 70)
                addToFine(20);
            else
                addToFine(2 * (daysPastDue - 1) / 7);
        }
    }
    public void close(LocalDate date) { dateChecked = date; }

    /**
     * Methods to alter fine: either set or add to a fine
     */
    public void setFine(double amount) { fine = amount; }
    public void addToFine(double amount) { fine += amount; }
}
