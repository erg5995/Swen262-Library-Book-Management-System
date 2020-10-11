package book_sort_strategy;

import data_classes.Book;

import java.util.Arrays;
import java.util.List;

public class AuthorSortStrategy implements BookSortStrategy {

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
        String pivot = books[high].getAuthor()[0];
        int i = (low-1);
        for (int j=low; j<high; j++)
        {
            if (compareStringsAlphabetically(books[j].getAuthor()[0], pivot))
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

}
