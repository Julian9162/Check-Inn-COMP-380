package CheckInn;

import java.io.*;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * ArchiveManager --- Program to add completed or cancelled reservations to a storage file for viewing
 * @author            Brian Diaz
 */
public class ArchiveManager {
    
    private File archiveFile = new File("CheckInn\\archive.txt"); // Stores archive csv file
    
    public ArchiveManager() {} // Empty constructor

    /**
     * Adds a reservation to the archive file
     * @param r A reservation object to be added to archive file
     * @param value An integer value indicating case in which reservation was finished
     * @exception Error writing to archive csv file
     * @ return No return value
     */
    public void addToArchive(Reservation r, String value) throws IOException {

        // Write reservation information to archive file
        BufferedWriter writer = new BufferedWriter(new FileWriter(archiveFile));
        writer.write(String.valueOf(r.getReservationID()));
        writer.write("," + r.getCustomer().getCustomerID());
        writer.write("," + r.getRoomType());
        writer.write("," + r.getGroupSize());
        writer.write("," + r.getCheckInDateStr() + "," + r.getCheckOutDateStr());
        writer.write("," + r.getRoomNumber()); 
        writer.write("," + value);
        writer.newLine();
        writer.close(); 
        
    } // End addToArchive(r, value)

    /**
     * Retreives a desired reservation in archive file
     * @param reservationID An identification number for the reservation object
     * @exception Desired reservation does not exist
     * @exception Error reading archive csv file
     * @return Returns desired reservation object
     */
    public Reservation getPastReservation(Long reservationID) {

        String filePath = ("CheckInn\\archive.txt"); // Store archive file path

    // Read archive file
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        
        String line; // Store record

        // While loop iterates while there is a record to be read
        // Ends after desired reservation is located
        while ((line = reader.readLine()) != null) {
            
            String[] parts = line.split(","); // Stores parts of record

            // Conditition checks if reservation ID in current record matches desired
            // reservation number.
            // If true, return desired reservation
            if (Long.parseLong(parts[0]) == reservationID) {
                // Save customer information 
                Customer c = CheckInnInterface.cusManager.getCustomer(Integer.parseInt(parts[1]));
                // Return reservation with corresponding information
                return new Reservation(reservationID, c, parts[2], 
                Integer.parseInt(parts[3]), parts[4], parts[5], false, parts[6]);
            } // End if
            
        } // End while

        return null; // Return null if reservation does not exist
        
    } // End try

    // Any errors reading archive file
    catch (IOException e) {
        System.out.println("Error Reading File: " + e.getMessage()); // Display error message
        return null; // Return null
    } // End catch
    
    } // End getPastReservation(reservationID)

} // End ArchiveManager class
