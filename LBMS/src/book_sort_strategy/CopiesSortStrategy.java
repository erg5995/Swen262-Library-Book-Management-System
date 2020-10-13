package book_sort_strategy;

import data_classes.Book;

import java.util.Arrays;
import java.util.List;

//Yoink https://www.geeksforgeeks.org/quick-sort/
public class CopiesSortStrategy implements BookSortStrategy {

    @Override
    public void sort(List<Book> books) {

        int low = 0, high = books.size() - 1;
        Book[] booksArr = books.toArray(new Book[0]);

        sort(booksArr, low, high);

        books = Arrays.asList(booksArr);

        books.removeIf(book -> book.getNumCopiesLeft() <= 0);

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
        int pivot = books[high].getNumCopiesLeft();
        int i = (low-1);
        for (int j=low; j<high; j++)
        {
            if (books[j].getNumCopiesLeft() < pivot)
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
