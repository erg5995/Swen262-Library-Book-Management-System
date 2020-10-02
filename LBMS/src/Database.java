import DataClasses.*;

import java.util.*;

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
    public void addUser(User user) { users.put(user.getId(), user); }
    public User getUser(int id) { return users.get(id); }

    public void addVisit(Visit visit) { visits.add(visit); }

    public List<Book> bookInfoSearch(Book param, boolean forLibrary)
    {
        if (forLibrary) {
            searchBooks(param, booksOwned.values(), librarySearch);
            return librarySearch;
        }
        searchBooks(param, booksInStore.values(), storeSearch);
        return storeSearch;
    }
    private void searchBooks(Book param, Collection<Book> search, List<Book> found)
    {
        found.clear();
        for (Book book : search) {
            if (!param.getTitle().equals("*") && !param.getTitle().equals(book.getTitle()))
                continue;
            if (!checkAuth(param.getAuthor(), book.getAuthor()))
                continue;
            if (param.getIsbn() != null && !param.getIsbn().equals("*") && !param.getIsbn().equals(book.getIsbn()))
                continue;
            if (param.getPublisher() != null && !param.getPublisher().equals("*") && !param.getPublisher().equals(book.getPublisher()))
                continue;
            found.add(book);
        }
    }
    private boolean checkAuth(String[] pAuth, String[] bAuth)
    {
        if (pAuth == null)
            return true;
        boolean hasAuth;
        for (String auth : pAuth)
            if (!auth.equals("*")) {
                hasAuth = false;
                for (String check : bAuth)
                    if (auth.equals(check)) {
                        hasAuth = true;
                        break;
                    }
                if (!hasAuth)
                    return false;
            }
        return true;
    }
}
