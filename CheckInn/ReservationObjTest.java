package CheckInn;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import org.junit.*;

public class ReservationObjTest {
    
    @Test
    public void resTest() {

        LocalDate LD = LocalDate.of(2024, 12, 11);
        Customer c = new Customer(121212, "julian@gmail.com", "Aguiar", "Julian");
        Reservation r = new Reservation(12345678912L, c, "Single", 0, "2024-12-11", "2024-12-12", false, "0");


        Assert.assertEquals("2024-12-11", r.getCheckInDateStr());
        Assert.assertEquals(LD, r.getCheckInDate());
        Assert.assertEquals(true, r.scheduleContains("2024-12-12"));

    }

}
