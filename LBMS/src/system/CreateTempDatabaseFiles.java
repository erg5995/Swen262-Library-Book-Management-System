package system;

import data_classes.Book;
import data_classes.Transaction;
import data_classes.User;
import data_classes.Visit;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateTempDatabaseFiles {

    private static String fileNames[] = {
            "booksOwned.ser",
            "booksInStore.ser",
            "checkedOutBooks.ser",
            "returnedBooks.ser",
            "visits.ser",
            "users.ser",
            "numBooksBought.ser",
            "fines.ser",
            "payments.ser"};

    public static void pleaseWriteData() {
        writeObject(new HashMap<String, Book>(), 0);
        writeObject(new HashMap<String, Book>(), 1);
        writeObject(new ArrayList<Transaction>(), 2);
        writeObject(new ArrayList<Transaction>(), 3);
        writeObject(new ArrayList<Visit>(), 4);
        writeObject(new HashMap<Integer, User>(), 5);
        writeObject(new ArrayList<Integer>(), 6);
        writeObject(new ArrayList<Double>(), 7);
        writeObject(new ArrayList<Double>(), 8);
    }

    private static void writeObject(Object object, int index) {
        try {
            FileOutputStream file = new FileOutputStream(fileNames[index]);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(object);
            out.close();
            file.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

}
