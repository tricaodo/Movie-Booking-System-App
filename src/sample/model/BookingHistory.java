package sample.model;

import javafx.beans.property.SimpleStringProperty;

public class BookingHistory {
    //movies.title, seats.number, movies.date, movies.time
    private SimpleStringProperty movieTitle;
    private SimpleStringProperty seat;
    private SimpleStringProperty date;
    private SimpleStringProperty time;

    public BookingHistory(String movieTitle, String seatNumber, String date, String time) {
        this.movieTitle = new SimpleStringProperty(movieTitle);
        this.seat = new SimpleStringProperty(seatNumber);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
    }

    public String getMovieTitle() {
        return movieTitle.get();
    }

    public SimpleStringProperty movieTitleProperty() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle.set(movieTitle);
    }

    public String getSeat() {
        return seat.get();
    }

    public SimpleStringProperty seatProperty() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat.set(seat);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }
}
