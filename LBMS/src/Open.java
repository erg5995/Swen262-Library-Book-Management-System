import DataClasses.Book;
import DataClasses.User;
import DataClasses.Visit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public class Open implements SysState {


    private Manager manager;
    private Database database;

    public Open(Manager theManager, Database theDatabase)
    {
        manager = theManager;
        database = theDatabase;
    }
    public String startVisit(int id, LocalDateTime time){

        User user = database.getUser(id);

        manager.addVisit(new Visit(user, time));

        //response format: arrive,visitor ID,visit date, visit start time
        return "arrive, " + id + ", CURRENTDATE, " + time;
    }

    public boolean checkOutBook(Book book){


        //notes: this method is a mess. I think this needs to be passed in a list of books? Because the checks and such
        //are acting like you can check in multiple books
        //this method requires a userID and also a number of books to checkout in order to use canUserCheckOut from tHe database class
        //Also, does this method do the dirty work of adding to the database, and getting the due date and such?


        //add to database
        ArrayList<Integer> books = new ArrayList<Integer>();
        //this is annoying. I get passed in a book, databases search takes in integers, and the isbn is a string *-*
        Integer isbn = Integer.parseInt(book.getIsbn());
        books.add(isbn);
        if((database.checkOutBooks(books))&& (database.userCanCheckOut(1,1))){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkInBook(List<Book> books, User user){
        return false;

    }

}
