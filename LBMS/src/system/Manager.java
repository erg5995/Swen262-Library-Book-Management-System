package system;

import book_sort_strategy.BookSortStrategy;
import commands.*;
import data_classes.Book;
import data_classes.User;
import data_classes.Visit;
import sys_state.Closed;
import sys_state.Open;
import sys_state.SysState;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager class - in charge of all system operations, and executing various commands. Holds the system Calendar and Database
 *
 * Author: Michael Driscoll
 */
public class Manager {

    private Calendar calendar;

    private Database database;

    private SysState state;

    private List<Visit> ongoingVisits;

    public SysState[] states;


    /**
     * Constructor for the Manager object
     * Creates states and puts them into an array for easy switching.
     */
    public Manager(){
        database = new Database();
        calendar = new Calendar();

        Open open = new Open(this, database,calendar);

        Closed closed = new Closed(this, database,calendar);

        states = new SysState[2];

        states[0] = open;

        states[1] = closed;

        ongoingVisits = new ArrayList<Visit>();
    }

    /**
     * Starts a visit for the indicated user
     * Utilizes the State pattern to handle starting a visit depending on the Library's state (open or closed)
     * @param id id of the user to start visit for
     * @return String in response format
     */
    public String startVisit(int id)
    {
        //this will be handled by the states.
        return state.startVisit(id, calendar.getCurrentTime());
    }

    /**
     * Checks out book for the indicated user
     * Utilizes the State pattern to handle book check outs depending on the Library's state(open or closed)
     * @param userId - id of the user who wishes to check out books
     * @param bookISBNs - list of book ids to check out
     * @return String in response format
     */
    public String checkOutBook(int userId, List<Integer> bookISBNs){
        //to be handled by states
        return state.checkOutBook(bookISBNs, userId);
    }

    /**
     * Checks in books for the indicated user
     * Utilizes State pattern to handle the check in depending on the Library's state.
     * @param userId - id of the user to check in the books
     * @param bookISBNs - list of book ids to check in
     * @return String in response format
     */
    public String checkInBook(int userId, List<Integer> bookISBNs){
        //to be handled by states
        return state.checkOutBook(bookISBNs, userId);

    }

    /**
     * Method for shutting down the system
     * Ends all ongoing visits, and reads them into the database.
     * Saves data before shutdown.
     */
    public void shutdownSystem(){
        //end all ongoing visits here, and save data in database

        for(Visit visit: ongoingVisits){
            LocalDateTime exitTime = calendar.getCurrentTime();
            visit.setExitTime(exitTime);
            database.addVisit(visit);
        }
        ongoingVisits.clear();
        //saves data into database for safe shutdown
        database.saveData();

    }

    /**
     * starts up system - tells database to re-initialize it's data.
     */
    public void startUpSystem(){
        //initialize data from database
        database.readData();
    }

    /**
     * adds visit to ongoingVisits list
     * @param visit - visit to add
     */
    public void addVisit(Visit visit){
        ongoingVisits.add(visit);
    }

    /**
     * Changes library state to a new state depending on the specified index
     * @param index - index of states array to set to: 0 sets it to open, 1 sets it to closed
     */
    public void setState(int index){

        if(index == 1){
            database.nightlyUpdate(calendar.getCurrentTime().toLocalDate().plusDays(1));
        }
        state = states[index];
    }

    /**
     * returns list of ongoing visits
     * @return list of ongoing visits
     */
    public List<Visit> getOngoingVisits() {
        return ongoingVisits;
    }

    /**
     * Creates and executes the Buy Command
     * @param numCopiesEach - num copies to buy
     * @param bookIds - id of books to buy
     * @return String in response format
     */
    public String buy(int numCopiesEach, List<Integer> bookIds){
        Command buyCommand = new BuyCommand(numCopiesEach,bookIds, database);
        return buyCommand.execute();
    }

    /**
     * Creates and executes the Pay Command
     * @param userId - user who is paying fines
     * @param amount - amount to pay off
     * @return String in response format
     */
    public String pay(int userId, double amount){
        Command payCommand = new PayCommand(userId, amount, database);
        return payCommand.execute();
    }

    /**
     * Creates and executes the Borrowed Command
     * @param userId - User who has borrowed books
     * @return String in response format
     */
    public String borrowed(int userId){
        Command borrowedCommand = new BorrowedCommand(userId);
        return borrowedCommand.execute();
    }

    /**
     * Creates and executes Register Command
     * @param firstName - users first name
     * @param lastName -  users last name
     * @param address - users address
     * @param phone - users phone number
     * @return String in response format
     */
    public String register(String firstName, String lastName, String address, String phone)
    {
        Command registerCommand = new RegisterCommand(firstName,lastName,address,phone, calendar,database);
        return registerCommand.execute();
    }

    /**
     * Creates and executes depart Command
     * @param userId - id of user who is leaving
     * @return String in response format
     */
    public String depart(int userId){
        Command departCommand = new DepartCommand(userId,this, calendar, database);
        return departCommand.execute();
    }


    /**
     * Creates and executes infoSearch command
     * boolean dictates whether the search is in the library or book store.
     * @param book - Query book to search for
     * @param forLibrary - true if library search, false if for bookstore search
     * @param strategy - strategy to sort results by
     * @return String in response format
     */
    public String infoSearch(Book book, boolean forLibrary, BookSortStrategy strategy){
        Command infoSearch = new InfoSearchCommand(book, forLibrary, database, strategy);
        return infoSearch.execute();
    }


    /**
     * Creates and executes DateTime Command
     * @return String in response format
     */
    public String dateTime(){
        Command dateTime = new DatetimeCommand();
        return dateTime.execute();
    }

    /**
     * Creates and executes Report Command
     * @param days - number of days to report upon
     * @return String in response format
     */
    public String report(int days){
        Command reportCommand = new ReportCommand(days);
        return reportCommand.execute();

    }

    /**
     * Creates and executes advance command
     * @param numDays - num of days to advance
     * @param numHours - num of hours to advance
     * @return String in response format
     */
    public String advance(int numDays, int numHours){
        Command advanceCommand = new AdvanceCommand(numDays,numHours,calendar);
        String result = advanceCommand.execute();
        database.nightlyUpdate(calendar.getCurrentTime().toLocalDate());
        return result;
    }

    /**
     * Tells if user is actively in a visit
     * @param user - user to check
     * @return boolean - true if visitor is currently in a visit
     */
    public boolean isVisiting(User user)
    {
        for(Visit visit: ongoingVisits){
            if(visit.getVisitor().equals(user)){
                return true;
            }
        }
        return false;

    }

    /**
     * Removes visitor from ongoing visits list
     * @param visit - visit to end
     */
    public void endVisit(Visit visit){
        ongoingVisits.remove(visit);
        LocalDateTime exitTime = calendar.getCurrentTime();
        visit.setExitTime(exitTime);
        database.addVisit(visit);

    }

    /**
     * Returns visit by user
     * @param user - user to get visit by
     * @return Visit with the specified user
     */
    public Visit getVisit(User user){
        for(Visit visit: ongoingVisits){
            if(visit.getVisitor().equals(user)){
                return visit;
            }
        }
        //this shouldn't ever happen
        return null;
    }

}
