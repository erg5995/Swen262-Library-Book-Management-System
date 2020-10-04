import DataClasses.Book;
import DataClasses.User;

import java.time.LocalDateTime;
import java.util.List;

public class Closed implements SysState {

    private Manager manager;

    public String startVisit(int id, LocalDateTime time){
        return "not implemented";
    }

    public boolean checkOutBook(Book book){
        return false;

    }

    public boolean checkInBook(List<Book> books, User user){
        return false;

    }


}
