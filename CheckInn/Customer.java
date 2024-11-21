package CheckInn;

/**
 * Customer ---- Entity class for representing a customer
 * @author       Julian Aguiar
 */
public class Customer {

    // Global variables
    private String first, last; // First name and last name of customer
    private String email; // Email of customer
    private int customerID; // Identification Number of customer

    /**
     * Customer entity constuctor saves a specific customer's information in object
     * @param customerID  The identification number of the customer
     * @param email       The email address of the customer
     * @param last        The last name of the customer
     * @param first       The first name of the customer
     * @exception         none
     * @return            none
     */
    Customer(int customerID, String email, String last, String first) {

        // Saves first and last name + customer email + customer identification number
        this.customerID = customerID;
        this.email = email;
        this.last = last;
        this.first = first;
        
    } // End Customer(first, last, email, customerID) constructor

    /**
     * Retreives first name of customer
     * @exception  none
     * @return     first name
     */ 
    public String getFirstName() {
        return first;
    } // End getFirstName()

    /**
     * Retreives last name of customer
     * @exception  none
     * @return     last name
     */ 
    public String getLastName() {
        return last;
    } // End getLastName()

    /**
     * Retreives email address of customer
     * @exception  none
     * @return     email address
     */     
    public String getEmail() {
        return email;
    } // End getEmail()

    /**
     * Retreives ID number of customer
     * @exception  none
     * @return     ID number
     */ 
    public int getCustomerID() {
        return customerID;
    } // End getCustomerID()

} // End Customer class
