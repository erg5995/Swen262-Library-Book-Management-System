package system;

import data_classes.Book;
import data_classes.Transaction;
import data_classes.User;
import data_classes.Visit;

import java.io.*;
import java.lang.reflect.Array;
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
     * HasMap<Integer, User>users.ser
     * List<Integer>        numBooksBought.ser
     * List<Double>         fines.ser
     * List<Double>         payments.ser
     */
    private static String line;

    public static void main(String[] args)
    {
        writeObject(new HashMap<String, Book>(), "booksOwned.ser");
        writeObject(new ArrayList<Transaction>(), "checkedOutBooks.ser");
        writeObject(new ArrayList<Transaction>(), "returnedBooks.ser");
        writeObject(new ArrayList<Visit>(), "visits.ser");
        writeObject(new HashMap<Integer, User>(), "users.ser");
        ArrayList<Integer> numBooksBought = new ArrayList<>();
        numBooksBought.add(0);
        writeObject(numBooksBought, "numBooksBought.ser");
        ArrayList<Double> doubleList = new ArrayList<>();
        doubleList.add(0.);
        writeObject(doubleList, "fines.ser");
        writeObject(doubleList, "payments.ser");

        HashMap<String, Book> booksInStore = new HashMap<>();
        try {
            FileReader file = new FileReader("LBMS\\resources\\books.txt");
            BufferedReader in = new BufferedReader(file);

            String temp, isbn, title, publisher;
            ArrayList<String> authors = new ArrayList<>();
            Book book;
            int len;
            line = in.readLine();
            while (line != null) {
                isbn = nextField();
                line = line.substring(1);
                title = line.substring(0, line.indexOf('\"'));
                line = line.substring(line.indexOf('\"') + 2);

                temp = nextField();
                authors.add(temp.substring(1));
                while (temp.charAt(temp.length() - 1) != '}') {
                    temp = nextField();
                    authors.add(temp);
                }
                temp = authors.get(authors.size() - 1);
                authors.set(authors.size() - 1, temp.substring(0, temp.length() - 1));
                line = line.substring(1);

                publisher = line.substring(0, line.indexOf('\"'));
                line = line.substring(line.indexOf('\"') + 2);
                book = new Book(isbn, title, authors.toArray(new String[0]), publisher, nextField(),
                                Integer.parseInt(line), 0, 0);
//                System.out.println("Title: " + title + "\tAuthors: " + authors + "\tPublisher: " + publisher);
                booksInStore.put(isbn, book);
                line = in.readLine();
                authors.clear();
            }
            in.close();
            file.close();
        } catch (IOException e) { e.printStackTrace(); }
        writeObject(booksInStore, "booksInStore.ser");
    }
    private static String nextField()
    {
        String ret = line.substring(0, line.indexOf(','));
        line = line.substring(line.indexOf(',') + 1);
        return ret;
    }
    private static void writeObject(Object object, String fileName)
    {
        try {
            FileOutputStream file = new FileOutputStream("LBMS\\resources\\" + fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(object);
            out.close();
            file.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
