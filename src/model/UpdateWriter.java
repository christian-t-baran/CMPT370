package model;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import entities.Robot;
import entities.RobotUpdate;
import teambuilder.Parser;


/**
 * This class packages data on a Robot metadata update into the update JSON format
 */
public class UpdateWriter {
	
	// testing directory to write updates to
	private final static String UPDATE_DIRECTORY = "librarian/updates/";
	
	// testing directory to write updates to
	// private final static String UPDATE_DIRECTORY = "testing/updates/";

	// parser used to encode updates
	private Parser parser;
	
	/**
	 * Constructor for UpdateWriter
	 */
	public UpdateWriter(){
		parser = new Parser();
	}
	
	/**
	 * Writes the updated metadata in a Robot r to a JSON file
	 * @param r RobotUpdate containing update metadata
	 * @postcond file with name 'serial'.txt now exists in update directory containing update JSON
	 */
	public void writeUpdate(RobotUpdate r){
		String update = parser.encodeRobotUpdate(r);
		
		int serial = r.getSerial();
				
		String updateFile = UPDATE_DIRECTORY + serial + ".json";
		
		PrintWriter out = null;
		try {
			out = new PrintWriter(updateFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		out.print(update);
		
		out.close();		
	}
	
	
	/**
	 * Testing method for UpdateWriter
	 * Requires ROBOT_DIRECTORY = "testing/librarian/" in LibrarianClient
	 */
	public static void main(String[] args) {
		
		// Spin up instance of Parser to read in a Robot
		String jsonRobot = null;
		
		Parser p = new Parser();
		
		// Read in and decode file
		try {
			jsonRobot = new String( readAllBytes( get("scripts/00001.json") ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Robot robby = p.decodeRobotJSON(jsonRobot);
		
		RobotUpdate r = new RobotUpdate( robby.getSerial(), 1 );
		
		// Spin up test UpdateWriter
		UpdateWriter test = new UpdateWriter();
		
		// Add some metadata
		r.setKills(5);
		r.setLived(1);
		r.setWins(1);
		
		// Write update
		test.writeUpdate(r);
		
		// Try to read the update
		try {
			System.out.println( new String( readAllBytes( get("testing/updates/1.json") ) ) );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
