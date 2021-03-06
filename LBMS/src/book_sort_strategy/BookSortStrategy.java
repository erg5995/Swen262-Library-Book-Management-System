package book_sort_strategy;

import data_classes.Book;

import java.util.List;

/**
 * An interface for all sorting strategies
 */
public interface BookSortStrategy {

    /**
     * Sorting algorithm
     *
     * @param book list of books to be sorted
     * @param forLibrary if the sorting was for the library or the store
     */
    public void sort(List<Book> book, boolean forLibrary);

    /**
     * Compare two dates assuming it's in the MM/DD/YYYY format
     *
     * @param str1 date1
     * @param str2 date2
     * @return true if the first date comes before the second one
     */
    default boolean compareDates(String str1, String str2) {
        //Dates are in YYYY-MM-DD format (probably)
        String[] date1 = str1.split("[/-]");
        String[] date2 = str2.split("[/-]");

        if(Integer.parseInt(date1[0]) < Integer.parseInt(date2[0])) {
            return false;
        } else if(Integer.parseInt(date1[0]) > Integer.parseInt(date2[0])) {
            return true;
        }

        if(date1.length == 1 || date2.length == 1)
            return true;

        if(Integer.parseInt(date1[1]) < Integer.parseInt(date2[1])) {
            return false;
        } else if(Integer.parseInt(date1[1]) > Integer.parseInt(date2[1])) {
            return true;
        }

        if(date1.length == 2 || date2.length == 2)
            return true;

        if(Integer.parseInt(date1[2]) < Integer.parseInt(date2[2])) {
            return false;
        } else if(Integer.parseInt(date1[2]) > Integer.parseInt(date2[2])) {
            return true;
        } else {
            return true;
        }
    }

    /**
     * Compares two strings in alphabetical order
     *
     * @param str1 first string
     * @param str2 second string
     * @return true if the first string comes before the second
     */
    default boolean compareStringsAlphabetically(String str1, String str2) {

        str1 = str1.toUpperCase();
        str2 = str2.toUpperCase();

        if(str1.equals("")) return false;
        if(str2.equals("")) return true;
        if((int)str1.charAt(0) < (int)str2.charAt(0)) {
            return true;
        }else if ((int)str1.charAt(0) > (int)str2.charAt(0)){
            return false;
        } else {
            return compareStringsAlphabetically(str1.substring(1), str2.substring(1));
        }
    }

}
