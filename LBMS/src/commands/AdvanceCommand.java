package commands;

/**
 * Description: For simulation purposes. This method will advance the simulated date of
 * the library ahead by a specified number of days and/or hours. The total number of
 * days/hours advanced must be tracked by the system and added to the current date as
 * appropriate.
 * Request: advance, number-of-days (0-7), [number-of-hours (0-23)];
 *
 */
public class AdvanceCommand implements Command {

    private int numDays;
    private int numHours;

    public AdvanceCommand(int day) {
        this.numDays = day;
    }

    public AdvanceCommand(int day, int hour){
        this.numHours = hour;
    }

    private boolean checkValidDay() {
        return numDays >= 0 && numDays <= 7;
    }

    private boolean checkValidHour() {
        return numHours >= 0 && numHours <= 23;
    }

    public String execute(){
        StringBuilder output = new StringBuilder();
        output.append("advance,");

        if (!checkValidDay()) {
            output.append("invalid-number-of-days,");
            output.append(this.numDays);
            return output.toString();
        }
        if (!checkValidHour()) {
            output.append("invalid-number-of-hours,");
            output.append(this.numHours);
            return output.toString();
        }

        output.append("success");

        return output.toString();
    }
}