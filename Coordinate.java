import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Coordinate implements Comparable<Coordinate> {
	
	public Airport airport;
	public int time;
	
	public ArrayList<Coordinate> adjacencyList = new ArrayList<>();
	
	public double cost;
	public Coordinate path;
	public boolean found;
	public boolean investigated;
	
	
	
	public Coordinate findPathTask2(HashMap<String,Coordinate> allCoordinatesHash, Plane p, Airport a, PriorityQueue<Coordinate> q) {
		
		Coordinate destination = new Coordinate(); destination.cost = Double.POSITIVE_INFINITY;
		
		while (q.size() != 0) {
			
			Coordinate o = q.poll();
			o.found = true;
			
			if (o.airport.equals(a))
				if (o.cost<destination.cost)
					destination = o;
			
			
			for (Coordinate neigh: o.adjacencyList) {
				
				if (!neigh.found) {
					if (o.airport.equals(neigh.airport)) {
						double cost = o.airport.parkingCost;
						if (o.cost + cost < neigh.cost) {
							q.remove(neigh);
							neigh.cost = o.cost + cost;
							neigh.path = o;
							q.add(neigh);
						}
					}

					else {
						double cost = o.airport.cost(neigh.airport, o.time, neigh.time);
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
		
		return destination;
	}	
	
	
	
	public void printPath(Coordinate o) throws IOException {
		
		if (this.equals(o)) {
			
			Main.task2.write(this.airport.code + " ");

			return;
		}
		
		if (this.path == null) {
			Main.task2.write("No possible solution.");
			return;
		}
		
		this.path.printPath(o);
		
		if (!this.airport.equals(this.path.airport))
			Main.task2.write(this.airport.code + " ");
		else
			Main.task2.write("PARK ");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public int compareTo(Coordinate o) {
		return (int) (this.cost - o.cost);
	}
	
	
	
	
}
