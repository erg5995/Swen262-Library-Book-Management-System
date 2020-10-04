import DataClasses.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {

//    Manager manager = new Manager();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String input = "";
        String response = "";
        String[] request = {""};
        String[] tokenizedRequest = {""};

        while(true) {

            input = scanner.nextLine();
            request = input.split(",");
            tokenizedRequest = parse(request);
            response = sendRequest(tokenizedRequest);

        }
    }

    private static String[] parse(String[] request) {

        switch (request[0]) {
            case "buy":
                if(request.length < 3) {
                    return null; //temporary solution, exceptions would be very helpful here
                }else if(!isNumeric(request[1])) {
                    return null;
                } else {
                    return request;
                }
        }

        return new String[0];
    }

    private static String sendRequest(String[] tokenizedRequest) {

        String response = "";

        switch (tokenizedRequest[0]) {
            case "buy":

                int quantity = Integer.parseInt(tokenizedRequest[1]);
                List<String> books = new ArrayList<>();

                for(int i = 2; i < tokenizedRequest.length; i++) {
                    books.add(tokenizedRequest[i]);
                }

//                ICommand buy = new BuyCommand(quantity, books);
//                response = buy.execute();

                break;

            default:
                break;
        }

        return response;
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
