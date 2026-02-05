import java.util.*;
import java.time.LocalTime;
public class CarParking{
    static Map<String, LocalTime> entryTimeMap = new HashMap<>();
    public static void main(String[] args){
      String[][] floor= new String[2][5];
      for(int i=0;i<2;i++){
            for(int j=0;j<5;j++){
                floor[i][j]=null;
            }
        }
      traffic(floor);
    }
   public static void start(String carNumber, LocalTime time,String floor[][]) {
    for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 5; j++) {
            if (floor[i][j] == null) {
                floor[i][j] =carNumber;
                entryTimeMap.put(carNumber, time);
                System.out.println("Car " + carNumber +
                        " parked at Floor " + (i + 1) +
                        ", Slot " + (j + 1) +
                        " at time " + time);
                return;
            }
        }
    }
    System.out.println("Parking Full!");
}
public static void leaving(String carNumber,LocalTime time,String floor[][]){
    for(int i=0;i<2;i++){
        for(int j=0;j<5;j++){
            if(carNumber.equals(floor[i][j])){
                floor[i][j]=null;
                int fine=trackFine(carNumber,time);
                entryTimeMap.remove(carNumber);
                 System.out.println("Car " + carNumber +
                        " leaving car from  floor " + (i + 1) +
                        ", Slot " + (j + 1) +
                        " at time " + time);
                        System.out.println("fine is"+fine);
                        return;
            }
        }
    }
    System.out.println("car not found in parking slots");
}
public static void traffic(String[][] floor) {
    Scanner sc = new Scanner(System.in);

    while (true) {
        System.out.println("Enter Car Number (or STOP to end):");
        String carNumber = sc.next();

        if (carNumber.equalsIgnoreCase("STOP")) {
            System.out.println("Parking closed.");
            break;
        }
        LocalTime time = LocalTime.now();
        System.out.println("Current time: " + time);

        System.out.println("Entry OR Exit:");
        String choice = sc.next();

        if (choice.equalsIgnoreCase("Entry")) {
            start(carNumber, time, floor);
        } else if (choice.equalsIgnoreCase("Exit")) {
            leaving(carNumber, time, floor);
        } else {
            System.out.println("Invalid choice!");
        }
    }
}
public static int trackFine(String carNumber, LocalTime exitTime) {
    LocalTime entryTime = entryTimeMap.get(carNumber);

    if (entryTime == null) {
        return 0; 
    }

    int hoursStayed = exitTime.getHour() - entryTime.getHour();

    if (hoursStayed <= 8) {
        return 0;
    }
    return (hoursStayed - 8) * 100;
}
}
