package book_sort_strategy;

import data_classes.Book;

import java.util.List;

public interface BookSortStrategy {

    public void sort(List<Book> book);

    //True indicates that date 1 is more recent than date 2
    default boolean compareDates(String str1, String str2) {
        //Dates are in MM/DD/YYYY format (probably)
        String[] date1 = str1.split("[/-]");
        String[] date2 = str2.split("[/-]");

        if(Integer.parseInt(date1[2]) < Integer.parseInt(date2[2])) {
            return false;
        } else if(Integer.parseInt(date1[2]) > Integer.parseInt(date2[2])) {
            return true;
        } else if(Integer.parseInt(date1[0]) < Integer.parseInt(date2[0])) {
            return false;
        } else if(Integer.parseInt(date1[0]) > Integer.parseInt(date2[0])) {
            return true;
        } else if(Integer.parseInt(date1[1]) < Integer.parseInt(date2[1])) {
            return false;
        } else if(Integer.parseInt(date1[1]) > Integer.parseInt(date2[1])) {
            return true;
        } else {
            return true;
        }
    }

    default boolean compareStringsAlphabetically(String str1, String str2) {
        if((int)str1.charAt(0) < (int)str2.charAt(0)) {
            return true;
        }else if ((int)str1.charAt(0) > (int)str2.charAt(0)){
            return false;
        } else {
            return compareStringsAlphabetically(str1.substring(1), str2.substring(1));
        }
    }

}
