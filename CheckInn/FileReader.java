package checkinn;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    public static void main(String [] args) throws FileNotFoundException {
        File file;
        Scanner scan;

        try {
            
            file = new File("src/checkinn/test");
            scan = new Scanner(file);

            
            String customerName = scan.nextLine();
            String roomType = scan.nextLine();
            int groupSize = Integer.parseInt(scan.nextLine());
            String checkInDate = scan.nextLine();
            String checkOutDate = scan.nextLine();
            String email = scan.nextLine();

            HotelManager hotel = new HotelManager();

            hotel.createReserve(customerName, roomType, groupSize, checkInDate, checkOutDate, email);

            System.out.println("It worked!");

            scan.close();

            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error creating reservation. Could not be created.");

        }

        


    }
}