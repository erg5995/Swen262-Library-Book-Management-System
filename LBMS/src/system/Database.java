package system;

import data_classes.*;

import java.time.LocalDate;
import java.util.*;

/**
 * system.Database class that stores all required info
 * Author: Thomas Linse
 */
public class Database
{
    private Map<String, Book> booksOwned, booksInStore;
    private List<Book> librarySearch, storeSearch;
    private List<Transaction> checkedOutBooks, returnedBooks, borrowSearch;
    private List<Visit> visits;
    private Map<Integer, User> users;

    //maybe call readData()?
    public Database() {}

    public void addUser(User user) { users.put(user.getId(), user); }
    public void addUser(String fname, String lname, String address, String phone)
    {
        addUser(new User(fname, lname, address, phone));
    }
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

    public boolean checkOutBooks(List<Integer> books)
    {
        for (int id : books)
            if (librarySearch.get(id).getNumCopiesLeft() == 0)
                return false;
        for (int id : books)
            librarySearch.get(id).checkOutCopy();
        return true;
    }
    public void addTransaction(Transaction trans) { checkedOutBooks.add(trans); }
    public void addTransaction(Book book, User user, LocalDate dueDate)
    {
        addTransaction(new Transaction(book, user, dueDate));
    }
    public void addTransaction(int bookID, int userID, LocalDate dueDate)
    {
        addTransaction(librarySearch.get(bookID), users.get(userID), dueDate);
    }

    public List<Transaction> findBorrowedBooks(User user)
    {
        for (Transaction item : checkedOutBooks)
            if (user.equals(item.getUser()))
                borrowSearch.add(item);
        return borrowSearch;
    }
    public List<Transaction> findBorrowedBooks(int userId) { return findBorrowedBooks(users.get(userId)); }

    /**
     * check methods
     */
    public boolean hasUser(String fname, String lname, String address, String phone)
    {
        for (User user : users.values())
            if (user.isSame(fname, lname, address, phone))
                return true;
        return false;
    }
    public boolean isValidUser(int userID) { return users.get(userID) != null; }
    public boolean isValidBook(String bookID) { return booksOwned.get(bookID) != null; }
    public boolean userCanCheckOut(int userID, int numBooks)
    {
        return users.get(userID).getNumBooksChecked() + numBooks > User.MAX_BOOKS_CHECKED;
    }
    //might not need- if caller has reference to User than they can just call hasDebt()
    public boolean hasOutstandingFine(int userID) { return users.get(userID).hasDebt(); }

}
