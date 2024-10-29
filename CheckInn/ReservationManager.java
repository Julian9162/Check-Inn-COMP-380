package CheckInn;

import java.io.File;
import java.io.*;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ArrayList;


// ReservationManager class manages all reservations for hotel.
// Allows user to create, cancel, and review reservations.
// Manages the reservation list csv file. This includes adding a new row, deleting
// a row, and changing reservation status.
// Stores all current and upcoming reservations in a linked list.
public class ReservationManager {

    CustomerManager CM = new CustomerManager(); // Might remove this

    // Global variables
    LinkedList<Reservation> reservation = new LinkedList<Reservation>(); // Linked list that stores 
                                                                         // upcoming and current reservations
    ArrayList<Long> reservationID = new ArrayList<Long>(); // Array list that stores reservation ID's
                                                                 // of reservations in linked list. Used for
                                                                 // easy searching of reservation object.

    // ReservationManager() constructor loads all reservations from csv file to linked list when called.
    public ReservationManager() {

        try {

            // Variables
            String line; // Stores a line in reservation list file
            File file = new File("CheckInn\\reservations.txt"); // Stores reservation list file
            BufferedReader reader = new BufferedReader(new FileReader(file)); // Reads lines from reservation list file
            
            // While loop is used to read each line from reservation list file and create a reservation object from data
            while ((line = reader.readLine()) != null) {

                // Split line into different parts and stores in string array
                String parts[] = line.split(",", 8);

                // Use CustomerManager class to obtain desired customer object
                Customer c = CM.getCustomer(Integer.parseInt(parts[1]));

                // Save status of current reservation being read
                boolean activeStatus;
                if (parts[6].compareTo("true") == 0) activeStatus = true;
                else activeStatus = false;

                // Create reservation object from data stored in array
                Reservation r = new Reservation(Long.parseLong(parts[0]), c, parts[2], Integer.parseInt(parts[3]), 
                                                parts[4], parts[5], activeStatus, parts[7]);

                
                // Store reservation object into reservation linked list
                reservation.add(r);
                // Store reservation object ID attribute into reservationID array
                reservationID.add(Long.parseLong(parts[0]));

            } // End while

            reader.close(); // Close BufferedReader

        } // End try

        // Error reading from reservation file
        catch(Exception e) {
            System.out.println("I/O Error ReservationManager " + e.getMessage());
        } // End catch

    } // End ReservationManager() constructor

    // generateKey() method creates and returns unique identification number for reservation
    private int generateKey(Customer c) {

        // Local variables
        int i, key, first, last;
        char fInit, lInit;

        // Find first and last initial of client that booked the reservation.
        fInit = c.getFirstName().charAt(0);
        lInit = c.getLastName().charAt(0);

        // Convert initials to corresponding ASCII code.
        first = (int) fInit;
        last = (int) lInit;

        // Generate reservation number that is 12 digits. Numbers correspond to the following:
        // Digits 1-4: Year reservation was booked
        // Digits 5-6: Month reservation was booked
        // Digits 7-8: Day reservtion was booked
        // Digits 9-12: First + Last Initial of client in ASCII code
        key = (100000000 * LocalDate.now().getYear()) + (1000000 * LocalDate.now().getMonthValue()) 
                + (10000 * LocalDate.now().getDayOfMonth()) + (first * 100) + last; 

        // Check if array list contains any reservation numbers.
        // If it does, check that key is unique and add. Make changes if needed.
        // If not, make new key the first element in array list.
        if (!reservationID.isEmpty()) {

            // Start at first element in array list
            i = 0;
            
            // Compare uniqueness to every element in array list
            while (i < reservationID.size()) {

                // If created key is not equal to reservation[i], move onto reservation[i+1] 
                if (key != reservationID.get(i)) {
                    i++;
                    continue;
                } // End if

                // If created key is equal to reservation[i], make adjustment and restart comparisons
                else {
                    key = key + 1;
                    i = 0;
                } // End else

            } // End while

            // Return newly generated key
            return key;

        } // End if

        // Return newly generated key
        else return key;

    } // End generateKey(c)

    // addToReservationFile() method adds newly created reservation object to reservation csv file.
    public void addToReservationFile(Reservation r) throws Exception {

        // Local variables
        // checkIn and checkOut store reservation dates in string form
        String checkIn = r.getCheckInDate().getMonthValue() + "/" + r.getCheckInDate().getDayOfMonth() + "/" +
                        r.getCheckInDate().getYear();
        String checkOut = r.getCheckOutDate().getMonthValue() + "/" + r.getCheckOutDate().getDayOfMonth() + "/" +
                        r.getCheckOutDate().getYear();

        // Writes lines to end of reservation list file
        BufferedWriter writer = new BufferedWriter(new FileWriter("CheckInn\\reservations.txt", true));

        // Write reservation data in following order: ReservationID, CustomerID, RoomType, GroupSize, CheckInDate,
        // CheckOutDate, ActiveStatus
        writer.write(r.getReservationID() + "," + r.getCustomer().getCustomerID() + "," + r.getRoomType() + "," 
                        + r.getGroupSize() + "," + checkIn + "," + checkOut + "," + r.getActiveStatus());
        writer.newLine(); // Create new line
        writer.close(); // Close BufferedWriter

    } // End addToReservationFile(r)

    // createReservation() method allows user to create a new reservation.
    // Generates unique confirmation number.
    // Adds newly created reservation object to reservation linked list
    // Stores reservation key in reservation array list for easy access to specified reservation.
    public void createReservation(String customerName, String roomType, int groupSize, String checkInDate, 
                                String checkOutDate, String email) {

        // Local variables
        Reservation r; // New reservation to be created.
        String[] name = customerName.split(" "); // Split first and last
        Customer c = CM.getCustomer(name[0], name[1], email); // Obtain a customer object

        // Creates new reservation. Includes basic reservation information and creates a new key.
        r = new Reservation(generateKey(c), c, roomType, groupSize, checkInDate, checkOutDate, false, "0");
        reservation.add(r); // Add to linked list
        reservationID.add(r.getReservationID()); // Add to array list

        // Add newly created reservation object to reservation csv file
        try {addToReservationFile(r);}
        catch(Exception e) {System.out.println("I/O Error: " + e.getMessage());}
        
    } // End createReservation(customerName, roomType, groupSize, checkInDate, checkOutDate) method
    
    // getReservation() method allows user to obtain desired reservation for further processing.
    public Reservation getReservation(long targetID) {
        
        // Save index of desired reservation object
        int index = reservationID.indexOf(targetID);

        // If it exists, return the desired reservation object
        if (index != -1) return reservation.get(index);

        Reservation r = CheckInnInterface.arcManager.getPastReservation(targetID);

        if (r != null) return r;
        else return null;

    } // End getReservation(targetID)

    // removeFromReservationFile() method removes a past reservation from reservation csv file.
    private void removeFromReservationFile(Reservation r) {

        try{
        
            // Local variables
            String line; // Stores record in reservation list file
            File oldFile = new File("CheckInn\\reservations.txt"); // Stores old csv file
            File newFile = new File("CheckInn\\temp.txt"); // Stores new csv file
            BufferedReader reader = new BufferedReader(new FileReader(oldFile)); // Reads from old file
            BufferedWriter writer = new BufferedWriter(new FileWriter(newFile, true)); // Writes to new file

            // While loop reads and processes each individual record in old csv file.
            while ((line = reader.readLine()) != null) {

                // Split line into its data fields
                String parts[] = line.split(",");

                // If ID of reservation to be removed does not match current record's reservation ID,
                // add record to new csv file.
                if (r.getReservationID() != Long.parseLong(parts[0])) {

                    writer.write(line);
                    writer.newLine();

                } // End if

                // If ID does match, do not add this record to new csv file. Continue to next record
                else continue;

            } // End while

            reader.close();
            writer.close();

            oldFile.delete(); // Delete old csv file
            newFile.renameTo(new File("CheckInn\\reservations.txt")); // Save new csv file

        } // End try

        catch(Exception e) {System.out.println("I/O Error: " + e.getMessage());}

    } // End removeFromReservationFile(r)

    // delete() method removes a past reservation from linked list and array list
    private void delete(int index) {

        reservation.remove(index); // Remove from linked list
        reservationID.remove(index); // Remove froma array list

    } // End delete(index)

    // removeReservation() method removes a reservation object from current/upcoming reservation csv file
    // and from reservation linked/array list
    // Adds past reservation to archive csv file
    public void removeReservation(long reservationNumber, int value) throws IOException {

        // Local variables
        int index = reservationID.indexOf(reservationNumber); // Stores index of target reservation
        Reservation r = getReservation(reservationNumber); // Stores target reservation

        // Use value to determine customer status in relation to their reservation.
        switch(value) {
        // Customer has cancelled the reservation
        case 2:
            CheckInnInterface.repManager.addEvent(r, "0", "Cancelled");
            CheckInnInterface.arcManager.addToArchive(r, "Cancelled");
            break;
        // Customer never arrived to hotel
        case 1:
            CheckInnInterface.repManager.addEvent(r, "0", "Absent");
            CheckInnInterface.arcManager.addToArchive(r, "Absent");
            break;
        // Customer sucessfully completed their reservation
        case 0:
            CheckInnInterface.repManager.addEvent(r, r.getRoomNumber(), "Completed");
            CheckInnInterface.arcManager.addToArchive(r, "Complete");
            switchReservationStatus(r);
            break;
        } // End switch

        removeFromReservationFile(r); // Remove reservation from csv file
        delete(index); // Remove reservation from linked list and array list

    } // End removeReservation(reservationNumber, value)
    
    // switchReservationStatus changes status of reservation and edits csv file to reflect change
    public void switchReservationStatus(Reservation r) {

        // Change reservation status
        r.switchActiveStatus();

        try{
        
            String line; // Store record
            File oldFile = new File("CheckInn\\reservations.txt"); // Store old csv file
            File newFile = new File("CheckInn\\temp.txt"); // Store new csv file
            String checkIn = r.getCheckInDate().getMonthValue() + "/" + r.getCheckInDate().getDayOfMonth() + 
                                "/" + r.getCheckInDate().getYear(); // Store reservation date
            String checkOut = r.getCheckOutDate().getMonthValue() + "/" + r.getCheckOutDate().getDayOfMonth() + 
                                "/" + r.getCheckOutDate().getYear(); // Store reservation date
            BufferedReader reader = new BufferedReader(new FileReader(oldFile)); // Read csv file
            BufferedWriter writer = new BufferedWriter(new FileWriter(newFile, true)); // Writer csv file

            // While loop reads and processes each individual line
            while ((line = reader.readLine()) != null) {

                // Split recprd into its attributes
                String parts[] = line.split(",");

                // If reservation ID does not match the current records's ID, write this record into new 
                // csv file unchanged.
                if (r.getReservationID() != Long.parseLong(parts[0])) {

                    writer.write(line); // Write line
                    writer.newLine(); // Enter new line

                } // End if

                // If they do match, add line with new status update
                else {

                    // Write updated record
                    writer.write(r.getReservationID() + "," + r.getCustomer().getCustomerID() + "," + 
                                    r.getRoomType() + "," + r.getGroupSize() + "," + checkIn + "," + 
                                    checkOut + "," + r.getActiveStatus());
                    // Enter new line
                    writer.newLine();

                } // End else

            } // End while

            oldFile.delete(); // Delete old file
            newFile.renameTo(new File("CheckInn\\reservations.txt")); // Save new file

            reader.close(); // Close reader
            writer.close(); // Close writer

        } // End try

        catch(Exception e) {System.out.println("u suck" + e.getMessage());}

    } // End switchReservationStatus(r)

} // End ReservationManager class