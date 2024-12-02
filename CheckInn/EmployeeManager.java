package CheckInn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.*;

/**
 * EmployeeManager --- Employee controller class manages employee entities and contains features that allow an employee to 
 *                     manage customer reservations, as well as make adjustments as needed for a functional hotel.
 * @author             Julian Aguiar
 */
public class EmployeeManager {
    
    // Global Variables
    private LinkedList<Employee> employee = new LinkedList<Employee>(); // Linked list contains list of employee objects
    private ArrayList<Integer> employeeUser = new ArrayList<Integer>(); // Array list contains ID numbers of each employee

    /**
     * EmployeeManager constructor loads employee information from csv file into employee linked list and array.
     * @exception  Error reading from employee csv file
     * @return     No return value
     */
    public EmployeeManager() {

        try {

            // Local variables
            String line; // Saves a record in employee csv file
            File file = new File("CheckInn\\employees.txt"); // Save name of employee csv file 
            BufferedReader reader = new BufferedReader(new FileReader(file)); // Buffered Reader

            // While loop reads each line from file and loads it into a new employee object.
            // Inserts each newly created object into employee linked list and array.
            while ((line = reader.readLine()) != null) {

                // Split record into parts
                String parts[] = line.split(",");
                // Create employee object from data stored in array
                Employee emp = new Employee(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
                // Store employee object into employee linked list and array
                employee.add(emp);
                employeeUser.add(Integer.parseInt(parts[0]));

            } // End while

            reader.close(); // Close BufferedReader

        } // End try

        // Error reading from employee csv file
        catch(Exception e) {
            System.out.println("I/O Error Employee Manager " + e.getMessage());
        } // End catch
 
    } // End EmployeeManager constructor

    /**
     Allow employee access to employee page if login information exists and matches
     @param user      User identification number of employee
     @param password  The password for the employee's login
     @exception       Inputted user identification number does not exist
     @exception       Inputted password does not match user's saved password
     @return          True if employee user ID exists and passwords match. False otherwise.
     */
    public boolean validateEmployee(int user, String password) {

        // Search for existing user ID and save
        int index = employeeUser.indexOf(user);

        // User ID exists; check if inputted password matches saved password
        if (index > -1) {
            // Retreive employee object with corresponding user ID
            Employee emp = employee.get(index);

            // Return true if passwords match
            if (emp.getPassword().compareTo(password) == 0) return true;
            else return false; // Passwords do not match; return false
        } // End if

        else return false; // Inputted user ID does not exist

    } // End validateEmployee(user, password)

    /**
     * Allows an employee to check in a customer into their hotel reservation if they check in on the correct date.
     * @param reservationID  The ID number of the reservation the customer will check into.
     * @exception            Customer attempts to check in on a date that is not their check in date
     * @exception            Reservation ID number does not exist
     * @exception            There is an error assigning a room to the reservation
     * @return               Returns a string that specifies if check in is a success, or reason for why check in was unsuccessful 
     */
    public String checkInCustomer(long reservationID) throws IOException {

        Reservation r; // Stores reservation object of reservation to be checked into

        // Reservation exists
        if (CheckInnInterface.resManager.getReservation(reservationID)!= null)
            //Save reservation object
            r = CheckInnInterface.resManager.getReservation(reservationID);

        // Reservation does not exist. Returns string specifying that reservation does not exist.
        else return "DNE";

        // Current date that customer is attempting to check into hotel is before their check in date.
        // Returns string specifying that current date is before check in date.
        if (LocalDate.now().isBefore(r.getCheckInDate().getLocalDate())) return "BeforeDate";

        // Current date that customer is attempting to check into hotel is after their check in date.
        // Returns string specifying that current date is after check in date.
        else if (LocalDate.now().isAfter(r.getCheckInDate().getLocalDate())) return "AfterDate";

        // There is an issue assigning a room to the reservation. 
        // Returns string specifying that assignation of room was unsuccessful.
        if (!CheckInnInterface.roomManager.updateRoom(r)) return "Unsuccessful";

        // Program successfully assigned a room to reservation. 
        else {
            // Switch reservation status of reservation from "inactive" (false) to "active" (true)
            CheckInnInterface.resManager.switchReservationStatus(r);
            // Enter event into today's report
            CheckInnInterface.repManager.addEvent(r, r.getRoomNumber(), "Arrived");
            // Returns string specifying that assignation of room was successful.
            return "Successful";
        } // End else
        
    } // End checkInCustomer(reservationID)

    /** 
     * Allows an employee to check out a customer from their reservation.
     * @param reservationID  ID number of reservation to be checked out.
     * @exception            Reservation ID number does not exist
     * @return               True if customer is successfully checked out. False otherwise.
     */ 
    public boolean checkOutCustomer(long reservationID) throws IOException {

        // Reservation number does not exist. Return false
        if (CheckInnInterface.resManager.getReservation(reservationID) == null) return false;

        // Save reservation object corresponding to reservation to be checked out.
        Reservation r = CheckInnInterface.resManager.getReservation(reservationID);

        // Remove reservation from room csv file
        CheckInnInterface.roomManager.assignReservationNumber(r.getRoomNumber(), 0);
        // Remove reservation from active reservation linked list and csv file.
        CheckInnInterface.resManager.removeReservation(reservationID, 0);

        return true; // Customer check out is a success

    } // End checkOutCustomer(reservationID)

    /**
     * Allows an employee to create a new reservation for a customer.
     * @param customerName  The full name of the customer
     * @param roomType      The desired room type for the reservation
     * @param groupSize     The amount of people that will be staying in the room
     * @param checkInDate   The check in date for the reservation
     * @param checkOutDate  The check out date for the reservation
     * @param email         The email of the customer
     * @exception           Any errors related to dates
     * @return              none
     */
    public void createNewReservation(String customerName, String roomType, int groupSize, String checkInDate, 
                                        String checkOutDate, String email) {

        // User reservation manager to create a new reservation
        CheckInnInterface.resManager.createReservation(customerName, roomType, groupSize, checkInDate, checkOutDate, email);

    } // End createNewReservation(customerName, roomType, groupSize, checkInDate, checkOutDate, email)

    /** 
     * Allows an employee to cancel a reservation for a customer
     * @param reservationID  Reservation ID number of reservation to be cancelled
     * @param value          An integer that specifies reason for cancellation of reservation (0 if customer cancelled or 1 if no show)
     * @exception            Reservation does not exist
     * @return               none
     */
    public void cancelReservation(long reservationID, int value) throws IOException {

        // Uses reservation manager to remove the reservation from reservation linked list and csv file.
        CheckInnInterface.resManager.removeReservation(reservationID, value);

    } // End cancelReservation(reservationID, value)

    /** 
     * Allows an employee to manually change the room availability of a room.
     * @param roomNumber    The room number for the room that will have its availability status changed.
     * @param availability  The new status of the availability for the room
     * @exception           Room number does not exist
     * @return              none
     */
    public void changeRoomAvailability(String roomNumber, boolean availability) throws IOException{

        CheckInnInterface.roomManager.updateRoomAvailability(roomNumber, availability);

    } // End changeRoomAvailability(roomNumber, availability)

    /** 
     * Calculates the cost for the amount of nights stayed in the reservation
     * @param               The reservation object r
     * @exception           Invalid RoomType
     * @return              Total cost for # of nights stayed
     */
    public int calculateTotalcost(Reservation r) {
        int pricePerNight;

        String roomType = r.getRoomType();
        
        switch (roomType) {
            case "Single":
                pricePerNight = 120;
                break;
            case "Double":
                pricePerNight = 180;
                break;
            case "Triple":
                pricePerNight = 240;
                break;
            case "Connected":
                pricePerNight = 200;
                break;
            case "Suite":
                pricePerNight = 400;
                break;
            case "Penthouse":
                pricePerNight = 1000;
                break;
            default: 
                System.out.println("Invalid room type: " + roomType);
                return 0;
        }

        return pricePerNight * r.nightsSpent();
    }
} // End EmployeeManager class
