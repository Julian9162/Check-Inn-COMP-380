package CheckInn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

 /**
 * Reservation --- This class is used to store and manage individual reservation objects. It representings a booking in
 * the system, storing details such as room type, group size, reservation dates etc. 
 * @authors            Julian Aguiar and Brian Diaz
 */


public class Reservation {
    
    // Global variables
    private Customer customer; // Saves customer information of customer that booked reservation
    private String roomType; // Saves desired room type for reservation
    private int groupSize; // Saves group size for reservation
    private long reservationID; // Saves the reservation identification number for reservation
    private boolean activeStatus; // Saves the status of reservation (whether it is active or not)
    private String roomNumber;
    private List<Date> schedule;
    private List<String> scheduleIndex;

    // Reservation constructor creates reservation object
    public Reservation(long reservationID, Customer customer, String roomType, int groupSize, 
                        String checkInDate, String checkOutDate, boolean activeStatus, String roomNumber) {

        // Saves reservation ID number + customer information + desired room type + group size + status of reservation
        this.reservationID = reservationID;
        this.customer = customer;
        this.roomType = roomType;
        this.groupSize = groupSize;
        this.activeStatus = activeStatus;
        this.roomNumber = roomNumber;

        setSchedule(checkInDate, checkOutDate);

    } // End Reservation(reservationID, customer, roomType, groupSize, checkInDate, checkOutDate) constructor

    // switchActiveStatus() method allows employee to switch the status of reseration to active or to not active
    public void switchActiveStatus() {
        activeStatus = !activeStatus;
    } // End switchActiveStatus()

    public void updateSchedule() {

        for (int i = 0; i <= schedule.size() - 1; i++) schedule.get(i).updateDate(roomType, 1);

    }

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

    public List<Date> getSchedule() {
        return schedule;
    }

    //getCheckInDate() returns date for checking in
    public Date getCheckInDate() {
        return schedule.get(0);
    } // End getCheckInDate()

    //getCheckOutDate() returns date for checking in
    public Date getCheckOutDate() {
        return schedule.get(schedule.size() - 1);
    } // End getCheckOutDate()

    // getCheckInDate() returns date for checking in
    public String getCheckInDateStr() {
        return schedule.get(0).dateString();
    } // End getCheckInDate()

    // getCheckOutDate() returns date for checking out
    public String getCheckOutDateStr() {
        return schedule.get(schedule.size() - 1).dateString();
    } // End getCheckOutDate()

    // getReservationID() returns identification number of this reservation
    public long getReservationID() {
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

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }
    
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setSchedule(String i, String o) {

        String s;
        LocalDate in;
        LocalDate out;
        LocalDate lD;
        Date d;
        schedule = new ArrayList<>();
        scheduleIndex = new ArrayList<>();

        // Saves check in date into LocalDate object
        String[] dateParts1 = i.split("-", 3);
        in = LocalDate.of(Integer.parseInt(dateParts1[0]), Integer.parseInt(dateParts1[1]), 
                                        Integer.parseInt(dateParts1[2]));

        // Saves check out date into LocalDate object
        String[] dateParts2 = o.split("-", 3); 
        out = LocalDate.of(Integer.parseInt(dateParts2[0]), Integer.parseInt(dateParts2[1]), 
                                        Integer.parseInt(dateParts2[2]));

        lD = in;
        while (!lD.isAfter(out)) {

            s = lD.getYear() + "-" + lD.getMonthValue() + "-" + lD.getDayOfMonth();
            d = CheckInnInterface.dateManager.getDate(s);

            schedule.add(d);
            scheduleIndex.add(s);
            lD = lD.plusDays(1);
            
        }

    }

    public boolean scheduleContains(String d) {

        if (scheduleIndex.contains(d)) return true;

        return false;
        
    }

   public int nightsSpent() {
        return schedule.size();
   }

} // End Reservation class
