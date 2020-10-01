package DataClasses;

import java.util.List;

/**
 * Data class for reports
 * Author: Thomas Linse
 */
public class Report
{
    /**
     * numVisitors: # of visitors, not # of Visits
     * numBooksOwned: max # of books owned by the library during specified time
     * booksPurchased: all books that were purchased for the library during " "
     */
    private int numVisitors, numBooksOwned;
    private double avgVisitTimeInHours, totalBookFines;
    private List<Book> booksPurchased;

    public Report(int visitors, int booksOwned, double avgTime, List<Book> booksBought, double totalFines)
    {
        numVisitors = visitors;
        numBooksOwned = booksOwned;
        avgVisitTimeInHours = avgTime;
        booksPurchased = booksBought;
        totalBookFines = totalFines;
    }

    /**
     * Getter methods
     * No setters because this will just transfer the data.
     */
    public int getNumVisitors() { return numVisitors; }
    public int getNumBooksOwned() { return numBooksOwned; }
    public double getAvgVisitTimeInHours() { return avgVisitTimeInHours; }
    public double getTotalBookFines() { return totalBookFines; }
    public List<Book> getBooksPurchased() { return booksPurchased; }
}
