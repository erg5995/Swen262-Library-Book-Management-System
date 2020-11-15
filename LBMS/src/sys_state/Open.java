package sys_state;

import data_classes.User;
import data_classes.Visit;
import system.Calendar;
import system.DataStorage;
import system.RequestManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * State class that represents the library in its Open state
 */


public class Open implements SysState {


    private RequestManager requestManager;
    private DataStorage dataStorage;
    private Calendar calendar;

    public Open(RequestManager theRequestManager, DataStorage theDataStorage, Calendar theCalendar)
    {
        requestManager = theRequestManager;
        dataStorage = theDataStorage;
        calendar = theCalendar;
    }

    /**
     *  Starts visit for visitor with id, at the current time
     * @param id - id of user to start visit for
     * @param time - time of visit start
     * @return response in proper format: arrive,visitor ID,visit date, visit start time
     */
    public String startVisit(int id, LocalDateTime time){
        User user = dataStorage.getUser(id);

        //error if user isnt registered
        if(user == null) {
            return "arrive,invalid-id";
        }
        //manager.addVisit(new Visit(user, time));

        //error if user already visiting
        List<Visit> visits = requestManager.getOngoingVisits();

        for(Visit visit: visits){
            if(visit.getVisitor().getId() == user.getId()){
                return "arrive,duplicate";
            }
        }
        requestManager.addVisit(new Visit(user, time));

        //response format: arrive,visitor ID,visit date, visit start time
        return "arrive, " + id + ", " + time.toLocalDate() + ", " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond();
    }

    /**
     * Checks out book for user
     * @param books - ids of books user wants to check out
     * @param userID - id of user who wants to check the books out
     * @return String in proper response format: borrow,due date;
     */
    public String checkOutBook(List<Integer> books, int userID){
        ArrayList<Integer> isbns = new ArrayList<>();
        //response format: borrow,due date;

        //errors: invalid id, invalid book id, book limit exceeded, outstanding fine

        User user = dataStorage.getUser(userID);
        //error: invalid id

        if(user == null){
            return "borrow,invalid-visitor-id";
        }

        //error: invalid book id
        ArrayList<Integer> invalid = new ArrayList<>();
        for (Integer id: books){
            if(!dataStorage.isValidLibraryID(id)){
                invalid.add(id + 1);
            }
        }
        if(!invalid.isEmpty())
        {
            return "borrow,invalid-book-id," + invalid;
        }

        //error: has outstanding fine
        if(user.hasDebt()){
            return "borrow,outstanding-fine," + user.getDebt();
        }

        //error - book limited exceeded
        if(!dataStorage.userCanCheckOut(userID,isbns.size()))
        {
            return "borrow,book-limit-exceeded";
        }
        dataStorage.checkOutBooks(userID, books);
        for(Integer id: books) {
            dataStorage.addTransaction(id, userID, calendar.getCurrentTime().toLocalDate(), calendar.getCurrentTime().toLocalDate().plusDays(7));
        }
        return "borrow," + calendar.getCurrentTime().toLocalDate().plusDays(7);
    }


    /**
     *
     * @param books - list of book ids to return to library
     * @param userID - user who wishes to return the books
     * @return string in the proper response format: return,success;
     */
    public String checkInBook(List<Integer> books, int userID){

        //response format: 	return,success;

        //errors: invalid user id, invalid book ids

        //alternate response: overdue + fine applied
        User user = dataStorage.getUser(userID);
       if(user == null) {
           return "return,invalid-visitor-id";
       }
       else{
           ArrayList<Integer> invalid = new ArrayList<>();
           //if invalid book
           for(Integer id: books){
               if(dataStorage.isNotValidBorrowID(id)){
                   invalid.add(id + 1);
               }
           }
           if(!invalid.isEmpty()){
               //whole transaction is invalid if any books aren't valid
               return "return,invalid-book-id," + invalid;
            }
           String result = dataStorage.returnBooks(user.getId(),books,calendar.getCurrentTime().toLocalDate());
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


