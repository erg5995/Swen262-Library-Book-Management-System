package system;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {

    private static String ERROR_MSG = "error";
    private static String WRONG_PARAM = "wrong parameters";
    private static String NOT_INTEGER = "must be an integer value";

    private static system.Manager manager = new system.Manager();

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

            if(tokenizedRequest[0].equals("error")) {
                System.out.println("Error: " + tokenizedRequest[1]);
            }

            response = sendRequest(tokenizedRequest);

            System.out.println(response);

        }
    }

    private static String[] parse(String[] request) {

        switch (request[0]) {
            case "buy":
                if(request.length != 3) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }else if(!isNumeric(request[1])) {
                    request[0] = ERROR_MSG;
                    request[1] = "parameter 1 " + NOT_INTEGER;
                }
                return request;
            case "register":
                if(request.length != 5) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }
                return request;
            case "arrive":
            case "depart":
                if(request.length != 2) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }else if(!isNumeric(request[0])) {
                    request[0] = ERROR_MSG;
                    request[1] = "parameter 1 " + NOT_INTEGER;
                }
                return request;
        }

        return new String[0];
    }

    private static String sendRequest(String[] tokenizedRequest) {

        String response = "";

        switch (tokenizedRequest[0]) {
            case "buy":

                int quantity = Integer.parseInt(tokenizedRequest[1]);
                List<Integer> books = new ArrayList<>();

                for(int i = 2; i < tokenizedRequest.length; i++) {
                    books.add(Integer.parseInt(tokenizedRequest[i]));
                }

                response = manager.buy(quantity, books);

                break;
            case "register":

                String firstName = tokenizedRequest[1];
                String lastName = tokenizedRequest[2];
                String address = tokenizedRequest[3];
                String phone = tokenizedRequest[4];

                response = manager.register(firstName, lastName, address, phone);

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
