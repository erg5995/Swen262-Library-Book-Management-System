package commands;

import data_classes.User;
import system.DataStorage;

public class PayCommand implements Command{


    private int userId;
    private double amount;

    private DataStorage dataStorage;

    public PayCommand(int userID, double sum, DataStorage data){
        userId = userID;
        amount = sum;
        dataStorage = data;
    }

    public String execute(){
        User user = dataStorage.getUser(userId);
        if(dataStorage.isNotValidUser(userId)){
            return "pay,invalid-visitor-id;";
        }
        else if((amount > user.getDebt()) || (amount < 0)){
            return "pay,invalid-amount," + amount + "," + user.getDebt() + ";";
        }
        else{
            //actually pay the debt
            double newBalance = dataStorage.pay(userId,amount);
            return "pay,success," + newBalance + ";";
        }
    }
}