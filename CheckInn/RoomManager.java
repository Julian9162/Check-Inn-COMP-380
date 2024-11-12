package CheckInn;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RoomManager {
    public String roomFile = "CheckInn\\rooms.txt"; // File with rooms goes here

    private List<String[]> getAllRooms() throws FileNotFoundException{
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

    public void assignReservationNumber(String roomNumber, long reservationNumber) throws IOException {
        List<String[]> rooms = getAllRooms(); 

        BufferedWriter writer = new BufferedWriter(new FileWriter(roomFile)); 
        for (String[] roomDetails : rooms) {
            if (roomDetails[0].equals(roomNumber)) { 
                roomDetails[3] = String.valueOf(reservationNumber);

                if (reservationNumber != 0) {
                    Reservation r = CheckInnInterface.resManager.getReservation(reservationNumber);
                    r.setRoomNumber(roomDetails[0]);
                }

            }
            writer.write(String.join(",", roomDetails)); // Writing the updated room details to the file
            writer.newLine();
        }
        writer.close(); 

    }

    public boolean verifyDateAvailability(String checkIn, String checkOut) {
    
        // Date Format: Year - Month - Day
        String[] inDate = checkIn.split("-");
        String[] outDate = checkOut.split("-");

        int[] in = new int[3];
        int[] out = new int[3];

        for (int i = 0; i < 3) {
		in[i] = Integer.parseInt(inDate[i]);
		out[i] = Integer.parseInt(outDate[i]);
	}
        
	LocalDate desiredCheckIn = LocalDate.of(in[0],in[1],in[2]);
	LocalDate desiredCheckout = LocalDate.of(out[0],out[1],out[2]);
        
	// Checking to see if the desired check out date is after the desired check in date
	if (!desiredCheckOut.isAfter(desiredCheckIn)) {
		System.out.println("Check out date must be after check in date");
		return false;
	}
        
	// Checking for overlapping reservations
	for (//go through reservations) {
		// Checks to see if the reservation dates overlap with the desired dates
		// First cond: Desired reservation check out date is before existing reservations check indate
		// Second cond: Desired reservation check in date is after existing reservations check outdate
		if (!(desiredCheckOut.isBefore(r.getCheckInDate()) || desiredCheckIn.isAfter(r.getCheckOutDate()))) {
			return false; // Date overlap found
		}
	}

	return true; //No overlapping reservations
    }
    
    public void updateRoomAvailability(String roomNumber, boolean availability) throws IOException{
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

    // Method to find room number based on room type and availability
    public String findRoomNumber(String roomType) throws FileNotFoundException {
        List<String[]> rooms = getAllRooms();
        
        for (String[] roomDetails: rooms) {
            // Checks index 1 which contains the roomtype to see if its the correct room type, and then checks 
            if (roomDetails[1].equals(roomType) && roomDetails[2].equals("true")) {
                return roomDetails[0];
            }
        }

        return null;

    }


    // Method to update room
    public boolean updateRoom (Reservation r) throws IOException {
        String roomNumber = findRoomNumber(r.getRoomType());

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