package commands;

import data_classes.Book;

public class InfoSearchCommand implements Command{

    private Book book;

    private boolean forLibrary;

    public InfoSearchCommand(Book theBook, boolean forTheLibrary){
        book = theBook;
        forLibrary = forTheLibrary;
    }
    public String execute(){
        return "not implemented";
    }
}