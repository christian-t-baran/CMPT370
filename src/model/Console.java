package model;

import java.util.LinkedList;
import java.util.List;

/**
 * This class models a Console for use by the Interpreter for logging purposes
 */
public class Console {

	// The list that holds the logged information
	private List<String> console;
	
	/**
	 * Constructor for Console
	 */
	public Console(){
		console = new LinkedList<String>();
	}
	
	/**
	 * Writes a string to the console
	 * @param s
	 */
	public void writeLine(String s){
		console.add(s);
	}	
	
	/**
	 * Returns a list of all the strings in the console
	 * @return
	 */
	public List<String> getAll(){
		return console;
	}
	
	/**
	 * Clears the console
	 */
	public void flush(){
		console.clear();
	}
	
	/**
	 * Testing method for console
	 * @param args
	 */
	public static void main(String[] args){
		Console test = new Console();
		
		test.writeLine("test 1");
		
		System.out.println("Printing console");
		
		System.out.println( test.getAll() + "\n");
		
		test.writeLine("test 2");
		
		System.out.println("Printing console");
		System.out.println( test.getAll() + "\n");


		test.flush();
		System.out.println( "Flushing console\n");
		
		test.writeLine("test 3");
		
		System.out.println("Printing console");
		System.out.println( test.getAll() + "\n");
	}
	
}
