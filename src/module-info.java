module Application.Theatre.Version2 {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;
    opens sample;
    opens sample.images;
    opens sample.view;
    opens sample.model;
    opens sample.poster;
    opens sample.picture;

}