package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import static java.lang.Math.round;

public class InventoryController implements Initializable {


    @FXML
    private TextField searchField, valueField, serialField, nameField;
    @FXML
    private TableView<InventoryContents> table;
    @FXML
    private TableColumn<InventoryContents,String> value;
    @FXML
    private TableColumn<InventoryContents,String> serial;
    @FXML
    private TableColumn<InventoryContents,String> name;
    @FXML
    private final FileChooser fileChooser = new FileChooser();

    ObservableList<InventoryContents> list = FXCollections.observableArrayList();

    SaveController saving = new SaveController();
    LoadController loading = new LoadController();



    public void initialize(URL url, ResourceBundle rb){

        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        serial.setCellValueFactory(new PropertyValueFactory<>("serial"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        //allows rows to be edited with a double click
        value.setCellFactory(TextFieldTableCell.forTableColumn());
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


    public void add() {


        //check values from text fields are of correct format
        boolean correct = true;

        try{
            float format = Float.parseFloat(valueField.getText());
        }catch(NumberFormatException e){

            //opens a window displaying error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Incorrect Value Format!");
            alert.setContentText("Value must be a number");
            alert.showAndWait();

            if(nameField.getText().length() <= 2 || nameField.getText().length() > 252){
                correct = false;
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Incorrect Name Format!");
                alert.setContentText("Name must be between 2 and 252 characters");
                alert.showAndWait();
            }
            if(serialField.getText().length() < 10 || serialField.getText().length() >10){
                correct = false;
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Incorrect Serial Number Format!");
                alert.setContentText("Serial Number must be at least and no more 10 characters");
                alert.showAndWait();
            }
        }
        if(correct){
            float format = Float.parseFloat(valueField.getText());
            String value = String.format("$%.2f", format);
            list.add(new InventoryContents(value, serialField.getText(), nameField.getText()));
        }

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

        //sets up save dialog window title and filename
        fileChooser.setTitle("Save as...");
        fileChooser.setInitialFileName("Inventory");

        //opens save dialog window and allows user to save with their name/directory of choice
        try{
            File file = fileChooser.showSaveDialog(new Stage());
            fileChooser.setInitialDirectory(file.getParentFile());

            if(file.getName().endsWith(".txt"))
                saving.tsv(file, list);
            if(file.getName().endsWith(".html"))
                saving.html(file, list);
            if(file.getName().endsWith(".json"))
                saving.json(file, list);

        }catch(Exception ignored){}
    }

    public void load(){

        //sets up title for load dialog window
        fileChooser.setTitle("Load");

        //opens load dialog window and allows user to load in document of choice into table
        try{
            File file = fileChooser.showOpenDialog(new Stage());
            fileChooser.setInitialDirectory(file.getParentFile());

            if(file.getName().endsWith(".txt"))
                loading.tsv(file, list, table);
            if(file.getName().endsWith(".html"))
                loading.html(file, list, table);
            if(file.getName().endsWith(".json"))
                loading.json(file, list, table);

        }catch(Exception ignored){}
    }
}
