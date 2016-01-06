package teambuilder;

import java.util.List;

import entities.Robot;
import entities.Team;

/**
 * This class contains methods for the TeamManager class to use to operate on teams
 */
public class TeamBuilderLogicLayer {

	private TeamManager teamManager;
	private LibrarianClient librarianClient;
	private int numTeams = 0;
	
	public TeamBuilderLogicLayer(int i){
		assert(i == 2 || i == 3 || i == 6);
		teamManager = new TeamManager(i);
		librarianClient = new LibrarianClient();
		numTeams = i;
	}
	
	
	/**
	 * Access number of teams in system
	 * @return number of teams
	 */
	public int getNumberOfTeams(){
		return this.numTeams;
	}
	
	/**
	 * Access the robot list from the library
	 * @return the robot list from the library
	 */
	public List<String> getRobotList(){
		return librarianClient.requestList();
	}
	
	/**
	 * Access a particular teams data
	 * @param teamNumber
	 * @return the team data specified by the teamNumber
	 * @precond must be valid team number
	 */
	public String getTeamData(int teamNumber){
		
		return teamManager.getTeamData(teamNumber);
		
	}
	
	/**
	 * Add a particular robot to a particular team
	 * @param teamNumber
	 * @param r
	 */
	public void addRobotToTeam(int teamNumber, int serialNumber){
		
	    Robot add = librarianClient.downloadRobot(serialNumber);
	    
		teamManager.addRobotToTeam(teamNumber, add);
	}
	
	/**
	 * Removes all Robots from a team
	 * @param teamNumber Team to remove robots from
	 */
	public void removeRobotsFromTeam(int teamNumber){
		teamManager.removeRobotsFromTeam(teamNumber);
	}
	
	/**
	 * Sends the full list of teams
	 * @return the full list of teams
	 */
	public List<Team>startMatch(){
		return teamManager.sendTeamList();
	}
	
	/**
	 * Checks if Team is full
	 * @param teamNumber
	 * @return true if full, false if not
	 */
	public boolean isFull(int teamNumber){
		return teamManager.isFull(teamNumber);
	}
	
	/**
	 * Checks if Team is empty
	 * @param teamNumber
	 * @return true if empty, false if not
	 */
	public boolean isEmpty(int teamNumber){
		return teamManager.isEmpty(teamNumber);
	}
	
	/**
	 * Main method which contains testing for the class
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TeamBuilderLogicLayer t = new TeamBuilderLogicLayer(2);

		System.out.println( t.getRobotList() );
		
		// Teams should not be full
		assert( !t.isFull(1) );
		assert( !t.isFull(2) );
		
		// Spin up some Robots, a TeamBuilderLogicLayer and add Robots to Team
		t.addRobotToTeam(1, 1);
		t.addRobotToTeam(1, 2);
		t.addRobotToTeam(1, 3);
		t.addRobotToTeam(1, 1);

		
		t.addRobotToTeam(2, 3);
		t.addRobotToTeam(2, 1);
		t.addRobotToTeam(2, 2);
		t.addRobotToTeam(2, 3);

		// Print Team 1
		System.out.println( t.getTeamData(1) );
				
		// Print Team 2
		System.out.println( t.getTeamData(2) );
		
		// test isFull()
		assert( t.isFull(1) );
		assert( t.isFull(2) );
		
		// test removeRobots
		t.removeRobotsFromTeam(1);
		t.removeRobotsFromTeam(2);
		
		// Teams should not be full
		assert( !t.isFull(1) );
		assert( !t.isFull(2) );
		
		// Print Team 1
		System.out.println( t.getTeamData(1) );
				
		// Print Team 2
		System.out.println( t.getTeamData(2) );
	}

}
