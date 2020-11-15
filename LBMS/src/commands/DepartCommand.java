package commands;

import data_classes.Visit;
import system.RequestManager;
import java.time.LocalTime;

public class DepartCommand implements Command{

    private int userId;
    private RequestManager requestManager;

    public DepartCommand(int userID, RequestManager manage){
        userId = userID;
        requestManager = manage;
    }
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