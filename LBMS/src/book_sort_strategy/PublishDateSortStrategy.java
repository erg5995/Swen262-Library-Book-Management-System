package book_sort_strategy;

import data_classes.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PublishDateSortStrategy implements BookSortStrategy {

    @Override
    public void sort(List<Book> books, boolean forLibrary) {

        int low = 0, high = books.size() - 1;

        sort((ArrayList<Book>)books, low, high);

        if(forLibrary)
            books.removeIf(book -> book.getNumCopiesLeft() <= 0);

    }

    private void sort(ArrayList<Book> books, int low, int high) {
        if (low < high)
        {
            int pi = partition(books, low, high);

            sort(books, low, pi-1);
            sort(books, pi+1, high);
        }
    }

    private int partition(ArrayList<Book> books, int low, int high)
    {
        String pivot = books.get(high).getPublishDate();
        int i = (low-1);
        for (int j=low; j<high; j++)
        {
            if (compareDates(books.get(j).getPublishDate(), pivot))
            {
                i++;

                Book temp = books.get(i);
                books.set(i, books.get(j));
                books.set(j, temp);
            }
        }

        Book temp = books.get(i + 1);
        books.set(i + 1, books.get(high));
        books.set(high, temp);

        return i+1;
    }
}
