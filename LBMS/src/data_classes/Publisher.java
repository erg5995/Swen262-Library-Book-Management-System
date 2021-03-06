package data_classes;

/**
 * A class to store publisher information
 */
public class Publisher implements java.io.Serializable {

    String publisher;

    public Publisher(String publisher) {
        this.publisher = publisher;
    }

    public String get() {
        return this.publisher;
    }

    @Override
    public String toString() {
        return this.publisher;
    }

}
