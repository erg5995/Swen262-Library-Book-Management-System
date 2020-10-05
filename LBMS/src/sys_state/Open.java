package sys_state;

import data_classes.Book;
import data_classes.Transaction;
import data_classes.User;
import data_classes.Visit;
import system.Calendar;
import system.Database;
import system.Manager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public class Open implements SysState {


    private Manager manager;
    private Database database;
    private Calendar calendar;

    public Open(Manager theManager, Database theDatabase, Calendar theCalendar)
    {
        manager = theManager;
        database = theDatabase;
        calendar = theCalendar;
    }

    /**
     *  Starts visit for visitor with id, at the current time
     * @param id - id of user to start visit for
     * @param time - time of visit start
     * @return response in proper format: arrive,visitor ID,visit date, visit start time
     */
    public String startVisit(int id, LocalDateTime time){
        User user = database.getUser(id);

        //error if user isnt registered
        if(user == null) {
            return "arrive,invalid-id";
        }
        manager.addVisit(new Visit(user, time));

        //error if user already visiting
        List<Visit> visits = manager.getOngoingVisits();

        for(Visit visit: visits){
            if(visit.getVisitor().getId() == user.getId()){
                return "arrive,duplicate";
            }
        }

        //response format: arrive,visitor ID,visit date, visit start time
        return "arrive, " + id + ", " + time.toLocalDate() + ", " + time;
    }

    /**
     * Checks out book for user
     * @param books - ids of books user wants to check out
     * @param userID - id of user who wants to check the books out
     * @return String in proper response format: borrow,due date;
     */
    public String checkOutBook(List<Integer> books, int userID){
        ArrayList<Integer> isbns = new ArrayList<Integer>();
        //response format: borrow,due date;

        //errors: invalid id, invalid book id, book limit exceeded, outstanding fine

        User user = database.getUser(userID);
        //error: invalid id

        if(user == null){
            return "borrow,invalid-visitor-id";
        }

        //error: invalid book id
        ArrayList<Integer> invalid = new ArrayList<Integer>();
        for (Integer id: books){
            if(!database.isValidBook("" + id)){
                invalid.add(id);
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
        if(!database.userCanCheckOut(userID,isbns.size()))
        {
            return "borrow,book-limit-exceeded";
        }
        database.checkOutBooks(books);
        for(Integer id: books) {
            database.addTransaction(id, user.getId(), calendar.getCurrentTime().toLocalDate().plusDays(7));
        }
        return "borrow," + calendar.getCurrentTime().toLocalDate().plusDays(7);
    }


    /**
     *
     * @param books - list of book ids to return to library
     * @param user - user who wishes to return the books
     * @return string in the proper response format: return,success;
     */
    public String checkInBook(List<Integer> books, User user){

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
               if(!database.isValidBook("" + id)){
                   invalid.add(id);
               }
           }
           if(!invalid.isEmpty()){
               //whole transaction is invalid if any books aren't valid
               return "return,invalid-book-id," + invalid;
            }
           String result = database.returnBooks(user.getId(),books,calendar.getCurrentTime().toLocalDate());
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


