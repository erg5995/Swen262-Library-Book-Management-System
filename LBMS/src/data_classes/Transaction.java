package data_classes;

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
     * Methods with inputted dates
     */
    public void update(LocalDate date)
    {
        int daysPastDue = (date.getYear() - dueDate.getYear()) * 365 + date.getDayOfYear() - dueDate.getDayOfYear() + 1;
        if (daysPastDue > 0) {
            pastDueDate();
            setFine(10);
            if (daysPastDue > 70)
                addToFine(20);
            else
                addToFine(2 * (daysPastDue - 1) / 7);
        }
    }

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
