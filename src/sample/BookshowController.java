package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import sample.model.BookShow;
import sample.model.Datasource;

public class BookshowController {
    @FXML
    private TableView<BookShow> bookshowsTableView;

    public void initialize(){
        bookshowsTableView.setItems(Datasource.getInstance().queryBookShowsForAdmin());
    }
}
