package sample.model;

import java.util.List;

public class Show {
    private List<Seat> seats;
    private int movieId;
    private String movieName;
    private String date;



    //    public Show(int movieId, int numOfRows, int numOfCols){
//        int lastRow = 'A' + numOfRows;
//        for(char letter = 'A'; letter < lastRow; letter++){
//            for(int i = 1; i <= numOfCols; i++){
//                String seatNumber = String.format("%s%02d", letter, i);
////                seats.add(new Seat(seatNumber));
////                Datasource.getInstance().addSeats(seatNumber);
//            }
//        }
//        this.movieId = movieId;
//    }
    public Show(int movieId, String movieName, String date){
        this.movieId = movieId;
        this.movieName = movieName;
        this.date = date;
        this.seats = Datasource.getInstance().getAllSeats();
    }

    public void bookSeat(String seatNumber){

        for(int i = 0; i < seats.size(); i++){
            Seat seat = seats.get(i);
            if(seat.getSeatNumber().equals(seatNumber)){
                if(!seat.reserve()){
                    System.out.println("Not available");
                }else{
                    System.out.println(seatNumber + " is booked");
                }
            }
        }
    }

    public int getMovieId() {
        return movieId;
    }

    public boolean isAvailable(String seatNumber){
        boolean available = false;
        for(int i = 0; i < seats.size(); i++){
            Seat seat = seats.get(i);
            if(seat.getSeatNumber().equals(seatNumber)){
                if(!seat.isBooked()){
                    available = true;
                }
            }
        }
        return available;
    }

    public List<Seat> getSeats(){
        return this.seats;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

}
