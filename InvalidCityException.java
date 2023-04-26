
/**
 * Date: 5/3/2022
 * This class extends the Exception class and is used to throw exceptions when the city is invalid and doesn't exist in the graph.
 * @author Furkan Agamak
 * SBU ID: 114528166
 * CSE 214 - R03 Recitation
 */

public class InvalidCityException extends Exception {
	
	/**
	 * This is a default constructor that allows the user to display an error message for InvalidCityException when the exception is thrown.
	 * @param message
	 * 		A String representing the error message that the user wants to show.
	 */
	public InvalidCityException(String message) {
		super(message);
	}
}
