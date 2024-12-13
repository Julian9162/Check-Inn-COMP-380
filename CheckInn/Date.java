package CheckInn;

import java.time.LocalDate;

/**
 * Date --- Entity class for date objects.
 * @author  Julian Aguiar
 */
public class Date {

    // Global Variables
    // overlap<room> Store amount of reservations booking a room of corresponding type
    private int overlapSi; // Single
    private int overlapD; // Double
    private int overlapT; // Triple
    private int overlapCR; // Connected Rooms
    private int overlapSu; // Suite
    private int overlapP; // Penthouse
    private LocalDate date; // Stores the specific date
    
    /**
     * Class constructor creates a new date object/record with default attributes
     * @param d    String representation of the date to be saved
     * @exception  none
     * @return     No return value
     */
    public Date(String d) {

        // Save year, month, and day of date in array
        String[] parts = d.split("-");
        // Save the date
        date = LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));

    } // End Date(d) constructor

    /**
     * Class constructor creates a date object of an existing date in date csv file
     * @param d     String representation of the date to be saved
     * @param sin   Amount of reservations booking a single
     * @param dou   Amount of reservations booking a double
     * @param trip  Amount of reservations booking a triple
     * @param conR  Amount of reservations booking connected rooms
     * @param sui   Amount of reservations booking a suite
     * @param pent  Amount of reservations booking a penthouse
     * @exception   Error reading from date csv file
     * @return      No return value
     */
    public Date(String d, int sin, int dou, int trip, int conR, int sui, int pent) {

        // Save year, month, and day of date in array
        String[] parts = d.split("-");
        // Save the date
        date = LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));

        // Save amount of reservations booked for each room type
        overlapSi = sin;
        overlapD = dou;
        overlapT = trip;
        overlapCR = conR;
        overlapSu = sui;
        overlapP = pent;

    } // End Date(d, sin, dou, trip, conR, sui, pent) constructor

    /**
     * Method checks if there is a room of a specific type available for this specific date
     * @param r     Desired room type
     * @param same  Used in case reservation is being edited and is changing room type
     * @exception   none
     * @return      True if limit for room has been reached. False if not
     */
    public boolean reachedLimit(String r, boolean same) {

        // If same is true, room type is not being changed. Thus, no extra room will be occupied,
        // and limit will not be reached
        if (same) return false; 

        // New reservation is being created OR room is changing room type
        // Switch statement checks if room has been reached for a specific room type
        // Returns false is there is no available room for desired room type
        switch (r) {

            // Checks if there is available single for this date
            case "Single":
                if (overlapSi < 75) return false;
                break;

            // Checks if there is available double for this date
            case "Double":
                if (overlapD < 75) return false;
                break;

            // Checks if there is available triple for this date
            case "Triple":
                if (overlapT < 50) return false;
                break;

            // Checks if there is available connected rooms for this date
            case "Connected":
                if (overlapCR < 60) return false;
                break;

            // Checks if there is available suite for this date
            case "Suite":
                if (overlapSu < 15) return false;
                break;

            // Checks if there is available penthouse for this date
            case "Penthouse":
                if (overlapP < 24) return false;
                break;

        } // End switch

        return true; // There is available room for desired room type

    } // End reachedLimit(d, same)

    /**
     * Increments or decrements amount of rooms being occupied for specific room type for this date
     * @param r    Desired room type
     * @param val  Value indicating if incrementing or decrementing
     * @exception  none
     * @return     none
     */
    public void updateDate(String r, int val) {

        // Switch statement used for updating only the desired room type
        // If val = 1, increment
        // If val = 0, decrement
        switch (r) {

            // Used for updating room type Single
            case "Single":
                if (val == 1) overlapSi++;
                else overlapSi--;
                break;

            // Used for updating room type Double
            case "Double":
                if (val == 1) overlapD++;
                else overlapD--;
                break;

            // Used for updating room type Triple
            case "Triple":
                if (val == 1) overlapT++;
                else overlapT--;
                break;

            // Used for updating room type Connected Rooms
            case "Connected":
                if (val == 1) overlapCR++;
                else overlapCR--;
                break;

            // Used for updating room type Suite
            case "Suite":
                if (val == 1) overlapSu++;
                else overlapSu--;
                break;

            // Used for updating room type Penthouse
            case "Penthouse":
                if (val == 1) overlapP++;
                else overlapP--;
                break;

        } // End switch

    } // End updateDate(r, val)

    /**
     * Retreives local date object of this date
     * @exception  none
     * @return     Local date object representation of date
     */ 
    public LocalDate getLocalDate() {
        return date;
    } // End getLocalDate()

    /**
     * Retreives date in string form
     * @exception  none
     * @return     String representation of date
     */ 
    public String dateString() {
        return date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth();
    } // End dateString()

    /**
     * Retreives string of comma separated values of all number of overlaps for each room type
     * @exception  none
     * @return     Comma separated values of overlaps in string form
     */ 
    public String getOverlaps() {
        return overlapSi + "," + overlapD + "," + overlapT + "," + overlapCR + "," + overlapSu + 
               "," + overlapP;
    } // End getOverlaps()

} // End Date class


