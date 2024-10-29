package checkinn;

import java.time.LocalDate;
import java.io.*;

public class EmployeeManager {
    
    EmployeeManager() {}

    public String checkInCustomer(int reservationID) throws IOException {

        Reservation r;
        ReservationManager RM = new ReservationManager();

        if (RM.getReservation(reservationID)!= null) r = RM.getReservation(reservationID);

        else return "DNE";

        if (LocalDate.now().isBefore(r.getCheckInDate())) return "BeforeDate";

        else if (LocalDate.now().isAfter(r.getCheckInDate())) return "AfterDate";

        RoomManager roomMan = new RoomManager();
        boolean success = roomMan.updateRoom(r);

        if (success == false) return "Unsuccessful";

        else {

            RM.switchReservationStatus(r);
            return "Successful";

        }

    }

    public boolean checkOutCustomer(int reservationID) throws IOException {

        ReservationManager ReM = new ReservationManager();
        RoomManager RoM = new RoomManager();

        if (ReM.getReservation(reservationID) == null) return false;

        Reservation r = ReM.getReservation(reservationID);

        RoM.assignReservationNumber(r.getRoomNumber(), 0);
        ReM.removeReservation(reservationID, 0);

        return true;

    }

    public void createNewReservation(String customerName, String roomType, int groupSize, String checkInDate, 
    String checkOutDate, String email) {

        ReservationManager RM = new ReservationManager();
        RM.createReservation(customerName, roomType, 0, checkInDate, checkOutDate, email);

    }

    public void cancelReservation(int reservationID, int value) {

        ReservationManager RM = new ReservationManager();
        RM.removeReservation(reservationID, value);

    }

    public void changeRoomAvailability(String roomNumber, boolean availability) throws IOException{

        RoomManager roomMan = new RoomManager();
        roomMan.updateRoomAvailability(roomNumber, availability);

    }

}
