package teambuilder;

import java.util.ArrayList;
import java.util.List;

import entities.Robot;
import entities.Team;

/**
 * This class has the methods for the robot teams
 */
public class TeamManager {
	private List<Team> teams;
	private int numTeams = 0;
	public TeamManager(int i){
		
		assert(i == 2 || i == 3 || i == 6);
		numTeams = i;
		teams = new ArrayList<Team>(numTeams);	
		
		for(int n = 0; n < i; n++){
			teams.add( new Team(n+1) );
		}
	}
	/**
	 * Adds a robot to a team
	 * @param teamNumber the team number of team to add
	 * @param r the robot to add to the given team
	 * @precond the team is not full
	 */
	public void addRobotToTeam(int teamNumber, Robot r){
		assert( !teams.get(teamNumber-1).isFull() );
		// TODO add index checking for teamNumber
		teams.get(teamNumber-1).addRobot(r);
	}
	
	/**
	 * Removes all robots from Team
	 * @param teamNumber number of team to remove robots from
	 */
	public void removeRobotsFromTeam(int teamNumber){
		// TODO add index checking for teamNumber
		
		teams.get(teamNumber-1).removeRobots();
	}
	
	/**
	 * Returns a list of teams
	 * @return the list of teams
	 * @precond each team is full
	 */
	public List<Team> sendTeamList(){
		for(Team t : teams){
			assert( t.isFull() );
		}
		return teams;
	}
	
	/**
	 * Returns team data
	 * @param teamNumber Team to get info from
	 * @return String representing team info number
	 */
	public String getTeamData(int teamNumber){
		
		return teams.get(teamNumber-1).toString();
	}
	
	/**
	 * To check if a particular team is full
	 * @param teamNumber Team to get info from
	 * @return true if full, false if not
	 */
	public boolean isFull(int teamNumber){
		return teams.get(teamNumber-1).isFull();
	}
	
	/**
	 * To check if a particular team is empty
	 * @param teamNumber Team to get info from
	 * @return true if empty, false if not
	 */
	public boolean isEmpty(int teamNumber){
		return teams.get(teamNumber-1).isEmpty();
	}
	
	/**
	 * Main method which contains testing for class
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TeamManager twoTeams = new TeamManager(2);
		TeamManager threeTeams = new TeamManager(3);
		TeamManager sixTeams = new TeamManager(6);
		
		// attempt to delete from empty teams
		twoTeams.removeRobotsFromTeam(1);
		threeTeams.removeRobotsFromTeam(1);
		sixTeams.removeRobotsFromTeam(1);
		
		// attempt to export lists from empty teams
		//twoTeams.sendTeamList();
		
		Robot r1 = new Robot(1,1,3,1,null);
		Robot r2 = new Robot(1,1,3,2,null);
		Robot r3 = new Robot(1,1,3,3,null);
		Robot r4 = new Robot(1,1,3,4,null);
		Robot r5 = new Robot(1,1,3,5,null);
		
		// add 1 robot to each team
		twoTeams.addRobotToTeam(1, r1);
		twoTeams.addRobotToTeam(2, r1);
		threeTeams.addRobotToTeam(1, r1);
		threeTeams.addRobotToTeam(2, r1);
		threeTeams.addRobotToTeam(3, r1);
		sixTeams.addRobotToTeam(1, r1);
		sixTeams.addRobotToTeam(2, r1);
		sixTeams.addRobotToTeam(3, r1);
		sixTeams.addRobotToTeam(4, r1);
		sixTeams.addRobotToTeam(5, r1);
		sixTeams.addRobotToTeam(6, r1);

		// attempt to export lists from partially filled teams
		// threeTeams.sendTeamList();
		 
		// fill teams
		twoTeams.addRobotToTeam(1, r2);
		twoTeams.addRobotToTeam(1, r3);
		twoTeams.addRobotToTeam(1, r4);
		
		twoTeams.addRobotToTeam(2, r2);
		twoTeams.addRobotToTeam(2, r3);
		twoTeams.addRobotToTeam(2, r4);
		
		threeTeams.addRobotToTeam(1, r2);
		threeTeams.addRobotToTeam(1, r3);
		threeTeams.addRobotToTeam(1, r4);
		
		threeTeams.addRobotToTeam(2, r2);
		threeTeams.addRobotToTeam(2, r3);
		threeTeams.addRobotToTeam(2, r4);

		threeTeams.addRobotToTeam(3, r2);
		threeTeams.addRobotToTeam(3, r3);
		threeTeams.addRobotToTeam(3, r4);
		
		sixTeams.addRobotToTeam(1, r2);
		sixTeams.addRobotToTeam(1, r3);
		sixTeams.addRobotToTeam(1, r4);
		
		sixTeams.addRobotToTeam(2, r2);
		sixTeams.addRobotToTeam(2, r3);
		sixTeams.addRobotToTeam(2, r4);


		sixTeams.addRobotToTeam(3, r2);
		sixTeams.addRobotToTeam(3, r3);
		sixTeams.addRobotToTeam(3, r4);
		

		sixTeams.addRobotToTeam(4, r2);
		sixTeams.addRobotToTeam(4, r3);
		sixTeams.addRobotToTeam(4, r4);
		
		sixTeams.addRobotToTeam(5, r2);
		sixTeams.addRobotToTeam(5, r3);
		sixTeams.addRobotToTeam(5, r4);
		
		sixTeams.addRobotToTeam(6, r2);
		sixTeams.addRobotToTeam(6, r3);
		sixTeams.addRobotToTeam(6, r4);

		// fully remove teams
		twoTeams.removeRobotsFromTeam(1);
		twoTeams.removeRobotsFromTeam(2);
		
		threeTeams.removeRobotsFromTeam(1);
		threeTeams.removeRobotsFromTeam(2);
		threeTeams.removeRobotsFromTeam(3);
		
		sixTeams.removeRobotsFromTeam(1);
		sixTeams.removeRobotsFromTeam(2);
		sixTeams.removeRobotsFromTeam(3);
		sixTeams.removeRobotsFromTeam(4);
		sixTeams.removeRobotsFromTeam(5);
		sixTeams.removeRobotsFromTeam(6);

		

		
		// fill teams
		twoTeams.addRobotToTeam(1, r1);
		twoTeams.addRobotToTeam(1, r2);
		twoTeams.addRobotToTeam(1, r3);
		twoTeams.addRobotToTeam(1, r4);
		
		twoTeams.addRobotToTeam(2, r1);
		twoTeams.addRobotToTeam(2, r2);
		twoTeams.addRobotToTeam(2, r3);
		twoTeams.addRobotToTeam(2, r4);
		
		threeTeams.addRobotToTeam(1, r1);
		threeTeams.addRobotToTeam(1, r2);
		threeTeams.addRobotToTeam(1, r3);
		threeTeams.addRobotToTeam(1, r4);
		
		threeTeams.addRobotToTeam(2, r1);
		threeTeams.addRobotToTeam(2, r2);
		threeTeams.addRobotToTeam(2, r3);
		threeTeams.addRobotToTeam(2, r4);

		threeTeams.addRobotToTeam(3, r1);
		threeTeams.addRobotToTeam(3, r2);
		threeTeams.addRobotToTeam(3, r3);
		threeTeams.addRobotToTeam(3, r4);
		
		sixTeams.addRobotToTeam(1, r1);
		sixTeams.addRobotToTeam(1, r2);
		sixTeams.addRobotToTeam(1, r3);
		sixTeams.addRobotToTeam(1, r4);
		
		sixTeams.addRobotToTeam(2, r3);
		sixTeams.addRobotToTeam(2, r2);
		sixTeams.addRobotToTeam(2, r3);
		sixTeams.addRobotToTeam(2, r4);

		sixTeams.addRobotToTeam(3, r1);
		sixTeams.addRobotToTeam(3, r2);
		sixTeams.addRobotToTeam(3, r3);
		sixTeams.addRobotToTeam(3, r4);
		
		sixTeams.addRobotToTeam(4, r1);
		sixTeams.addRobotToTeam(4, r2);
		sixTeams.addRobotToTeam(4, r3);
		sixTeams.addRobotToTeam(4, r4);
		
		sixTeams.addRobotToTeam(5, r1);
		sixTeams.addRobotToTeam(5, r2);
		sixTeams.addRobotToTeam(5, r3);
		sixTeams.addRobotToTeam(5, r4);
		
		sixTeams.addRobotToTeam(6, r1);
		sixTeams.addRobotToTeam(6, r2);
		sixTeams.addRobotToTeam(6, r3);
		sixTeams.addRobotToTeam(6, r4);
		
		// export teams
		
		twoTeams.sendTeamList();
		threeTeams.sendTeamList();
		sixTeams.sendTeamList();

		
		

		
	}

}
