package sample.model;

import javafx.beans.property.SimpleStringProperty;

public class BookShow {
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty movie;
    private SimpleStringProperty date;
    private SimpleStringProperty time;
    private SimpleStringProperty seat;

    public BookShow(String firstName, String lastName, String movie, String date, String time, String seat){
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.movie = new SimpleStringProperty(movie);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.seat = new SimpleStringProperty(seat);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getMovie() {
        return movie.get();
    }

    public SimpleStringProperty movieProperty() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie.set(movie);
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

    public String getSeat() {
        return seat.get();
    }

    public SimpleStringProperty seatProperty() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat.set(seat);
    }
}
