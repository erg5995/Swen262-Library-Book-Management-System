import DataClasses.Book;
import DataClasses.User;

import java.time.LocalDateTime;
import java.util.List;

public interface SysState {



    public String startVisit(int id, LocalDateTime time);

    public boolean checkOutBook(Book book);

    public boolean checkInBook(List<Book> books, User user);




}
