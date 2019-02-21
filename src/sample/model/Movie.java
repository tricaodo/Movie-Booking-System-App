package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Movie {
    private SimpleIntegerProperty id;
    private SimpleStringProperty title;
    private SimpleStringProperty description;
    private SimpleStringProperty date;
    private SimpleStringProperty time;
    private SimpleStringProperty url;

    public Movie(String title, String description, String date, String time, String url) {
        this.id = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.url = new SimpleStringProperty(url);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
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

    public String getUrl() {
        return url.get();
    }

    public SimpleStringProperty urlProperty() {
        return url;
    }

    public void setUrl(String url) {
        this.url.set(url);
    }
}
