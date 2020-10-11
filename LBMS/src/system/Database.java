package system;

import data_classes.*;

import java.io.*;
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
    private List<Integer> numBooksBought;
    private List<Double> fines, payments;
    private String[] fileNames;

    public Database()
    {
        librarySearch = new ArrayList<>();
        storeSearch = new ArrayList<>();
        borrowSearch = new ArrayList<>();
        fileNames = new String[]{"LBMS\\resources\\booksOwned.ser",
                                 "LBMS\\resources\\booksInStore.ser",
                                 "LBMS\\resources\\checkedOutBooks.ser",
                                 "LBMS\\resources\\returnedBooks.ser",
                                 "LBMS\\resources\\visits.ser",
                                 "LBMS\\resources\\users.ser",
                                 "LBMS\\resources\\numBooksBought.ser",
                                 "LBMS\\resources\\fines.ser",
                                 "LBMS\\resources\\payments.ser"};
        readData();
    }

    public void addUser(User user) { users.put(user.getId(), user); }
    public int addUser(String fname, String lname, String address, String phone)
    {
        User user = new User(fname, lname, address, phone);
        addUser(user);
        return user.getId();
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

    public boolean checkOutBooks(int userID, List<Integer> books)
    {
        users.get(userID).checkOutBooks(books.size());
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
        borrowSearch.clear();
        for (Transaction item : checkedOutBooks)
            if (user.equals(item.getUser()))
                borrowSearch.add(item);
        return borrowSearch;
    }
    public List<Transaction> findBorrowedBooks(int userId) { return findBorrowedBooks(users.get(userId)); }

    public String returnBooks(int userID, List<Integer> bookIDs, LocalDate date)
    {
        users.get(userID).checkInBooks(bookIDs.size());
        double totFines = 0;
        for (int i = 0; i < bookIDs.size(); i++) {
            Transaction trans = borrowSearch.get(bookIDs.get(i));
            checkedOutBooks.remove(trans);
            trans.getBook().returnCopy();
            trans.close(date);
            if (trans.isOverdue())
                totFines += trans.getFine();
            else
                bookIDs.remove(i--);
            returnedBooks.add(trans);
        }
        if (totFines == 0)
            return "success";
        fines.set(0, fines.get(0) + totFines);
        users.get(userID).addFine(totFines);
        StringBuilder ret = new StringBuilder("overdue,$" + String.format("%.00f", totFines));
        for (int bookID : bookIDs)
            ret.append(",").append(bookID);
        return ret.toString();
    }

    public double pay(int userID, double amount)
    {
        User user = users.get(userID);
        if (amount > user.getDebt())
            return -1;
        payments.set(0, payments.get(0) + amount);
        return user.addPayment(amount);
    }

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
        for (int i = 0; i < 3; i++)
            time[i] /= (visits.size() - v + 1);
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

    public Report generateReport(int days, LocalDate day)
    {
        day = day.minusDays(days);
        int numBooks = 0, booksBought = 0, v;
        // counts the number of books owned by the library
        for (Book book : booksOwned.values())
            numBooks += book.getNumCopies();
        // counts the number of books bought in the last (days) days
        for (int i = 0; i < days; i++)
            booksBought += numBooksBought.get(i);
        // calculates the average visit time of the last (days) days
        int[] time = new int[3], temp;
        for (v = visits.size() - 1; v >= 0 && !visits.get(v).getEntryDate().isBefore(day); v--) {
            temp = visits.get(v).getTimeSpent();
            for (int i = 0; i < 3; i++)
                time[i] += temp[i];
        }
        for (int i = 0; i < 3; i++)
            time[i] /= (visits.size() - v + 1);
        //calculates all the fines fined and the payments made in the last (days) days
        double totFines = 0, totPayments = 0;
        for (int i = 0; i < days; i++) {
            totFines += fines.get(i);
            totPayments += payments.get(i);
        }
        // returns a report summarizing all the statistics calculated above
        return new Report(users.size(), numBooks, booksBought, time, totPayments, totFines - totPayments);
    }

    /**
     * updates all necessary data that a day has passed
     */
    public void nightlyUpdate(LocalDate today)
    {
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
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNames[0]));
            booksOwned = (Map<String,Book>) in.readObject();
            in.close();
        } catch (Exception e) { e.printStackTrace(); }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNames[1]));
            booksInStore = (Map<String,Book>) in.readObject();
            in.close();
        } catch (Exception e) { e.printStackTrace(); }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNames[2]));
            checkedOutBooks = (List<Transaction>) in.readObject();
            in.close();
        } catch (Exception e) { e.printStackTrace(); }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNames[3]));
            returnedBooks = (List<Transaction>) in.readObject();
            in.close();
        } catch (Exception e) { e.printStackTrace(); }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNames[4]));
            visits = (List<Visit>) in.readObject();
            in.close();
        } catch (Exception e) { e.printStackTrace(); }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNames[5]));
            users = (Map<Integer, User>) in.readObject();
            in.close();
        } catch (Exception e) { e.printStackTrace(); }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNames[6]));
            numBooksBought = (List<Integer>) in.readObject();
            in.close();
        } catch (Exception e) { e.printStackTrace(); }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNames[7]));
            fines = (List<Double>) in.readObject();
            in.close();
        } catch (Exception e) { e.printStackTrace(); }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileNames[8]));
            payments = (List<Double>) in.readObject();
            in.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
    public void saveData()
    {
        writeObject(booksOwned, 0);
        writeObject(booksInStore, 1);
        writeObject(checkedOutBooks, 2);
        writeObject(returnedBooks, 3);
        writeObject(visits, 4);
        writeObject(users, 5);
        writeObject(numBooksBought, 6);
        writeObject(fines, 7);
        writeObject(payments, 8);
    }
    private void writeObject(Object object, int index)
    {
        try {
            FileOutputStream file = new FileOutputStream(fileNames[index]);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(object);
            out.close();
            file.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

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
    public boolean isValidBookISBN(String bookISBN) { return booksOwned.get(bookISBN) != null; }
    public boolean isValidLibraryID(int bookID) { return bookID > -1 && bookID < librarySearch.size(); }
    public boolean isValidBorrowID(int bookID) { return bookID > -1 && bookID < borrowSearch.size(); }
    public boolean isValidStoreID(int bookID) { return bookID > -1 && bookID < storeSearch.size(); }
    public boolean userCanCheckOut(int userID, int numBooks)
    {
        return users.get(userID).getNumBooksChecked() + numBooks > User.MAX_BOOKS_CHECKED;
    }
    public boolean isEmployee(int userID) { return users.get(userID).getType() == User.UserRole.EMPLOYEE; }
    //might not need- if caller has reference to User than they can just call hasDebt()
    public boolean hasOutstandingFine(int userID) { return users.get(userID).hasDebt(); }
}
