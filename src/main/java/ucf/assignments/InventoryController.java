package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

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
    ErrorController error = new ErrorController();


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

        searchFilter();
    }

    public void searchFilter(){

        //allows for filtering of table
        FilteredList<InventoryContents> filteredList = new FilteredList<>(list);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(input ->{

                if(newValue == null || newValue.isEmpty())
                    return true;

                String lowerCaseFilter = newValue.toLowerCase();

                if(input.getName().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if(input.getSerial().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else return input.getValue().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<InventoryContents> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);

    }


    @FXML
    public void addClicked(ActionEvent actionEvent) { add(); }
    @FXML
    public void removeClicked(ActionEvent actionEvent) { remove(); }
    @FXML
    public void saveClicked(ActionEvent actionEvent) { save(); }
    @FXML
    public void loadClicked(ActionEvent actionEvent) { load(); }



    public void add() {

        //check values from text fields are of correct format
        boolean correct = true;

        try{
            float format = Float.parseFloat(valueField.getText());
        }catch(NumberFormatException e){

            //opens a window displaying value error message
            error.value();
        }
        if(nameField.getText().length() <= 2 || nameField.getText().length() > 252){
            correct = false;

            //opens a window displaying name error message
            error.name();
        }
        if(serialField.getText().length() != 10){
            correct = false;

            //opens a window displaying serial error message
            error.serial();
        }
        if(correct) {
            float format = Float.parseFloat(valueField.getText());
            String value = String.format("$%.2f", format);
            list.add(new InventoryContents(value, serialField.getText(), nameField.getText()));
        }


        //add values to table
        table.setItems(list);
        searchFilter();

    }
    public void remove(){

        //assigns an id to each row of the table
        int selectID = table.getSelectionModel().getSelectedIndex();

        //utilizes id and removes row of content from table
        list.remove(selectID);
        table.setItems(list);
        searchFilter();

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

    public void valueEdited(TableColumn.CellEditEvent<InventoryContents, String> inventoryContentsStringCellEditEvent) {
        
        InventoryContents contents = table.getSelectionModel().getSelectedItem();
        contents.setValue(inventoryContentsStringCellEditEvent.getNewValue());

        try{
            float num = Float.parseFloat(String.valueOf(contents.getValue()));
        }catch(NumberFormatException e){

            //opens a window displaying value error message
            error.value();
        }

    }

    public void serialEdited(TableColumn.CellEditEvent<InventoryContents, String> inventoryContentsStringCellEditEvent) {

        InventoryContents contents = table.getSelectionModel().getSelectedItem();
        contents.setSerial(inventoryContentsStringCellEditEvent.getNewValue());

        if(contents.getSerial().length() != 10){

            //opens a window displaying serial error message
            error.serial();
        }
    }

    public void nameEdited(TableColumn.CellEditEvent<InventoryContents, String> inventoryContentsStringCellEditEvent) {

        InventoryContents contents = table.getSelectionModel().getSelectedItem();
        contents.setName(inventoryContentsStringCellEditEvent.getNewValue());

        if(contents.getName().length() <= 2 || contents.getName().length() > 252){

            //opens a window displaying name error message
            error.name();
        }
    }
}
