package commands;

import data_classes.TimeBetween;
import data_classes.Visit;
import system.Calendar;
import system.Database;
import system.Manager;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DepartCommand implements Command{

    private int userId;
    private Manager manager;
    private Calendar calendar;

    public DepartCommand(int userID, Manager manage, Calendar calend){
        userId = userID;
        manager = manage;
        calendar = calend;
    }
    public String execute() {
        if(!manager.isVisiting(userId)){
            return "depart,invalid-id;";
        }
        else{
            Visit visit = manager.getVisit(userId);
            manager.endVisit(visit);

            LocalTime exit = visit.getExitTime();
            String formatTime = "" + exit.getHour() + ":" + exit.getMinute() + ":" + exit.getSecond() + "";
            int[] duration = TimeBetween.hourMinuteSecond(visit.getEntryTime(),visit.getExitTime());
            return "depart," + userId + "," + formatTime + "," + duration[0] + ":" + duration[1] + ":" + duration[2] + ";";
        }
    }
}