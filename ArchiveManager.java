package checkinn;

import java.io.*;

public class ArchiveManager {
    public String archiveFile = ""; // Archive File
    public ArchiveManager() {}

    // Method to write removed Reservation into txt file "archive.txt"
    public void addToArchive(Reservation r, String value) throws IOException {

        // Local variables
        // checkIn and checkOut store reservation dates in string form
        String checkIn = r.getCheckInDate().getMonthValue() + "/" + r.getCheckInDate().getDayOfMonth() + "/" +
                        r.getCheckInDate().getYear();
        String checkOut = r.getCheckOutDate().getMonthValue() + "/" + r.getCheckOutDate().getDayOfMonth() + "/" +
                        r.getCheckOutDate().getYear();

        BufferedWriter writer = new BufferedWriter(new FileWriter(archiveFile));
        writer.write(r.getReservationID());
        writer.write("," + r.getCustomer().getCustomerID());
        writer.write("," + r.getRoomType());
        writer.write("," + checkIn + "," + checkOut);
        writer.write("," + r.getRoomNumber()); 
        writer.write("," + value);
        writer.newLine();
        writer.close();
    }


    // loadCSV
    // makes new array list and uses buffered reader or scanner to load CSV data into array list
    

    public findReservationID() {
    // findReservationID
    // makes reservations array list and uses loadCSV to get data from reservation.txt and loads
    // into reservatiosn array list, 

    // Goes thru reservations array list one string at a time 
    // if the first index of the string matches the reservation ID then will call 
    // a method to create the reservation using data from reservation.txt

    // If not found in reservation.txt it will check archives
    // archived reservations array list is created using loadCSV 
    // Goes thru archived reservations array list
    // checks first index to see if it matches reservation ID
    // calls method to create reservation using data from archive.txt
    }


    // method that creates reservations based on data from either reservations.txt or archive.txt

}