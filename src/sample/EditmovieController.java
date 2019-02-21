package sample;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import sample.model.Datasource;
import sample.model.Movie;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;


public class EditmovieController {

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



    private DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    private String path;
    private String absPath;
    private int movieId;

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
                path = selectedImage.getPath();
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public void processResult(Movie movie){
        System.out.println(movie.getTitle());
        movieId = movie.getId();
        titleField.setText(movie.getTitle());
        descField.setText(movie.getDescription());
        LocalDate ld = LocalDate.parse(movie.getDate(), DATEFORMATTER);
        datePicker.setValue(ld);
        timeField.setText(movie.getTime());
        path = movie.getUrl();
        posterImage.setImage(new Image("file:" + path));
    }

    @FXML
    public boolean updateMovie(){
        String title = titleField.getText();
        String desc = descField.getText();
        String date = datePicker.getValue().toString();
        String time = timeField.getText();

        if(Datasource.getInstance().updateMovieById(movieId, title, desc, date, time, path)){
            copyFilmPoster();
            return true;
        }else{
            System.out.println("Something went wrong while clicking update!!!");
            return false;
        }
    }

    public void copyFilmPoster(){

        Path source = Paths.get(path);
        Path destination = Paths.get("//Users//trido//Desktop//JavaProject//Application_Theatre_Version2//src//sample//poster//" + source.getFileName());
        try {
            Files.copy(source, destination);
        }catch (IOException e){
            e.getStackTrace();
        }

    }

}
