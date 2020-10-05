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
    private Database database;

    public DepartCommand(int userID, Manager manage, Calendar calend, Database data){
        userId = userID;
        manager = manage;
        calendar = calend;
        database = data;
    }
    public String execute() {
        if(!manager.isVisiting(database.getUser(userId))){
            return "depart,invalid-id;";
        }
        else{
            Visit visit = manager.getVisit(database.getUser(userId));
            manager.endVisit(visit);

            LocalTime exit = visit.getExitTime();
            String formatTime = "" + exit.getHour() + ":" + exit.getMinute() + ":" + exit.getSecond() + "";
            int[] duration = TimeBetween.hourMinuteSecond(visit.getEntryTime(),visit.getExitTime());
            return "depart," + userId + "," + formatTime + "," + duration[0] + ":" + duration[1] + ":" + duration[2] + ";";
        }
    }
}