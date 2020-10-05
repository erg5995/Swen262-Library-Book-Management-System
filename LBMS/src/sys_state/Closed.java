package sys_state;

import data_classes.Book;
import data_classes.User;
import system.Calendar;
import system.Database;
import system.Manager;

import java.time.LocalDateTime;
import java.util.List;

public class Closed implements SysState {

    private Manager manager;
    private Database database;
    private Calendar calendar;

    public Closed(Manager theManager, Database theDatabase, Calendar theCalendar){
        manager = theManager;
        database = theDatabase;
        calendar = theCalendar;
    }

    public String startVisit(int id, LocalDateTime time){
        return "arrive,library-closed";
    }

    public String checkOutBook(List<Integer> books, int userID){
        //they cannot check out a book while the library is closed
        return "borrow,library-closed";

    }

    public String checkInBook(List<Integer> books, User user){
        //apparently users can check in a book while library is closed, need to handle this
        // set return time to be the next opening time and day


        return "";

    }


}
