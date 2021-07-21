package ucf.assignments;

public class ColumnData {

    private float value;
    private String serial;
    private String name;

    public ColumnData(float value, String serial, String name) {
        this.value = value;
        this.serial = serial;
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public String getSerial() {
        return serial;
    }

    public String getName() {
        return name;
    }
}
