package commands;

import book_sort_strategy.BookSortStrategy;
import data_classes.Book;
import system.DataStorage;

import java.util.List;

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