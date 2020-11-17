package system;

import book_sort_strategy.*;
import data_classes.Book;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client extends Application {

    private static String ERROR_MSG = "error";
    private static String WRONG_PARAM = "wrong parameters";
    private static String NOT_INTEGER = "must be an integer value";

    private static RequestManager requestManager;

    public static void main(String[] args) {
        requestManager = new RequestManager();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Library Book Management System");
        primaryStage.show();
        primaryStage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            requestManager.shutdownSystem();
            System.exit(0);
        });
    }

    /**
     * Parses input string and returns a corresponding error message if found.
     *
     * @param input user's input
     * @return response
     */
    public String input(String input) {
        try {
            String response;
            String[] tokenizedReq;
            String[] confirmedReq;

            tokenizedReq = split(input);
            confirmedReq = errorCheck(tokenizedReq);

            if (confirmedReq[0].equals("error")) {
                return "Error: " + confirmedReq[1];
            }
            response = sendRequest(confirmedReq);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception Caught: " + e + "\nPlease make sure you're input is formatted correctly.";
        }
    }

    /**
     * Splits the user's request string.
     *
     * @param request user's request string
     * @return an array of parameters passed by the user
     */
    private String[] split(String request) {
        int commas = 0;
        boolean count = true;
        List<Integer> splitIdxs = new ArrayList<>();
        for(int i = 0; i < request.length(); i++) {
            if(request.charAt(i) == (',') && count) {
                splitIdxs.add(i);
                commas++;
            }
            else if(request.charAt(i) == ('{')) count = false;
            else if(request.charAt(i) == ('}')) count = true;
        }
        splitIdxs.add(request.length());

        String[] tokenizedRequest = new String[commas + 1];

        int startIdx = -1;
        int endIdx;
        for(int i = 0; i < splitIdxs.size(); i++) {
            if(splitIdxs.get(i) == 0) break;
            endIdx = splitIdxs.get(i);
            tokenizedRequest[i] = request.substring(startIdx + 1, endIdx);
            startIdx = splitIdxs.get(i);
        }

        return tokenizedRequest;
    }

    /**
     * Checks each command for correct arguments.
     *
     * @param request an array of command and its arguments
     * @return a String array of error messages
     */
    private String[] errorCheck(String[] request) {
        if (request.length == 0 || (request.length == 1 && !"report".equals(request[0]) && !"datetime".equals(request[0]))) {
            request = new String[] {ERROR_MSG, "missing arguments"};
            return request;
        }
        switch (request[0]) {
            case "buy":
                if(request.length < 3) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }else if(isNotNumeric(request[1])) {
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
            case "borrowed":
                if(request.length != 2) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }else if(isNotNumeric(request[1])) {
                    request[0] = ERROR_MSG;
                    request[1] = "parameter 1 " + NOT_INTEGER;
                }
                break;
            case "info": 	//info,title,{authors},[isbn, [publisher,[sort order]]];
                if(request.length < 3 || request.length > 6) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }
                checkSort(request);
                break;
            case "search":
                if(request.length > 6) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }
                checkSort(request);
                break;
            case "borrow":
                if(request.length != 3) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }else if(isNotNumeric(request[1])) {
                    request[0] = ERROR_MSG;
                    request[1] = "parameter 1 " + NOT_INTEGER;
                }
                if (request[2].charAt(0) == '{')
                    request[2] = request[2].substring(1, request[2].length() - 1);
                String[] bookIds = request[2].split(",");
                for(String str: bookIds) {
                    if(isNotNumeric(str)) {
                        request[0] = ERROR_MSG;
                        request[1] = "parameter 2 " + NOT_INTEGER;
                        break;
                    }
                }
                break;
            case "return":
                if(request.length < 3) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }

                for(int i = 1; i < request.length; i++) {
                    if(isNotNumeric(request[i])) {
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
                }else if(isNotNumeric(request[1])) {
                    request[0] = ERROR_MSG;
                    request[1] = "parameter 1 " + NOT_INTEGER;
                }else if(isNotNumeric(request[2])) {
                    request[0] = ERROR_MSG;
                    request[1] = "parameter 2 " + NOT_INTEGER;
                }
                break;
            case "advance":
                if(request.length > 3) {
                    request[0] = ERROR_MSG;
                    request[1] = WRONG_PARAM;
                }else{
                    for(int i = 1; i < request.length; i++) {
                        if(isNotNumeric(request[i])) {
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
                } else if (request.length == 2) {
                    if (request[1].isEmpty()) {
                        request = new String[] {"report"};
                    } else if (isNotNumeric(request[1])) {
                        request[0] = ERROR_MSG;
                        request[1] = "parameter " + request[1] + " " + NOT_INTEGER;
                        break;
                    }
                }
            case "datetime":
                break;
            default:
                request[0] = ERROR_MSG;
                request[1] = "unrecognized command";
                break;
        }

        return request;
    }

    /**
     * Calls a corresponding command based on the request received.
     *
     * @param tokenizedRequest an array of command and its arguments
     * @return response from the command
     */
    private String sendRequest(String[] tokenizedRequest) {

        String response = "";

        switch (tokenizedRequest[0]) {
            case "buy":
                int quantity = Integer.parseInt(tokenizedRequest[1]);
                List<Integer> books = new ArrayList<>();

                for(int i = 2; i < tokenizedRequest.length; i++) {
                    books.add(Integer.parseInt(tokenizedRequest[i]) - 1);
                }
                response = requestManager.buy(quantity, books);
                break;
            case "register":
                String firstName = tokenizedRequest[1];
                String lastName = tokenizedRequest[2];
                String address = tokenizedRequest[3];
                String phone = tokenizedRequest[4];
                response = requestManager.register(firstName, lastName, address, phone);
                break;
            case "arrive":
                response = requestManager.startVisit(Integer.parseInt(tokenizedRequest[1]));
                break;
            case "depart":
                response = requestManager.depart(Integer.parseInt(tokenizedRequest[1]));
                break;
            case "info": //info,title,{authors},[isbn, [publisher,[sort order]]];
            case "search":
                int length = tokenizedRequest.length;
                Book bookToFind = new Book(null, tokenizedRequest[1], null, null, null, 0, 0, 0);
                BookSortStrategy strategy = null;

                if ( length >= 3) {
                    String authorList = tokenizedRequest[2];
                    if (authorList.charAt(0) == '{')
                        authorList = authorList.substring(1, authorList.length() - 1);
                    bookToFind.setAuthor(authorList.split(","));
                    if (length >= 4) {
                        bookToFind.setIsbn(tokenizedRequest[3]);
                        if (length >= 5) {
                            bookToFind.setPublisher(tokenizedRequest[4]);
                            if (length == 6)
                                strategy = getBookSortStrategy(tokenizedRequest[5]);
                        }
                    }
                }

                response = requestManager.infoSearch(bookToFind, tokenizedRequest[0].equals("info"), strategy);
                break;
            case "borrow":

                int userId = Integer.parseInt(tokenizedRequest[1]);

                String[] isbns = tokenizedRequest[2].split(",");

                List<Integer> ids = new ArrayList<>();

                for(String str: isbns) {
                    ids.add(Integer.parseInt(str) - 1);
                }

                response = requestManager.checkOutBook(userId, ids);
                break;
            case "borrowed":
                response = requestManager.borrowed(Integer.parseInt(tokenizedRequest[1]));
                break;
            case "return":
                userId = Integer.parseInt(tokenizedRequest[1]);

                List<Integer> bookIds = new ArrayList<>();

                for(int i = 2; i < tokenizedRequest.length; i++) {
                    bookIds.add(Integer.parseInt(tokenizedRequest[i]) - 1);
                }

                response = requestManager.checkInBook(userId, bookIds);

                break;
            case "pay":

                userId = Integer.parseInt(tokenizedRequest[1]);
                int amount = Integer.parseInt(tokenizedRequest[2]);

                response = requestManager.pay(userId, amount);
                break;
            case "advance":

                int numDays = Integer.parseInt(tokenizedRequest[1]);
                int numHours = 0;
                if(tokenizedRequest.length == 3) {
                    numHours = Integer.parseInt(tokenizedRequest[2]);
                }

                response = requestManager.advance(numDays, numHours);
                break;
            case "report":
                int days = 0;
                if(tokenizedRequest.length == 2) {
                    days = Integer.parseInt(tokenizedRequest[1]);
                }

                response = requestManager.report(days);
                break;
            case "datetime":
                response = requestManager.dateTime();
            default:
                break;
        }

        return response;
    }

    /**
     * Gets a book sorting strategy
     *
     * @param strat strategy name
     * @return A Strategy object
     */
    private BookSortStrategy getBookSortStrategy(String strat) {
        switch (strat) {
            case "author":
                return new AuthorSortStrategy();
            case "checkedcopies":
                return new CheckedCopiesSortStrategy();
            case "copies":
                return new CopiesSortStrategy();
            case "publishdate":
                return new PublishDateSortStrategy();
            case "title":
                return new TitleSortStrategy();
        }
        return null;
    }

    /**
     * Checks of the string is numeric
     *
     * @param str string to check
     * @return true if the string is numeric or false
     */
    private boolean isNotNumeric(String str) {
        try {
            Double.parseDouble(str);
            return false;
        } catch(NumberFormatException e){
            return true;
        }
    }

    private void checkSort(String[] request) {
        ArrayList<String> sorts = new ArrayList<>(Arrays.asList("author","title","publishdate","copies","checkedcopies"));
        if (request.length == 6 && !sorts.contains(request[5])) {
            request[0] = ERROR_MSG;
            request[1] = "Invalid sorting order.";
        }
    }
}
