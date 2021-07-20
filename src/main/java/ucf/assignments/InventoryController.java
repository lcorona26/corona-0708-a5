package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;

import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    FileController action = new FileController();



    @FXML
    private TextField searchField, valueField, numField, nameField;

    @FXML
    private final FileController file = new FileController();




    public void initialize(URL url, ResourceBundle rb){
    }



    @FXML
    public void addClicked(ActionEvent actionEvent) {
        add();
    }
    @FXML
    public void removeClicked(ActionEvent actionEvent) {
        remove();
    }
    @FXML
    public void searchClicked(ActionEvent actionEvent) {
        search();
    }
    @FXML
    public void updateClicked(ActionEvent actionEvent) {
        update();
    }
    @FXML
    public void saveClicked(ActionEvent actionEvent) {
        action.save();
    }
    @FXML
    public void loadClicked(ActionEvent actionEvent) {
        action.load();
    }




    public void add(){

    }
    public void remove(){

    }
    public void update(){

    }
    public void search(){

    }

}
