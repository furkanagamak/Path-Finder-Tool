
/**
 * Date: 5/3/2022
 * This class describes a CityRoute which holds the route between the cities(represented by a LinkedList of Strings), and the size
 * of the route between the cities(represented by an int variable). The class has methods that allow the user to manipulate 
 * the instance variables (such as getters and setters).
 * @author Furkan Agamak
 * SBU ID: 114528166
 * CSE 214 - R03 Recitation
 */

import java.util.*;

public class CityRoute {
	
	/**
	 * A LinkedList of String variables representing the route between the cities.
	 */
	LinkedList<String> route = new LinkedList<String>();
	/**
	 * An int variable representing the size of the city route.
	 */
	int size = 0;
	
	/**
	 * This is a method that gets the route between the cities.
	 * @return
	 * 		A LinkedList representing the route of cities.
	 */
	public LinkedList<String> getRoute() {
		return this.route;
	}
	
	/**
	 * This is a method that sets the route between the cities.
	 * @param route
	 * 		A LinkedList representing the route of cities.
	 */
	public void setRoute(LinkedList<String> route) {
		this.route = route;
	}
	
	/**
	 * This is a method that gets the size of the city route.
	 * @return
	 * 		An int variable representing the size of the route between cities.
	 */
	public int getSize() {
		return this.size;
	}
	
	/**
	 * This is a method that sets the size of the city route.
	 * @param size
	 * 		An int variable representing the size of the route between cities.
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	/**
	 * This is a String representation of the CityRoute class. It displays the route of cities.
	 * @return
	 * 		A String variable representing the CityRoute class.
	 */
	public String toString() {
		String str = this.route.toString();
		str = str.replace("[", "").replace("]", "").replace(" ", "");
		str = str + ": " + this.getSize();
		str = str.replace(",", "->");
		return str;
	}
}
