package sys_state;

import data_classes.User;
import system.Calendar;
import system.DataStorage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * State class that represents the library in its closed state
 */
public class Closed extends SysState {

    public Closed(DataStorage theDataStorage, Calendar theCalendar){ super(theDataStorage, theCalendar); }

    /**
     * Does not start the visit since the library is closed
     * @param id - id of visitor
     * @param time - time to start visit
     * @return String in response format
     */
    public String startVisit(int id, LocalDateTime time){ return "arrive,library-closed"; }

    /**
     * check out book method, does not check out book since LBMS does not perform
     * that task while in the closed state.
     * @param books - books to not check out
     * @param userID - id of user to check out
     * @return String in response format
     */
    public String checkOutBook(List<Integer> books, int userID){
        //they cannot check out a book while the library is closed
        return "borrow,library-closed";
    }

    /**
     * checks in book and treats it like it was checked in the next day
     * @param books - books to check in
     * @param userID - user who is returning books
     * @return String in response format
     */
    public String checkInBook(List<Integer> books, int userID){
        // set return time to be the next opening time and day
        //response format: 	return,success;
        //errors: invalid user id, invalid book ids

        //alternate response: overdue + fine applied
        User user = dataStorage.getUser(userID);
        if(user == null) {
            return "return,invalid-visitor-id";
        }

        List<Integer> invalid = validate(new ArrayList<>(books), true);
        if(!invalid.isEmpty()){
            //whole transaction is invalid if any books aren't valid
            return "return,invalid-book-id," + invalid;
        }
        //must calculate time to check in book
        LocalDateTime endTime = calendar.getCurrentTime().plusDays(1).withHour(8);

        String result = dataStorage.returnBooks(user.getId(),books,calendar.getCurrentTime().toLocalDate());
        return (books.isEmpty() ? "" : books.toString() + " were unable to be returned because either they've been " +
                "returned already, or you do not have them checked out.\n") + "return," + result;
    }


}
