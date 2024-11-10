package ParkingSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Number of parking spots: ");
        int parkingSpots = scanner.nextInt();
        System.out.print("Number of gates: ");
        int gatesCount = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Input file name: ");
        String fileName = Paths.get("").toAbsolutePath() + "\\" + scanner.nextLine();

        ParkingSystem.Semaphore semaphore = new ParkingSystem.Semaphore(parkingSpots);
        ParkingSystem.Gate[] gates = new ParkingSystem.Gate[gatesCount];
        for (int i = 0; i < gatesCount; i++){
            gates[i] = new ParkingSystem.Gate(i + 1);
        }

        ArrayList<ParkingSystem.CarThread> carThreads = new ArrayList<>();

        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" ");

                int gateNum = Integer.parseInt(parts[1].substring(0, parts[1].length() - 1));
                int carID = Integer.parseInt(parts[3].substring(0, parts[3].length() - 1));
                int arrivalTime = Integer.parseInt(parts[5].substring(0, parts[5].length() - 1));
                int parkingTime = Integer.parseInt(parts[7]);
                ParkingSystem.CarThread thread = new ParkingSystem.CarThread(carID, gates[gateNum - 1], arrivalTime, parkingTime, semaphore);
                thread.start();
                carThreads.add(thread);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (var thread: carThreads){
            thread.join();
        }
        scanner.close();
        System.out.println("Total Cars Served: " + carThreads.size());
        System.out.println("Current Cars in Parking: 0");
        System.out.println("Details");
        for (var gate: gates){
            System.out.println("Gate " + gate.getNum() + " served " + gate.getCount() + " cars.");
        }
    }
}