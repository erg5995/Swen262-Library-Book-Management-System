package system;

import data_classes.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * system.Database class that stores all required info
 * Author: Thomas Linse
 */
public class DataStorage
{
    private Map<String, Book> booksOwned, booksInStore;
    private List<Book> librarySearch, storeSearch;
    private List<Transaction> checkedOutBooks, returnedBooks, borrowSearch;
    private List<Visit> visits;
    private Map<Integer, User> users;
    private List<Integer> numBooksBought;
    private List<Double> fines, payments;
    private String[] fileNames;

    public DataStorage()
    {
        librarySearch = new ArrayList<>();
        storeSearch = new ArrayList<>();
        borrowSearch = new ArrayList<>();
        String dir = "LBMS/resources/";
        fileNames = new String[]{dir + "booksOwned.ser",      dir + "booksInStore.ser",
                                 dir + "checkedOutBooks.ser", dir + "returnedBooks.ser",
                                 dir + "visits.ser",          dir + "users.ser",
                                 dir + "numUsers.txt",        dir + "numBooksBought.ser",
                                 dir + "fines.ser",           dir + "payments.ser"};
        readData();
    }

    /**
     * Registers a new user into the database.
     *
     * @param user user to be put in the database
     */
    public void addUser(User user) { users.put(user.getId(), user); }
    public int addUser(String fname, String lname, String address, String phone)
    {
        User user = new User(fname, lname, address, phone);
        addUser(user);
        return user.getId();
    }
    public User getUser(int id) { return users.get(id); }

    /**
     * Adds a new visit to the library.
     *
     * @param visit visit to be added
     */
    public void addVisit(Visit visit) { visits.add(visit); }

    /**
     * Gets a list of books in the store or the library.
     *
     * @param param Book parameters
     * @param forLibrary if the search is for the library or the store
     * @return A list of books
     */
    public List<Book> bookInfoSearch(Book param, boolean forLibrary)
    {
        if (forLibrary) {
            searchBooks(param, booksOwned.values(), librarySearch);
            return librarySearch;
        }
        searchBooks(param, booksInStore.values(), storeSearch);
        return storeSearch;
    }

    /**
     * Searches for books in the library or the store.
     *
     * @param param
     * @param search
     * @param found
     */
    private void searchBooks(Book param, Collection<Book> search, List<Book> found)
    {
        found.clear();
        for (Book book : search) {
            if (!"*".equals(param.getTitle()) && !book.getTitle().equals(param.getTitle()))
                continue;
            if (!checkAuth(param.getAuthorList(), book.getAuthorList()))
                continue;
            if (param.getIsbn() != null && !param.getIsbn().equals("*") && !param.getIsbn().equals(book.getIsbn()))
                continue;
            if (param.getPublisherString() != null && !param.getPublisherString().equals("*") && !param.getPublisherString().equals(book.getPublisherString()))
                continue;
            found.add(book);
        }
    }

    /**
     * Checks if the user has permission to execute the command.
     *
     * @param pAuth
     * @param bAuth
     * @return
     */
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

    /**
     * Checks out books that a user requested.
     *
     * @param userID user checking out the book
     * @param bookIDs books being checked out
     * @return the IDs of the books that had no available copies
     */
    public List<Integer> checkOutBooks(int userID, List<Integer> bookIDs)
    {
        List<Integer> unavailableBooks = new ArrayList<>();
        for (int i = 0; i < bookIDs.size(); i++)
            if (librarySearch.get(bookIDs.get(i)).getNumCopiesLeft() > 0)
                librarySearch.get(bookIDs.get(i)).checkOutCopy();
            else
                unavailableBooks.add(bookIDs.remove(i--) + 1);
        users.get(userID).checkOutBooks(bookIDs.size() - unavailableBooks.size());
        return unavailableBooks;
    }

    /**
     * Adds a transaction to the database.
     *
     * @param trans transaction to be added
     */
    public void addTransaction(Transaction trans) { checkedOutBooks.add(trans); }

    /**
     * Adds a transaction to the database.
     *
     * @param book book
     * @param user user
     * @param dateChecked date checked out
     * @param dueDate due date
     */
    public void addTransaction(Book book, User user, LocalDate dateChecked, LocalDate dueDate)
    {
        addTransaction(new Transaction(book, user, dateChecked, dueDate));
    }

    /**
     * Adds a transaction to the database.
     *
     * @param bookID book
     * @param userID user
     * @param dateChecked date checked out
     * @param dueDate due date
     */
    public void addTransaction(int bookID, int userID, LocalDate dateChecked, LocalDate dueDate)
    {
        addTransaction(librarySearch.get(bookID), users.get(userID), dateChecked, dueDate);
    }

    /**
     * Finds a list of borrowed books for a user.
     *
     * @param user user
     * @return a list of books the user has borrowed
     */
    public List<Transaction> findBorrowedBooks(User user)
    {
        borrowSearch.clear();
        for (Transaction item : checkedOutBooks)
            if (user.equals(item.getUser()))
                borrowSearch.add(item);
        return borrowSearch;
    }

    /**
     * Finds a list of borrowed books for a user.
     *
     * @param userId user id
     * @return a list of books the user has borrowed
     */
    public List<Transaction> findBorrowedBooks(int userId) { return findBorrowedBooks(users.get(userId)); }

    /**
     * Returns books that have been lent out to the library.
     *
     * @param userID user who borrowed the book
     * @param bookIDs the list of book ids to be returned
     * @param date the date returning
     * @return success or overdue fee if the books are returned late
     */
    public String returnBooks(int userID, List<Integer> bookIDs, LocalDate date)
    {
        ArrayList<Integer> overdue = new ArrayList<>();
        int attempted = bookIDs.size();
        double totFines = 0;
        for (int i = 0; i < bookIDs.size(); i++) {
            Transaction trans = borrowSearch.get(bookIDs.get(i));
            if (trans != null && trans.getUser().getId() == userID) {
                checkedOutBooks.remove(trans);
                borrowSearch.set(bookIDs.get(i), null);
                trans.getBook().returnCopy();
                trans.close(date);
                if (trans.isOverdue()) {
                    totFines += trans.getFine();
                    overdue.add(bookIDs.get(i));
                }
                bookIDs.remove(i--);
                returnedBooks.add(trans);
            } else
                bookIDs.set(i, bookIDs.get(i) + 1);
        }
        users.get(userID).checkInBooks(attempted - bookIDs.size());
        if (totFines == 0)
            return "success";
        fines.set(0, fines.get(0) + totFines);
        users.get(userID).addFine(totFines);
        StringBuilder ret = new StringBuilder("overdue,$" + String.format("%.00f", totFines));
        for (int bookID : overdue)
            ret.append(",").append(bookID);
        return ret.toString();
    }

    /**
     * Manages user payment and subtract the amount from the user's debt.
     *
     * @param userID user making the payment
     * @param amount amount being paid
     * @return the user's current debt
     */
    public double pay(int userID, double amount)
    {
        User user = users.get(userID);
        if (amount > user.getDebt())
            return -1;
        payments.set(0, payments.get(0) + amount);
        return user.addPayment(amount);
    }

    /**
     * Purchases a list of books from the store.
     *
     * @param quantity quantity being bought
     * @param bookIDs list of books to purchase
     * @return a list of books bought
     */
    public List<Book> buyBooks(int quantity, List<Integer> bookIDs)
    {
        List<Book> books = new ArrayList<>();
        Book book;
        for (int bookID : bookIDs) {
            book = storeSearch.get(bookID);
            book.setNumCopies(book.getNumCopies() + quantity);
            books.add(book);
            booksOwned.putIfAbsent(book.getIsbn(), book);
            numBooksBought.set(0, numBooksBought.get(0) + quantity);
        }
        return books;
    }

    /**
     * Generates a standard report
     *
     * @return report
     */
    public Report generateReport()
    {
        int numBooks = 0, booksBought = 0, v;
        // counts the number of books owned by the library
        for (Book book : booksOwned.values())
            numBooks += book.getNumCopies();
        // calculates the average visit time
        int[] time = new int[3], temp;
        for (v = visits.size() - 1; v >= 0; v--) {
            temp = visits.get(v).getTimeSpent();
            for (int i = 0; i < 3; i++)
                time[i] += temp[i];
        }
        divideTime(time, visits.size());
        //calculates all the fines fined and the payments made, and the number of books bought for the library
        double totFines = 0, totPayments = 0;
        for (int i = 0; i < fines.size(); i++) {
            booksBought += numBooksBought.get(i);
            totFines += fines.get(i);
            totPayments += payments.get(i);
        }
        // returns a report summarizing all the statistics calculated above
        return new Report(users.size(), numBooks, booksBought, time, totPayments, totFines - totPayments);
    }

    /**
     * Generates a report based on the number of days.
     *
     * @param days number of days
     * @param day start date
     * @return report
     */
    public Report generateReport(int days, LocalDate day)
    {
        day = day.minusDays(days);
        int numBooks = 0, booksBought = 0, v;
        // counts the number of books owned by the library
        for (Book book : booksOwned.values())
            numBooks += book.getNumCopies();
        // counts the number of books bought in the last (days) days
        for (int i = 0; i < days && i < numBooksBought.size(); i++)
            booksBought += numBooksBought.get(i);
        // calculates the average visit time of the last (days) days
        int[] time = new int[3], temp;
        for (v = visits.size() - 1; v >= 0 && !visits.get(v).getEntryDate().isBefore(day); v--) {
            temp = visits.get(v).getTimeSpent();
            for (int i = 0; i < 3; i++)
                time[i] += temp[i];
        }
        divideTime(time, visits.size() - v);
        //calculates all the fines fined and the payments made in the last (days) days
        double totFines = 0, totPayments = 0;
        for (int i = 0; i < days && i < fines.size(); i++) {
            totFines += fines.get(i);
            totPayments += payments.get(i);
        }
        // returns a report summarizing all the statistics calculated above
        return new Report(users.size(), numBooks, booksBought, time, totPayments, totFines - totPayments);
    }

    /**
     * A helper function to divide time.
     *
     * @param time time
     * @param size size to be divided
     */
    private void divideTime(int[] time, int size)
    {
        if (size != 0) {
            for (int i = 0; i < 2; i++) {
                time[i + 1] += (time[i] % size) * 60;
                time[i] /= size;
            }
            time[2] /= size;
        }
    }

    /**
     * updates all necessary data that a day has passed
     */
    public void nightlyUpdate(LocalDate today)
    {
        if (checkedOutBooks != null)
            for (Transaction trans : checkedOutBooks)
                trans.update(today);
        numBooksBought.add(0, 0);
        fines.add(0, 0.);
        payments.add(0, 0.);
    }
    public void advanceUpdate(int days, LocalDate newDay)
    {
        for (int i = 1; i < days; i++){
            numBooksBought.add(0, 0);
            fines.add(0, 0.);
            payments.add(0, 0.);
        }
        nightlyUpdate(newDay);
    }

    /**
     * Serialize methods
     */
    @SuppressWarnings("unchecked")
    public void readData()
    {
        System.out.println("Reading data...");
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNames[0]));
            booksOwned = (Map<String,Book>) in.readObject();
            in.close();
        } catch (Exception e) { System.out.println("Couldn't read in books owned by library."); }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNames[1]));
            booksInStore = (Map<String,Book>) in.readObject();
            in.close();
        } catch (Exception e) { System.out.println("Couldn't read in books in the store."); }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNames[2]));
            checkedOutBooks = (List<Transaction>) in.readObject();
            in.close();
        } catch (Exception e) { System.out.println("Couldn't read in books that are currently checked out."); }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNames[3]));
            returnedBooks = (List<Transaction>) in.readObject();
            in.close();
        } catch (Exception e) { System.out.println("Couldn't read in the transactions for returned books."); }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNames[4]));
            visits = (List<Visit>) in.readObject();
            in.close();
        } catch (Exception e) { System.out.println("Couldn't read in the visits."); }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNames[5]));
            users = (Map<Integer, User>) in.readObject();
            in.close();
            if (!users.isEmpty()) {
                BufferedReader numUsers = new BufferedReader(new FileReader(fileNames[6]));
                User[] temp = users.values().toArray(new User[0]);
                String line = numUsers.readLine();
                temp[0].setIdCounter(Integer.parseInt(line));
            }
        } catch (Exception e) { System.out.println("Couldn't read in the users."); }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNames[7]));
            numBooksBought = (List<Integer>) in.readObject();
            in.close();
        } catch (Exception e) { System.out.println("Couldn't read in the number of books bought."); }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNames[8]));
            fines = (List<Double>) in.readObject();
            in.close();
        } catch (Exception e) { System.out.println("Couldn't read in the amount of fines applied."); }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNames[9]));
            payments = (List<Double>) in.readObject();
            in.close();
        } catch (Exception e) { System.out.println("Couldn't read in the amount of payments received."); }
    }

    /**
     * Save the data locally
     */
    public void saveData()
    {
        System.out.println("Saving data...");
        writeObject(booksOwned, 0);
        writeObject(booksInStore, 1);
        writeObject(checkedOutBooks, 2);
        writeObject(returnedBooks, 3);
        writeObject(visits, 4);
        writeObject(users, 5);
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileNames[6]));
            User[] temp = users.values().toArray(new User[0]);
            out.write(String.valueOf(temp[0].getNumVisitors()));
            out.flush();
            out.close();
        } catch (Exception e) { System.out.println("Couldn't save number of users."); }
        writeObject(numBooksBought, 7);
        writeObject(fines, 8);
        writeObject(payments, 9);
    }
    private void writeObject(Object object, int index)
    {
        try {
            FileOutputStream file = new FileOutputStream(fileNames[index]);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(object);
            out.close();
            file.close();
        } catch (Exception e) { System.out.println("Couldn't save to " + fileNames[index] + "."); }
    }

    /**
     * Checks if DataStorage has a specific user.
     *
     * @param fname User's first name
     * @param lname User's last name
     * @param address User's address
     * @param phone User's phone number
     *
     * @return the user
     */
    public boolean hasUser(String fname, String lname, String address, String phone)
    {
        for (User user : users.values())
            if (user.isSame(fname, lname, address, phone))
                return true;
        return false;
    }

    /**
     * Checks if the user is valid.
     *
     * @param userID user id
     * @return true if valid, false if not
     */
    public boolean isNotValidUser(int userID) { return users.get(userID) == null; }

    /**
     * Checks if the ISBN is valid.
     *
     * @param bookISBN isbn
     * @return true is valid, false if not
     */
    public boolean isValidBookISBN(String bookISBN) { return booksOwned.get(bookISBN) != null; }

    /**
     * Checks if the book is in the library
     *
     * @param bookID book id
     * @return true if valid, false if not
     */
    public boolean isValidLibraryID(int bookID) { return bookID > -1 && bookID < librarySearch.size(); }

    /**
     * Checks if the book has been borrowed.
     *
     * @param bookID book id
     * @return true if borrowed, false if not
     */
    public boolean isNotValidBorrowID(int bookID) { return bookID <= -1 || bookID >= borrowSearch.size(); }

    /**
     * Checks if the book exists in the store.
     *
     * @param bookID book id
     * @return true if exists, false if not
     */
    public boolean isValidStoreID(int bookID) { return bookID > -1 && bookID < storeSearch.size(); }

    /**
     * checks if the user can checkout more books
     *
     * @param userID user id
     * @param numBooks number of books
     * @return true if the user can borrow more books, false if not
     */
    public boolean userCanCheckOut(int userID, int numBooks) {
        return users.get(userID).getNumBooksChecked() + numBooks <= User.MAX_BOOKS_CHECKED;
    }

    /**
     * checks if the user is an employee
     *
     * @param userID user id
     * @return true if the user is an employee, false if not
     */
    public boolean isEmployee(int userID) { return users.get(userID).getType() == User.UserRole.EMPLOYEE; }

    /**
     * checks if the user has any outstanding fines
     *
     * @param userID user id
     * @return the user's fine
     */
    public boolean hasOutstandingFine(int userID) { return users.get(userID).hasDebt(); }
}
