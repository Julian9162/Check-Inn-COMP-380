package CheckInn;

import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ArrayList;

 /**
 * ReservationManager --- This class manages all reservations for the hotel, allows user operations such as creating, cancelling, and reviewing reservations. Manages the reservation 
 * list csv file, and stores all current and upcoming reservations in a linked list. 
 * @authors        Julian Aguiar and Brian Diaz
 */

public class ReservationManager {

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
                Customer c = CheckInnInterface.cusManager.getCustomer(Integer.parseInt(parts[1]));

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

    public boolean checkDates(String checkIn, String checkOut, String roomType, Reservation r) {

        Date d;
        String s;
        String[] parts;
        boolean same;
        LocalDate in, out, temp;

        parts = checkIn.split("-");
        in = LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        
        parts = checkOut.split("-");
        out = LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));

        if (in.isAfter(out)) return false;

        temp = in;
        while (!temp.isAfter(out)) {

            same = false;
            s = temp.getYear() + "-" + temp.getMonthValue() + "-" + temp.getDayOfMonth();

            if (r != null && r.scheduleContains(s)) same = true;

            d = CheckInnInterface.dateManager.checkDate(s, roomType, same);

            if (d == null) return false;

            temp = temp.plusDays(1);

        }

        return true;

    }

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

        // Writes lines to end of reservation list file
        BufferedWriter writer = new BufferedWriter(new FileWriter("CheckInn\\reservations.txt", true));

        // Write reservation data in following order: ReservationID, CustomerID, RoomType, GroupSize, CheckInDate,
        // CheckOutDate, ActiveStatus
        
        writer.write(r.getReservationID() + "," + r.getCustomer().getCustomerID() + "," + r.getRoomType() + "," 
                        + r.getGroupSize() + "," + r.getCheckInDateStr() + "," + r.getCheckOutDateStr() + "," + r.getActiveStatus() + "," + r.getRoomNumber());
        writer.newLine(); // Create new line
        writer.close(); // Close BufferedWriter

    } // End addToReservationFile(r)

    // createReservation() method allows user to create a new reservation.
    // Generates unique confirmation number.
    // Adds newly created reservation object to reservation linked list
    // Stores reservation key in reservation array list for easy access to specified reservation.
    public Reservation createReservation(String customerName, String roomType, int groupSize, String checkInDate, 
                                String checkOutDate, String email) {

        // Local variables
        Reservation r; // New reservation to be created.
        String[] name = customerName.split(" "); // Split first and last
        Customer c = CheckInnInterface.cusManager.getCustomer(name[0], name[1], email); // Obtain a customer object

        if (!checkDates(checkInDate, checkOutDate, roomType, null)) return null;

        // Creates new reservation. Includes basic reservation information and creates a new key.
        r = new Reservation(generateKey(c), c, roomType, groupSize, checkInDate, checkOutDate, false, "0");
        r.updateSchedule();
        CheckInnInterface.dateManager.updateDateFile();
        reservation.add(r); // Add to linked list
        reservationID.add(r.getReservationID()); // Add to array list

        // Add newly created reservation object to reservation csv file
        try {addToReservationFile(r);}
        catch(Exception e) {System.out.println("I/O Error: here " + e.getMessage());}

        CheckInnInterface.repManager.addEvent(r, "0", "Created");

        return r;
        
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
        
            BufferedWriter writer = new BufferedWriter(new FileWriter("CheckInn\\reservations.txt"));

            for (int i = 0; i < reservation.size(); i++) {

                Reservation res = reservation.get(i);

                if (res.getReservationID() != r.getReservationID()) {

                    writer.write(res.getReservationID() + "," + res.getCustomer().getCustomerID() + "," +
                    res.getRoomType() + "," + res.getGroupSize() + "," + res.getCheckInDateStr() + "," + 
                    res.getCheckOutDateStr() + "," + res.getActiveStatus() 
                    + "," + res.getRoomNumber());

                    writer.newLine();

                }

            }

            writer.close();

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
            CheckInnInterface.dateManager.removeFromDates(r);
            CheckInnInterface.repManager.addEvent(r, "0", "Cancelled");
            CheckInnInterface.arcManager.addToArchive(r, "Cancelled");
            break;
        // Customer never arrived to hotel
        case 1:
            CheckInnInterface.dateManager.removeFromDates(r);
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

    public void modifyReservationFile() {

        try{
        
            BufferedWriter writer = new BufferedWriter(new FileWriter("CheckInn\\reservations.txt"));

            for (int i = 0; i < reservation.size(); i++) {

                Reservation res = reservation.get(i);

                writer.write(res.getReservationID() + "," + res.getCustomer().getCustomerID() + "," +
                res.getRoomType() + "," + res.getGroupSize() + "," + res.getCheckInDateStr() + "," + 
                res.getCheckOutDateStr() + "," + res.getActiveStatus() 
                + "," + res.getRoomNumber());

                writer.newLine();

            }

            writer.close();

        } // End try

        catch(Exception e) {System.out.println("IO Error ResManager switch: " + e.getMessage());}

    }
 
    // switchReservationStatus changes status of reservation and edits csv file to reflect change
    public void switchReservationStatus(Reservation r) {
        
        // Change reservation status
        if (r != null) r.switchActiveStatus();

        modifyReservationFile();

    } // End switchReservationStatus(r)

    public boolean editReservation(long reservationID, String roomType, int groupSize, String checkIn, 
                                    String checkOut) {

        Reservation r = getReservation(reservationID);

        if (r != null) {

            if (!roomType.equals(r.getRoomType())) {

                if (!checkDates(checkIn, checkOut, roomType, null)) return false;

                else {

                    CheckInnInterface.dateManager.removeFromDates(r);
                    r.setRoomType(roomType);
                    r.setSchedule(checkIn, checkOut);
                    r.updateSchedule();
                    CheckInnInterface.dateManager.updateDateFile();

                }

            }

            else {

                if (!checkIn.equals(r.getCheckInDateStr()) || !checkOut.equals(r.getCheckOutDateStr())) {

                    if (!checkDates(checkIn, checkOut, roomType, r)) return false;

                    else {

                        CheckInnInterface.dateManager.removeFromDates(r);
                        r.setSchedule(checkIn, checkOut);
                        r.updateSchedule();
                        CheckInnInterface.dateManager.updateDateFile();
                        
                    }

                }

            }

            r.setGroupSize(groupSize);
            CheckInnInterface.repManager.addEvent(r, "0", "Edited");
            modifyReservationFile();
            return true;
            
        }

        return false;

    }

} // End ReservationManager class
