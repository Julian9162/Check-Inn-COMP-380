package CheckInn;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.ArrayList;
import java.time.LocalDate;

/**
 * DateManager --- Controller class that manages creation, storage, and access of date entities.
 * @author         Julian Aguiar
 */
public class DateManager {

    // Global Variables
    private LinkedList<Date> dates = new LinkedList<Date>(); // Date linked list
    private ArrayList<String> dateS = new ArrayList<String>(); // Date string array list for indexing
    private File dateFile = new File("CheckInn\\dates.txt"); // Stores date csv file
    
    /**
     * Class constructor loads all date records from date.txt csv file into date object linked list and array
     * @exception  Error reading from date csv file
     * @return     No return value
     */
    public DateManager() {

        try {

            // Local variables
            String line; // Stores a record from csv file
            BufferedReader reader = new BufferedReader(new FileReader(dateFile)); // Reading through file
            
            // While loop saves each record from csv file into a date object and adds to class lists
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(","); // Stores each field from record
                String[] dv = parts[0].split("-"); // Stores year, month, and day of date
                // Saves the local date object of the date
                LocalDate d = LocalDate.of(Integer.parseInt(dv[0]), Integer.parseInt(dv[1]), Integer.parseInt(dv[2]));
                
                // Removes any dates that have already passed from csv file and does not add to lists
                if (LocalDate.now().isAfter(d)) {

                    removeFromDateFile(parts[0]);
                    continue;

                } // End if

                // Create new date object and add to date lists
                dates.add(new Date(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), 
                Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), 
                Integer.parseInt(parts[6])));
                dateS.add(parts[0]);

            } // End while

            reader.close(); // Close buffered reader

        } // End try

        // Error handling
        catch(Exception e) {System.out.println("I/O Error DateManager Constr: " + e.getMessage());}

    } // End DateManager constructor

    /**
     * Algorithm used for swaping two dates in object lists
     * @param m    Date to the left on list
     * @param n    Date to the right on list
     * @exception  none
     * @return     No return value
     */
    public void swapDates(int m, int n) {

        // Save date to the left temporarily
        Date d = dates.get(m);
        String s = dateS.get(m);

        // Swap m and n in object linked list
        dates.set(m, dates.get(n));
        dates.set(n, d);

        // Swap m and n in date string list
        dateS.set(m, dateS.get(n));
        dateS.set(n, s);

    } // End void swapDates(m, n)

    /**
     * Bubble Sort algorithm used for sorting date csv file and object lists in chronological order
     * @exception  none
     * @return     No return value
     */
    public void sortDates() {

        // Outer for loop runs n - 1 times
        for (int i = 1; i <= dates.size(); i++) {

            // Inner for loop runs n times
            for (int j = 0; j < dates.size() - 1; j++) {

                // If date to left is after date to right, swap them in Date list objects
                if (dates.get(j).getLocalDate().isAfter(dates.get(j + 1).getLocalDate())) 
                    swapDates(j, j + 1); // Swaps the dates

            } // End for

        } // End for

        updateDateFile(); // Update csv file

    } // End sortDates()

    /**
     * Adds a new date to date csv file
     * @param d    Date object to be added
     * @exception  Error writing to csv file
     * @return     No return value
     */
    public void addToDateFile(Date d) {

        try {

            // Adds new date to end of the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(dateFile, true));
            writer.write(d.dateString() + "," + d.getOverlaps()); 
            writer.newLine();
            writer.close();

            // Sorts the csv file and object list
            sortDates();

        } // End try

        // Exception handling
        catch(Exception e) {System.out.println("I/O Error DateManager add: " + e.getMessage());}

    } // End addToDateFile(d)

    /**
     * Saves modifications made to date objects into csv file
     * @exception  Error writing to csv file
     * @return     No return value
     */
    public void updateDateFile() {

        try{
        
            BufferedWriter writer = new BufferedWriter(new FileWriter(dateFile)); // Writer

            // For loop writes each record into new line
            for (int i = 0; i < dates.size(); i++) {

                // Saves date object at index i in date object list
                Date d = dates.get(i);

                // Saves date records into csv file
                writer.write(d.dateString() + "," + d.getOverlaps());
                writer.newLine();

            } // End for

            writer.close(); // Closer writer

        } // End try

        // Error handling
        catch(Exception e) {System.out.println("IO Error DateManager update: " + e.getMessage());}

    } // End updateDateFile()

    /**
     * Removes a record from csv file
     * @param s    Date to be removed from csv file
     * @exception  Error writing to csv file
     * @return     No return value
     */
    public void removeFromDateFile(String s) {

        try{
        
            // Local Variables
            Date date = dates.get(dateS.indexOf(s)); // Save date object to be removed
            BufferedWriter writer = new BufferedWriter(new FileWriter(dateFile)); // Writer

            // For loop compares date to be removed with every record in csv file. 
            // Removes date record when found
            for (int i = 0; i < dates.size(); i++) {

                Date d = dates.get(i); // Saves date object at index i

                // Compare date objects
                // If they are the same, delete it
                if (!d.getLocalDate().isEqual(date.getLocalDate())) {

                    writer.write(d.dateString() + "," + d.getOverlaps());
                    writer.newLine();

                } // End if

            } // End for

            writer.close(); // Close writer

        } // End try

        // Error handling
        catch(Exception e) {System.out.println("I/O Error: remove" + e.getMessage());}

    } // End removeFromDateFile(s)

    /**
     * Retrieves a specific date from date list or creates a new date and returns it
     * @param d    Desired date in string representation
     * @exception  None
     * @return     Desired date object
     */
    public Date getDate(String d) {

        // If date object does not exist in date list, create a new date and return
        if (!dateS.contains(d)) { 
            
            Date date = new Date(d); // Create new date object
            dates.add(date); // Add date to linked list
            dateS.add(d); // Add date to string array list
            addToDateFile(date); // Add date to csv file
            return date; // Return new date object

        } // End if

        // Date object exists. Return desired date object
        else return dates.get(dateS.indexOf(d));

    } // End getDate(d)

    /**
     * Checks if there is a room of desired room type available for a specific date
     * @param d    String representation of desired date to be checked
     * @param r    Desired room type to be checked
     * @param b    Value indicating if a reservation is being edited and is already occupying this date
     * @exception  Null date limit reached for desired room type
     * @return     Desired date object
     */
    public Date checkDate(String d, String r, boolean b) {

        // If date object does not exist, no reservations have been booked for that date
        // Create new date and return 
        if (!dateS.contains(d)) return new Date(d);

        // Saves desired date object
        Date date = dates.get(dateS.indexOf(d));

        // Checks if there are available rooms of type r for this date
        // Returns null if not
        if (date.reachedLimit(r, b)) return null;

        // There is space. Return date
        else return date;

    } // End checkDate

    /**
     * Assigns a reservation for a specific date and updates the date object
     * @param d    Date object that reservation will be assigned to
     * @param r    Desired room type
     * @exception  none
     * @return     none
     */
    public void assignDate(Date d, String r) {
     
        // Increments the date object at the desired room type
        d.updateDate(r, 1);
    
    } // End assignDate(d, r)

    /**
     * Updates a list of date objects in a reservation to no longer be assigned to that reservation
     * @param r    The reservation that will be removed from the dates
     * @exception  Error in update to csv file
     * @return     none
     */
    public void removeFromDates(Reservation r) {

        // Local variables 
        String s; // Stores date string 
        Date d; // Stores date object

        // For loop removes reservation from each date in its schedule
        for (int i = 0; i <= r.getSchedule().size() - 1; i++) {

            s = r.getSchedule().get(i).dateString(); // Save date string
            d = dates.get(dateS.indexOf(s)); // Save date object
            d.updateDate(r.getRoomType(), 0); // Updte date to no longer count reservation

        } // End for

        updateDateFile(); // Update date file

    } // End removeFromDates(r)

} // End DateManager class
