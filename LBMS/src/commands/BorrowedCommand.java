package commands;

import data_classes.Transaction;
import system.Database;

import java.util.List;

/**
 * borrowed,n,[<nl>[books]];
 * n is the number of books that will follow. If n is 0, no books will follow.
 * Each book will appear on a single line in the format: id,,isbn,title, date-borrowed<nl>
 * id is the temporary identifier for each book. IDs are assigned on a per-query basis and are only valid until the next borrowed query is executed.
 * isbn is the ISBN for the book.
 * title is the title of the book.
 * date-borrowed is the date that the book was borrowed in the format YYYY/MM/DD.
 */
public class BorrowedCommand implements Command{

    private int userId;
    private Database database;
    private List<Transaction> borrowedBooks;

    public BorrowedCommand(int userID, Database database){
        this.userId = userID;
        this.database = database;
    }

    public String execute(){
        StringBuilder output = new StringBuilder();
        output.append("borrowed,");

        // check for null
        if (database == null) {
            return "";
        }

        // check for invalid user id
        if (!database.isValidUser(userId)) {
            output.append("invalid-visitor-id");
            return output.toString();
        }

        // check if the user has no books checked out
        borrowedBooks = database.findBorrowedBooks(userId);
        if (borrowedBooks.size() == 0) {
            output.append("0");
            return output.toString();
        }

        for (int i = 0; i < borrowedBooks.size(); i++) {
            output.append("\n");
            output.append(i + 1).append(","); // id
            output.append(borrowedBooks.get(i).getBook().getIsbn()).append(",");
            output.append(borrowedBooks.get(i).getBook().getTitle()).append(",");
            output.append(borrowedBooks.get(i).getDateChecked().toString());
        }

        return output.toString();
    }

}