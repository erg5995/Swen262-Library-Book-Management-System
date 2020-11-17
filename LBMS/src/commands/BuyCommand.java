package commands;

import data_classes.Book;
import system.DataStorage;

import java.util.List;

/**
 * buy,success,n[books];
 * n is the number of books that will follow. If n is 0, no books will follow.
 * Each book will appear on a single line in the format: isbn,title, {authors},publish-date,quantity<nl>
 * isbn is the ISBN for the book.
 * title is the title of the book.
 * authors is the comma-separated list of the book's authors.
 * publish-date is the publication date of the book.
 * quantity is the number of copies of th ebook that were purchased.
 */
public class BuyCommand implements Command{

    private int numCompiesEach;
    private List<Integer> bookIds;
    private DataStorage dataStorage;

    public BuyCommand(int numCompies, List<Integer> buybookIds, DataStorage data){
        numCompiesEach = numCompies;
        bookIds = buybookIds;
        dataStorage = data;
    }

    /**
     * Executes the command
     *
     * @return command output
     */
    public String execute(){

        StringBuilder bookString = new StringBuilder();
        for (int id : bookIds)
            if (!dataStorage.isValidStoreID(id))
                return "buy,invalid-book-id [" + (id + 1) + "]";
        List<Book> books = dataStorage.buyBooks(numCompiesEach,bookIds);


        for(Book book: books){
            bookString.append(book.toString()).append(",").append(numCompiesEach).append("\n");
        }

        return "buy,success," + bookIds.size() + "\n" + bookString + ";";
    }
}