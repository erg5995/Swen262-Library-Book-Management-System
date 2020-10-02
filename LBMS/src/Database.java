import DataClasses.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    //maybe call readData()?
    public Database() {}

    public boolean hasUser(String fname, String lname, String address, String phone)
    {
        for (User user : users.values())
            if (user.isSame(fname, lname, address, phone))
                return true;
        return false;
    }
}
