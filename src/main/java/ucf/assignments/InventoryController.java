package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.net.URL;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.FloatStringConverter;

import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    FileController action = new FileController();



    @FXML
    private TextField searchField, valueField, serialField, nameField;
    @FXML
    private TableView<InventoryContents> table;
    @FXML
    private TableColumn<ColumnData,Float> value;
    @FXML
    private TableColumn<ColumnData,String> serial;
    @FXML
    private TableColumn<ColumnData,String> name;

    ObservableList<InventoryContents> list = FXCollections.observableArrayList();




    public void initialize(URL url, ResourceBundle rb){

        value.setCellValueFactory(new PropertyValueFactory<ColumnData, Float>("value"));
        serial.setCellValueFactory(new PropertyValueFactory<ColumnData, String>("serial"));
        name.setCellValueFactory(new PropertyValueFactory<ColumnData, String>("name"));

        value.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        serial.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setCellFactory(TextFieldTableCell.forTableColumn());

        table.setEditable(true);
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
    public void saveClicked(ActionEvent actionEvent) {
        action.save();
    }
    @FXML
    public void loadClicked(ActionEvent actionEvent) {
        action.load();
    }




    public void add(){

        //allows float value to be retrieved from text field
        float value = Float.parseFloat(valueField.getText());

        //get values from text fields
        list.add(new InventoryContents(value, serialField.getText(), nameField.getText()));

        //add values to table
        table.setItems(list);

    }
    public void remove(){

        //assigns an id to each row of the table
        int selectID = table.getSelectionModel().getSelectedIndex();

        //utilizes id and removes row of content from table
        table.getItems().remove(selectID);

    }

    public void search(){

    }

}
