import DataClasses.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Database class that stores all required info
 * Author: Thomas Linse
 */
public class Database
{
    private Map<String, Book> booksOwned, booksInStore;
    private List<Book> librarySearch, storeSearch;
    private List<Transaction> checkedOutBooks, returnedBooks;
    private List<Visit> visits;
    private Map<Integer, User> users;
}
