import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String fileName = "in.txt"; // Path to the input file
        ParkingLot parkingLot = new ParkingLot();
        ArrayList<Thread> threads = new ArrayList<>();
        CountDownLatch startLatch = new CountDownLatch(1);
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line by commas and trim whitespace
                String[] parts = line.split(",\\s*");
                if (parts.length == 4) {
                    String gate = parts[0].split(" ")[1];
                    String carId = parts[1].split(" ")[1];
                    int arrivalTime = Integer.parseInt(parts[2].split(" ")[1]);
                    int parkingDuration = Integer.parseInt(parts[3].split(" ")[1]);

                    // Initialize and start a new CarThread for each car
                    CarThread carThread = new CarThread(carId, gate, arrivalTime, parkingDuration, parkingLot);
                    Thread thread = new Thread(carThread);
                    threads.add(thread);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int carsServed = 0;
        for (Thread th: threads) {
            th.start();
            ++carsServed;
        }

//        startLatch.countDown();


        for (Thread th: threads) {
            th.join();
        }
        System.out.println("Total Cars Served: "+ carsServed);
        System.out.println("Current Cars in Parking: " + parkingLot.getSpotsAvailable());
        System.out.println("Details: ");

    }
}