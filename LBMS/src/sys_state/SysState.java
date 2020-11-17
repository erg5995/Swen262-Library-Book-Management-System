package sys_state;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for the library states
 *
 * Author: Michael Driscoll
 */
public interface SysState {


    // method to start visit
    public String startVisit(int id, LocalDateTime time);

    // method to check out book
    public String checkOutBook(List<Integer> books, int userID);

    //method to check in book
    public String checkInBook(List<Integer> books, int user);




}
