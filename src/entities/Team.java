package entities;

import java.util.ArrayList;
import java.util.List;

import entities.Robot;

/**
 * This class models a Team entity for use in the RoboSport370 simulator
 */
public class Team {
	
	// Constants for Team numbers
	private static final int MAX_TEAMS = 6;
	private static final int MIN_TEAMS = 1;
	
	// Constants for Robot positions on teams
	private static final int MAX_POSITION = 4;
	private static final int MIN_POSITION = 1;
	
	private final int num;
	private List<Robot> robots;
	//this is the number of robots that are still alive
	private int robotsAlive;
	
	
	/**
	 * Creates a new empty Team with team number num
	 * @param num Team number (between 1 to 6)
	 */
	public Team(int num){
		assert( (num <= MAX_TEAMS ) && (num >= MIN_TEAMS) );
		
		this.robotsAlive = 4;
		this.num = num;
		robots = new ArrayList<Robot>(5);
	}


	/**
	 * @return Team number
	 */
	public int getTeamNum(){
		return num;
	}
	
	/**
	 * @return number of robots alive
	 */
	public int getRobotsAlive(){
		return robotsAlive;
	}
	
	/**
	 * Decrements the number of robots alive by one
	 */
	public void robotDied(){
		this.robotsAlive--;
	}

	/**
	 * Returns the Robot at the i position
	 * @param i Robot to return
	 * @return i-th robot in Team
	 */
	public Robot getRobot(int i){
		assert( (i <= MAX_POSITION) && (i >= MIN_POSITION) );

		return robots.get(i-1);
	}

	/**
	 * @return true if Team is full, false if not
	 */
	public boolean isFull(){
		return robots.size() == MAX_POSITION;
	}
	
	/**
	 * @return true if Team is empty, false if not
	 */
	public boolean isEmpty(){
		return robots.isEmpty();
	}
	
	/**
	 * Adds a Robot to the Team
	 * @param r Robot to add to team
	 * @precond Team is not full
	 */
	public void addRobot(Robot r){
		assert( !this.isFull() );
		
		// Set Robot Team & Member Numbers
		r.setTeam(num);
		r.setMemberNum( robots.size() + 1 );
		
		robots.add(r);
	}
	
	/**
	 * Removes all robots from the team
	 * @postcond Team is empty
	 */
	public void removeRobots(){
		robots.clear();
	}
	
	public String toString(){
		String t = "Team " + this.getTeamNum();
		
		for(Robot r: robots){
			t += "\n" + r.toString();
		}
		
		return t;
	}
	
	/**
	 * Main function for Team containing tests
	 */
	public static void main(String[] args){
		// Create Teams with upper & lower bounds on Team number
		Team testLower = new Team(1);
		Team testUpper = new Team(6);
		
		// Test out of bounds conditions (these dump with assertions)
		// Team testBelow = new Team(-1);
		// Team testAbove = new Team(7);
		
		// Test Team numbers were assigned properly
		assert( testLower.getTeamNum() == 1 );
		assert( testUpper.getTeamNum() == 6 );
		
		// Test isEmpty with empty Teams
		assert( testLower.isEmpty() );
		assert( testLower.isEmpty() );
		
		// Test isFull with empty Teams
		assert( !testLower.isFull() );
		assert( !testUpper.isFull() );
		
		// Attempt to delete when empty (assertion fails)
		// testLower.removeRobot(1);
		
		// Create Robots to add to Teams
		
		// Add 1 Robot to Team
		
		// Get Robot from Team
		
		// Delete from Team
		
		// Fill Teams
		
		// Test isFull()
	
		// Attempt to add to full Team
		
		// Delete all Robots from Team
		
		// Test isFull()
	}

}
