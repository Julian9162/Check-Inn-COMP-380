package CheckInn;

import java.time.LocalDate;

public class Date {

    private int overlapSi;
    private int overlapD;
    private int overlapT;
    private int overlapCR;
    private int overlapSu;
    private int overlapP;
    private LocalDate date;
    
    public Date(String d) {

        String[] parts = d.split("-");
        date = LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));

    }

    public Date(String d, int sin, int dou, int trip, int conR, int sui, int pent) {

        String[] parts = d.split("-");
        date = LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));

        overlapSi = sin;
        overlapD = dou;
        overlapT = trip;
        overlapCR = conR;
        overlapSu = sui;
        overlapP = pent;

    }

    public boolean reachedLimit(String r) {

        switch (r) {

            case "Single":
                if (overlapSi < 75) return false;
                break;

            case "Double":
                if (overlapD < 75) return false;
                break;

            case "Triple":
                if (overlapT < 50) return false;
                break;

            case "Connected":
                if (overlapCR < 60) return false;
                break;

            case "Suite":
                if (overlapSu < 15) return false;
                break;

            case "Penthouse":
                if (overlapP < 24) return false;
                break;

        }

        return true;

    }

    public void updateDate(String r, int val) {

        switch (r) {

            case "Single":
                if (val == 1) overlapSi++;
                else overlapSi--;
                break;

            case "Double":
                if (val == 1) overlapD++;
                else overlapD--;
                break;

            case "Triple":
                if (val == 1) overlapT++;
                else overlapT--;
                break;

            case "Connected":
                if (val == 1) overlapCR++;
                else overlapCR--;
                break;

            case "Suite":
                if (val == 1) overlapSu++;
                else overlapSu--;
                break;

            case "Penthouse":
                if (val == 1) overlapP++;
                else overlapP--;
                break;

        }

    }

    public LocalDate getLocalDate() {
        return date;
    }

    public String dateString() {
        return date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth();
    }

    public String getOverlaps() {
        return overlapSi + "," + overlapD + "," + overlapT + "," + overlapCR + "," + overlapSu + "," + overlapP;
    }

}


