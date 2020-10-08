package book_sort_strategy;

import data_classes.Book;

import java.util.Arrays;
import java.util.List;

public class PublishDateSortStrategy implements BookSortStrategy {

    @Override
    public void sort(List<Book> books) {

        int low = 0, high = books.size() - 1;
        Book[] booksArr = (Book[]) books.toArray();

        sort(booksArr, low, high);

        books = Arrays.asList(booksArr);

    }

    private void sort(Book[] books, int low, int high) {
        if (low < high)
        {
            int pi = partition(books, low, high);

            sort(books, low, pi-1);
            sort(books, pi+1, high);
        }
    }

    private int partition(Book[] books, int low, int high)
    {
        String pivot = books[high].getPublishDate();
        int i = (low-1);
        for (int j=low; j<high; j++)
        {
            if (compareDates(books[j].getPublishDate(), pivot))
            {
                i++;

                Book temp = books[i];
                books[i] = books[j];
                books[j] = temp;
            }
        }

        Book temp = books[i+1];
        books[i+1] = books[high];
        books[high] = temp;

        return i+1;
    }

    //True indicates that date 1 is more recent than date 2
    private static boolean compareDates(String str1, String str2) {
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
}
