package CheckInn;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.ArrayList;
import java.time.LocalDate;

public class DateManager {

    private LinkedList<Date> dates = new LinkedList<Date>();
    private ArrayList<String> dateS = new ArrayList<String>();
    private File dateFile = new File("CheckInn\\dates.txt");
    
    public DateManager() {

        try {

            String line;
            BufferedReader reader = new BufferedReader(new FileReader(dateFile));
            
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");
                String[] dv = parts[0].split("-");

                LocalDate d = LocalDate.of(Integer.parseInt(dv[0]), Integer.parseInt(dv[1]), Integer.parseInt(dv[2]));
                if (LocalDate.now().isAfter(d)) {

                    removeFromDateFile(parts[0]);
                    continue;

                }

                dates.add(new Date(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), 
                Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), 
                Integer.parseInt(parts[6])));

                dateS.add(parts[0]);

            }

            reader.close();

        }

        catch(Exception e) {System.out.println("I/O Error DateManager Constr: " + e.getMessage());}

    }

    public void swapDates(int m, int n) {

        Date d = dates.get(m);
        String s = dateS.get(m);

        dates.set(m, dates.get(n));
        dates.set(n, d);

        dateS.set(m, dateS.get(n));
        dateS.set(n, s);

    }

    public void sortDates() {

        int count = 0;

        for (int i = 1; i <= dates.size(); i++) {

            for (int j = 0; j < dates.size() - 1; j++) {

                if (dates.get(j).getLocalDate().isAfter(dates.get(j + 1).getLocalDate())) 
                    swapDates(j, j + 1);

                count++;

            }

        }

        updateDateFile();
        System.out.println(count + " werwerwerwerwee");

    }

    public void addToDateFile(Date d) {

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(dateFile, true));
            writer.write(d.dateString() + "," + d.getOverlaps());
            writer.newLine();
            writer.close();

            sortDates();
        }

        catch(Exception e) {System.out.println("I/O Error DateManager add: " + e.getMessage());}

    }

    public void updateDateFile() {

        try{
        
            BufferedWriter writer = new BufferedWriter(new FileWriter(dateFile));

            for (int i = 0; i < dates.size(); i++) {

                Date d = dates.get(i);

                writer.write(d.dateString() + "," + d.getOverlaps());
                writer.newLine();

            }

            writer.close();

        } // End try

        catch(Exception e) {System.out.println("IO Error DateManager update: " + e.getMessage());}

    }

    public void removeFromDateFile(String s) {

        try{
        
            Date date = dates.get(dateS.indexOf(s));
            BufferedWriter writer = new BufferedWriter(new FileWriter(dateFile));

            for (int i = 0; i < dates.size(); i++) {

                Date d = dates.get(i);

                if (!d.getLocalDate().isEqual(date.getLocalDate())) {

                    writer.write(d.dateString() + "," + d.getOverlaps());
                    writer.newLine();

                }

            }

            writer.close();

        } // End try

        catch(Exception e) {System.out.println("I/O Error: remove" + e.getMessage());}

    }

    public Date getDate(String d) {

        if (!dateS.contains(d)) { 
            
            Date date = new Date(d);
            dates.add(date);
            dateS.add(d);
            addToDateFile(date);
            return date;

        }

        else return dates.get(dateS.indexOf(d));

    }

    public Date checkDate(String d, String r) {

        if (!dateS.contains(d)) return new Date(d);

        Date date = dates.get(dateS.indexOf(d));

        if (date.reachedLimit(r)) return null;

        else return date;

    }

    public void assignDate(Date d, String r) {
     
        d.updateDate(r, 1);
    
    }

    public void removeFromDates(Reservation r) {

        String s;

        for (int i = 0; i <= r.getSchedule().size() - 1; i++) {

            s = r.getSchedule().get(i).dateString();
            r.getSchedule().get(i).updateDate(s, 0);;

        }

        updateDateFile();

    }

}
