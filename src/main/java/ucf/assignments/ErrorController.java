/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Luis Corona
 */
package ucf.assignments;

import javafx.scene.control.Alert;

public class ErrorController {

    public void name(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Incorrect Name Format!");
        alert.setContentText("Name must be between 2 and 252 characters");
        alert.showAndWait();

    }
    public void serial(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Incorrect Serial Number Format!");
        alert.setContentText("Serial Number must be at least and no more 10 characters");
        alert.showAndWait();

    }
    public void value(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Incorrect Value Format!");
        alert.setContentText("Value must be a number");
        alert.showAndWait();
    }

}
