package CheckInn;

import java.io.*;
import java.time.LocalDate;
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
     *  Verifies the availability of dates for a new reservation 
     * @param     checkIn  The desired check in date
     * @param     checkOut The desired check out date
     * @return	  true if the dates are valid and do not overlap with existing reservations
     */
    public boolean verifyDateAvailability(String checkIn, String checkOut) {
    
        // Date Format: Year - Month - Day
        String[] inDate = checkIn.split("-");
        String[] outDate = checkOut.split("-");

	// Convert the string into integers
        int[] in = new int[3];
        int[] out = new int[3];

        for (int i = 0; i < 3) {
		in[i] = Integer.parseInt(inDate[i]);
		out[i] = Integer.parseInt(outDate[i]);
	}

	// Create localDate objects for the desired check in and check out date
	LocalDate desiredCheckIn = LocalDate.of(in[0],in[1],in[2]);
	LocalDate desiredCheckOut = LocalDate.of(out[0],out[1],out[2]);
        
	// Checking to see if the desired check out date is after the desired check in date
	if (!desiredCheckOut.isAfter(desiredCheckIn)) {
		System.out.println("Check out date must be after check in date");
		return false; 
	}
        
	// Checking for overlapping reservations
	for (//go through reservations) {
		// Checks to see if the desired reservation dates overlap with the existing reservation dates 
		// First cond: Desired reservation check out date is before existing reservations check indate
		// Second cond: Desired reservation check in date is after existing reservations check outdate
		if (!(desiredCheckOut.isBefore(r.getCheckInDate()) || desiredCheckIn.isAfter(r.getCheckOutDate()))) {
			return false; // Overlapping reservation dates found.
		}
	}

	return true; //No overlapping reservations
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
            assignReservationNumber(roomNumber,r.getReservationID());
            updateRoomAvailability(roomNumber, false);
            return true; // For successful 
        } else {
            return false; // For unsuccessful
        }
    }

    public int findRoom(Reservation r) {

        return 1;

    }
    
}
