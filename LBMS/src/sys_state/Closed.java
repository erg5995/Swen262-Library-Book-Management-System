package sys_state;

import data_classes.Book;
import data_classes.User;
import system.Manager;

import java.time.LocalDateTime;
import java.util.List;

public class Closed implements SysState {

    private Manager manager;

    public String startVisit(int id, LocalDateTime time){
        return "arrive,library-closed";
    }

    public boolean checkOutBook(List<Book> books){
        //they cannot check out a book while the library is closed

        return false;

    }

    public boolean checkInBook(List<Book> books, User user){
        //apparently users can check in a book while library is closed, need to handle this
        return false;

    }


}
