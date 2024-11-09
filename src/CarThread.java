import java.util.concurrent.CountDownLatch;

public class CarThread implements Runnable{
    private String id;
    private int arrivalTime;
    private int parkingTime;
    private ParkingLot parkingLot;
    private String gate;
    private CountDownLatch startLatch;

    public CarThread(String id, String gate, int arrivalTime, int parkingTime, ParkingLot parkingLot) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.parkingTime = parkingTime;
        this.parkingLot = parkingLot;
        this.gate = gate;
//        this.startLatch = startLatch;
    }

    @Override
    public void run() {
        System.out.println("Car "+ id+" from Gate "+ gate + " arrived at time "+ arrivalTime);
        try {
            Thread.sleep(arrivalTime*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long startTime = System.currentTimeMillis();
        boolean hasWaited = false;

        //Park
        try {
            boolean told=false;
            // Try to park and keep track of the waiting time
            while (!parkingLot.parkCar()) {
                if (!told){
                    System.out.println("Car "+ id +" from Gate " + gate + " waiting for a spot.");
                    told=true;
                }
                hasWaited = true; // The thread is waiting to park
                Thread.sleep(1000);
            }

            long endTime = System.currentTimeMillis();
            long waitingTime = (long) Math.ceil((endTime - startTime) / 1000.0);
            if (hasWaited){
                System.out.println("Car "+ id +" from Gate " + gate + " parked after waiting for "+ waitingTime +" units of time. (Parking Status: " + parkingLot.getSpotsAvailable() + " spots occupied)");
            }else{
                System.out.println("Car "+ id +" from Gate " + gate + " parked. (Parking Status: " + parkingLot.getSpotsAvailable() + " spots occupied)");
            }
            Thread.sleep(parkingTime*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        parkingLot.leaveParking();
        System.out.println("Car "+id+" from Gate "+gate+" left after "+parkingTime+" units of time. (Parking Status: "+parkingLot.getSpotsAvailable()+" spots occupied)");

    }

}
