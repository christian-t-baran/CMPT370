package model;

import java.util.Iterator;
import java.util.List;

import containers.Pair;
import entities.Robot;
import entities.Team;

/**This is a singleton class that stores the team list, all changes to robots should occur here*/
public class TeamList {
	
	private static TeamList t = null;
	private static List<Team> teams;
	protected TeamList(){
		
	}
	public static TeamList getInstance(){
		if(t == null){
			t = new TeamList();
		}
		return t;
	}
	
	/**
	 *This is used to initialize the storage on the team list created by the teamBuilder 
	 */
	public static void init(List<Team> list){
		teams = list;// this is the team list that we operate on
		
	}
	/**This returns a robot at a specific location
	 *@param the pair the first integer is the team number the second is the robots position
	 */
	public static Robot getRobot(Pair<Integer,Integer> p){
		Robot r = teams.get(p.left()-1).getRobot(p.right());
		return r;
	}
	
	/**check if the list is empty*/
	public boolean isEmpty(){
		return teams.isEmpty();
	}
	
	/**
	 * Kills the robot that the pair is passed too
	 * @param p
	 * @return 0 if the robot was killed
	 */
	public static int KillRobot(Pair<Integer,Integer> p){
		Robot r = getRobot(p);
		r.setCurrentHealth(0);
		teams.get(r.getTeam()-1).robotDied();
		return 0;
		
	}
	
	/**
	 * Decrements the number of robots alive on a team
	 * @param teamNumber team number where robot died
	 */
	public static void robotDied(int teamNumber){
		teams.get(teamNumber-1).robotDied();
	}
	
	/**
	 * 
	 * @param teamNumber of the team you want the count for
	 * @return number of robots still alive
	 */
	public static int getNumberOfRobotsAlive(int teamNumber){
		return teams.get(teamNumber-1).getRobotsAlive();
	}
	
	/**
	 * This returns the number of teams
	 * @return number of teams
	 */
	public static int numberOfTeams(){
		return teams.size();
	}
	
	/**returns an iterator on the teamlist*/
	public static Iterator<Team> iterator(){
		Iterator<Team> iter = teams.iterator();
		return iter;
	}
	
}
