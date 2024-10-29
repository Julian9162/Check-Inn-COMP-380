package CheckInn;

import java.io.*;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class ArchiveManager {
    public File archiveFile = new File("CheckInn\\archive.txt");
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
        writer.write(String.valueOf(r.getReservationID()));
        writer.write("," + r.getCustomer().getCustomerID());
        writer.write("," + r.getRoomType());
        writer.write("," + r.getGroupSize());
        writer.write("," + checkIn + "," + checkOut);
        writer.write("," + r.getRoomNumber()); 
        writer.write("," + value);
        writer.newLine();
        writer.close();
    }


    public Reservation getPastReservation(Long reservationID) {
    String filePath = ("CheckInn\\archive.txt");
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        
        while ((line = reader.readLine()) != null) {
            String [] parts = line.split(",");

            if (Long.parseLong(parts[0]) == reservationID) {
                Customer c = CheckInnInterface.cusManager.getCustomer(Integer.parseInt(parts[1]));
                return new Reservation(reservationID, c, parts[2], 
                Integer.parseInt(parts[3]), parts[4], parts[5], false, parts[6]);
            }
        }

        return null;
    }

    catch (IOException e) {
        System.out.println("Error Reading FIle: " + e.getMessage());

        return null;
    }
    

    }


}