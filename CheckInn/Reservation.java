package checkinn;

public class Reservation {
    
    private String customerName;
    private String roomType;
    private int groupSize;
    private String checkInDate;
    private String checkOutDate;
    private int reserveNum;
    private String email;
    private boolean active = false;

    public Reservation (String customerName, String roomType, int groupSize, String checkInDate, 
                        String checkOutDate, String email) {

        this.customerName = customerName;
        this.roomType = roomType;
        this.groupSize = groupSize;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.email = email;

    }

    public void setReserveNum(int reserveNum) {
        this.reserveNum = reserveNum;
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

    public int getReserveNum() {
        return reserveNum;
    }

    public String getEmail() {
        return email;
    }

    public boolean getActiveStatus() {
        return active;
    }

}
