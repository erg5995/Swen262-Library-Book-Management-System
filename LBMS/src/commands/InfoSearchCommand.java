package commands;

import book_sort_strategy.BookSortStrategy;
import data_classes.Book;
import system.Database;

import java.util.List;

public class InfoSearchCommand implements Command{

    private Book book;

    private boolean forLibrary;

    private Database database;

    private BookSortStrategy strategy;



    public InfoSearchCommand(Book theBook, boolean forTheLibrary, Database data, BookSortStrategy strat){
        book = theBook;
        forLibrary = forTheLibrary;
        database = data;
        strategy = strat;
    }
    public String execute() {
        //this is the case if the sort order is invalid, idk how this could happen from this class....
        if(strategy == null)
        {
            if(forLibrary)
                return "info,invalid-sort-order;";
            else
                return "search,invalid-sort-order;";
        }
        else {

            List<Book> toSort = database.bookInfoSearch(book, forLibrary);
            strategy.sort(toSort);
            String formatString = "\n";
            int id = 1;
            for(Book book: toSort){
                formatString = formatString + book.getNumCopies() + id + book.toString() + "\n";
                id++;
            }
            if(forLibrary)
                return "info," + toSort.size() + formatString + ";";
            else
                return "search," + toSort.size() + formatString + ";";
        }
    }
}