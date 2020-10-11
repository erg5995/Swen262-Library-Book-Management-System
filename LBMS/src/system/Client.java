package system;

import book_sort_strategy.BookSortStrategy;
import book_sort_strategy.CopiesSortStrategy;
import book_sort_strategy.PublishDateSortStrategy;
import book_sort_strategy.TitleSortStrategy;
import data_classes.Book;

import java.util.ArrayList;
import java.util.Arrays;
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
        String[] tokenizedReq = {""};
        String[] confirmedReq = {""};

        while(true) {

            System.out.print("> ");
            input = scanner.nextLine();
            tokenizedReq = split(input);
            confirmedReq = errorCheck(tokenizedReq);

            if(confirmedReq[0].equals("error")) {
                System.out.println("> Error: " + confirmedReq[1]);
            }

            response = sendRequest(confirmedReq);

            System.out.println("> " + response);

        }
    }

    private static String[] split(String request) {
        int commas = 0;
        boolean count = true;
        int[] splitIdxs = new int[request.length()]; //This should p̶r̶o̶b̶a̶b̶l̶y̶  definitely be a list, but whatever
        int splitIdx = 0;
        for(int i = 0; i < request.length(); i++) {
            if(request.charAt(i) == (',') && count) {
                splitIdxs[splitIdx++] = i;
                commas++;
            }
            if(request.charAt(i) == ('{')) count = false;
            if(request.charAt(i) == ('}')) count = true;
        }
        splitIdxs[splitIdx] = request.length();

        String[] tokenizedRequest = new String[commas + 1];

        int startIdx = -1;
        int endIdx = 0;
        for(int i = 0; i < splitIdxs.length; i++) {
            if(splitIdxs[i] == 0) break;
            endIdx = splitIdxs[i];
            tokenizedRequest[i] = request.substring(startIdx + 1, endIdx);
            startIdx = splitIdxs[i];
        }

        return tokenizedRequest;
    }

    private static String[] errorCheck(String[] request) {

        switch (request[0]) {
            case "buy":
                if(request.length != 3) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }else if(!isNumeric(request[1])) {
                    request[0] = ERROR_MSG;
                    request[1] = "parameter 1 " + NOT_INTEGER;
                }
                break;
            case "register":
                if(request.length != 5) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }
                break;
            case "arrive":
            case "depart":
                if(request.length != 2) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }else if(!isNumeric(request[1])) {
                    request[0] = ERROR_MSG;
                    request[1] = "parameter 1 " + NOT_INTEGER;
                }
                break;
            case "info": 	//info,title,{authors},[isbn, [publisher,[sort order]]],bool;
                if(request.length < 4 || request.length > 7) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }
                break;
            case "borrow":
                if(request.length != 3) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }else if(!isNumeric(request[1])) {
                    request[0] = ERROR_MSG;
                    request[1] = "parameter 1 " + NOT_INTEGER;
                }

                String[] bookIds = request[2].substring(1, request[2].length() - 1).split(",");
                for(String str: bookIds) {
                    if(!isNumeric(str)) {
                        request[0] = ERROR_MSG;
                        request[1] = "parameter 2 " + NOT_INTEGER;
                        break;
                    }
                }
                break;
            case "borrowed":
                if(request.length != 3) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }else if(!isNumeric(request[1])) {
                    request[0] = ERROR_MSG;
                    request[1] = "parameter 1 " + NOT_INTEGER;
                }
                break;
            case "return":
                if(request.length < 3) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }

                for(int i = 1; i < request.length; i++) {
                    if(!isNumeric(request[i])) {
                        request[0] = ERROR_MSG;
                        request[1] = "parameter " + i + " " + NOT_INTEGER;
                        break;
                    }
                }
                break;
            case "pay":
                if(request.length != 3) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }else if(!isNumeric(request[1])) {
                    request[0] = ERROR_MSG;
                    request[1] = "parameter 1 " + NOT_INTEGER;
                }else if(!isNumeric(request[2])) {
                    request[0] = ERROR_MSG;
                    request[1] = "parameter 2 " + NOT_INTEGER;
                }
                break;
            case "advance":
                if(request.length < 2 || request.length > 3) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }else{
                    for(int i = 1; i < request.length; i++) {
                        if(!isNumeric(request[i])) {
                            request[0] = ERROR_MSG;
                            request[1] = "parameter " + i + " " + NOT_INTEGER;
                            break;
                        }
                    }
                }
                break;
            case "report":
                if(request.length > 2) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }else{
                    for(int i = 1; i < request.length; i++) {
                        if(!isNumeric(request[i])) {
                            request[0] = ERROR_MSG;
                            request[1] = "parameter " + i + " " + NOT_INTEGER;
                            break;
                        }
                    }
                }
            case "datetime":
            default:
                break;
        }

        return request;
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
                break;
            case "arrive":
                response = manager.startVisit(Integer.parseInt(tokenizedRequest[1]));
                break;
            case "depart":
                response = manager.depart(Integer.parseInt(tokenizedRequest[1]));
                break;
            case "info": //info,title,{authors},[isbn, [publisher,[sort order]]],bool;

                String title = tokenizedRequest[1];
                String authorList = tokenizedRequest[2].substring(1, tokenizedRequest[2].length() - 1);
                String[] authors = authorList.split(",");

                String searchingLibrary = tokenizedRequest[tokenizedRequest.length - 1];
                boolean forLibrary = searchingLibrary.equals("true");

                int length = tokenizedRequest.length;

                Book bookToFind = new Book(null, null, null, null, null, 0, 0, 0);
                BookSortStrategy strategy = null;

                if(length == 4) {
                    bookToFind = new Book(null, title, authors, null, null, 0, 0, 0);
                }else if(length == 5) {
                    bookToFind = new Book(tokenizedRequest[3], title, authors, null, null, 0, 0, 0);
                }else if(length == 6) {
                    bookToFind = new Book(tokenizedRequest[3], title, authors, tokenizedRequest[4], null, 0, 0, 0);
                }else if(length == 7) {
                    bookToFind = new Book(tokenizedRequest[3], title, authors, tokenizedRequest[4], null, 0, 0, 0);
                    String strat = tokenizedRequest[5];

                    if(strat.equals("author")) {
//                        strategy = new AuthorSortStrategy();
                    }else if(strat.equals("checkedcopies")) {
//                        strategy = new CheckedCopiesSortStrategy();
                    }else if(strat.equals("copies")) {
                        strategy = new CopiesSortStrategy();
                    }else if(strat.equals("publishdate")) {
                        strategy = new PublishDateSortStrategy();
                    }else if(strat.equals("title")) {
                        strategy = new TitleSortStrategy();
                    }
                }

                response = manager.infoSearch(bookToFind, forLibrary, strategy);
                break;
            case "borrow":

                int userId = Integer.parseInt(tokenizedRequest[1]);

                String[] isbns = tokenizedRequest[2].substring(1, tokenizedRequest[2].length() - 1).split(",");

                List<Integer> ids = new ArrayList<>();

                for(String str: isbns) {
                    ids.add(Integer.parseInt(str));
                }

                response = manager.checkOutBook(userId, ids);
                break;
            case "borrowed":
                response = manager.borrowed(Integer.parseInt(tokenizedRequest[1]));
                break;
            case "return":
                userId = Integer.parseInt(tokenizedRequest[1]);

                List<Integer> bookIds = new ArrayList<>();

                for(int i = 2; i < tokenizedRequest.length; i++) {
                    bookIds.add(Integer.parseInt(tokenizedRequest[i]));
                }

                manager.checkInBook(userId, bookIds);

                break;
            case "pay":

                userId = Integer.parseInt(tokenizedRequest[1]);
                int amount = Integer.parseInt(tokenizedRequest[2]);

                response = manager.pay(userId, amount);
                break;
            case "advance":

                int numDays = Integer.parseInt(tokenizedRequest[1]);
                int numHours = 0;
                if(tokenizedRequest.length == 3) {
                    numHours = Integer.parseInt(tokenizedRequest[2]);
                }

                response = manager.advance(numDays, numHours);
                break;
            case "report":
                int days = 0;
                if(tokenizedRequest.length == 2) {
                    days = Integer.parseInt(tokenizedRequest[1]);
                }

                response = manager.report(days);
                break;
            case "datetime":
                response = manager.dateTime();
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
