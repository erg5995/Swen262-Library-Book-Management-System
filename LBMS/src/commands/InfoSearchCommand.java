package commands;

import book_sort_strategy.BookSortStrategy;
import data_classes.Book;
import system.DataStorage;

import java.util.List;

/**
 * info,title,{authors},[isbn, [publisher,[sort order]]];
 * title is the title of the book.
 * authors is the comma-separated list of authors of the book.
 * isbn is the 13-digit International Standard Book NUmber (ISBN) for the book.
 * publisher is the name of the book's publisher.
 * sort order is one of: title, publish-date, book-status Sorting of the title will be alphanumerical
 * from 0..1-A-Z, publish date will be newest first, and book status will only show books with at least
 * one copy available for check out.
 */
public class InfoSearchCommand implements Command{

    private Book book;

    private boolean forLibrary;

    private DataStorage dataStorage;

    private BookSortStrategy strategy;

    public InfoSearchCommand(Book theBook, boolean forTheLibrary, DataStorage data, BookSortStrategy strat){
        book = theBook;
        forLibrary = forTheLibrary;
        dataStorage = data;
        strategy = strat;
    }

    /**
     * Executes the command
     *
     * @return command output
     */
    public String execute() {
        List<Book> toSort = dataStorage.bookInfoSearch(book, forLibrary);

        if(strategy != null) {
            strategy.sort(toSort, forLibrary);
        }

        StringBuilder formatString = new StringBuilder("\n");
        int id = 1;
        for(Book book: toSort){
            formatString.append(id).append(",").append(book.toString()).append("\n");
            id++;
        }
        if(forLibrary)
            return "info," + toSort.size() + formatString + ";";
        else
            return "search," + toSort.size() + formatString + ";";
    }
}