package teambuilder;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import entities.Robot;
import teambuilder.Parser;

/**
 * This class serves as the system boundary between the RoboSport370 system and the Robot Librarian
 * @author Christian
 *
 */
public class LibrarianClient {
	
	// Path to look for Robots in //
	
	// for interpreter tests
	// private static final String ROBOT_DIRECTORY = "testing/librarian/";
	
	// for live games
	private static final String ROBOT_DIRECTORY = "librarian/robots/";

	// Instance of Parser class used by LibrarianClient to parse JSON
	private Parser parser;
	
	/**
	 * Constructor for LibrarianClient
	 */
	public LibrarianClient(){
		parser = new Parser();
	}
	
	/**
	 * Requests the List of Robots in the Robot Librarian
	 * @return List of Robots in the system.
	 */
	public List<String> requestList(){
		List<String> robots = new LinkedList<String>();
		
		File dir = new File(ROBOT_DIRECTORY);
		
		for (File f : dir.listFiles() ){
			if ( f.getName().endsWith(".json") ){
				String jsonRobot = null;
				
				try {
					jsonRobot = new String( readAllBytes( get( f.getCanonicalPath() ) ) );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				robots.add( parser.getSerialName( jsonRobot  ) );
			}
		}
		
		
		return robots;
	}
	
	/**
	 * Takes a serial number and downloads corresponding robot
	 * @param r serial number of Robot to download
	 * @return downloaded Robot
	 * @precond Robot's serial corresponds to valid file
	 */
	public Robot downloadRobot(int r){
		String file = ROBOT_DIRECTORY + r + ".json";
		
		assert( new File(file).exists() );
		
		String jsonRobot = null;

		try {
			jsonRobot = new String( readAllBytes( get( file ) ) );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return parser.decodeRobotJSON(jsonRobot);
	}
	
	/**
	 * Tests for LibrarianClient
	 * Must have ROBOT_DIRECTORY = "testing/librarian/";
	 */
	public static void main(String[] args){
		LibrarianClient test = new LibrarianClient();
		
		// Read files in test directory and print list of Robots
		System.out.println( test.requestList() );
		
		// Download a robot and assert
		Robot tank = test.downloadRobot(1);
		
		// Test to see tank was parsed properly
		assert( tank.getSerial() == 1 );
		assert( tank.getName().equals("tank") );
		assert( tank.getMaxHealth() == 2);
		assert( tank.getFirepower() == 3);
		assert( tank.getMaxMoves() == 1);
		
		// Attempt to load non-existent file (fails)
		/*
		 * tank = test.downloadRobot("4");
		 * 
		 */
		
	}
}
