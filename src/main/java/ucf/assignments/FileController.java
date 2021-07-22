package ucf.assignments;

import javafx.collections.ObservableList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FileController{


    public void tsv(File file, ObservableList<InventoryContents> list){

        try{
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(String.valueOf(list).replace("blank1", "").replace("blank2", "")
                    .replace("blank3", "").replace(" ", "").replace(",", "\n")
                    .replace("[", "").replace("]", ""));
            printWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void html(File file, ObservableList<InventoryContents> list){

        try{
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write("<body>\n\n<h1>Inventory List</h1>\n\n<main>");
            printWriter.write(String.valueOf(list).replace("blank1", "").replace("blank2", "")
                    .replace("blank3", "").replace(" ", "").replace(",", "<br><br>")
                    .replace("[", "").replace("]", "").replace("\t", "&emsp;"));
            printWriter.write(".</main>\n\n</body>");
            printWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
    public void json(File file, ObservableList<InventoryContents> list){

        try{
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(String.valueOf(list).replace("\t", " ").replace("\n", "\n\t}").replace("blank2", ", \"serial number\": " )
                    .replace("blank3", ", \"name\": ").replace("blank1", "\n\t{\n\t\t\"value\": ")
                    .replace("[", "{\n\t\"Inventory\": [\n").replace("]", "\n    ]\n}"));
            printWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
