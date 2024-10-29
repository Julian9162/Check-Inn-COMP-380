package checkinn;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;

// ReportManager class manages all report objects
// Creates new report or modifies existing report daily.
// Adds new record to report every time an event occurs in relation to customer and reservation
public class ReportManager {

    // Global variables
    File reportList = new File("report_list.txt"); // File that is list of all report files
    ArrayList<String> report = new ArrayList<String>(); // Array of all report file names

    // ReportManager constructor loads all report objects from report_list.txt file into array list
    public ReportManager() {

        try {

            String line; // Saves line in report
            BufferedReader reader = new BufferedReader(new FileReader(reportList)); // Reads from report_list
            
            // While loop adds all report files into array list
            while ((line = reader.readLine()) != null) report.add(line);

            reader.close(); // Close reader

        } // End try

        catch(Exception e) {}

    } // End ReportManager() constructor

    // addEvent() method adds new record to today's report. 
    // Creates new file incase file does not exist.
    public void addEvent(Reservation reservation, Room room, String event) {

        try {

            // Saves name of today's report file
            String fileName = "report_" + LocalDate.now().getMonthValue() + "_" + LocalDate.now().getDayOfMonth()
                            + "_" + LocalDate.now().getYear() + ".txt";

            // Saves file of today's report
            File file = new File(fileName);
            // Writes to today's report file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            // Obtains object of today's report
            Report todaysReport = new Report(fileName);
            // Saves current time
            String time = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute();

            // Switch that adds specific message to today's report.
            // Message depends on what event is.
            // Events include: Customer creates reservation, customer cancells reservation, customer checks out,
            // customer checks in, customer never shows up
            switch (event) {

            case "Created":
                todaysReport.addToReport("TIME: " + time + " - CUSTOMER: " + reservation.getCustomer().getCustomerID()
                + " - RESERVATION: " + reservation.getReservationID() + " - MESSAGE: Reservation Created");
                break;

            case "Cancelled":
                todaysReport.addToReport("TIME: " + time + " - CUSTOMER: " + reservation.getCustomer().getCustomerID()
                + " - RESERVATION: " + reservation.getReservationID() + " - MESSAGE: Reservation Cancelled");
                break;

            case "Completed":
                todaysReport.addToReport("TIME: " + time + " - CUSTOMER: " + reservation.getCustomer().getCustomerID()
                + " - RESERVATION: " + reservation.getReservationID() + " - MESSAGE: Customer Checked Out");
                break;

            case "Absent":
                todaysReport.addToReport("TIME: " + time + " - CUSTOMER: " + reservation.getCustomer().getCustomerID()
                + " - RESERVATION: " + reservation.getReservationID() + " - MESSAGE: Customer Never Arrived");
                break;

            case "Arrived":
                todaysReport.addToReport("TIME: " + time + " - CUSTOMER: " + reservation.getCustomer().getCustomerID()
                + " - RESERVATION: " + reservation.getReservationID() + " - MESSAGE: Customer Checked In");
                break;

            } // End switch

        } // End try

        catch(Exception e) {}

    } // End addEvent(reservation, room, event) method
    
} // End ReportManager class
