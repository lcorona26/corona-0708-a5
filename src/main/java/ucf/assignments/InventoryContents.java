package ucf.assignments;

public class InventoryContents {

    private String value;
    private String serial;
    private String name;

    public InventoryContents(String value, String serial, String name) {
        this.value = value;
        this.serial = serial;
        this.name = name;
    }

    public String getValue() {return value; }

    public String getSerial() {
        return serial;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return  "blank1" + this.value + "blank2"+ "\t"  + "\"" + this.serial + "\"" + "blank3" + "\t" + "\"" + this.name + "\"" + "\n";
    }

}
