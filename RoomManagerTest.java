import java.io.IOException;

public class RoomManagerTest {
    public static void main(String[] args) throws IOException{ // Declare that main can throw IOException
        RoomManager manager = new RoomManager();
        manager.roomFile = "C:/Users/Brian/Desktop/380L Project/rooms.txt"; // Set the correct file path

        // Test assignReservationNumber
        manager.assignReservationNumber("101", "R12345");

        // Test updateRoomAvailability
        manager.updateRoomAvailability("102", false);
        
        // Program will terminate if an exception is thrown
    }
}
