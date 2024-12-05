package CheckInn;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * RoomManager --- Program that manages all room operations such as assigning a reservation number to 
 * a room, updating and verifying the availability of a room. 
 * @author            Brian Diaz
 */

/**
     *  Retrieves all room details from the room csv file
     * @exception    Error if the room file is not found or cannot be opened.
     * @return       A list of string arrays, where each array represnets the details of a room. Each
     * array element corresponds to a field in the rooms.txt csv file. 
     */
public class RoomManager {
    public String roomFile = "CheckInn\\rooms.txt"; // CSV file is placed here in "roomFile"

    private List<String[]> getAllRooms() throws FileNotFoundException{
        List<String[]> rooms = new ArrayList<>(); // Creates array that will store all the room details
        Scanner scanner = new Scanner(new File(roomFile)); 

	// Going thru contents of roomFile
	// Adds the contents of roomFile to "roomDetails" list
	// The room details are then added to the array "rooms" which stores the details for each room. 
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();				
            String[] roomDetails = line.split(",");
            rooms.add(roomDetails);
        }
        scanner.close(); // Close the scanner 
        return rooms;
    }
	
     /**
     *  Assigns a reservation number to a specified room and updates the room file. 
     * @param     reservationNumber	The reservation number to be assigned to the room. If 0, the room has no reservations
     * @param     roomNumber		The number of the room to which the reservation is assigned 
     * @exception    IOException if any error occurs while reading or writing to the room file. 
     */
    public void assignReservationNumber(String roomNumber, long reservationNumber) throws IOException {
        // Calls getAllRooms method to retrieve room details of the CSV file
	List<String[]> rooms = getAllRooms(); 

        BufferedWriter writer = new BufferedWriter(new FileWriter(roomFile)); 
        for (String[] roomDetails : rooms) {
	    // Checks to see if the room number at index 0 of the CSV matches the parameter "roomNumber" 
	    // If it does match, then the reservation number is updated for the matched room
            if (roomDetails[0].equals(roomNumber)) { 
                roomDetails[3] = String.valueOf(reservationNumber);
		// If the reservation number is not 0, assign the reservation details to the room
                if (reservationNumber != 0) {
		    // Retrieves the reservation object with given reservation number
		    // Then sets the room number for the reservation object
                    Reservation r = CheckInnInterface.resManager.getReservation(reservationNumber);
                    r.setRoomNumber(roomDetails[0]);
                }

            }
            writer.write(String.join(",", roomDetails)); // Writing the updated room details to the file
            writer.newLine();
        }
        writer.close(); 

    }
	
	
     /**
     *  Updates the availability status of a specified room in the room file. 
     * @param     availability		The new availability status of the room (true for avail, false for unavail)
     * @param     roomNumber		The number of the room to update
     * @exception    IOException if any error occurs while reading or writing to the room file. 
     */
    public void updateRoomAvailability(String roomNumber, boolean availability) throws IOException{
        List<String[]> rooms = getAllRooms(); 

        BufferedWriter writer = new BufferedWriter(new FileWriter(roomFile)); 
        // Iterates through each room in the list of room details 
	for (String[] roomDetails : rooms) { 
	    // Checks if the current room matches the specified room number 
	    // if it does match then the availability status of the matched room is updated at index 2
            if (roomDetails[0].equals(roomNumber)) { 
                roomDetails[2] = String.valueOf(availability);
            }
            writer.write(String.join(",", roomDetails)); // Writing the updated room details to the file
            writer.newLine();
        }
        writer.close(); 
    }

     /**
     *  finds the room number of the first available room of the specified room type. 
     * @param     roomType	The type of room to search for.
     * @return	  The room number of the first room the matches specified room type and is available, it will
     * return null if no room is found. 
     * @exception    FileNotFoundException if the room file is not found or cannot be opened
     */
    public String findRoomNumber(String roomType) throws FileNotFoundException {
        List<String[]> rooms = getAllRooms();
        // Iterates through each room in the list of room details
        for (String[] roomDetails: rooms) {
            // Checks if the room matches the specified room type, and checks the availbility of the room
            if (roomDetails[1].equals(roomType) && roomDetails[2].equals("true")) {
		// Returns the room number of the matching room 
                return roomDetails[0];
            }
        }
	// Returns null if no matching room is found
        return null;

    }


     /**
     *  Updates a room by assigning it to a reservation and marking it as unavailable. 
     * @param     r	The reservation object containing the room type and reservation ID
     * @return	  true if a room was succesfully assigned to the reservation, and false if no room was found
     * @exception    IOException if an error occurs while reading or writing to the file. 
     */
    public boolean updateRoom (Reservation r) throws IOException {
        // Find an available room that matches the room type spceified in the reservation object.
	String roomNumber = findRoomNumber(r.getRoomType());
	
	// If a matching room is found
	// The reservation is assigned to the room
	// The rooms availability is updated to false (unavailable) 
        if (roomNumber != null) {
			// Only allows a room to be assigned to a reservation if the room is clean. 
			if (isRoomClean(roomNumber)) {
				assignReservationNumber(roomNumber,r.getReservationID());
				updateRoomAvailability(roomNumber, false);
				markRoomCleanStatus(roomNumber, false);
				return true; // For successful 
			} 
			
        }
        return false; // For unsuccessful
    }

     /**
     *  Checks whether a specified room is clean.  
     * @param    roomNumber		The number of the room to check.
     * @return	  True if the room is clean, false otherwise.
     * @exception    FileNotFoundException		If the room file is not found.
     */
    public boolean isRoomClean (String roomNumber) throws FileNotFoundException {
	    List<String[]> rooms = getAllRooms();

	    for (String [] roomDetails : rooms) {
		    if(roomDetails[0].equals(roomNumber)) {
			    return roomDetails[4].equals("true");
		    }
	    }

	    return false;
    }

     /**
     *  Updates the cleanliness status of a specified room in the room file.
     * @param    roomNumber			The number of the room to check.
     * @param    isClean			True to mark the room as clean, false to mark it as dirty
     * @exception    IOException 	if an error occurs while reading or writing to the file. 
     */
    public void markRoomCleanStatus(String roomNumber, boolean isClean) throws IOException {
	    List<String[]> rooms = getAllRooms();

            BufferedWriter writer = new BufferedWriter(new FileWriter(roomFile));
	    for (String [] roomDetails : rooms) {
		    if (roomDetails[0].equals(roomNumber)) {
			    roomDetails[4] = String.valueOf(isClean);
		    }
		    writer.write(String.join(",", roomDetails));
		    writer.newLine();
	    }
	    writer.close();
    } 

    public int findRoom(Reservation r) {

        return 1;

    }
    
}
