package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.File;
import java.net.URL;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;

import java.util.ResourceBundle;

public class InventoryController implements Initializable {


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
    @FXML
    private final FileChooser fileChooser = new FileChooser();

    ObservableList<InventoryContents> list = FXCollections.observableArrayList();

    FileController method = new FileController();



    public void initialize(URL url, ResourceBundle rb){

        //initializes table columns
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        serial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        //allows rows to be edited with a double click
        value.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        serial.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setCellFactory(TextFieldTableCell.forTableColumn());

        table.setEditable(true);

        //sets up extensions
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("HTML", "*.html"),
                new FileChooser.ExtensionFilter("JSON", "*.json"),
                new FileChooser.ExtensionFilter("TSV", "*.txt"));

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
    public void saveClicked(ActionEvent actionEvent) {
        save();
    }
    @FXML
    public void loadClicked(ActionEvent actionEvent) {
        load();
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
    public void save(){

        fileChooser.setTitle("Save as...");
        fileChooser.setInitialFileName("Inventory");

        try{
            File file = fileChooser.showSaveDialog(new Stage());
            fileChooser.setInitialDirectory(file.getParentFile());

            if(file.getName().endsWith(".txt"))
                method.tsv(file, list);
            if(file.getName().endsWith(".html"))
                method.html(file, list);
            if(file.getName().endsWith(".json"))
                method.json(file, list);

        }catch(Exception ignored){}

    }

    public void load(){
        fileChooser.setTitle("Load");

        try{
            File file = fileChooser.showOpenDialog(new Stage());
            fileChooser.setInitialDirectory(file.getParentFile());
        }catch(Exception ignored){}

    }
}
