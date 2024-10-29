package CheckInn;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RoomManager {
    public String roomFile = ""; // File with rooms goes here

    private List<String[]> getAllRooms() throws FileNotFoundException {
        List<String[]> rooms = new ArrayList<>(); // Creates array that will store all the room details
        Scanner scanner = new Scanner(new File(roomFile)); 

		// Going thru contents of roomFile
		// Adds the contents of roomFile to "roomDetails" list
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();				
            String[] roomDetails = line.split(",");
            rooms.add(roomDetails);
        }
        scanner.close(); // Close the scanner 
        return rooms;
    }

    public void assignReservationNumber(String roomNumber, String reservationNumber) throws IOException {
        List<String[]> rooms = getAllRooms(); 

        BufferedWriter writer = new BufferedWriter(new FileWriter(roomFile)); 
        for (String[] roomDetails : rooms) {
            if (roomDetails[0].equals(roomNumber)) { 
                // Because reservation # index is empty, a new index for 4th element (reservation number) must be added
                if (roomDetails.length == 3) {
                    roomDetails = Arrays.copyOf(roomDetails, 4); // Expand to 4 elements
                    roomDetails[3] = ""; // Initialize the 4th element (reservationNumber) to an empty string
                }
				// Adds reservation number if index is empty
                if (roomDetails[3].isEmpty()) {
                    roomDetails[3] = reservationNumber; // Add reservation number
                }
            }
            writer.write(String.join(",", roomDetails)); // Writing the updated room details to the file
            writer.newLine();
        }
        writer.close(); 
    }

    public void updateRoomAvailability(String roomNumber, boolean availability) throws IOException {
        List<String[]> rooms = getAllRooms(); 

        BufferedWriter writer = new BufferedWriter(new FileWriter(roomFile)); 
        for (String[] roomDetails : rooms) { // Goes through each room and checks whether it's the room we want to update
            if (roomDetails[0].equals(roomNumber)) { // Checking if the current room matches the room to be updated
                roomDetails[2] = String.valueOf(availability); // Updates the availability of the room
            }
            writer.write(String.join(",", roomDetails)); // Writing the updated room details to the file
            writer.newLine();
        }
        writer.close(); 
    }
}
