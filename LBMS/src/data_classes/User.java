package data_classes;

import java.io.Serializable;

/**
 * Dataclass for users
 * Author: Thomas Linse
 */
public class User implements Serializable {
    /**
     * enum for the type of user, used to
     * distinguish between what a regular
     * visitor and en employee have access
     * to within the system
     */
    public enum UserRole {EMPLOYEE, VISITOR}

    public final static int MAX_BOOKS_CHECKED = 5;

    /**
     * private variables for the user: idCounter is static
     * so that it's the same for every instantiation of User,
     * it's used to give each user a unique id.
     * numBooksChecked represents ow many books the User
     * currently has checked out.
     */
    private static int idCounter;
    private int id, numBooksChecked;
    private String firstName, lastName, address, phone;
    private UserRole type;
    private double debt;

    /**
     * Constructors- constructor sets User to VISITOR role by default
     */
    public User(String fname, String lname, String address, String phone, UserRole role)
    {
        id = ++idCounter;
        numBooksChecked = 0;
        firstName = fname;
        lastName = lname;
        this.address = address;
        this.phone = phone;
        type = role;
        debt = 0;
    }
    public User(String fname, String lname, String address, String phone)
    {
        this(fname, lname, address, phone, UserRole.VISITOR);
    }

    /**
     * Getter methods that return each attribute
     */
    public int getNumVisitors() { return idCounter; }
    public int getId() { return id; }
    public int getNumBooksChecked() { return numBooksChecked; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public UserRole getType() { return type; }
    public double getDebt() { return debt; }
    public boolean hasDebt() { return debt > 0; }

    /** Set idCounter on system startup */
    public void setIdCounter(int count) { idCounter = count; }

    /**
     * methods for altering the debt of the user
     */
    public void addFine(double fine) { debt += fine; }
    public double addPayment(double payment)
    {
        debt -= payment;
        return debt;
    }

    /**
     * methods for altering numBooksChecked
     */
    public boolean checkOutBooks(int num)
    {
        if (num + numBooksChecked > MAX_BOOKS_CHECKED)
            return false;
        numBooksChecked += num;
        return true;
    }
    public boolean checkInBooks(int num)
    {
        if (num > numBooksChecked) return false;
        numBooksChecked -= num;
        return true;
    }

    /**
     * methods for altering type
     */
    public void hire() { type = UserRole.EMPLOYEE; }
    public void fire() { type = UserRole.VISITOR; }

    public boolean isSame(String fname, String lname, String address, String phone)
    {
        return firstName.equals(fname) && lastName.equals(lname) && this.address.equals(address) && this.phone.equals(phone);
    }
}