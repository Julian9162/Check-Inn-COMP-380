package CheckInn;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.util.LinkedList;
import java.util.ArrayList;

// CustomerManager class is controller class for customer entities
public class CustomerManager {

    // Local variables
    LinkedList<Customer> customer = new LinkedList<Customer>(); //Data structure used to store customer objects
    ArrayList<Integer> customerID = new ArrayList<Integer>(); // Data structure used to store customer ID numbers
    ArrayList<String> customerEmail = new ArrayList<String>(); // Data structure used store customer emails

    // CustomerManager constructor that loads customer data stored in customer.txt csv file 
    // to customer object data structure when called
    public CustomerManager() {

        try {

            // Variables
            String line; // Stores a line in customer list file
            File file = new File("customers.txt"); // Stores customer list file
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
            System.out.println("I/O Error CustomerManager " + e.getMessage());
        } // End catch
 
    } // End CustomerManager constructor

    // generateID() method will create new 7-digit identification number for new customer
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

    // addToCustFile() method will add new customer object to customer list file
    private void addToCustFile(Customer c) throws Exception {

        // Writes lines to end of customer list file
        BufferedWriter writer = new BufferedWriter(new FileWriter("customers.txt", true));
        // Write customer data in following order: customerID, email, lastName, firstName
        writer.write(c.getCustomerID() + "," + c.getEmail() + "," + c.getLastName() + "," + c.getFirstName());
        writer.newLine(); // Create new line
        writer.close(); // Close BufferedWriter

    } // End addToCustFile(c)

    // getCustomer(targetID) method searches and returns desired customer object by using target customer 
    // identification number. Returns null if customer does not exist.
    public Customer getCustomer(int targetID) {

        int index = customerID.indexOf(targetID); // Saves index of targetID in customerID array

        // If desired customer object exists, return it.
        if (index != -1) return customer.get(index);

        // If desired customer object does not exist, return null.
        else return null;
        
    } // End getCustomer(targetID)

    // getCustomer(first, last, targetEmail) method searches and returns desired customer object by using target
    // customer email. Creates and returns new customer object if customer does not already exist.
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