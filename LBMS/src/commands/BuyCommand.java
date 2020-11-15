package commands;

import data_classes.Book;
import system.DataStorage;

import java.util.List;

public class BuyCommand implements Command{

    private int numCompiesEach;
    private List<Integer> bookIds;
    private DataStorage dataStorage;
    public BuyCommand(int numCompies, List<Integer> buybookIds, DataStorage data){
        numCompiesEach = numCompies;
        bookIds = buybookIds;
        dataStorage = data;
    }
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