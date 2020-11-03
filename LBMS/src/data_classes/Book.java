package data_classes;

import java.io.Serializable;
import java.util.Arrays;

/**
 *  Class Book
 *
 *  Data class for the Book Object
 *
 *  Author: Michael Driscoll
 */

public class Book implements Serializable {

    //attributes

    private String isbn;
    private String title;
    private Author author;
    private Publisher publisher;
    private String publishDate;
    private int pageCount;
    private int numCopies;
    private int numCopiesOut;

    //constructor
    public Book(String theIsbn, String theTitle, String[] theAuthor, String thePublisher, String thePublishDate, int thePageCount, int theNumCopies, int theNumCopiesOut){
        isbn = theIsbn;
        title = theTitle;
        author = new Author(theAuthor);
        publisher = new Publisher(thePublisher);
        publishDate = thePublishDate;
        pageCount = thePageCount;
        numCopies = theNumCopies;
        numCopiesOut = theNumCopiesOut;
    }

    //getters

    public String getIsbn(){
        return isbn;
    }

    public String getTitle(){
        return title;
    }

    public String[] getAuthorList(){
        return author.getAuthorList();
    }

    public Author getAuthor() {
        return this.author;
    }

    public String getPublisherString(){
        return publisher.get();
    }

    public Publisher getPublisher() {
        return this.publisher;
    }

    public String getPublishDate(){
        return publishDate;
    }

    public int getPageCount(){
        return pageCount;
    }

    public int getNumCopies(){
        return numCopies;
    }

    public int getNumCopiesOut(){
        return numCopiesOut;
    }

    public int getNumCopiesLeft() { return numCopies - numCopiesOut; }

    //setters

    public void setIsbn(String newIsbn){
        isbn = newIsbn;
    }

    public void setTitle(String newTitle){
        title = newTitle;
    }

    public void setAuthor(String[] newAuthor){
        author = new Author(newAuthor);
    }

    public void setPublisher(String newPublisher){
        publisher = new Publisher(newPublisher);
    }

    public void setPublishDate(String newPublishDate){
        publishDate = newPublishDate;
    }

    public void setPageCount(int newPageCount){
        pageCount = newPageCount;
    }

    public void setNumCopies(int newNumCopies){
        numCopies = newNumCopies;
    }

    public void setNumCopiesOut(int newNumCopiesOut){
        numCopiesOut = newNumCopiesOut;
    }

    public void checkOutCopy() { numCopiesOut++; }
    public void returnCopy() { numCopiesOut--; }

    @Override
    public String toString() {
        String authors = author.toString();
        return "" + isbn + "," + title + ",{" + authors.substring(1, authors.length() - 1) + "}," + publisher + "," + publishDate + "," + (numCopies - numCopiesOut);
    }
}
