package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import sample.model.Datasource;
import sample.model.User;

public class UserController {
    @FXML
    private TableView<User> userManagementTableView;

    public void initialize(){
        userManagementTableView.setItems(Datasource.getInstance().queryUsers());

    }
}
