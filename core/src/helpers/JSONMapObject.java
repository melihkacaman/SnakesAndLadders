package helpers;

public class JSONMapObject {
    private int id;
    private String type;
    private int firstValue;
    private int secondValue;

    public JSONMapObject(int id, String type, int firstValue, int secondValue) {
        this.id = id;
        this.type = type;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(int firstValue) {
        this.firstValue = firstValue;
    }

    public int getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(int secondValue) {
        this.secondValue = secondValue;
    }
}
