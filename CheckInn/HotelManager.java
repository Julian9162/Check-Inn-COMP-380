package checkinn;

import java.util.LinkedList;
import java.util.ArrayList;
import java.time.LocalDate;
import java.io.File;
import java.io.PrintWriter;


// HotelManager class manages all reservations for hotel.
// Allows user to create, cancel, and review reservations.
public class HotelManager {

    LinkedList<Reservation> reservation = new LinkedList<Reservation>();
    ArrayList<Integer> reserveNum = new ArrayList<Integer>();

    // generateKey method creates unique identification number for reservation
    private void generateKey(Reservation newReserve) {

        // Local variables
        int i, key, first, last;
        char fInit, lInit;

        // Find first and last initial of client that booked the reservation.
        fInit = newReserve.getCustomerName().charAt(0);
        lInit = newReserve.getCustomerName().charAt(newReserve.getCustomerName().indexOf(" ") + 1);

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
        if (!reserveNum.isEmpty()) {

            // Start at first element in array list
            i = 0;
            
            // Compare uniqueness to every element in array list
            while (i < reserveNum.size()) {

                // If created key is not equal to reservation[i], move onto reservation[i+1] 
                if (key != reserveNum.get(i)) {
                    i++;
                    continue;
                } // End if

                // If created key is equal to reservation[i], make adjustment and restart comparisons
                else {
                    key = key + 1;
                    i = 0;
                } // End else

            } // End while

            // Add new reservation number to array list
            reserveNum.add(key);
            // Assign reservation number to new reservation.
            newReserve.setReserveNum(key);
            reservation.add(newReserve);

        } // End if

        else {
            // Add new reservation number to array list and assign to new reservation.
            reserveNum.add(key);
            newReserve.setReserveNum(key);
            reservation.add(newReserve);
        } // End else

    } // End generateKey(newReserve)

    // createReserve method allows user to create a new reservation.
    // Generates unique confirmation number.
    // Stores reservation key in reservation array list for easy access to specified reservation.
    public void createReserve(String customerName, String roomType, int groupSize, String checkInDate, 
                                String checkOutDate, String email) {

        // Local variables
        Reservation newReserve; // New reservation to be created.

        // Creates new reservation. Includes basic reservation information.
        newReserve = new Reservation(customerName, roomType, groupSize, checkInDate, checkOutDate, email);

        // Generate unique key for reservation.
        generateKey(newReserve);

       // generateConfirmation(newReserve);
        
    } // End createReserve(customerName, roomType, groupSize, checkInDate, checkOutDate) method

    // getReserve method allows user to obtain desired reservation for further processing.
    public Reservation getReserve(int desiredRes) {
        
        return reservation.get(reserveNum.indexOf(desiredRes));

    }

    public void cancelReserve(int reservationNumber) {
        
        int target = reserveNum.indexOf(reservationNumber);
        reserveNum.remove(target);
        reservation.remove(target);

    }
    
} // End HotelManager