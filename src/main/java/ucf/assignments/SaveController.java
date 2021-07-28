/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Luis Corona
 */
package ucf.assignments;

import javafx.collections.ObservableList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SaveController {


    public void tsv(File file, ObservableList<InventoryContents> list){

        //prints contents of table to .txt file with tab separated format
        try{
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(String.valueOf(list).replace("\"", "").replace("blank1", "").replace("blank2", "")
                    .replace("blank3", "").replace(" ", "").replace(",", "\n")
                    .replace("[", "").replace("]", ""));
            printWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void html(File file, ObservableList<InventoryContents> list){

        //prints contents of table to .html file with html format
        try{
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write("""
                    <html>
                    <head>
                    <style>
                    table,th,td {border: 1px solid black;
                    border-collapse:collapse;}th, td {padding: 15px;}
                    </style></head>""");
            printWriter.write("""
                    <body>

                    <h1>Inventory List</h1>

                    <table style = "width: 80%">
                    <tr>
                     <th>Value</th>
                    <th>Serial Number</th>
                    <th>Name</th>
                    </tr>
                    """);
            printWriter.write(String.valueOf(list).replace("\"", "").replace("blank1", "<tr>\n <td>")
                    .replace("blank2", "</td>\n").replace("blank3", "</td>\n")
                    .replace(" ", "").replace(",", "</td>\n</tr>\n")
                    .replace("[", "").replace("]", "").replace("\t", "<td>"));
            printWriter.write("</table>\n\n</body>\n</html>");
            printWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void json(File file, ObservableList<InventoryContents> list){

        //prints contents of table to .json file with json format
        try{
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(String.valueOf(list).replace("\t", " ").replace("\n", "\n\t}")
                    .replace("blank2", ", \"serial number\": " ).replace("blank3", ", \"name\": ")
                    .replace("blank1", "\n\t{\n\t  \"value\": ").replace(",", ",\n\t  ")
                    .replace("[", "{\n    \"inventory\": [\n").replace("]", "\n    ]\n}"));
            printWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
