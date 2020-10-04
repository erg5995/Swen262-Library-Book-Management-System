package sys_state;

import data_classes.Book;
import data_classes.User;
import data_classes.Visit;
import system.Database;
import system.Manager;

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

    public boolean checkOutBook(List<Book> books){


        //notes: this method is a mess. I think this needs to be passed in a list of books? Because the checks and such
        //are acting like you can check in multiple books
        //this method requires a userID and also a number of books to checkout in order to use canUserCheckOut from tHe database class
        //Also, does this method do the dirty work of adding to the database, and getting the due date and such?


        //should this return a string? what is the boolean for? i think the response string would make more sense personally.

        //add to database

        ArrayList<Integer> isbns = new ArrayList<Integer>();

        for(Book book: books){
            isbns.add(Integer.parseInt(book.getIsbn()));
        }

        //may need to add parameter to pass in a user id here to use this?
        if((database.checkOutBooks(isbns))&& (database.userCanCheckOut(1,isbns.size()))){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkInBook(List<Book> books, User user){

       if(!database.hasUser(user.getFirstName(),user.getLastName(),user.getAddress(),user.getPhone())) {
           //probably return a string about the failure
       }
       else{
           //if the user has debt, it should return a different string
           if(user.hasDebt()){
               //need something to calculate the debt?
               //double fine =
               //need a way to check which books are overdue, to be able to return the correct IDs,
               //might be besst to access transactions through a getter to check if overdue, and add the fines
           }
           //if the user doesn't have a string, a different string is returned
           else{
               //if invalid book
               //whole transaction is a failure

               //else
               //transaction is a success and should be returned as such.


           }

       }


        return false;

    }

}
