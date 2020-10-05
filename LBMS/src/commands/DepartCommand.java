package commands;

public class DepartCommand implements Command{

    private int userId;

    public DepartCommand(int userID){
        userId = userID;
    }
    public String execute(){
        return "not implemented";
    }
}