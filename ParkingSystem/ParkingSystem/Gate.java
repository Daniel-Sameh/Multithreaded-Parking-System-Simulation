package ParkingSystem;

public class Gate {
    protected int num;
    protected int count = 0;
    Gate(int num){ 
        this.num = num;
    }
    public int getNum(){ return num; }
    public synchronized void increment(){ count++; }
    public int getCount(){ return count; }
}
