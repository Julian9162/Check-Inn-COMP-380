package checkinn;

// Class for customer entity
public class Customer {

    // Global variables
    private String first, last; // First name and last name of customer
    private String email; // Email of customer
    private int customerID; // Identification Number of customer

    // Customer constructor that creates customer object based off existing customer data
    Customer(int customerID, String email, String last, String first) {

        // Saves first and last name + customer email + customer identification number
        this.customerID = customerID;
        this.email = email;
        this.last = last;
        this.first = first;
        
    } // End Customer(first, last, email, customerID) constructor

    // getFirstName() returns first name of customer
    public String getFirstName() {
        return first;
    } // End getFirstName()

    // getLastName() returns first name of customer
    public String getLastName() {
        return last;
    } // End getLastName()

    // getFirstName() returns email of customer
    public String getEmail() {
        return email;
    } // End getEmail()

    // getFirstName() returns identification number of customer
    public int getCustomerID() {
        return customerID;
    } // End getCustomerID()

} // End Customer class