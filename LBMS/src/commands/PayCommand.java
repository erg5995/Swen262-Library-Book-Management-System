package commands;

public class PayCommand implements Command{


    private int userId;
    private double amount;
    public PayCommand(int userID, double sum){
        userId = userID;
        amount = sum;
    }

    public String execute(){
        return "not implemented";
    }
}