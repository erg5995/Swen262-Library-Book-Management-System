package system;

import data_classes.Book;
import data_classes.Visit;
import sys_state.Closed;
import sys_state.Open;
import sys_state.SysState;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Manager {

    private Calendar calendar;

    private Database database;

    private SysState state;

    private List<Visit> ongoingVisits;

    public SysState[] states;




    public Manager(){
        database = new Database();
        calendar = new Calendar();

        Open open = new Open(this, database);

        Closed closed = new Closed(this, database);

        states[0] = open;

        states[1] = closed;

        state = new Open(this, database);

        ongoingVisits = new ArrayList<Visit>();

    }

    /**
     *
     * @param id id of the user to start visit for
     * @return
     */
    public String startVisit(int id)
    {

        //this will be handled by the states.



        //placeholder
        state.startVisit(id, LocalDateTime.MAX);
        return state.startVisit(id, LocalDateTime.MAX);


    }

    public String checkOutBook(int userId, List<Integer> bookISBNs){
        //to be handled by states

        //placeholder
        ArrayList<Book> books = new ArrayList<Book>();
        books.add(new Book("","", new String[]{""},"","",0,0,0));

        state.checkOutBook(bookISBNs);
        return "not implemented";
    }

    public String checkInBook(int userId, List<Integer> bookISBNs){

        //to be handled by states
        //placeholder
        ArrayList<Book> books = new ArrayList<Book>();
        books.add(new Book("","", new String[]{""},"","",0,0,0));
        state.checkOutBook(bookISBNs);
        return "not implemented";
    }

    public void shutdownSystem(){
        //end all ongoing visits here, and save data in database

        for(Visit visit: ongoingVisits){

            //placeholder, will need to return the time from the calendar class
            // LocalDateTime exitTime = calendar.getDateTime();
            LocalDateTime exitTime = LocalDateTime.now();
            visit.setExitTime(exitTime);
            database.addVisit(visit);

        }
        ongoingVisits.clear();

        //database.saveData();
    }

    public void startUpSystem(){
        //initialize data from database
        //database.readData();
        //maybe update fines, and overdue book booleans?
    }

    public void addVisit(Visit visit){
        ongoingVisits.add(visit);
    }

    //this needs to be added to uml
    public void setState(int index){
        state = states[index];
    }




}
