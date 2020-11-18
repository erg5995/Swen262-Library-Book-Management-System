package sys_state;

import system.Calendar;
import system.DataStorage;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for the library states
 *
 * Author: Michael Driscoll
 */
public abstract class SysState {
    protected DataStorage dataStorage;
    protected Calendar calendar;

    protected SysState(DataStorage ds, Calendar cal) {
        dataStorage = ds;
        calendar = cal;
    }

    // method to start visit
    public abstract String startVisit(int id, LocalDateTime time);

    // method to check out book
    public abstract String checkOutBook(List<Integer> books, int userID);

    //method to check in book
    public abstract String checkInBook(List<Integer> books, int user);

    protected List<Integer> validate(List<Integer> IDs, boolean returning) {
        for(int i = 0; i < IDs.size(); i++)
            if(returning) {
                if (!dataStorage.isNotValidBorrowID(IDs.get(i)))
                    IDs.remove(i--);
            } else if (dataStorage.isValidLibraryID(IDs.get(i)))
                IDs.remove(i--);
            else
                IDs.set(i, IDs.get(i) + 1);
        return IDs;
    }
}
