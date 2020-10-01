package DataClasses;

public class User {
    /**
     * enum for the type of user, used to
     * distinguish between what a regular
     * visitor and en employee have access
     * to within the system
     */
    public enum UserRole {EMPLOYEE, VISITOR}

    private final int MAX_BOOKS_CHECKED = 5;

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
     * Constructors- default constructor sets User to VISITOR role
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
     * Getter methods that return each attribute excluding idCounter
     */
    public int getId() { return id; }
    public int getNumBooksChecked() { return numBooksChecked; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public UserRole getType() { return type; }
    public double getDebt() { return debt; }

    /**
     * methods for altering the debt of the user
     */
    public void addFine(double fine) { debt += fine; }
    //returns positive number when debt isn't fully paid,
    //returns zero when debt has been paid for by exact amount,
    //returns negative number when over paid representing the
    // amount to give back to the payer
    public double addPayment(double payment)
    {
        if (payment <= debt) {
            debt -= payment;
            return debt;
        }
        payment -= debt;
        debt = 0;
        return -payment;
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
    public void checkInBooks(int num) throws ArithmeticException
    {
        if (num > numBooksChecked)
            throw new ArithmeticException("Can't check in more books than you have checked out.");
        numBooksChecked -= num;
    }

    /**
     * methods for altering type
     */
    public void hire() { type = UserRole.EMPLOYEE; }
    public void fire() { type = UserRole.VISITOR; }
}