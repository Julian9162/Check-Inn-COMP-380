package CheckInn.Rooms;

public abstract class Room {
    protected int roomNumber;
    protected int pricePerNight;
    protected boolean Availiabity;

    public Room(int roomNumber, boolean Availiabity) {
        this.roomNumber = roomNumber;
        this.Availiabity = Availiabity;
    }
    
    public int getPricePerNight() {
        return pricePerNight;
    }

    public int getroomNumber() {
        return roomNumber;
    }

    public boolean getAvailiabity() {
        return Availiabity;
    } 
}