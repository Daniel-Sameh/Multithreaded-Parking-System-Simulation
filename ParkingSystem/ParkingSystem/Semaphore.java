package ParkingSystem;

class Semaphore {
    protected int value;
    protected int available;
    protected int total;
    protected Semaphore(){ total = value = 0; }
    protected Semaphore(int initial){ total = available = value = initial; }
    public int getAvailable(){ return available; }
    public int getOccupied(){ return total - getAvailable(); }
    public synchronized void P(){
        value--;
        if (value < 0)
        try { wait(); } 
        catch( InterruptedException e ){}
        available--;
    }
    public synchronized void V(){
        available++;
        value++;
        if (value <= 0) 
            notify();
    }
}
    