
/**
 * Date: 5/3/2022
 * This class describes an IslandNetwork which represents the graph of cities. The class holds the list of routes of the graph
 * of cities(represented by a List of Strings) and stores the cities in the graph(represented by a HashMap). The class has methods that allow 
 * the user to manipulate the instance variables (such as getters and setters).
 * @author Furkan Agamak
 * SBU ID: 114528166
 * CSE 214 - R03 Recitation
 */

import big.data.*;
import java.util.*;

public class IslandNetwork {
	
	/**
	 * A List object made up of String variables representing the list of routes of the graph of the cities.
	 */
	static List<String> routes = new ArrayList<String>(); 
	/**
	 * A HashMap representing the cities in the graph.
	 */
	private static HashMap<String, City> graph;
	
	/**
	 * This is a method that loads the file from the specified URL address.
	 * @param url
	 * 		A String variable representing the specified URL that will be loaded.
	 * @return
	 * 		An IslandNetwork object representing the graph of cities that was loaded specifically from the URL.
	 */
	public static IslandNetwork loadFromFile(String url) {
		String roads = "";
		String cities = "";
		int k = 0;
		int m = 0;
		DataSource d = DataSource.connectXML(url); // This line was taken from Professor Esmaili's examples in Homework 7 instructions. It was applied to this homework's specifications.
		graph = new HashMap<String, City>();
		IslandNetwork n = new IslandNetwork();
		d.load(); // These seven lines were taken from Professor Esmaili's examples in Homework 7 instructions. 
		roads = d.fetchString("roads"); // These seven lines were taken from Professor Esmaili's examples in Homework 7 instructions.
		roads = roads.substring(2, roads.length() - 2); // These seven lines were taken from Professor Esmaili's examples in Homework 7 instructions.
		cities = d.fetchString("cities"); // These seven lines were taken from Professor Esmaili's examples in Homework 7 instructions.
		cities = cities.substring(1, cities.length() - 1).replace("\"", ""); // These seven lines were taken from Professor Esmaili's examples in Homework 7 instructions.
		String[] roadsArr = roads.split("\",\""); // These seven lines were taken from Professor Esmaili's examples in Homework 7 instructions.
		String[] citiesArr = cities.split(","); // These seven lines were taken from Professor Esmaili's examples in Homework 7 instructions. They were applied to this homework's specifications.
		while(k < citiesArr.length) {
			graph.put(citiesArr[k], new City(citiesArr[k]));
			k++;
		}
		while(m < roadsArr.length) {
			String[] arr = roadsArr[m].split(",");
			String tempStr = arr[2];
			int i = Integer.valueOf(tempStr);
			String str1 = arr[1];
			String str0 = arr[0];
			graph.get(str0).insertNeigh(i, str1);
			m++;
		}
		return n;
	}
	
	/**
	 * This is a method that finds the maximum flow between two cities.
	 * @param from
	 * 		A String variable representing the name of the city that the flow will start from.
	 * @param to
	 * 		A String variable representing the name of the city that the flow will end at.
	 * @return
	 * 		A String variable representing the maximum flow between the two specified cities.
	 * @throws InvalidCityException
	 * 		Throws this exception if the specified city names cannot be found in the graph.
	 */
	public String maxFlow(String from, String to) throws InvalidCityException {
		String routeStr = "Routing:\n";
		List<String> l = new LinkedList<String>(this.graph.keySet());
		int i = 0;
		int sum = 0;
		int max = 0;
		while(i < l.size()) {
			String str = l.get(i);
			this.graph.get(str).setTempNeighbor((HashMap<String, Integer>) this.graph.get(str).neighbors.clone());
			i++;
		}
		CityRoute route = maxFlowHelper(to, from);
		for(;route.getSize() > 0;) {
			routeStr += route + "\n";
			sum = sum + route.getSize();
			max = maxFlowHelper(to, from).getSize();
			int k = 0;
			while(k < route.getRoute().size() - 1) {
				int num = this.graph.get(route.getRoute().get(k)).tempNeighbors.get(route.getRoute().get(k + 1));
				graph.get(route.getRoute().get(k)).tempNeighbors.put(route.getRoute().get(k + 1), (num - max));
				k++;
			}
			route = maxFlowHelper(to, from);
		}
		
		if(max == 0) {
			String theStr = "There are no routes available.";
			return theStr;
		}
		return routeStr + "Maximum Flow: " + sum;
	}
	
	/**
	 * This is a method that acts as a helper for the maxFlow() method. The method uses recursion to
	 * compute the maximum flow of a route.
	 * @param str1
	 * 		A String variable representing the name of the city that the flow will start from.
	 * @param str2
	 * 		A String variable representing the name of the city that the flow will end at.
	 * @return
	 * 		A CityRoute object representing the route between two specified cities.
	 * @throws InvalidCityException
	 * 		Throws this exception if the specified city names cannot be found in the graph.
	 */
	private CityRoute maxFlowHelper(String str1, String str2) throws InvalidCityException {
		CityRoute r = new CityRoute();
		int size = 0;
		int max = 0;
		if(this.graph.get(str2) == null || this.graph.get(str1) == null) {
			throw new InvalidCityException("The city is invalid.");
		}
		City c = this.graph.get(str2);
		int j = 0;
		List<String> neighborList = new LinkedList<String>(c.tempNeighbors.keySet());
		while(j < neighborList.size()) {
			String str = neighborList.get(j);
			City theCity = this.graph.get(str);
			if(this.graph.get(str1) == theCity) {
				size = c.tempNeighbors.get(str1);
			}
			else {
				size = Math.min(maxFlowHelper(str1, theCity.getName()).getSize(), c.tempNeighbors.get(str));
			}
			if(size > max) {
				max = size;
				r = maxFlowHelper(str1, theCity.getName());
			}
			j++;
		}
		r.setSize(max);
		r.getRoute().addFirst(str2);
		return r;
	}
	
	/**
	 * This is a method that runs a DFS(Depth First Search) from the specified city in the graph.
	 * @param from
	 * 		A String variable representing the name of the city that the DFS will start from.
	 * @return
	 * 		A List that is made up of String variables representing the cities that will be visited. 
	 * @throws InvalidCityException
	 * 		Throws this exception if the specified city name cannot be found in the graph.
	 */
	public List<String> dfs(String from) throws InvalidCityException {
		if(this.graph.get(from) == null) {
			throw new InvalidCityException("The city is invalid.");
		}
		this.routes.clear();
		clearVisit();
		dfsHelper(from);
		return this.routes;
	}
	
	/**
	 * This is a method that clears the Boolean visited variables of the cities in the graph. The method
	 * does not take any parameters and does not return anything.
	 */
	private void clearVisit() {
		int j = 0;
		List<String> l = new LinkedList<String>(this.graph.keySet());
		while(j < l.size()) {
			String str = l.get(j);
			City c = this.graph.get(str);
			c.setVisit(false);
			j++;
		}
	}
	
	/**
	 * This is a method that acts as a helper for the dfs() method. The method uses recursion to find the neighbors
	 * of the specified city that can be visited and adds them to the list of routes. This method does not return
	 * anything.
	 * @param str
	 * 		A String variable representing the name of the city that the DFS will start from.
	 */
	private void dfsHelper(String str) {
		City c = this.graph.get(str);
		c.setVisit(true);
		int j = 0;
		List<String> l = new LinkedList<String>(c.neighbors.keySet());
		Collections.sort(l);
		while(j < l.size()) {
			String theStr = l.get(j);
			City theCity = this.graph.get(theStr);
			if(theCity.getVisit() == false) {
				this.routes.add(theStr);
				dfsHelper(theCity.getName());
			}
			j++;
		}
	}
	
	/**
	 * This is a method that gets the cities in the graph.
	 * @return
	 * 		A HashMap representing the cities that are in the graph.
	 */
	public HashMap<String, City> getGraph() {
		return this.graph;
	}
	
	/**
	 * This is a method that sets the cities in the graph.
	 * @param graph
	 * 		A HashMap representing the cities that are in the graph.
	 */
	public void setGraph(HashMap<String, City> graph) {
		this.graph = graph;
	}
	
	/**
	 * This is a method that gets the shortest route between the two specified cities in the graph.
	 * @param dest
	 * 		A String variable that the shortest route algorithm will end at.
	 * @param start
	 * 		A String variable that the shortest route algorithm will start from.
	 * @return
	 * 		A String variable representing the shortest route between the two specified cities.
	 * @throws InvalidCityException
	 * 		Throws this exception if the specified city names cannot be found in the graph.
	 */
	public String shortestRoute(String to, String from) throws InvalidCityException {
		String routeStr = "Path:\n";
		List<String> l = new LinkedList<String>(this.graph.keySet());
		int i = 0;
		int sum = 0;
		int min = 0;
		while(i < l.size()) {
			String str = l.get(i);
			this.graph.get(str).setTempNeighbor((HashMap<String, Integer>) this.graph.get(str).neighbors.clone());
			i++;
		}
		CityRoute route = shortestRouteHelper(to, from);
		for(;route.getSize() > 0;) {
			routeStr += route + "\n";
			sum = sum + route.getSize();
			min = shortestRouteHelper(to, from).getSize();
			int k = 0;
			while(k < route.getRoute().size() - 1) {
				int num = this.graph.get(route.getRoute().get(k)).tempNeighbors.get(route.getRoute().get(k + 1));
				graph.get(route.getRoute().get(k)).tempNeighbors.put(route.getRoute().get(k + 1), (num - min));
				k++;
			}
			route = shortestRouteHelper(to, from);
		}
		
		if(min == 0) {
			String theStr = "There are no routes available.";
			return theStr;
		}
		return routeStr + "Cost: " + sum;
	}
	
	/**
	 * This is a method that acts as a helper method for the shortestRoute() method. It helps us find the shortest
	 * route between the two specified cities.
	 * @param str1
	 * 		A String variable that the shortest route algorithm will end at.
	 * @param str2
	 * 		A String variable that the shortest route algorithm will start from.
	 * @return
	 * 		A CityRoute object representing the route between two specified cities.
	 * @throws InvalidCityException
	 * 		Throws this exception if the specified city names cannot be found in the graph.
	 */
	private CityRoute shortestRouteHelper(String str1, String str2) throws InvalidCityException {
		CityRoute r = new CityRoute();
		int size = 0;
		int min = 0;
		if(this.graph.get(str2) == null || this.graph.get(str1) == null) {
			throw new InvalidCityException("The city is invalid.");
		}
		City c = this.graph.get(str2);
		int j = 0;
		List<String> neighborList = new LinkedList<String>(c.tempNeighbors.keySet());
		while(j < neighborList.size()) {
			String str = neighborList.get(j);
			City theCity = this.graph.get(str);
			if(this.graph.get(str1) == theCity) {
				size = c.tempNeighbors.get(str1);
			}
			else {
				size = Math.max(shortestRouteHelper(str1, theCity.getName()).getSize(), c.tempNeighbors.get(str));
			}
			if(size < min) {
				min = size;
				r = shortestRouteHelper(str1, theCity.getName());
			}
			j++;
		}
		r.setSize(min);
		r.getRoute().addFirst(str2);
		return r;
	}
}
