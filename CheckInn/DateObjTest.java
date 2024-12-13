package CheckInn;

import org.junit.Test;
import org.junit.*;

public class DateObjTest {
    
    @Test
    public void testDate() {

        Date d = new Date("2024-12-11", 1, 10, 30, 4, 8, 24);
        Assert.assertEquals(true, d.reachedLimit("Penthouse", false));
        Assert.assertEquals(false, d.reachedLimit("Triple", false));
        Assert.assertEquals("2024-12-11", d.dateString());

    }

}
