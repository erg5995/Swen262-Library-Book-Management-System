package commands;
import data_classes.User;
import system.Calendar;
import system.DataStorage;

/**
 * register,first name,last name,address, phone-number;
 * first name is the first name of the visitor.
 * last name is the last name of the visitor.
 * address is the address of the visitor.
 * phone-number is the phone number of the visitor.
 */
public class RegisterCommand implements Command{

    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    private Calendar calendar;
    private DataStorage dataStorage;

    public RegisterCommand(String first, String last, String addy, String phoneNum, Calendar cal, DataStorage data){
        firstName = first;
        lastName = last;
        address = addy;
        phone = phoneNum;

        calendar = cal;
        dataStorage = data;
    }

    /**
     * Executes the command
     *
     * @return command output
     */
    public String execute() {
        if(dataStorage.hasUser(firstName,lastName,address,phone)){
            return "register,duplicate;";
        }
        else {
            User user = new User(firstName, lastName, address, phone);
            dataStorage.addUser(user);
            return "register," + String.format("%010d", user.getId()) + "," + calendar.getCurrentTime().toLocalDate() + ";";
        }
    }
}
