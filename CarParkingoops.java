import java.util.*;
import java.time.LocalTime;
class CarParkingoops{
    public static void main(String[] args){
         ParkingLot lot = new ParkingLot(2, 5);
        lot.traffic();
    }
}
interface OPerations{
    void start(String carNumber);
    void leaving(String carNumber);
    void traffic();
    int trackFine(String carNumber, LocalTime exitTime);
}
class ParkingLot implements OPerations{

    String[][] floor;
    Map<String, LocalTime> entryTimeMap;  
    ParkingLot(int floors, int slots) {
    floor = new String[floors][slots];
    entryTimeMap = new HashMap<>();
}
public void traffic() {

    Scanner sc = new Scanner(System.in);

    while (true) {

        System.out.println("Enter Car Number (or STOP):");
        String carNumber = sc.next();

        if (carNumber.equalsIgnoreCase("STOP")) {
            break;
        }

        System.out.println("Entry OR Exit:");
        String choice = sc.next();

        if (choice.equalsIgnoreCase("Entry")) {
            start(carNumber);
        }
        else if (choice.equalsIgnoreCase("Exit")) {
            leaving(carNumber);
        }
    }
}
public void start(String carNumber) {

    LocalTime time = LocalTime.now();

    for (int i = 0; i < floor.length; i++) {
        for (int j = 0; j < floor[i].length; j++) {

            if (floor[i][j] == null) {

                floor[i][j] = carNumber;
                entryTimeMap.put(carNumber, time);

                System.out.println(
                    "Car parked at Floor " + (i+1) +
                    " Slot " + (j+1) +
                    " Time " + time
                );

                return;
            }
        }
    }

    System.out.println("Parking Full!");
}
public void leaving(String carNumber) {

    LocalTime exitTime = LocalTime.now();

    for (int i = 0; i < floor.length; i++) {
        for (int j = 0; j < floor[i].length; j++) {

            if (carNumber.equals(floor[i][j])) {

                floor[i][j] = null;

                int fine = trackFine(carNumber, exitTime);
                entryTimeMap.remove(carNumber);

                System.out.println(
                    "Car leaving from Floor " + (i+1) +
                    " Slot " + (j+1)
                );

                System.out.println("Fine = " + fine);

                return;
            }
        }
    }

    System.out.println("Car not found!");
}
public int trackFine(String carNumber, LocalTime exitTime) {

    LocalTime entryTime = entryTimeMap.get(carNumber);

    if (entryTime == null) return 0;

    int hours = exitTime.getHour() - entryTime.getHour();

    if (hours <= 8) return 0;

    return (hours - 8) * 100;
}   
}

