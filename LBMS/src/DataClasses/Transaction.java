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

    public Book getBook() { return book; }
    public User getUser() { return user; }
    public LocalDate dueDate() { return dueDate; }
    public LocalDate getDateChecked() { return dateChecked; }
    public double getFine() { return fine; }
    public boolean isOverdue() { return isOverdue; }

    public void setFine(double amount) { fine = amount; }
    public void addToFine(double amount) { fine += amount; }
    public void pastDueDate() { isOverdue = true; }
}
