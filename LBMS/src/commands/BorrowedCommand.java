package commands;

public class BorrowedCommand implements Command{

    private int userId;
    public BorrowedCommand(int userID){
        userId = userID;
    }
    public String execute(){
        return "not implemented";
    }
}