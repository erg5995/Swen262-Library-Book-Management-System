package system;

import data_classes.Visit;

public interface IManager
{
    boolean isVisiting(int userID);
    Visit getVisit(int userID);
    void endVisit(Visit visit);
}
