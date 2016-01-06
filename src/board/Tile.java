package board;
import java.util.*;

import containers.Pair;
import entities.Robot;
import model.Logger;
import model.TeamList;
import teambuilder.TeamBuilderLogicLayer;



/**represents each of the tiles on the game board*/
public class Tile {
	/**Type of the tile that is stores*/
	public enum tileType{
		PLAIN,//this is a type cost of 1 to enter
		FOREST,// This is a type with cost of 2
		MOUNTAIN,//This has a type cost of 3
		WATER //This can't be moved too
	}
	
	
	//this list holds the robots that are on the current tile
	private final LinkedList<Pair<Integer,Integer>> robotList;
	private tileType tile;
	private Logger log;
	
	
	/**
	 * Constructor
	 * @param t, the type of the tile
	 */
	public Tile(tileType t){
		//Initialize the linked list of robots
		robotList = new LinkedList<Pair<Integer,Integer>>();
		//Set the tile type
		tile = t;
		log = Logger.getInstance(TeamList.numberOfTeams());
	}
	
	/**
	 * gives a reference to the tile robot list
	 * @return the robot tile list
	 */
	public List<Pair<Integer, Integer>> getRobotlist(){
		return this.robotList;
	}
	
	/**
	 * Inserts a robot on to the tile list
	 * @param Takes a pair and where the first integer is team Number and the second is position on the team
	 */
	public void placeRobotOnTile(Pair<Integer,Integer> p){
		robotList.add(p);
		
	}
		
	
	/**
	 * Removes a robot from a tile
	 * @param Takes a pair and where the first integer is team Number and the second is position on the team
	 * @return Returns the Pair that is removed from the list
	 */
	public Pair<Integer,Integer> removeRobotFromTile(Pair<Integer,Integer> p){
		robotList.remove(p);
		
		return p;
	}
	
	//TODO Change to spreading damage
	/**
	 * sets the damage for all of the robots
	 * @param the damage that is dealt to the robots on this tile
	 * @param robot shooting
	 */
	public int damageRobots(int damage, Pair<Integer, Integer> shooter){
		int counter = 0;
		
		List< Pair<Integer, Integer> > deadRobots = 
				new LinkedList< Pair< Integer, Integer> >();
		
		// distribute damage
		for(Pair<Integer, Integer> current: robotList){
			counter = counter + damage;
			Robot r = TeamList.getRobot(current);

			r.setCurrentHealth((r.getCurrentHealth() - damage));
			log.incrementAbsorbed(current, damage + log.getAbsorbed(current) );
			//if the robots died mark robot as dead on the team
			
			if(r.getCurrentHealth() <= 0){
				log.incrementDied(current, 1);
				//increment that shooter killed
				log.incrementKills(shooter, 1);
				//decrement that the robot died
				deadRobots.add( current );
				TeamList.robotDied(r.getTeam());
			}
			
		}
		
		// clean up dead robots
		for(Pair<Integer, Integer> robot: deadRobots){
			robotList.remove(robot);
		}
		
		return counter;
	}
	
	/**
	 * Sets the coordinates of the robot
	 * @param x, coordinate of the robot
	 * @param y, coordinate of the robot
	 * @param r, a pair containing a robot
	 */
	public void setRobotCoordinates(int x, int y, Pair<Integer, Integer> r){
		// add the location of the robot
		Robot rob = TeamList.getRobot(r);
		rob.setCoordinates(x, y);
	}
	
	/**
	 * checks if the list is empty
	 * @return true if empty otherwise false
	 */
	public boolean isEmpty(){
		return robotList.isEmpty();
	}
	
	/**
	 * Getter that returns the type for this tile
	 * @return The type of tile
	 */
	public tileType getTileType() {
		return tile;
	}
	
	/**
	 * Gets the dominant robot team in a list
	 * @return The dominant team number, or zero if no robots
	 */
	public int getDominant(){
		if(robotList.isEmpty()){
			return 0;
		}
		Iterator<Pair<Integer, Integer>> iter = robotList.iterator();
		//use an array to store the number of robots from each team
		int[] teamNumber = new int[6];

		// get the count of robots on each team
		while(iter.hasNext()){
			Pair<Integer,Integer> p = iter.next();
			int a = p.left() -1;
			teamNumber[a]++;
		}
		
		//get the team with the most robots
		int max = -1;
		int maxTeam = -1;

		for (int i = 0; i < teamNumber.length; i++) {
		    if (teamNumber[i] > max) {
		      max = teamNumber[i];
		      maxTeam = i;
		    }
		}
		
		//turn from an array index to a team number
		return maxTeam+1;
	}
	
	/**
	 * Gives the number of robots on the tile
	 * @return the number of robots on a tile
	 */
	public int numberRobotsOnTile(){
		return robotList.size();
	}
	
	/**
	 * Updates the distance that a robot has travelled
	 * @param dist
	 * @param robot
	 */
	public void updateDistance(int dist,Pair<Integer,Integer> robot){
		log.incrementDistance(robot, dist + log.getDistance(robot) );
		//uupdate logger distance
	}
	
	
	/**
	 * this sets the damage that a robot dealt
	 * @param damage
	 * @param robot
	 * @return 0 if successful 
	 */
	public int updateDamageDealt(Pair<Integer,Integer> robot, int totalDamageDone){
		log.incrementDamageDealt(robot,totalDamageDone + log.getDamageDealt(robot) );
		
		//update the logger here
		return 0;
	}
	
	/**
	 * this returns the fire power of a robot
	 * @param robot
	 * @return
	 */
	public int getfirePower(Pair<Integer,Integer> robot){
		Robot rob = TeamList.getRobot(robot);
		return rob.getFirepower();
	}

	public void setTileType(tileType t){
		this.tile = t;
	}
	
	public static void main(String[] args) {
		TeamBuilderLogicLayer t = new TeamBuilderLogicLayer(2);
		int i = 1;
		
		while(i < 3){
			int j =0;
			while(j < 4){
				t.addRobotToTeam(i, 1);
				j++;
			}
			i++;
		}
		TeamList.init(t.startMatch());
		

		
		Pair<Integer, Integer> a = new Pair<Integer, Integer>(1, 1);
		Pair<Integer, Integer> b = new Pair<Integer, Integer>(1, 2);
		Pair<Integer, Integer> c = new Pair<Integer, Integer>(1, 3);
		Pair<Integer, Integer> d = new Pair<Integer, Integer>(2, 1);
		Pair<Integer, Integer> e = new Pair<Integer, Integer>(2, 2);
		Pair<Integer, Integer> f = new Pair<Integer, Integer>(2, 3);
		Pair<Integer, Integer> g = new Pair<Integer, Integer>(1, 4);
		tileType tile = tileType.PLAIN;
		Tile test = new Tile(tile);
		test.placeRobotOnTile(a);
		test.placeRobotOnTile(b);
		test.placeRobotOnTile(c);
		test.placeRobotOnTile(d);
		test.placeRobotOnTile(e);
		test.placeRobotOnTile(f);
		test.placeRobotOnTile(g);
		
		Robot r = TeamList.getRobot(g);
		System.out.print(r.getMemberNum());

		System.out.println(test.getfirePower(d));
		
		
		test.damageRobots(2, a);
		
		System.out.println(TeamList.getNumberOfRobotsAlive(1));

		test.damageRobots(2, a);
		System.out.println(TeamList.getNumberOfRobotsAlive(1));
		if (test.numberRobotsOnTile() != 7) {
			System.out.println("Error number of robtos on tile");
		}
		if (test.getDominant() != 1) {
			System.out.println("Error getDominant");
		}

	}
	
}
