package DataClasses;

import java.time.LocalDate;

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
}
