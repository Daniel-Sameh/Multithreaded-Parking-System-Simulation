import java.util.ArrayList;
import java.util.concurrent.Semaphore;
public class ParkingLot {
    private static Semaphore parkingSpots;
    private static int current;

    public ParkingLot(){
        current = 0;
        parkingSpots = new Semaphore(4);
    }

    public synchronized boolean parkCar() {
        try {
            if (parkingSpots.tryAcquire()) {
                ++current;
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized void leaveParking() {
        parkingSpots.release();
        --current;
    }

    public synchronized int getSpotsAvailable(){
//        return parkingSpots.availablePermits();
        return current;
    }


}
