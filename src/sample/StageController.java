package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import sample.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StageController {
    @FXML
    private GridPane gridPaneSeats;
    @FXML
    private ImageView imageView;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private Button bookshowBtn;
    @FXML
    private TextField movieField;
    @FXML
    private DatePicker datePicker;

    private Show currentShow = null;

    private User currentUser = null;

    private List<Seat> seats = null;

    private List<Booking> bookshows = null;

    private final int NUMBER_OF_SEATS = 40;

    private int currentUserId;
    private int currentMovieId;
    private String currentSeatNumber;

    private Image availableSeat = new Image("sample/images/available.png");
    private Image unavailableSeat = new Image("sample/images/unavailable.png");
    private Image bookedSeat = new Image("sample/images/selectedSeat.png");

    private List<String> reserveSeat = new ArrayList<>();
    private DateTimeFormatter DATETIMEFORMATER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void initialize(Show show, User user, String urlImage, String date) {

        currentShow = show;
        currentUser = user;
        bookshows = Datasource.getInstance().getBookShows();

        Image image = new Image("file:" + urlImage);
        imageView.setImage(image);
        imageView.setFitWidth(135d);
        imageView.setFitHeight(175d);

        movieField.setText(show.getMovieName());
        datePicker.setValue(LocalDate.parse(date, DATETIMEFORMATER));

        // find the in database and set isBooked true if the seat in on the bookshow table;
        seats = currentShow.getSeats();

        ToggleButton[] buttons = new ToggleButton[NUMBER_OF_SEATS];

        int row = 0;
        int col = 0;


        for (int i = 0; i < seats.size(); i++) {
            buttons[i] = new ToggleButton("");

            buttons[i].setPrefWidth(27d);
            buttons[i].setMaxWidth(27d);
            buttons[i].setMinWidth(27d);

            buttons[i].setPrefHeight(27d);
            buttons[i].setMaxHeight(27d);
            buttons[i].setMinHeight(27d);

            buttons[i].setGraphic(seatImage(availableSeat));
            buttons[i].setId(seats.get(i).getSeatNumber());

            if (col % 8 == 0) {
                row++;
                col = 0;
            }
            col++;

            gridPaneSeats.add(buttons[i], col, row);

            // loop for booking show
            for (int j = 0; j < bookshows.size(); j++) {

                Booking bookshow = bookshows.get(j);

                String seatFromBookShow = bookshow.getSeatNumber();
                String seatFromSeats = seats.get(i).getSeatNumber();

                // If the current user id equal to the user id in booking
                if (currentUser.getId() == bookshow.getUserId()) {

                    if (currentUserId == 0) {
                        currentUserId = currentUser.getId();
                    }
                    // if the current movie id equal to the movie id in booking
                    if (currentShow.getMovieId() == bookshow.getMovieId()) {

                        if (currentMovieId == 0) {
                            currentMovieId = currentShow.getMovieId();
                        }

                        //if the seats from the table equal to the seat in booking
                        if (seatFromSeats.equals(seatFromBookShow)) {
                            if (currentSeatNumber == null) {
                                currentSeatNumber = seatFromBookShow;
                            }

                            // => set buttons being selected
                            buttons[i].setSelected(true);
                            buttons[i].setGraphic(seatImage(bookedSeat));

                        }

                        // if the current movie id not equal to the movie id in booking
                    }

                    // If the current user id not equal to the user id in booking
                } else {

                    // if the current movie id equal to the movie id in booking
                    if (currentShow.getMovieId() == bookshow.getMovieId()) {

                        if (seatFromSeats.equals(seatFromBookShow)) {
                            // => set buttons being disable and set the seat image
                            buttons[i].setGraphic(seatImage(unavailableSeat));
                            buttons[i].setDisable(true);
                        }
                    }
                }
            }

            int finalBtn = i;
            buttons[finalBtn].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ToggleButton seatNumber = buttons[finalBtn];
                    if (seatNumber.isSelected()) {
                        reserveSeat.add(seatNumber.getId());
                        bookshowBtn.setDisable(false);
                        seatNumber.setGraphic(seatImage(bookedSeat));
                    } else {

                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.setTitle("Are you sure ?");
                        dialog.setContentText("Are you sure to cancel seat: " + seatNumber.getId());

                        dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
                        dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
                        Optional<ButtonType> results = dialog.showAndWait();

                        currentSeatNumber = seatNumber.getId();

                        if (results.isPresent() && results.get() == ButtonType.YES) {



                            // check if the user has just booked the seats or not
                            if (reserveSeat.size() > 0) {
                                reserveSeat.remove(currentSeatNumber);
                                seatNumber.setGraphic(seatImage(availableSeat));

                            // check if the user booked the seats and want to make any change
                            } else {
                                Datasource.getInstance().deleteBookShow(currentUserId, currentMovieId, currentSeatNumber);
                                seatNumber.setGraphic(seatImage(availableSeat));
                            }
                        }else {
                            seatNumber.setSelected(true);
                        }
                        if(reserveSeat.size() > 0){
                            bookshowBtn.setDisable(false);
                        }else{
                            bookshowBtn.setDisable(true);
                        }
                    }
                }
            });
        }

    }

    public void bookSeats() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Book Seat");
        alert.setContentText("Are you sure to book those seats?");
        if(reserveSeat.size() > 0) {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                    for (String seat : reserveSeat) {
                        Datasource.getInstance().addBookShows(currentUser.getId(), currentShow.getMovieId(), seat);
                        mainAnchorPane.getScene().getWindow().hide();
                    }
            }
        }
    }

    public void cancel(){
        mainAnchorPane.getScene().getWindow().hide();
    }

    public ImageView seatImage(Image image){
        ImageView imageViewForSeat = new ImageView(image);
        imageViewForSeat.setFitWidth(32d);
        imageViewForSeat.setFitHeight(32d);
        return imageViewForSeat;
    }
}
