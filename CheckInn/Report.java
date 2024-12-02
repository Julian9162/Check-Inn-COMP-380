package CheckInn;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.time.LocalDate;

/**
 * Report --- A report entity that saves a report for a specific date and all records associated with report.
 * @author    Julian Aguiar
 */
public class Report {

    // Global variables
    File reportFile; // Stores report file
    String fileName, title; // Stores name of report and title of report
    ArrayList<String> record = new ArrayList<String>(); // Array of all events in report

    /**
     * Report constructor creates a report object. Saves a specific report file.
     * @param fileName  The name of the report file
     * @exception       There is an issue reading from the file.
     * @return          none
     */
    public Report(String fileName) {

        // Local variables
        String line; // Stores a line from the report
        this.fileName = fileName; // Stores the name of the file

        try {

            // Save the report file
            reportFile = new File(fileName);
            // Reads from report file
            BufferedReader reader = new BufferedReader(new FileReader(reportFile));

            // The report is not empty. Saves the first line as the title
            if ((line = reader.readLine()) != null) title = line;

            // The report is empty.
            else {

                BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile)); // Writes to new report
                // Writes title of new report into first line
                writer.write("CheckInn Report: " + LocalDate.now().getMonthValue() + "/" + LocalDate.now().getDayOfMonth()
                                + "/" + LocalDate.now().getYear());
                writer.newLine(); // Adds new line
                reader.close(); // Close file reader
                writer.close(); // Close file writer
                return; // End method
 
            } // End else

            // While loop adds all records to array list
            while ((line = reader.readLine()) != null) record.add(line);

            reader.close(); // Close reader

        } // End try

        // Catch exception
        catch(Exception e) {System.out.println("I/O Error Report Entity: " + e.getMessage());}

    } // End Report constructor

    /**
     * Adds a new event to this report. 
     * @param line  The line to be added to this report
     * @exception   There is an issue writing to the file.
     * @return      none
     */
    public void addToReport(String line) {

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile, true)); // Writes to this report
            writer.write(line); // Writes new event line to the report
            writer.newLine(); // Writes a new line
            writer.close(); // Close writer
            record.add(line); // Add record to record array

        } // End try

        catch(Exception e) {System.out.println("Error in Report object: " + e.getMessage());}

    } // End addToReport(line)

    /**
     * Retrieves the file of the report
     * @return  The file of the report
     */
    public File getReportFile() {
        return reportFile;
    } // End getReportFile()

    /**
     * Retrieves the file name of the report
     * @return  The file nameof the report
     */
    public String getFileName() {
        return fileName;
    } // End getFileName()

    /**
     * Retrieves the title of the report
     * @return  The title of the report
     */
    public String getTitle() {
        return title;
    } // End getTitle()
    
} // End Report class
