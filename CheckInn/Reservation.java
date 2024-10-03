package checkinn;

public class Reservation {
    private String customerName;
    private String roomType;
    private int groupSize;
    private String checkInDate;
    private String checkOutDate;
    //private String confirmNumber;


    public Reservation (String customerName, String roomType, int groupSize, String checkInDate, String checkOutDate) {
        this.customerName = customerName;
        this.roomType = roomType;
        this.groupSize = groupSize;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;

    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

}
