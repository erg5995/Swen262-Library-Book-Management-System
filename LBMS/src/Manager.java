import DataClasses.Book;
import DataClasses.User;
import DataClasses.Visit;
import SysStates.Open;
import SysStates.SysState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Manager {

    //private Calendar calendar;

    private Database database;

    private SysState state;

    private List<Visit> ongoingVisits;

    public Manager(){
        database = new Database();
        //calendar = new Calendar();

        state = new Open();

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
        state.checkOutBook(new Book("","", new String[]{""},"","",0,0,0));
        return "not implemented";
    }

    public String checkInBook(int userId, List<Integer> bookISBNs){
        //to be handled by states
        //placeholder
        state.checkOutBook(new Book("","", new String[]{""},"","",0,0,0));
        return "not implemented";
    }

    public void shutdownSystem(){
        //idk what to do here
    }

    public void startUpSystem(){
        //idk what to do here
    }

    public void addVisit(Visit visit){
        ongoingVisits.add(visit);
    }




}
