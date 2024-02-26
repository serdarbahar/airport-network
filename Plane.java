
public class Plane {
	
	public String name;

	Plane() {
	}
	
	Plane(String n) {
		this.name = n;
	}
	
	public int flightTime(double d) {

		if (name.equals("Carreidas 160")) {

			if (d<=175) {
				return 6;
			}
			if (d>175 && d<=350) {
				return 12;
			}
			if (d>350) {
				return 18;
			}
			
		}

		else if (name.equals("Orion III")) {
			
			if (d<=1500) {
				return 6;
			}
			if (d>1500 && d<=3000) {
				return 12;
			}
			if (d>3000) {
				return 18;
			}

		}

		else if (name.equals("Skyfleet S570")) {
			
			if (d<=500) {
				return 6;
			}
			if (d>500 && d<=1000) {
				return 12;
			}
			if (d>1000) {
				return 18;
			}

		}

		else if (name.equals("T-16 Skyhopper")) {
			
			if (d<=2500) {
				return 6;
			}
			if (d>2500 && d<=5000) {
				return 12;
			}
			if (d>5000) {
				return 18;
			}
			
		}

		return 0;

		
		
	}
	
	
	
	
	
	
	
}
