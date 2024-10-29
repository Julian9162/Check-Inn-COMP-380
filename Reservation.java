package checkinn;

import java.time.LocalDate;

// Reservation class used to store reservation objects
public class Reservation {
    
    // Global variables
    private Customer customer; // Saves customer information of customer that booked reservation
    private String roomType; // Saves desired room type for reservation
    private int groupSize; // Saves group size for reservation
    private LocalDate checkInDate; // Saves the check in date for the reservation
    private LocalDate checkOutDate; // Saves the check out date for the reservation
    private int reservationID; // Saves the reservation identification number for reservation
    private boolean activeStatus; // Saves the status of reservation (whether it is active or not)
    private String roomNumber;

    // Reservation constructor creates reservation object
    public Reservation(int reservationID, Customer customer, String roomType, int groupSize, 
                        String checkInDate, String checkOutDate, boolean activeStatus, String roomNumber) {

        // Saves reservation ID number + customer information + desired room type + group size + status of reservation
        this.reservationID = reservationID;
        this.customer = customer;
        this.roomType = roomType;
        this.groupSize = groupSize;
        this.activeStatus = activeStatus;
        this.roomNumber = roomNumber;

        // Saves check in date into LocalDate object
        String[] dateParts1 = checkInDate.split("/");
        this.checkInDate = LocalDate.of(Integer.parseInt(dateParts1[2]), Integer.parseInt(dateParts1[0]), 
                                        Integer.parseInt(dateParts1[1]));

        // Saves check out date into LocalDate object
        String[] dateParts2 = checkOutDate.split("/");
        this.checkOutDate = LocalDate.of(Integer.parseInt(dateParts2[2]), Integer.parseInt(dateParts2[0]), 
                                        Integer.parseInt(dateParts2[1]));

    } // End Reservation(reservationID, customer, roomType, groupSize, checkInDate, checkOutDate) constructor

    // switchActiveStatus() method allows employee to switch the status of reseration to active or to not active
    public void switchActiveStatus() {
        activeStatus = !activeStatus;
    } // End switchActiveStatus()

    // getCustomer() returns customer information of customer that booked this reservation
    public Customer getCustomer() {
        return customer;
    } // End getCustomer()

    // getRoomType() returns desired room type of reservatiom
    public String getRoomType() {
        return roomType;
    } // End getRoomType()

    // getGroupSize() method returns group size
    public int getGroupSize() {
        return groupSize;
    } // End getGroupSize()

    // getCheckInDate() returns date for checking in
    public LocalDate getCheckInDate() {
        return checkInDate;
    } // End getCheckInDate()

    // getCheckOutDate() returns date for checking out
    public LocalDate getCheckOutDate() {
        return checkOutDate; 
    } // End getCheckOutDate()

    // getReservationID() returns identification number of this reservation
    public int getReservationID() {
        return reservationID;
    } // End getReservationID()

    // getActiveStatus() returns status of this reservation
    public boolean getActiveStatus() {
        return activeStatus;
    } // End getActiveStatus

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

} // End Reservation class
