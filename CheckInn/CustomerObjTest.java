package CheckInn;

import org.junit.*;

//testing Customer.java
public class CustomerObjTest {
    
    @Test
    public void cusTest() {
        //initialize class
        Customer c = new Customer(1234, "test@gmail.com", "last name", "first name");

        //test methods of class
        Assert.assertEquals("last name", c.getLastName());
        Assert.assertEquals("first name", c.getFirstName());
        Assert.assertEquals("test@gmail.com", c.getEmail());
        Assert.assertEquals(1234, c.getCustomerID());
    }
}
