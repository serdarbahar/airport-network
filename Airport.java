import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Airport implements Comparable<Airport>{
	
	public static double RADIUS = 6371;

	public String code;
	public Airfield airfield;
	public double latitude;
	public double longitude;
	public double parkingCost;
	
	public ArrayList<Airport> adjacencyList = new ArrayList<>();
	
	public double cost;
	public ArrayList<Double> costList = new ArrayList<>();
	public int time;
	public Airport path;
	public boolean found;
	
	public int park = 0;
	public int tryPark = 0;
	

	
	
	public void findPathTask1(Airport a, PriorityQueue<Airport> q, int timeOrigin) {
		
		while (q.size() != 0) {
			
			Airport o = q.poll();
			o.found = true;
			
			if (o.equals(a))
				return;
			
			for (Airport neigh: o.adjacencyList) {
				
				if (!neigh.found) {
					double cost = o.cost(neigh, timeOrigin);
					if (o.cost + cost < neigh.cost) {		
						q.remove(neigh);
						neigh.cost = o.cost + cost;
						neigh.path = o;
						q.add(neigh);	
					}
				}
			}
		}
	}
	
	

	
	
	
	
	
	
	
	
	public void printPath(Airport o) throws IOException {
		
		if (this.equals(o)) {
			Main.task1.write(this.code + " ");
			for (int i = 0; i<this.park; i++) {
				Main.task1.write("PARK ");
			}
			return;
		}
		
		if (this.path == null) {
			Main.task1.write("No possible solution.");
			return;
		}
		
		this.path.printPath(o);
		Main.task1.write(this.code + " ");	
		for (int i = 0; i<this.park; i++) {
			Main.task1.write("PARK ");
		}

		
	}

	
	
	public double cost(Airport a, int timeOrigin) {
		
		return 300 * this.getWeatherCost(timeOrigin) * a.getWeatherCost(timeOrigin) + this.distance(a);
			
	}
	
	public double cost(Airport a, int timeOrigin, int timeDest) {
		
		return 300 * this.getWeatherCost(timeOrigin) * a.getWeatherCost(timeDest) + this.distance(a);
			
	}
	
	private double getWeatherCost(int timeOrigin) {
		
		int b = this.airfield.weatherList.get(timeOrigin);
		
		
		int[] binary = new int[5];
		int i = 0;
		
		while (b > 0) {
			
			binary[4-i] = b % 2;
			b /= 2;
			i++;

		}

		return (0.05*binary[0] + 1) * (0.05*binary[1] + 1) * (0.10*binary[2] + 1) * (0.15*binary[3] + 1) * (0.20*binary[4] + 1);
		
		
	}
	
	public double distance(Airport a) {
		
		return 2 * RADIUS * Math.asin(
				Math.sqrt(
						Math.pow(Math.sin((a.latitude*Math.PI/180 - latitude*Math.PI/180)/2),2) +
						Math.cos(a.latitude*Math.PI/180) * Math.cos(latitude*Math.PI/180) * 
						Math.pow(Math.sin((a.longitude*Math.PI/180 - longitude*Math.PI/180)/2),2)));
		
	}
	
	@Override
	public int compareTo(Airport o) {
		
		return (int) (this.cost - o.cost);
		
		

	}
	
	
	
	
	
	
}
