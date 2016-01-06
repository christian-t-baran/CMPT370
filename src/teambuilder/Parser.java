package teambuilder;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import entities.Robot;
import entities.RobotUpdate;
import forth.ForthObject;
import forth.ForthVariable;
import forth.ForthWord;

/**
 * This class models a JSONParser for decoding and encoding JSON for communication with the Robot
 * Librarian
 * @author Christian
 */
public class Parser {
	
	JSONParser parser;
	
	/**
	 * Constructor for the Parser class
	 */
	public Parser(){
		parser = new JSONParser();
	}
	
	/**
	 * Takes JSON representing a Robot and decodes it into a Robot
	 * @param r JSON text for a Robot
	 * @return Decoded Robot object
	 */
	public Robot decodeRobotJSON(String r){
		Object obj = null;
		
		// Attempt to parse string
		try {
			obj = parser.parse(r);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Cast Object to jsonObject
		JSONObject jsonObject = (JSONObject) ( (JSONObject) obj).get("script");

		
		// Take array of code
		JSONArray JSONCode = (JSONArray) jsonObject.get("code");
				
		// Make instruction map
		Map<String, ForthObject> code = new TreeMap<String, ForthObject>();
				
		// Loop through code array converting each entry into a ForthObject
		for(int i = 0; i < JSONCode.size(); i++){
			JSONObject forth = (JSONObject) JSONCode.get(i);
			String name = null;
			ForthObject v = null;
					
			// Handle case where object was a variable
			if( forth.containsKey("variable") ){
				name = (String) forth.get("variable");
				v = new ForthVariable(name);
			}
			// Handle case where object was a word
			else if( forth.containsKey("word") ){
				forth = (JSONObject) forth.get("word");
				name = (String) forth.get("name");
				v = new ForthWord(name, (String) forth.get("body") );
			}
					
					// Insert ForthObject into dictionary
					code.put(name, v);
		}
				
		// Create new Robot with values from jsonObject
		Robot newRobot = new Robot( ( (Long) jsonObject.get("health") ).intValue()
								, ( (Long) jsonObject.get("firepower") ).intValue()
								, ( (Long) jsonObject.get("movement") ).intValue()
								, ( (Long) jsonObject.get("serial") ).intValue()
								, code ); 
		
		// Get metadata values from jsonObject
		newRobot.setName( (String) jsonObject.get("name") );
		
		return newRobot;
	}
	
	/**
	 * Gets the serial number and name of the Robot in a String r
	 * @param r Robot script
	 * @return serial number and name of Robot
	 */
	public String getSerialName(String r){
		Object obj = null;
		
		// Attempt to parse string
		try {
			obj = parser.parse(r);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Cast Object to jsonObject
		JSONObject jsonObject = (JSONObject) ( (JSONObject) obj).get("script");
		
		String returnString = "";
		
		returnString += ( (Long) jsonObject.get("serial") ).intValue() + " - " +  (String) jsonObject.get("name");
		
		return returnString;
		
	}
	
	/**
	 * Takes a Robot with updated metadata and encodes it into JSON Text
	 * @param r Robot object
	 * @return JSON text representing Robot Update
	 */
	public String encodeRobotUpdate(RobotUpdate r){
		Map rob = new LinkedHashMap();
		
		// Build map from Robot
		rob.put( "serial", r.getSerial() );
		rob.put( "wins", r.getWins() );
		rob.put( "losses", r.getLosses() );
		rob.put( "executions", r.getExecutions() );
		rob.put( "lived", r.getLived() );
		rob.put( "died", r.getDied() );
		rob.put( "absorbed", r.getAbsorbed() );
		rob.put( "damages", r.getDamageDealt() );
		rob.put( "kills", r.getKills() );
		rob.put( "distance", r.getDistance() );
		
		Map update = new LinkedHashMap();
		update.put( "update", rob);
		
		String jsonText = JSONValue.toJSONString(update);
		
		return jsonText;
	}
	
	/**
	 * Testing functions for Parser class
	 */
	public static void main(String[] args){
		String jsonRobot = null;
		
		Parser test = new Parser();
		
		try {
			jsonRobot = new String( readAllBytes( get("testing/librarian/1.json") ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		Robot tank = test.decodeRobotJSON(jsonRobot);
		
		// Test getSerianName()
		assert ( test.getSerialName(jsonRobot).equals("1 - tank") );
		
		// Test to see tank was parsed properly
		assert( tank.getSerial() == 1 );
		assert( tank.getName().equals("tank") );
		assert( tank.getMaxHealth() == 2);
		assert( tank.getFirepower() == 3);
		assert( tank.getMaxMoves() == 1);
		
		
		try {
			System.out.println( test.getSerialName( new String( readAllBytes( get("testing/librarian/1.json") ) ) ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assert( test.getSerialName(jsonRobot).equals("1 - tank") );		

		// Test encoding an update from a Robot and writing to a file
		

		/*
		 * TO DO UPDATE TESTING
		 
		String testUpdate = null; 
		 
		String tankUpdate = test.encodeRobotUpdate(tank);
	
		System.out.println(tankUpdate);
		
		PrintWriter out = null;
		try {
			out = new PrintWriter("testing/updates/tank.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		out.print(tankUpdate);
		
		out.close();
		
		try {
			System.out.println( new String( readAllBytes( get("testing/updates/tank.txt") ) ) );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
		
	}
}
