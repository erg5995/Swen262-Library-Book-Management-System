package commands;

import data_classes.Visit;
import system.RequestManager;
import java.time.LocalTime;

/**
 * depart,visitor ID,visit end time,visit duration;
 * visitor ID is the unique, 10-digit visitor identifier.
 * visit end time is the end time of the visit in the format HH:MM:SS where HH is an hour from 00 (midnight) to 23 (11PM).
 * visit duration is the duration of the visit in the format hh:mm:ss (hours, minutes, seconds)
 */

public class DepartCommand implements Command{

    private int userId;
    private RequestManager requestManager;

    public DepartCommand(int userID, RequestManager manage){
        userId = userID;
        requestManager = manage;
    }

    /**
     * Executes the command
     *
     * @return command output
     */
    public String execute() {
        if(!requestManager.isVisiting(userId)){
            return "depart,invalid-id;";
        }
        else{
            Visit visit = requestManager.getVisit(userId);
            requestManager.endVisit(visit);

            LocalTime exit = visit.getExitTime();
            String formatTime = "" + exit.getHour() + ":" + exit.getMinute() + ":" + exit.getSecond() + "";
            int[] duration = visit.getTimeSpent();
            return "depart," + userId + "," + formatTime + "," + duration[0] + ":" + duration[1] + ":" + duration[2] + ";";
        }
    }
}