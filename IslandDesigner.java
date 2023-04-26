
/**
 * Date: 5/3/2022
 * This class describes an IslandDesigner which lets the user get access to info about the cities on the island. The class lets 
 * the user perform DFS(Depth First Search) from any of the cities on the island to any of the other cities on the island. It also 
 * lets the user compute the maximum network flow from any of the cities on the island to any of the other cities on the island. 
 * For Extra Credit, a shortest path algorithm which allows the user to compute the shortest path from any of the cities on the 
 * island to any of the other cities on the island was implemented.
 * @author Furkan Agamak
 * SBU ID: 114528166
 * CSE 214 - R03 Recitation
 */

import big.data.*;
import java.util.*;

public class IslandDesigner {
	
	/**
	 * This is the main method which takes in user input and lets the user interact with the cities on the island. It the user perform 
	 * DFS(Depth First Search) from any of the cities on the island to any of the other cities on the island. It also lets the user 
	 * compute the maximum network flow from any of the cities on the island to any of the other cities on the island. For Extra Credit purposes, 
	 * a shortest path algorithm which allows the user to compute the shortest path from any of the cities on the island to any of the other 
	 * cities on the island was implemented.
	 * @param args
	 */
	public static void main(String[] args) {
		boolean flag = false;
		IslandNetwork network = new IslandNetwork();
		String address = "";
		System.out.println("Welcome to the Island Designer, because, when you're trying to stay above water, Seas get degrees!");
		System.out.println();
		Scanner keyboard = new Scanner(System.in); // Creating a Scanner object to take input from the user.
		do { // Loading the map from the URL specified by the user.
			try {
				System.out.print("Please enter the URL: ");
				address = keyboard.nextLine();
				System.out.println();
				network = IslandNetwork.loadFromFile(address);
				flag = true;
			} catch(DataSourceException exception) {
				System.out.println();
				System.out.println("The URL that you entered is not valid. The map cannot be loaded.");
				System.out.println();
			}
		} while(flag == false);
		
		List<String> cityList = new ArrayList<String>(network.getGraph().keySet());
		System.out.println();
		System.out.println("The map was loaded.");
		System.out.println();
		System.out.println();
		System.out.println("Cities:");
		System.out.println("---------------------");
		int j = 0;
		Collections.sort(cityList);
		while(j < cityList.size()) {
			String theCity = cityList.get(j);
			System.out.println();
			System.out.println(theCity);
			j++;
		}
		System.out.println();
		System.out.println();
		System.out.println();
		String input = "";
		String roads = "";
		System.out.printf("%-45s %s%n", "Road", "Capacity");
		int m = 0;
		DataSource d = DataSource.connectXML(address); // These five lines were taken from Professor Esmaili's examples in Homework 7 instructions.
		d.load(); // These five lines were taken from Professor Esmaili's examples in Homework 7 instructions. 
		roads = d.fetchString("roads"); // These five lines were taken from Professor Esmaili's examples in Homework 7 instructions.
		roads = roads.substring(2, roads.length() - 2); // These five lines were taken from Professor Esmaili's examples in Homework 7 instructions.
		String[] roadsArr = roads.split("\",\""); // These five lines were taken from Professor Esmaili's examples in Homework 7 instructions. They were applied to this homework's specifications.
		System.out.println("-------------------------------------------------------");
		while(m < roadsArr.length) {
			String[] theRoads = roadsArr[m].split(",");
			System.out.println();
			System.out.printf("%-49s %s%n", theRoads[0] + " to " + theRoads[1], theRoads[2]);
			m++;
		}
		System.out.println("\n\n\nMenu:\n\tD) Destinations reachable (Depth First Search)\n\tF) Maximum Flow\n"
					+ "\tS) Shortest Path (Extra Credit)\n\tQ) Quit");
		do {
			System.out.println();
			System.out.print("Please select an option: ");
			input = keyboard.nextLine();
				
			switch(input.toUpperCase()) {
				
				case "D": // Allows the user to see the destinations reachable(based on Depth First Search) from the specified city.
					String startingCity = "";
					String str = "";
					System.out.println();
					System.out.print("Please enter the starting city: ");
					startingCity = keyboard.nextLine();
					try {
						List<String> temp = network.dfs(startingCity);
						str = temp.toString();
						System.out.println();
						System.out.println("DFS Starting from " + startingCity + ":");
						System.out.println();
					} catch(InvalidCityException exception) {
						System.out.println();
						System.out.println();
						System.out.println("The city that you entered does not exist. Please remember that the "
									+ "names of the cities are case-sensitive.");
						System.out.println();
					}
					if(str.equals("[]")) {
						System.out.println();
						System.out.println("There are no destinations that exist after this city.");
						System.out.println();
					}
					else {
						System.out.println(str = str.replace("[", "").replace("]", ""));
					}
					break;
						
				case "F": // Allows the user to see the maximum network flow between the two specified cities.
					String startCity = "";
					String destination = "";
					System.out.println();
					System.out.print("Please enter the starting city: ");
					startCity = keyboard.nextLine();
					System.out.println();
					System.out.print("Please enter the destination: ");
					destination = keyboard.nextLine();
					System.out.println();
					try {
						String flow = network.maxFlow(startCity, destination);
						System.out.println(flow);
					} catch(InvalidCityException exception) {
						System.out.println();
						System.out.println("The city that you entered does not exist. Please remember that the "
									+ "names of the cities are case-sensitive.");
						System.out.println();
					}
					break;
					
				case "S": // (For Extra Credit) Allows the user to see the shortest path between the two specified cities. 
					// Note: The algorithm does not fully function but I tried my best to code it for partial credit. 
					String startNode = "";
					String destinationNode = "";
					System.out.println();
					System.out.print("Please enter the starting node: ");
					startNode = keyboard.nextLine();
					System.out.println();
					System.out.print("Please enter the destination node: ");
					destinationNode = keyboard.nextLine();
					System.out.println();
					try {
						String dijkstra = network.shortestRoute(startNode, destinationNode);
						System.out.println(dijkstra);
					} catch(InvalidCityException exception) {
						System.out.println();
						System.out.println("The city that you entered does not exist. Please remember that the "
									+ "names of the cities are case-sensitive.");
						System.out.println();
					}
					break;
						
				case "Q": // Quits the menu.
					System.out.println();
					System.out.println("You can go on your own way. Good bye!");
					System.out.println();
					break;
					
				default: // When the user does not pick one of the valid menu options.
					System.out.println();
					System.out.println();
					System.out.println("The option that you entered is invalid.");
					System.out.println();
			}
		} while(input.equalsIgnoreCase("Q") == false);
	}
}
