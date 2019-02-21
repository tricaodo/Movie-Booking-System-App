package sample;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;

import javafx.stage.Stage;
import sample.model.Datasource;
import sample.model.Movie;


public class AddmovieController {

    private File selectedImage;
    @FXML
    private ImageView posterImage;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea descField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField timeField;
    @FXML
    private TableView<Movie> tableView;
    @FXML
    private BorderPane mainBorderPane;

    private String absPath = "";
    private String pathInDB = "";

    private ObservableList<Movie> movies;

    private ContextMenu contextMenu;

    public void initialize(){
        movies = Datasource.getInstance().listMovies();
        tableView.setItems(movies);
        contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete Movie");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Movie movie = tableView.getSelectionModel().getSelectedItem();
                deleteMovie(movie);
            }
        });

        contextMenu.getItems().add(deleteMenuItem);

        tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2){
                    Movie selectedMovie = tableView.getSelectionModel().getSelectedItem();

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    Dialog<ButtonType> dialog = new Dialog<>();
                    fxmlLoader.setLocation(getClass().getResource("view/editmovie.fxml"));

                    try{
                        dialog.getDialogPane().setContent(fxmlLoader.load());
                    }catch (IOException e){
                        e.getStackTrace();
                    }
                    EditmovieController editmovieController = fxmlLoader.getController();
                    editmovieController.processResult(selectedMovie);

                    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

                    Optional<ButtonType> results = dialog.showAndWait();
                    if(results.isPresent() && results.get() == ButtonType.OK){
                        editmovieController.updateMovie();
                        refreshTable();
                    }


                }
            }
        });
        tableView.setContextMenu(contextMenu);
    }

    @FXML
    public void addMovie(){

        String title = titleField.getText();
        System.out.println("Title: " + title);
        String desc = descField.getText();
        String date = null;
        if(datePicker.getValue() != null){
            date = datePicker.getValue().toString();
        }
        String time = timeField.getText();

        if(title.isEmpty() || desc.isEmpty() || date == null || time.isEmpty() || absPath.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Warning");
            alert.setContentText("Please fill out all the fields");
            alert.showAndWait();
        }else{
            // copy poster and evaluate the path for database
            copyFilmPoster();
            if(Datasource.getInstance().addMovie(title,desc, date, time, pathInDB)){
                resetFields();
                System.out.println("Insert movie successfully");
            }

        }
    }

    @FXML
    public void uploadImageClick(ActionEvent event) throws IOException {

        try {
            FileChooser fc = new FileChooser();
            selectedImage = fc.showOpenDialog(null);
            // checking that input file is not null and handling the exception
            if (selectedImage == null)
                return;
            else if (ImageIO.read(selectedImage) == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please upload an image in JPG or PNG format!",
                        ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    return;
                }
            } else {
                Image img = SwingFXUtils.toFXImage(ImageIO.read(selectedImage), null);
                posterImage.setImage(img);
                absPath = selectedImage.getPath();
                System.out.println(absPath);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }

    }

    public void copyFilmPoster(){
        absPath = selectedImage.getPath();

        Path source = Paths.get(absPath);
        Path destination = Paths.get("//Users//trido//Desktop//JavaProject//Application_Theatre_Version2//src//sample//poster//" + source.getFileName());
        pathInDB = destination.toString();

        try {
            Files.copy(source, destination);
        }catch (IOException e){
            e.getStackTrace();
        }

    }

    public void deleteFilmPoster(String deletePath){
        Path deleteImage = Paths.get(deletePath);
        try{
            Files.delete(deleteImage);
            System.out.println("Delete image: " + deletePath);
        }catch (IOException e){
            e.getStackTrace();
        }
    }

    @FXML
    public void cancel(){
        Platform.exit();
    }

    public void refreshTable(){
        movies = Datasource.getInstance().listMovies();
        tableView.setItems(movies);
    }
    @FXML
    public void back(){
        mainBorderPane.getScene().getWindow().hide();

    }

    public void resetFields(){
        posterImage.setImage(new Image("sample/images/defaultPoster.png"));
        absPath = "";
        titleField.setText("");
        descField.setText("");
        datePicker.setValue(null);
        timeField.setText("");
    }

    public void deleteMovie(Movie movie){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Movie");
        alert.setContentText("Do you want to delete movie: " + movie.getTitle() );
        Optional<ButtonType> results = alert.showAndWait();
        if(results.isPresent() && results.get() == ButtonType.OK){
            if(Datasource.getInstance().deleteMovieById(movie.getId())){
                deleteFilmPoster(movie.getUrl());
                refreshTable();
            }
        }

        

    }
}
