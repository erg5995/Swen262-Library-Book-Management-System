package commands;
import data_classes.User;
import system.Calendar;
import system.Database;

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
    private Database database;

    public RegisterCommand(String first, String last, String addy, String phoneNum, Calendar cal, Database data){
        firstName = first;
        lastName = last;
        address = addy;
        phone = phoneNum;

        calendar = cal;
        database = data;
    }
    public String execute() {
        if(database.hasUser(firstName,lastName,address,phone)){
            return "register,duplicate;";
        }
        else {
            User user = new User(firstName, lastName, address, phone);
            database.addUser(user);
            return "register," + user.getId() + "," + calendar.getCurrentTime().toLocalDate() + ";";
        }
    }
}
