package tools;

public class MutableInt {

    private int value;

    public MutableInt(int value) { this.value = value; }

    public void setValue(int value) { this.value = value; }
    public int  getValue() { return this.value; }
    public MutableInt increment(){
        value += 1;
        return this;
    }
}