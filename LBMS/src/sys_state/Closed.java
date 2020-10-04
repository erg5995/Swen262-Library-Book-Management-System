package sys_state;

import data_classes.Book;
import data_classes.User;
import system.Database;
import system.Manager;

import java.time.LocalDateTime;
import java.util.List;

public class Closed implements SysState {

    private Manager manager;
    private Database database;

    public Closed(Manager theManager, Database theDatabase){
        manager = theManager;
        database = theDatabase;
    }

    public String startVisit(int id, LocalDateTime time){
        return "arrive,library-closed";
    }

    public String checkOutBook(List<Integer> books){
        //they cannot check out a book while the library is closed

        return "";

    }

    public String checkInBook(List<Integer> books, User user){
        //apparently users can check in a book while library is closed, need to handle this
        return "";

    }


}
