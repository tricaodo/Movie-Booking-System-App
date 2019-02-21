package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.model.*;

import java.io.IOException;
import java.util.Optional;

public class MovielistsController {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane movieListPane;
    private Button profileBtn = new Button();
    private Button backBtn = new Button();
    @FXML
    private FlowPane menuFlowPane;

    private User currentUser = null;

    private ObservableList<Movie> movies;

    public void initialize(User user){

        currentUser = user;

        if(user.getAuthority() != 1){
            createBtnForMenu("Back", 60, 475);
            backBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    back();
                }
            });

        }else{
            createBtnForMenu("Management", 100, 430);
            backBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FXMLLoader fxmlLoader = loadFXML("view/dashboard.fxml");
                    try{
                        Parent root = fxmlLoader.load();
                        DashboardController dashboardController = fxmlLoader.getController();
                        dashboardController.initialize(currentUser);
                        Stage stage = loadStage(root);
                        movieListPane.getScene().getWindow().hide();
                        stage.show();

                    }catch (IOException e){
                        e.getStackTrace();
                    }
                }
            });
        }

        profileBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openProfile();
            }
        });

        currentUser = user;
        movies = Datasource.getInstance().listMovies();

        gridPane.setHgap(15);
        gridPane.setVgap(15);

        int col = 0;
        int row = 0;

        Button[] movieBtn = new Button[movies.size()];

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        for(int i = 0; i < movies.size(); i++){

            String url = movies.get(i).getUrl();

            Image image = new Image("file:"+url);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(125d);
            imageView.setFitHeight(160d);

            movieBtn[i] = new Button("",imageView);
            movieBtn[i].setMinWidth(125d);
            movieBtn[i].setMinHeight(160d);
            gridPane.add(movieBtn[i], col, row);
            col ++;

            if(col == 4){
                row ++;
                col = 0;
            }

            int index = i;

            movieBtn[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int movieId = movies.get(index).getId();
                    String movieName = movies.get(index).getTitle();
                    String date = movies.get(index).getDate();
                    Show show = new Show(movieId, movieName, date);

                    FXMLLoader fxmlLoader = loadFXML("view/stage.fxml");
                    try{
                        Parent root = fxmlLoader.load();
                        Stage stage = loadStage(root);
                        StageController stageController = fxmlLoader.getController();
                        stageController.initialize(show, currentUser, url, date);
                        stage.initOwner(movieListPane.getScene().getWindow());
                        stage.initModality(Modality.WINDOW_MODAL);
                        stage.show();
                    }catch (IOException e){
                        e.getStackTrace();
                    }
                }
            });

        }


    }

    @FXML
    public void back(){
        try{
            Parent root = loadFXML("view/login.fxml").load();
            Stage stage = loadStage(root);
            gridPane.getScene().getWindow().hide();
            stage.show();
        }catch (IOException e){
            e.getStackTrace();
        }
    }

    public void openProfile(){
        try{
            FXMLLoader fxmlLoader = loadFXML("view/profile.fxml");
            Parent root = fxmlLoader.load();
            Stage stage = loadStage(root);
            ProfileController profileController = fxmlLoader.getController();
            profileController.initialize(currentUser);
            stage.initOwner(movieListPane.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        }catch (IOException e){
            e.getStackTrace();
        }
    }

    public Stage loadStage(Parent root){
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        scene.getStylesheets().add(this.getClass().getResource("stylesheet/style.css").toExternalForm());
        return stage;
    }

    public FXMLLoader loadFXML(String url){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(url));
        return fxmlLoader;
    }

    public void createBtnForMenu(String text, double width, int hgap){
        backBtn.setText(text);
        backBtn.getStyleClass().add("headerBtn");
        backBtn.prefWidth(width);
        menuFlowPane.getChildren().add(backBtn);

        profileBtn.setText("Profile");
        profileBtn.setPrefWidth(60.0);
        profileBtn.setPrefHeight(40.0);
        profileBtn.getStyleClass().add("headerBtn");
        menuFlowPane.getChildren().add(profileBtn);
        menuFlowPane.setHgap(hgap);
    }
}
