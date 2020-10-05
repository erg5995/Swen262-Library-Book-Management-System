package commands;

import data_classes.Book;
import system.Database;

import java.util.List;

public class BuyCommand implements Command{

    private int numCompiesEach;
    private List<Integer> bookIds;
    private Database database;
    public BuyCommand(int numCompies, List<Integer> buybookIds, Database data){
        numCompiesEach = numCompies;
        bookIds = buybookIds;
        database = data;
    }
    public String execute(){

        String bookString = "";

        List<Book> books = database.buyBooks(numCompiesEach,bookIds);


        for(Book book: books){
            bookString = bookString + book.toString()+ "," + numCompiesEach + "\n";
        }

        return "buy,success," + bookIds.size() + "\n" + bookString;
    }
}