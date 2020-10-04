package sys_state;

import data_classes.Book;
import data_classes.User;

import java.time.LocalDateTime;
import java.util.List;

public interface SysState {



    public String startVisit(int id, LocalDateTime time);

    public boolean checkOutBook(List<Integer> books);

    public boolean checkInBook(List<Integer> books, User user);




}
