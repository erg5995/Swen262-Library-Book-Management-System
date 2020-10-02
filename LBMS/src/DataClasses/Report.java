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
    private int numVisitors, numBooksOwned, numBooksPurchased;
    private int[] avgVisitTime;
    private double collectedFines, outstandingFines;

    public Report(int visitors, int booksOwned, int booksBought, int[] avgTime, double collected, double outstanding)
    {
        numVisitors = visitors;
        numBooksOwned = booksOwned;
        avgVisitTime = avgTime;
        numBooksPurchased = booksBought;
        collectedFines = collected;
        outstandingFines = outstanding;
    }

    /**
     * Getter methods
     * No setters because this will just transfer the data.
     */
    public int getNumVisitors() { return numVisitors; }
    public int getNumBooksOwned() { return numBooksOwned; }
    public int[] getAvgVisitTimeInHours() { return avgVisitTime; }
    public double getCollectedFines() { return collectedFines; }
    public double getOutstandingFines() { return outstandingFines; }
    public int getNumBooksPurchased() { return numBooksPurchased; }

    public String toString()
    {
        String ret = "Number of Books: " + numBooksOwned + "\nNumber of Visitors: " + numVisitors + "\nAverage Length ";
        ret += "of Visit: " + String.format("%02d", avgVisitTime[0]) + ":" + String.format("%02d", avgVisitTime[1]);
        ret += ":" + String.format("%02d", avgVisitTime[2]) + "\nNumber of Books Purchased: " + numBooksPurchased;
        ret += "\nFines Collected: $" + String.format("%.2f", collectedFines) + "\nFines Outstanding: $";
        return ret + String.format("%.2f", outstandingFines);
    }
}
