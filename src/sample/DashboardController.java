package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.model.User;

import java.io.IOException;

public class DashboardController {
    private User currentUser = null;
    @FXML
    private AnchorPane dashboardPane;

    public void initialize(User user){
        currentUser = user;
    }

    @FXML
    public void openAddMovie(){
        Stage stage = changeScene("view/addmovie.fxml", "stylesheet/style.css");
        stage.initOwner(dashboardPane.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    @FXML
    public void openUserManagement(){
        Stage stage = changeScene("view/usermanagement.fxml", "stylesheet/style.css");
        stage.initOwner(dashboardPane.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    @FXML
    public void backToLogin(){
        Stage stage = changeScene("view/login.fxml", "stylesheet/style.css");
        dashboardPane.getScene().getWindow().hide();
        stage.show();
    }
    @FXML
    public void openReservedHistory(){
        Stage stage = changeScene("view/bookshowmanagement.fxml", "stylesheet/style.css");
        stage.initOwner(dashboardPane.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    @FXML
    public void openBookShows(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("view/movielists.fxml"));
        try{
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(this.getClass().getResource("stylesheet/style.css").toExternalForm());
            stage.setScene(scene);

            MovielistsController movielistsController = fxmlLoader.getController();
            movielistsController.initialize(currentUser);

            dashboardPane.getScene().getWindow().hide();

            stage.setResizable(false);
            stage.show();

        }catch (IOException e){
            e.getStackTrace();
        }
    }

    public Stage changeScene(String sceneUrl, String styleUrl){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource(sceneUrl).openStream());
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(this.getClass().getResource(styleUrl).toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            return stage;

        }catch (IOException e){
            e.getStackTrace();
            return null;
        }
    }
}
