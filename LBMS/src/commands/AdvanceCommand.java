package commands;

public class AdvanceCommand implements Command{


    private int numDays;
    private int numHours;

    public AdvanceCommand(int days, int hours){
        numDays = days;
        numHours = hours;
    }
    public String execute(){
        return "not implemented";
    }
}