package CheckInn;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.time.LocalDate;

// Report class saves information about a specific report
public class Report {

    // Global variables
    File reportFile; // Stores report file
    String fileName, title; // Stores name of report and title of report
    ArrayList<String> record = new ArrayList<String>(); // Array of all events in report

    // Report constructor creates report object. Saves specific report file.
    public Report(String fileName) {

        // Local variables
        String line; // Stores line being read
        this.fileName = fileName;

        try {

            reportFile = new File(fileName);
            // Reads from report file
            BufferedReader reader = new BufferedReader(new FileReader(reportFile));

            if ((line = reader.readLine()) != null) title = line;
            
            else {

                BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile));
                writer.write("CheckInn Report: " + LocalDate.now().getMonthValue() + "/" + LocalDate.now().getDayOfMonth()
                            + "/" + LocalDate.now().getYear());
                writer.newLine();
                reader.close();
                writer.close();
                return;

            }

            // While loop adds all records to array list
            while ((line = reader.readLine()) != null) record.add(line);

            reader.close(); // Close reader

        } // End try

        catch(Exception e) {}

    } // End Report constructor

    // addToReport method adds new event to report file and array list
    public void addToReport(String line) {

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile, true));
            writer.write(line);
            writer.newLine();
            writer.close();

            record.add(line);

        }

        catch(Exception e) {System.out.println("Error in Report object: " + e.getMessage());}

    }

    // ATTRIBUTE GETTERS

    public File getReportFile() {

        return reportFile;

    }

    public String getFileName() {

        return fileName;

    }

    public String getTitle() {

        return title;

    }
    
} // End Report class
