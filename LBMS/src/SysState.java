import DataClasses.Book;
import DataClasses.User;

import java.time.LocalDateTime;
import java.util.List;

public interface SysState {



    public String startVisit(int id, LocalDateTime time);

    public boolean checkOutBook(List<Book> books);

    public boolean checkInBook(List<Book> books, User user);




}
