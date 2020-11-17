package system;

import data_classes.Visit;

/**
 * An interface for the manager class
 */
public interface IManager
{
    boolean isVisiting(int userID);
    Visit getVisit(int userID);
    void endVisit(Visit visit);
}
