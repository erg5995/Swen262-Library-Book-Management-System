package system;

import data_classes.Book;
import data_classes.Transaction;
import data_classes.User;
import data_classes.Visit;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class InitDatabase
{
    /**
     * Map<String, Book>    booksOwned.ser
     * Map<String, Book>    booksInStore.ser
     * List<Transaction>    checkedOutBooks.ser
     * List<Transaction>    returnedBooks.ser
     * List<Visit>          visits.ser
     * List<User>           users.ser
     * List<Integer>        numBooksBought.ser
     * List<Double>         fines.ser
     * List<Double>         payments.ser
     */

    public void main(String[] args)
    {
        writeObject(new HashMap<String, Book>(), "booksOwned.ser");
        writeObject(new ArrayList<Transaction>(), "checkedOutBooks.ser");
        writeObject(new ArrayList<Transaction>(), "returnedBooks.ser");
        writeObject(new ArrayList<Visit>(), "visits.ser");
        writeObject(new ArrayList<User>(), "users.ser");
        writeObject(new ArrayList<Integer>(), "numBooksBought.ser");
        writeObject(new ArrayList<Double>(), "fines.ser");
        writeObject(new ArrayList<Double>(), "payments.ser");

        try {
            FileInputStream file = new FileInputStream("");
        } catch (IOException ignored) {}
    }
    private void writeObject(Object object, String fileName)
    {
        try {
            FileOutputStream file = new FileOutputStream("..\\..\\resources\\" + fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(object);
            out.close();
            file.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
