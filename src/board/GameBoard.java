package board;

import java.util.*;

import board.Tile.tileType;
import containers.Pair;
import entities.Robot;
import model.CoordinateMapper;
import model.TeamList;
import teambuilder.TeamBuilderLogicLayer;



/** stores the game board implementation */
public class GameBoard {
	public final int max;
	public final int min;

	//Internal hashmap that store the gameboard
	public HashMap<Key, Tile> gameBoardMap;

	/**
	 * This is the constructor to build the game board
	 * @param width
	 */
	public GameBoard(int width) {
		if(width == 11){
			max = 5;
			min = -5;
				
		}
		else if(width == 9){			
			max = 4;
			min = -4;
		
		}
		else if(width == 7){
			max = 3;
			min = -3;
		}
		else{
			throw new IndexOutOfBoundsException();
		}
		
		
		
		// initialize game board
		gameBoardMap = new HashMap<Key, Tile>();
		// have to initialize all the game board tiles in the hashMap
		//the board is built in 2 parts starting with the top half
		int counterX = 0; // this is the inital x coordinate to start at
		int counterY = min; // the y coordinate starts at the min index value
		
		//loop over adding the tile to the hashmap
		while(counterY <= 0){
			int i = counterX;
			counterX--;
			while(i <= max){
				Tile t = new Tile(tileType.PLAIN);
				Key k = new Key(i, counterY);
				gameBoardMap.put(k, t);
				i++;
			}
			counterY++;
		}
		
		counterX = max-1;
		counterY = 1;
		
		while(counterY <= max){
			int i = counterX;
			counterX--;
			while(i >= min){
				Tile t = new Tile(tileType.PLAIN);
				Key k = new Key(i, counterY);
				gameBoardMap.put(k, t);
				i--;
			}
			counterY++;
		}
		 
		if(width == 11){
			setTileType(2,-2,tileType.WATER);
			setTileType(2,-4,tileType.MOUNTAIN);
			setTileType(-2,4, tileType.MOUNTAIN);
			setTileType(-3,1, tileType.FOREST);
			setTileType(1,-2,tileType.WATER);
			setTileType(3,-3,tileType.MOUNTAIN);
			setTileType(0,-1, tileType.FOREST);
			setTileType(5,0, tileType.FOREST);
			setTileType(-3,2, tileType.FOREST);
			setTileType(-4,1, tileType.FOREST);
			setTileType(-4,4, tileType.FOREST);
			setTileType(1,1, tileType.FOREST);
			setTileType(0,0, tileType.FOREST);
			setTileType(-1,-3, tileType.FOREST);
			setTileType(1,-5, tileType.FOREST);
			setTileType(1,3, tileType.FOREST);
			setTileType(0,4, tileType.FOREST);
			setTileType(4,-1, tileType.FOREST);
			
		}
		else if(width == 9){			
			setTileType(2,-2,tileType.WATER);
			setTileType(2,-4,tileType.MOUNTAIN);
			setTileType(-2,4, tileType.MOUNTAIN);
			setTileType(-3,1, tileType.FOREST);
			setTileType(1,-2,tileType.WATER);
			setTileType(3,-3,tileType.MOUNTAIN);
			setTileType(3,1, tileType.FOREST);
			setTileType(0,4, tileType.MOUNTAIN);
			setTileType(0,0, tileType.MOUNTAIN);
			setTileType(3,0, tileType.FOREST);
			setTileType(-3,0, tileType.MOUNTAIN);
			setTileType(0,1, tileType.FOREST);
			setTileType(1,1, tileType.MOUNTAIN);
		}
		else{
			setTileType(0,0,tileType.MOUNTAIN);
			setTileType(1,1,tileType.MOUNTAIN);
			setTileType(-3,1, tileType.FOREST);
			setTileType(-2,3, tileType.FOREST);
			setTileType(-1,2, tileType.FOREST);
			setTileType(-2,3, tileType.FOREST);
		}
		
	}

	/*
	 *internal method to set the type of a tile 
	 * @param x coordinate of where to set
	 * @param y coordinate of where to set
	 * @param tile type to set it to be
	 */
	private void setTileType(int x, int y, tileType tile){
		Tile t = gameBoardMap.get(new Key(x , y));
		t.setTileType(tile);
	}
	

	
	/**
	 * This adds a robot to the tile at that coordinate
	 * @param x coordinate of the game board tile
	 * @param y coordinate of the game board tile
	 * @param r The pair of the robot location in the team list
	 */
	public void addRobotToTile(int x, int y, Pair<Integer, Integer> r) {
		// gets the tile from the hash map and adds the robot to the tile
		Tile t = gameBoardMap.get(new Key(x, y));
		
		// Update robot's coordinates
		TeamList.getRobot(r).setCoordinates(x, y);
				
		// sets the coordinates of the of the robot in the robot
		t.setRobotCoordinates(x, y, r);
		
		t.placeRobotOnTile(r);
		
	}

	/**
	 * sets the damage to all robots on that tile
	 * @param x coordinate of the game board tile
	 * @param y coordinate of the game board tile
	 * @param damage this is the damage to be dealt to robots on the tile
	 */
	public void sendDamage(int x, int y, int damage) {
		Tile t = gameBoardMap.get(new Key(x, y));
		t.damageRobots(damage, new Pair<Integer, Integer>(1,1) );
	}

	/**
	 * returns the type of the tile
	 * 
	 * @param x coordinate of the tile
	 * @param y coordinate of the tile
	 */
	public tileType typeOfTile(int x, int y) {

		Tile t = gameBoardMap.get(new Key(x, y));
		return t.getTileType();
	}

	/**
	 * This says if there is any robots on this tile
	 * 
	 * @param x The x coordinate of the hex we are checking
	 * @param y the y coordinate of the hex that we are checking
	 * @return true if contains any robots else fasle
	 */
	public boolean containsRobots(int x, int y) {

		Tile t = gameBoardMap.get(new Key(x, y));
		return !(t.isEmpty());
	}
	
	/**
	 * Internal method to calculate the cost of the move
	 * @param a1, starting axis coordinate
	 * @param b1, ending axis coordinate
	 * @return cost of the move on one axis
	 */
	private int getCost (int x, int y){
			Tile t = gameBoardMap.get( new Key(x, y) );
			tileType tile = t.getTileType();
			
			int cost = -1;
			
			
			
			switch(tile){
				case PLAIN:
					cost = 1;
					break;
				case FOREST: 
					cost = 2;
					break;
				case MOUNTAIN:
					cost = 3;
					break;
				case WATER:
					cost = -1;
					break;
			}

		
		return cost;
		
	}



	/**
	 * This gives the potential cost of moving to a coordinate
	 * @param x1 coordinate of where move is starting from
	 * @param y1 coordinate of where move is starting from
	 * @param x2 coordinate of where we are moving to
	 * @param y2 coordinate of where we are moving to
	 * @return the potential cost of the move
	 */
	public int potentialCost(int x1, int y1, int x2, int y2){
		int cost = 0;
		
		if( !checkIfInBounds(x2, y2) ){
			cost -= 1;
		}
		else if( (x1 != x2) || (y1 != y2) ){
			cost = potentialCostHelper(x1, y1, x2, y2, 0);
		}
		
		return cost;
	}
	
	/**
	 * internal method for computing the recursive cost 
	 * @param x1 coordinate of where move is starting from
	 * @param y1 coordinate of where move is starting from
	 * @param x2 coordinate of where we are moving to
	 * @param y2 coordinate of where we are moving to
	 * @param depth how far we have travelled from the start
	 * @return the cost of that move
	 */
	private int potentialCostHelper(int x1, int y1, int x2, int y2, int depth){
		
		int minCost = -1;
		
		// if depth is greater than 3, robot would not be able to move anyways
		if( depth > 3){
			minCost = -1;
		}
		// if out of bounds, unreachable
		else if( !checkIfInBounds(x1, y1) ){
			minCost = -1;
		}
		// else if water, unreachable to go through
		else if( getCost(x1, y1) == -1){
			minCost = -1;
		}
		// if path reached, cost is cost of entering
		else if( ( x1 == x2 ) &&  ( y1 == y2 ) ){
			minCost = getCost(x1, y1);
		}
		// otherwise check all possible path extensions
		else{
			for(int i = 0; i < 6; i++){
				Pair<Integer, Integer> extend = CoordinateMapper.extendPath(x1, y1, i);
				int extendX = extend.left();
				int extendY = extend.right();
			
				int extendCost = potentialCostHelper(extendX, extendY, x2, y2,  depth+1);
			
				// if minCost is currently negative and a path was found
				if( ( minCost < 0 ) && (extendCost > 0) ){
					minCost = extendCost;
				}
				// if minCost is currently positive and a cheaper path was found
				else if( ( minCost > 0 ) && (extendCost > 0) && ( extendCost < minCost ) ){
					minCost = extendCost;
				}			
			}
			
			// if a path was found and not at start, add cost of tile
			if(minCost > 0 && depth > 0){
				minCost += getCost(x1, y1);
			}
		}
		
		return minCost;
	}


	/**
	 * checks if given coordinates are within the bounds of the board
	 * @param x coordinate that we have to check
	 * @param y coordinate that we have to check
	 * @return True if the coordinates are within bounds otherwise false
	 */
	public boolean checkIfInBounds(int x, int y) {
		if (gameBoardMap.get(new Key(x, y)) == null) {
			return false;
		}
		return true;
	}

	/**
	 * returns the list of robots on a specific tile
	 * @param x
	 * @param y
	 * @return a list of robots or null
	 */
	public List<Pair<Integer, Integer>> getRobotList(int x, int y) {
		Tile t = gameBoardMap.get(new Key(x, y));
		return t.getRobotlist();
	}

	/**
	 * moves the robot to a different location
	 * @param x2 the coordinates of where we are going
	 * @param y2 the coordinates of where we are going 
	 * @param r the robot that we are moving
	 * @return 0 if successful, else -1
	 */
	public int moveRobotPosition(int x2, int y2, Pair<Integer, Integer> r) {
		Robot rob = TeamList.getRobot(r);
		Pair<Integer,Integer> robotLocation = rob.getCoordinates();
		int x1 = robotLocation.left();
		int y1 = robotLocation.right();
		if(checkIfInBounds(x2, y2)){
			int cost = potentialCost(x1, y1, x2, y2);
			// if we can move to the new tile
			if( cost > 0){
				Tile current = gameBoardMap.get(new Key(x1,y1));
				Tile dest = gameBoardMap.get(new Key(x2,y2));
				Pair<Integer, Integer> bot = current.removeRobotFromTile(r);
				dest.placeRobotOnTile(bot);
				dest.setRobotCoordinates(x2, y2, bot);
				current.updateDistance(cost, bot);
				
			}
			else{
				return -1;
			}
		}
		else {
			throw new IndexOutOfBoundsException("The given coordinates are out of bounds");
		}
		return 0;
	}
	
	/**
	 * This returns information on the tile that the gui uses
	 * @param x coordinate of tile tile asked for
	 * @param y coordinate of the tile asked for
	 * @return A pair with the left being the number of robots on the tile, right is the team number of dominant type 
	 */
	public Pair<Integer,Integer> tileInfo(int x, int y){
		Tile t = gameBoardMap.get(new Key(x , y));
		Pair<Integer,Integer> p = new Pair<Integer, Integer>(t.numberRobotsOnTile(), t.getDominant());
		return p;
	}
	
	/**
	 * This handles a robot firing at a coordinate
	 * @param x portion of the coordinate
	 * @param y portion of the coordinate
	 * @param robot the robot that shot
	 * @return 0 if fired and hit something
	 */
	public int fire(int x, int y, Pair<Integer,Integer> robot){
		Tile t = gameBoardMap.get(new Key(x , y));
		int damage = t.getfirePower(robot);
		if(!t.isEmpty()){
			int totalDamageDone = t.damageRobots(damage, robot);
			t.updateDamageDealt(robot,totalDamageDone);
		}
		else{
			return -1;
		}
		return 0;
	}

	public static void main(String[] args) {
		
		TeamBuilderLogicLayer t = new TeamBuilderLogicLayer(2);
		t.addRobotToTeam(1, 1);
		t.addRobotToTeam(1, 1);
		t.addRobotToTeam(1, 1);
		t.addRobotToTeam(1, 1);

		t.addRobotToTeam(2, 1);
		t.addRobotToTeam(2, 1);
		t.addRobotToTeam(2, 1);
		t.addRobotToTeam(2, 1);
		TeamList.init(t.startMatch());
		GameBoard g = new GameBoard(9);
		g.addRobotToTile(1, -2, new Pair<Integer, Integer>(1, 1));
		g.addRobotToTile(1, -2, new Pair<Integer, Integer>(1, 2));
		g.addRobotToTile(0, -4, new Pair<Integer, Integer>(1, 3));
		g.addRobotToTile(-1, -2, new Pair<Integer, Integer>(1, 4));
		if (!g.containsRobots(1, -2)) {
			System.out.println("ERROR!");
		}
		if (!g.checkIfInBounds(100, -332)) {
			System.out.println("ERROR!");
		}
		if (!g.checkIfInBounds(-4, -4)) {
			System.out.println("ERROR!");
		}
		if (!g.checkIfInBounds(4, 1)) {
			System.out.println("ERROR!");
		}
		
		GameBoard g1 = new GameBoard(11);
		if (g1.checkIfInBounds(7, 1)) {
			System.out.println("ERROR!");
		}
		
		//GameBoard g2 = new GameBoard(7);

		
		int test0 = g.potentialCost(0, 0, 0, 0);
		int testextend1 = g.potentialCost(0, 0, -1, 1);
		int testextend2 = g.potentialCost(0, 0, 1, 1);
		int testextend3 = g.potentialCost(0, 0, 3, 0);
		int testforest = g.potentialCost(-2, 0, -3, 1);
		int testmountain = g.potentialCost(2, -3, 2, -4);
		int testWater = g.potentialCost(1, -2, 2, -2);
		int testextendpastrange = g.potentialCost(0, 0, 4, -4);
		int testoutofbounds = g.potentialCost(0, 0, 5, 5);
		
		System.out.println(test0);
		System.out.println(testextend1);
		System.out.println(testextend2);
		System.out.println(testextend3);
		System.out.println(testforest);
		System.out.println(testmountain);
		System.out.println(testWater);
		System.out.println(testextendpastrange);
		System.out.println(testoutofbounds);
		
		return;
	}
}
