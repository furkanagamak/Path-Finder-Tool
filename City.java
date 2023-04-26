
/**
 * Date: 5/3/2022
 * This class describes a City which holds the name of the city(represented by a String variable), the neighbors of the
 * city(represented by a HashMap), whether the city is discovered or not(represented by a Boolean variable), whether the
 * city is visited or not(represented by a Boolean variable), and temporary neighbors of the city(represented by a HashMap). 
 * The class has methods that allow the user to manipulate the instance variables (such as getters and setters). This class 
 * implements Comparable<City> and overrides the compareTo method which allows us to compare two cities alphabetically. The
 * class represents each node of the graph.
 * @author Furkan Agamak
 * SBU ID: 114528166
 * CSE 214 - R03 Recitation
 */

import java.util.*;

public class City implements Comparable<City> {
	
	/**
	 * A String variable representing the name of the city.
	 */
	private String name;
	/**
	 * A HashMap representing the neighbors of the city. The key of the HashMap is the city name and the Integer is the road cost.
	 */
	protected HashMap<String,Integer> neighbors = new HashMap<String, Integer>();
	/**
	 * A Boolean variable representing whether the city is discovered or not. This is initialized to false.
	 */
	public Boolean discovered = false;
	/**
	 * A Boolean variable representing whether the city is visited or not. This is initialized to false.
	 */
	public Boolean visited = false;
	/**
	 * A HashMap representing the temporary neighbors of the city. The key of the HashMap is the city name and the Integer is the road cost.
	 */
	public HashMap<String, Integer> tempNeighbors = new HashMap<String, Integer>();
	
	/**
	 * This is a method that compares two City objects alphabetically by looking at their names. 
	 * @param
	 * 		A City object representing the city that this city will be compared to. 
	 * @return
	 * 		An int variable representing the difference between the two cities alphabetically.
	 */
	public int compareTo(City o) {
		int order = this.name.compareTo(o.name);
		return order;
	}
	
	/**
	 * This is a constructor which constructs a City object with the specified name of the city.
	 * @param name
	 * 		A String variable representing the name of the city.
	 */
	public City(String name) {
		this.name = name;
	}
	
	/**
	 * This is a method that gets the neighbors of the city.
	 * @return
	 * 		A HashMap representing the neighbors of the city.
	 */
	public HashMap<String, Integer> getNeighbor() {
		return this.neighbors;
	}
	
	/**
	 * This is a method that sets the neighbors of the city.
	 * @param neighbors
	 * 		A HashMap representing the neighbors of the city.
	 */
	public void setNeighbor(HashMap<String, Integer> neighbors) {
		this.neighbors = neighbors;
	}
	
	/**
	 * This is a method that gets the name of the city.
	 * @return
	 * 		A String variable representing the name of the city.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * This is a method that sets the name of the city.
	 * @param name
	 * 		A String variable representing the name of the city.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This is a method that gets whether the city is discovered or not.
	 * @return
	 * 		A Boolean variable representing whether this city is discovered or not.
	 */
	public Boolean getDiscover() {
		return this.discovered;
	}
	
	/**
	 * This is a method that sets whether the city is discovered or not.
	 * @param discovered
	 * 		A Boolean variable representing whether this city is discovered or not.
	 */
	public void setDiscover(Boolean discovered) {
		this.discovered = discovered;
	}
	
	/**
	 * This is a method that gets whether the city is visited or not.
	 * @return
	 * 		A Boolean variable representing whether this city is visited or not.
	 */
	public Boolean getVisit() {
		return this.visited;
	}
	
	/**
	 * This is a method that sets whether the city is visited or not.
	 * @param visited
	 * 		A Boolean variable representing whether this city is visited or not.
	 */
	public void setVisit(Boolean visited) {
		this.visited = visited;
	}
	
	/**
	 * This is a method that gets the temporary neighbors of the city.
	 * @return
	 * 		A HashMap representing the temporary neighbors of the city.
	 */
	public HashMap<String, Integer> getTempNeighbor() {
		return this.tempNeighbors;
	}
	
	/**
	 * This is a method that sets the temporary neighbors of the city.
	 * @param tempNeighbors
	 * 		A HashMap representing the temporary neighbors of the city.
	 */
	public void setTempNeighbor(HashMap<String, Integer> tempNeighbors) {
		this.tempNeighbors = tempNeighbors;
	}
	
	/**
	 * This is a method that allows us to insert a neighbor of the city. This method does not return anything.
	 * @param cost
	 * 		An int variable representing the cost of the road between the inserted neighbor and this city.
	 * @param name
	 * 		A String variable representing the name of the neighbor.
	 */
	public void insertNeigh(int cost, String name) {
		this.neighbors.put(name, cost);
	}
}
