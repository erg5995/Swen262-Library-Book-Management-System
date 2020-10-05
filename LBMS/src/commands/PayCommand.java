package commands;

import data_classes.User;
import system.Database;

public class PayCommand implements Command{


    private int userId;
    private double amount;

    private Database database;

    public PayCommand(int userID, double sum, Database data){
        userId = userID;
        amount = sum;
        database = data;
    }

    public String execute(){
        User user = database.getUser(userId);
        if(!database.isValidUser(userId)){
            return "pay,invalid-visitor-id;";
        }
        else if((amount > user.getDebt()) || (amount < 0)){
            return "pay,invalid-amount," + amount + "," + user.getDebt() + ";";
        }
        else{
            //actually pay the debt
            double newBalance = database.pay(userId,amount);
            return "pay,success," + newBalance + ";";
        }
    }
}