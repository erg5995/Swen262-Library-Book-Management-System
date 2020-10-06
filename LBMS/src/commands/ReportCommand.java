package commands;

public class ReportCommand implements Command{



    private int numDays;
    public ReportCommand(int days){
        numDays = days;
    }
    public String execute(){
        return "not implemented";
    }
}