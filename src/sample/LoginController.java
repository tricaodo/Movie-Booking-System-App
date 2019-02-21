package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import sample.model.Datasource;
import sample.model.Seat;
import sample.model.Show;
import sample.model.User;

import java.io.IOException;

public class LoginController {
    @FXML
    private GridPane loginGridPane;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginBtn;
    @FXML

    public void initialize(){
        loginGridPane.getStylesheets().add(getClass().getResource("stylesheet/style.css").toExternalForm());
    }
    @FXML
    public void signup(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("view/signup.fxml"));

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Sign Up");
        dialog.setHeaderText("Being Our Member To Get More Deals");

        dialog.initOwner(loginGridPane.getScene().getWindow());

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            e.printStackTrace();
        }
        dialog.show();
    }

    @FXML
    public void login(){
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = Datasource.getInstance().queryUserByUserNameAndPass(username, password);

        if(user != null){

            if(user.getAuthority() == 1){
                try{

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    Parent root = fxmlLoader.load(getClass().getResource("view/dashboard.fxml").openStream());
                    DashboardController dashboardController = fxmlLoader.getController();
                    dashboardController.initialize(user);
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(this.getClass().getResource("stylesheet/style.css").toExternalForm());
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();

                }catch (IOException e){
                    e.getStackTrace();
                }
            }else{
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("view/movielists.fxml"));
                try{

//                    Parent root = fxmlLoader.load(getClass().getResource("view/movielists.fxml").openStream());
                    Parent root = fxmlLoader.load();
                    MovielistsController movielistsController = fxmlLoader.getController();
                    movielistsController.initialize(user);
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("stylesheet/style.css").toExternalForm());
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                }catch (IOException e){
                    e.getStackTrace();
                }

            }
            loginBtn.getScene().getWindow().hide();
            System.out.println("Login successfully");
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong information");
            alert.setContentText("Username or Password is wrong");
            alert.showAndWait();
        }
    }

    @FXML
    public void cancel(){
        Platform.exit();
    }
}
