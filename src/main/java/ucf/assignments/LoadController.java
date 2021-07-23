package ucf.assignments;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;



public class LoadController {

    public void tsv(File file, ObservableList<InventoryContents> list, TableView<InventoryContents> table){

        try{

            Scanner scanner = new Scanner(new File(String.valueOf(file)));

            int i= 0;
            ArrayList<String> column = new ArrayList<>();

            //scans each line of file
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();

                //each line is then scanned and then stored into and array list
                Scanner lineScanner = new Scanner(line);
                while(lineScanner.hasNext()){
                    String part = lineScanner.next();
                    column.add(part);
                }
            }
            //using arraylist, column data is added to each column in table
            for(int j = 0; j< column.size(); j++){
                float value = Float.parseFloat(column.get(i));
                list.add(new InventoryContents(value, column.get(i+1), column.get(i+2)));
                i+=3;
            }
            table.setItems(list);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void html(File file, ObservableList<InventoryContents> list, TableView<InventoryContents> table){

        File inputFile = new File(String.valueOf(file));

        try {

            //parses html file
            Document doc = Jsoup.parse(inputFile, "utf-8");

            int i = 6;
            ArrayList<String> data = new ArrayList<>();

            //splits the text of the html document and adds it to an arraylist
            String[] content = doc.body().text().split(" ");
            Collections.addAll(data, content);

            //using arraylist, text data from html file is then added to table
            for(int j = 6; j<data.size(); j++){
                float value = Float.parseFloat(data.get(i));
                list.add(new InventoryContents(value, data.get(i+1), data.get(i+2)));
                i+=3;
            }
            table.setItems(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void json(File file, ObservableList<InventoryContents> list, TableView<InventoryContents> table) throws IOException {

        //converts json file content to string
        String json = new String(Files.readAllBytes(Paths.get(String.valueOf(file))));

        //parses string content
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();


        JsonArray arr = jsonObject.getAsJsonArray("inventory");
        for(int i = 0; i<arr.size();i++){

            //string content is then iterated through, assigning data to variables.
            float value = arr.get(i).getAsJsonObject().get("value").getAsFloat();
            String serial = arr.get(i).getAsJsonObject().get("serial number").getAsString();
            String name = arr.get(i).getAsJsonObject().get("name").getAsString();

            //these variables are then added to the table
            list.add(new InventoryContents(value, serial, name));
        }
        table.setItems(list);
    }
}
