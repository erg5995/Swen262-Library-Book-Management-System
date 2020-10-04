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

        Open open = new Open(this, database,calendar);

        Closed closed = new Closed(this, database,calendar);

        states[0] = open;

        states[1] = closed;

        //state = new Open(this, database,calendar);

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
        return state.startVisit(id, calendar.getCurrentTime());
    }

    public String checkOutBook(int userId, List<Integer> bookISBNs){
        //to be handled by states
        return state.checkOutBook(bookISBNs, userId);
    }

    public String checkInBook(int userId, List<Integer> bookISBNs){

        //to be handled by states

        return state.checkOutBook(bookISBNs, userId);

    }

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
        state = states[index];
    }

    public List<Visit> getOngoingVisits() {
        return ongoingVisits;
    }
}
