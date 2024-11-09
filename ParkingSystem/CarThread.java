package ParkingSystem;

public class CarThread extends Thread {
    protected int carID;
    protected Gate gate;
    protected int arrivalTime;
    protected int parkingTime;
    protected Semaphore semaphore;
    CarThread(int carID, Gate gate, int arrivalTime, int parkingTime, Semaphore semaphore){
        this.carID = carID;
        this.gate = gate;
        this.arrivalTime = arrivalTime;
        this.parkingTime = parkingTime;
        this.semaphore = semaphore;
    }
    protected synchronized static void printLog(String text){
        System.out.println(text);
    }
    @Override
    public void run(){
        try {
            Thread.sleep(arrivalTime * 1000);
            // arrival time
            String log = "Car " + carID + " from Gate " + gate.getNum() + " arrived at time " + arrivalTime;
            // if waiting
            if (semaphore.getAvailable() == 0){
                log += "Car " + carID + " from Gate " + gate.getNum() + " waiting for a spot.";
                printLog(log);
                double startTime = (double)System.nanoTime();
                semaphore.P();
                double endTime = (double)System.nanoTime();
                long diff = (long)Math.ceil((endTime - startTime) / 1e9);
                // waiting time
                printLog("Car " + carID + " from Gate " + gate.getNum() + " parked after waiting for " + diff + " units of time. (Parking Status: " + semaphore.getOccupied() + " spots occupied)");
            }
            else { 
                printLog(log);
                semaphore.P();
                printLog("Car " + carID + " from Gate " + gate.getNum() + " parked. (Parking Status: " + semaphore.getOccupied() + " spots occupied)");
            }
            gate.increment();
            Thread.sleep(parkingTime * 1000);
            semaphore.V();
            // parking time
            printLog("Car " + carID + " from Gate " + gate.getNum() + " left after " + parkingTime + " units of time. (Parking Status: " + semaphore.getOccupied() + " spots occupied)");

        } catch (InterruptedException e) {
            return;
        }
    }
}
