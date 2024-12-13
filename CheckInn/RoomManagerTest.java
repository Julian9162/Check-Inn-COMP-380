package CheckInn;

import org.junit.*;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public class RoomManagerTest {
    
    @Test
    public void testGetAllRooms() {

        List<String[]> rooms;
        RoomManager RM = new RoomManager();

    private RoomManager roomManager;
    private String testRoomFile = "CheckInn\\test_rooms.txt";

    @BeforeEach
    void setUp() throws IOException {
        // Set up a test CSV file for testing
        roomManager = new RoomManager();
        roomManager.roomFile = testRoomFile;

        BufferedWriter writer = new BufferedWriter(new FileWriter(testRoomFile));
        writer.write("101,Single,true,0,true\n");
        writer.write("102,Double,true,0,true\n");
        writer.write("103,Suite,false,12345,false\n");
        writer.close();
    }

    @AfterEach
    void tearDown() {
        // Clean up the test file after each test
        new File(testRoomFile).delete();
    }

    @Test
    void testGetAllRooms() throws FileNotFoundException {
        List<String[]> rooms = roomManager.getAllRooms();
        assertEquals(3, rooms.size(), "Should retrieve 3 rooms from the file");
        assertEquals("101", rooms.get(0)[0], "First room number should be 101");
        assertEquals("true", rooms.get(0)[2], "First room should be available");
    }

    @Test
    void testAssignReservationNumber() throws IOException {
        roomManager.assignReservationNumber("101", 56789);
        List<String[]> rooms = roomManager.getAllRooms();
        assertEquals("56789", rooms.get(0)[3], "Reservation number for room 101 should be updated");
    }

    @Test
    void testUpdateRoomAvailability() throws IOException {
        roomManager.updateRoomAvailability("101", false);
        List<String[]> rooms = roomManager.getAllRooms();
        assertEquals("false", rooms.get(0)[2], "Room 101 should be marked unavailable");
    }

    @Test
    void testFindRoomNumber() throws FileNotFoundException {
        String roomNumber = roomManager.findRoomNumber("Double");
        assertEquals("102", roomNumber, "Should find the first available Double room");
    }

    @Test
    void testIsRoomClean() throws FileNotFoundException {
        assertTrue(roomManager.isRoomClean("101"), "Room 101 should be clean");
        assertFalse(roomManager.isRoomClean("103"), "Room 103 should not be clean");
    }

    @Test
    void testMarkRoomCleanStatus() throws IOException {
        roomManager.markRoomCleanStatus("101", false);
        List<String[]> rooms = roomManager.getAllRooms();
        assertEquals("false", rooms.get(0)[4], "Room 101 should be marked dirty");
    }

    @Test
    void testUpdateRoom() throws IOException {
        Reservation mockReservation = new Reservation(56789, "Single");
        CheckInnInterface.resManager = new MockReservationManager(mockReservation); // Mock dependency
        assertTrue(roomManager.updateRoom(mockReservation), "Room should be successfully updated");
    }

    }
    
}
