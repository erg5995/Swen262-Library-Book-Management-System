package commands;

public class RegisterCommand implements Command{


    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public RegisterCommand(String first, String last, String addy, String phoneNum){
        firstName = first;
        lastName = last;
        address = addy;
        phone = phoneNum;
    }
    public String execute(){
        return "not implemented";
    }
}
