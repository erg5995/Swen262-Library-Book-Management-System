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
}
