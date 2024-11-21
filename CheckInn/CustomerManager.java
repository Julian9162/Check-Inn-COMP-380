package CheckInn;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * CustomerManager --- Controller class that manages creation, storage, and access of customer entities.
 * @author             Julian Aguiar
 */
public class CustomerManager {

    // Global variables
    private LinkedList<Customer> customer = new LinkedList<Customer>(); // Linked List used to store customer objects
    private ArrayList<Integer> customerID = new ArrayList<Integer>(); // Array list used to store customer ID numbers
    private ArrayList<String> customerEmail = new ArrayList<String>(); // Array list used store customer emails

    /**
     * Class constructor loads all customer records from customer.txt csv file into customer object linked list and arrays
     * @exception  Error reading from customer csv file
     * @return     No return value
     */
    public CustomerManager() {

        try {

            // Local variables
            String line; // Stores a line in customer list file
            File file = new File("CheckInn\\customers.txt"); // Stores customer list file
            BufferedReader reader = new BufferedReader(new FileReader(file)); // Reads lines from customer list file
            
            // While loop is used to read each line from customer list file and create a customer object from data
            while ((line = reader.readLine()) != null) {

                // Split line into different parts and stores in string array
                String parts[] = line.split(",");
                // Create customer object from data stored in array
                Customer c = new Customer(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
                // Store customer object into customer linked list
                customer.add(c);
                // Store customer object ID attribute into customerID array
                customerID.add(Integer.parseInt(parts[0]));
                // Store customer object email attribute into customerEmail array
                customerEmail.add(parts[1]);

            } // End while

            reader.close(); // Close BufferedReader

        } // End try

        // Error reading from customer file
        catch(Exception e) {
            System.out.println("I/O Error in CustomerManager constructor: " + e.getMessage());
        } // End catch
 
    } // End CustomerManager constructor

    /**
     * Creates a unique 7-digit ID number for a new customer being added to customer csv file
     * @param first  First name of the customer
     * @param last   Last name of the customer
     * @exception    none
     * @return       Returns newly generated customer ID
     */
    private int generateID(String first, String last) {

        // Local variables
        int firstSecond, thirdFourth; // Used for saving first four digits of ID number
        char fInit, lInit; // Used for saving initials of customer

        // Find first and last initial of new customer.
        fInit = first.charAt(0);
        lInit = last.charAt(0);

        // Convert initials to corresponding ASCII code.
        firstSecond = (int) fInit;
        thirdFourth = (int) lInit;

        // Digits 1-4: Corresponding ASCII code of initials.
        // Digits 5-7: Random number between 0-200
        // Returns new identification number
        return (100000 * firstSecond) + (1000 * thirdFourth) + firstSecond + thirdFourth + (int) (Math.random() * 200);

    } // End generateID(first, last) method

    /**
     * Adds new customer to customer.txt csv file
     * @param c    A customer object containing information on a new customer to be added to customer csv file
     * @exception  No exception
     * @return     none
     */
    private void addToCustFile(Customer c) throws Exception {

        // Writes lines to end of customer list file
        BufferedWriter writer = new BufferedWriter(new FileWriter("CheckInn\\customers.txt", true));
        // Write customer data in following order: customerID, email, lastName, firstName
        writer.write("\n" + c.getCustomerID() + "," + c.getEmail() + "," + c.getLastName() + "," + c.getFirstName());
        writer.newLine(); // Create new line
        writer.close(); // Close BufferedWriter

    } // End addToCustFile(c)

    /**
     * Retreives an existing customer object from customer linked list by using the customer's ID number.
     * @param targetID  The customer ID to be searched in customer ID array for retreival of desired customer.
     * @exception       Customer ID does not exist
     * @return          Returns desired customer object
     */
    public Customer getCustomer(int targetID) {

        int index = customerID.indexOf(targetID); // Saves index of targetID in customerID array

        // If desired customer object exists, return it.
        if (index != -1) return customer.get(index);

        // If desired customer object does not exist, return null.
        else return null;
        
    } // End getCustomer(targetID)

    /**
     * Retreives an existing customer object from customer linked list using customers email address OR
     * creates new customer object if customer does not exist.
     * @param first        The first name of the customer
     * @param last         The last name of the customer
     * @param targetEmail  The email of the desired customer
     * @exception          No exception
     * @return             Returns desired customer object or newly created customer object
     */
    public Customer getCustomer(String first, String last, String targetEmail) {

        int index = customerEmail.indexOf(targetEmail); // Saves index of emailID in customerEmail array

        // If desired customer object already exists, return it.
        if (index != -1) return customer.get(index);

        // If desired customer object does not exist, create and return new customer object
        else {

            // Create new customer object with newly generated customer ID number
            Customer c = new Customer(generateID(first, last), targetEmail, last, first);
            customer.add(c); // Add new customer object to linked list.

            // Add customer object to customer csv file
            try{addToCustFile(c);}
            catch(Exception e) {System.out.println("I/O Error: " + e.getMessage());}

            // Return customer newly created customer object
            return c;

        } // End else

    } // End getCustomer(first, last, targetEmail)

} // End CustomerManager
