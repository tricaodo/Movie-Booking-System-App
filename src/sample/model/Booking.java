package sample.model;

public class Booking {

    private int bookshowId;
    private int userId;
    private int movieId;
    private String seatNumber;

    public Booking(int userId, int movieId, String seatNumber) {
        this.userId = userId;
        this.movieId = movieId;
        this.seatNumber = seatNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookshowId() {
        return bookshowId;
    }

    public void setBookshowId(int bookshowId) {
        this.bookshowId = bookshowId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
