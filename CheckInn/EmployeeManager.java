package CheckInn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.io.File;

public class EmployeeManager {
    

    private LinkedList<Employee> employee = new LinkedList<Employee>();
    private ArrayList<Integer> employeeUser = new ArrayList<Integer>(); 

    public EmployeeManager() {

        try {

            String line; 
            File file = new File("CheckInn\\employees.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file)); 
            
            while ((line = reader.readLine()) != null) {

                String parts[] = line.split(",");
                // Create customer object from data stored in array
                Employee emp = new Employee(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
                // Store eustomer object into customer linked list
                employee.add(emp);
                employeeUser.add(Integer.parseInt(parts[0]));
                // Store customer object ID attribute into customerID array
                // Store customer object email attribute into customerEmail array

            }

            reader.close(); // Close BufferedReader

        } // End try

        // Error reading from customer file
        catch(Exception e) {
            System.out.println("I/O Error Employee Manager " + e.getMessage());
        } // End catch
 
    } // End CustomerManager constructor

    public boolean validateEmployee(int user, String password) {

        int index = employeeUser.indexOf(user);

        if (index > -1) {
            Employee emp = employee.get(index);

            if (emp.getPassword().compareTo(password) == 0) return true;
            else return false;
        }

        else return false;

    }

    public String checkInCustomer(long reservationID) throws IOException {

        Reservation r;

        if (CheckInnInterface.resManager.getReservation(reservationID)!= null) 
                        r = CheckInnInterface.resManager.getReservation(reservationID);

        else return "DNE";

        if (LocalDate.now().isBefore(r.getCheckInDate())) return "BeforeDate";

        else if (LocalDate.now().isAfter(r.getCheckInDate())) return "AfterDate";

        if (!CheckInnInterface.roomManager.updateRoom(r)) return "Unsuccessful";

        else {

            CheckInnInterface.resManager.switchReservationStatus(r);
            return "Successful";

        }

    }

    public boolean checkOutCustomer(long reservationID) throws IOException {

        if (CheckInnInterface.resManager.getReservation(reservationID) == null) return false;

        Reservation r = CheckInnInterface.resManager.getReservation(reservationID);

        CheckInnInterface.roomManager.assignReservationNumber(r.getRoomNumber(), 0);
        CheckInnInterface.roomManager.updateRoomAvailability(r.getRoomNumber(), true);
        CheckInnInterface.resManager.removeReservation(reservationID, 0);

        return true;

    }

    public void createNewReservation(String customerName, String roomType, int groupSize, String checkInDate, 
    String checkOutDate, String email) {

        ReservationManager RM = new ReservationManager();
        RM.createReservation(customerName, roomType, 0, checkInDate, checkOutDate, email);

    }

    public void cancelReservation(long reservationID, int value) throws IOException {

        ReservationManager RM = new ReservationManager();
        RM.removeReservation(reservationID, value);

    }

    public void changeRoomAvailability(String roomNumber, boolean availability) throws IOException{

        RoomManager roomMan = new RoomManager();
        roomMan.updateRoomAvailability(roomNumber, availability);

    }

}
