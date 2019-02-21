package sample.model;


public class Seat {

    private String seatNumber;
    private boolean isBooked = false;

    public Seat(String seatNumber){
        this.seatNumber = seatNumber;
    }

    public boolean reserve(){
        if(!this.isBooked){
            this.isBooked = true;
            return true;
        }
        return false;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }
}
