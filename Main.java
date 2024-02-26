import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

	public static FileWriter task1;
	public static FileWriter task2;

	public static void main(String[] args) throws IOException {
		
		String task1path = args[4];
		File task1File = new File(task1path);
		String task2path = args[5];
		File task2File = new File(task2path);
		
		task1 = new FileWriter(task1File);
		task2 = new FileWriter(task2File);
		
		String path1 = args[0];
		File airportsFile = new File(path1);
		Scanner scanner1 = new Scanner(airportsFile); scanner1.nextLine();
		
		HashMap<String, Airport> airportHash = new HashMap<>();
		HashMap<String, Airfield> airfieldHash = new HashMap<>();
		
		while (scanner1.hasNextLine()) {
			String line = scanner1.nextLine();
			String[] arr = line.split(",");
			Airport airport = new Airport();
			airport.code = arr[0]; 
			
			Airfield airfield;
			if (airfieldHash.containsKey(arr[1])) {
				airfield = airfieldHash.get(arr[1]);	
			}
			else {
				airfield = new Airfield();
				airfield.code = arr[1];
				airfieldHash.put(arr[1], airfield);
			}
			
			airport.airfield = airfield;
			airport.latitude =  Double.parseDouble(arr[2]);
			airport.longitude = Double.parseDouble(arr[3]);
			airport.parkingCost = Double.parseDouble(arr[4]);
			
			airportHash.put(airport.code, airport);		
		}
		scanner1.close();
		

		String path2 = args[1];
		File directionsFile = new File(path2);
		Scanner scanner2 = new Scanner(directionsFile); scanner2.nextLine();
		
		while (scanner2.hasNextLine()) {
			String line = scanner2.nextLine();
			String[] arr = line.split(",");
			
			Airport origin = airportHash.get(arr[0]);
			origin.adjacencyList.add(airportHash.get(arr[1]));
			
		}
		scanner2.close();
		
		
		String path3 = args[2];
		File weatherFile = new File(path3);
		Scanner scanner3 = new Scanner(weatherFile); scanner3.nextLine();
		
		while (scanner3.hasNextLine()) {
			String line = scanner3.nextLine();
			String[] arr = line.split(",");
			Airfield airfield = airfieldHash.get(arr[0]);
			
			if (airfield != null)
				airfield.weatherList.put(Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
		}
			
		scanner3.close();
		

		

		
		String path4 = args[3];
		File missionsFile = new File(path4);
		Scanner scanner4 = new Scanner(missionsFile); 
		scanner4.nextLine();
		
		//TASK 1
		double startTime = System.currentTimeMillis();
		while (scanner4.hasNextLine()) {
			
			String line = scanner4.nextLine();
			String[] arr = line.split(" ");
			Airport origin = airportHash.get(arr[0]); Airport destination = airportHash.get(arr[1]);
			int timeOrigin = Integer.parseInt(arr[2]);
			
			PriorityQueue<Airport> q = new PriorityQueue<>();
			for (Airport a: airportHash.values())  {		
				a.cost = Double.POSITIVE_INFINITY;
				a.found = false;
				q.add(a);
			}
			q.remove(origin);
			origin.cost = 0;
			q.add(origin);
			
			origin.findPathTask1(destination, q, timeOrigin);
			
			destination.printPath(origin);
			
			String s = String.format("%.5f%n",destination.cost);
			task1.write(s);
			
		}
		
		System.out.println((System.currentTimeMillis() - startTime)/1000.0);
		
		scanner4.close();
		
		System.out.println();
		
		//TASK 2
		startTime = System.currentTimeMillis();
		
		Scanner scanner5 = new Scanner(missionsFile);
		Plane plane = new Plane(scanner5.nextLine());
		
		HashMap<String, Coordinate> allCoordinatesHash = new HashMap<>();
		
		
		for (Airport a: airportHash.values()) {
			for (int i = 1680296400; i<=1682888400; i+=6*60*60 ) {
				
				Coordinate coor = new Coordinate();
				coor.airport = a;
				coor.time = i;
				allCoordinatesHash.put(coor.airport.code+coor.time, coor);
			}
		}
		
		
		
		
		for (Coordinate c: allCoordinatesHash.values()) {
			
			int t = c.time + 6*60*60;
			if (t<=1682888400) {
				c.adjacencyList.add(allCoordinatesHash.get(c.airport.code +  t));
			}


			for (Airport a: c.airport.adjacencyList) {

				int arrivalTime = c.time + plane.flightTime(c.airport.distance(a))*60*60;

				if (arrivalTime <= 1682888400) {
					c.adjacencyList.add(allCoordinatesHash.get(a.code + arrivalTime));
				}

			}
		}
	

		
		while (scanner5.hasNextLine()) {
			
			String line = scanner5.nextLine();
			String[] arr = line.split(" ");
			Airport origin = airportHash.get(arr[0]); Airport destination = airportHash.get(arr[1]);
			int timeOrigin = Integer.parseInt(arr[2]); int timeDest = Integer.parseInt(arr[3]);
			
			PriorityQueue<Coordinate> q = new PriorityQueue<>();
			for (Coordinate c: allCoordinatesHash.values()) {
				if (c.time <= timeDest) {
					c.path = null;
					c.cost = Double.POSITIVE_INFINITY;
					c.found = false;
					q.add(c);
					
				}
			}
			
			Coordinate originCoordinate = allCoordinatesHash.get(origin.code + timeOrigin);
			
			q.remove(originCoordinate);
			originCoordinate.cost = 0;
			q.add(originCoordinate);
			
			Coordinate destCoordinate = originCoordinate.findPathTask2(allCoordinatesHash, plane, destination, q);
			

			String s;
			
			if (destCoordinate.cost != Double.POSITIVE_INFINITY) {
				destCoordinate.printPath(originCoordinate);


				s = String.format("%.5f%n",destCoordinate.cost);
			}
			
			else {
				s = "No possible flight.";
			}
			
			task2.write(s);
			
		}
		
		System.out.println((System.currentTimeMillis() - startTime)/1000.0);
		
		
		scanner5.close();
		
		
		
		task1.close();
		task2.close();

		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
