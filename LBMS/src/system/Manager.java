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

        //database.saveData();
        // database

        //update fines and overdues
        // to be done in database

    }

    public void startUpSystem(){
        //initialize data from database
        //database.readData();
        //
    }

    public void addVisit(Visit visit){
        ongoingVisits.add(visit);
    }

    //this needs to be added to uml
    public void setState(int index){

        if(index == 1){

        }
        state = states[index];
    }

    public List<Visit> getOngoingVisits() {
        return ongoingVisits;
    }

    public String buy(int numCopiesEach, List<Integer> bookIds){
        Command buyCommand = new BuyCommand(numCopiesEach,bookIds, database);
        return buyCommand.execute();
    }

    public String pay(int userId, double amount){
        Command payCommand = new PayCommand(userId, amount, database);
        return payCommand.execute();
    }

    public String borrowed(int userId){
        Command borrowedCommand = new BorrowedCommand(userId);
        return borrowedCommand.execute();
    }

    public String register(String firstName, String lastName, String address, String phone)
    {
        Command registerCommand = new RegisterCommand(firstName,lastName,address,phone, calendar,database);
        return registerCommand.execute();
    }

    public String depart(int userId){
        Command departCommand = new DepartCommand(userId,this, calendar, database);
        return departCommand.execute();
    }


    public String infoSearch(Book book, boolean forLibrary, BookSortStrategy strategy){
        Command infoSearch = new InfoSearchCommand(book, forLibrary, database, strategy);
        return infoSearch.execute();
    }



    public String dateTime(){
        Command dateTime = new DatetimeCommand();
        return dateTime.execute();
    }

    public String report(int days){
        Command reportCommand = new ReportCommand(days);
        return reportCommand.execute();

    }

    public String advance(int numDays, int numHours){
        Command advanceCommand = new AdvanceCommand(numDays,numHours,calendar,this);
        String result = advanceCommand.execute();
        database.nightlyUpdate(calendar.getCurrentTime().toLocalDate());
        return result;
    }

    public boolean isVisiting(User user)
    {
        for(Visit visit: ongoingVisits){
            if(visit.getVisitor().equals(user)){
                return true;
            }
        }
        return false;

    }

    public void endVisit(Visit visit){
        ongoingVisits.remove(visit);
        LocalDateTime exitTime = calendar.getCurrentTime();
        visit.setExitTime(exitTime);
        database.addVisit(visit);

    }

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
