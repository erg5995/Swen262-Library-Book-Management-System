package DataClasses;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Dataclass for transactions
 * Author: Thomas Linse
 */
public class Transaction
{
    private Book book;
    private User user;
    private LocalDate dueDate, dateChecked;
    private double fine;
    private boolean isOverdue;

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
        isOverdue = false;
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
    public boolean isOverdue() { return isOverdue; }

    /**
     * Methods to alter fine: either set or add to a fine
     */
    public void setFine(double amount) { fine = amount; }
    public void addToFine(double amount) { fine += amount; }

    /**
     * Setter method for isOverdue. Once a book is overdue,
     * it can't go back to now being overdue so this will
     * only set it to true
     */
    public void pastDueDate() { isOverdue = true; }
}
