package DataClasses;

import java.util.List;

public class Report
{
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

    public int getNumVisitors() { return numVisitors; }
    public int getNumBooksOwned() { return numBooksOwned; }
    public double getAvgVisitTimeInHours() { return avgVisitTimeInHours; }
    public double getTotalBookFines() { return totalBookFines; }
    public List<Book> getBooksPurchased() { return booksPurchased; }
}
