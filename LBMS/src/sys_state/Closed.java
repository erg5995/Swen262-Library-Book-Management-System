package sys_state;

import data_classes.Book;
import data_classes.TimeBetween;
import data_classes.User;
import system.Calendar;
import system.Database;
import system.Manager;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * State class that represents the library in its closed state
 */
public class Closed implements SysState {

    private Manager manager;
    private Database database;
    private Calendar calendar;

    public Closed(Manager theManager, Database theDatabase, Calendar theCalendar){
        manager = theManager;
        database = theDatabase;
        calendar = theCalendar;
    }

    /**
     * Does not start the visit since the library is closed
     * @param id - id of visitor
     * @param time - time to start visit
     * @return String in response format
     */
    public String startVisit(int id, LocalDateTime time){
        return "arrive,library-closed";
    }

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
     * @param user - user who is returning books
     * @return String in response format
     */
    public String checkInBook(List<Integer> books, User user){
        //apparently users can check in a book while library is closed, need to handle this
        // set return time to be the next opening time and day

        //response format: 	return,success;

        //errors: invalid user id, invalid book ids

        //alternate response: overdue + fine applied

        if(!database.hasUser(user.getFirstName(),user.getLastName(),user.getAddress(),user.getPhone())) {
            return "return,invalid-visitor-id";
        }
        else{
            ArrayList<Integer> invalid = new ArrayList<>();
            //if invalid book
            for(Integer id: books){
                if(!database.isValidBorrowID(id)){
                    invalid.add(id);
                }
            }
            if(!invalid.isEmpty()){
                //whole transaction is invalid if any books aren't valid
                return "return,invalid-book-id," + invalid;
            }
            //must calculate time to check in book
            LocalDateTime endTime = calendar.getCurrentTime().plusDays(1).withHour(8);

            String result = database.returnBooks(user.getId(),books,endTime.toLocalDate());
            //returned books and it succeeded
            if (result.equals("success")){
                return "return,success";
            }
            else{
                //this will happen if there is a fine due to overdue books
                return "return," + result;
            }

        }
    }


}
