package commands;

import java.util.List;

public class BuyCommand implements Command{

    private int numCompiesEach;
    private         List<Integer> bookIds;
    public BuyCommand(int numCompies, List<Integer> buybookIds){
        numCompiesEach = numCompies;
        bookIds = buybookIds;
    }
    public String execute(){
        return "not implemented";
    }
}