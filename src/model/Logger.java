package model;
import java.util.*;
import entities.RobotUpdate;
import containers.Pair;

/**A logger to keep track of Robot and Team Statistics:
 * 
 * @author Atridad Lahiji (11138815) Group B2
 */
public class Logger {
	
	/**Maximum number of teams:*/
	private int MAX_TEAMS = 6;
	
	/**Maximum number of robots on a given team:*/
	private static final int MAX_ROBOTS = 4;
	
	/**Number of attributes to keep log of:*/
	private static final int MAX_ATTRIB = 11;
	
	/**The robot stats tracking:*/
	private static ArrayList<ArrayList<ArrayList<Integer>>> robotStatsTeam =  new ArrayList<ArrayList<ArrayList<Integer>>>();
	
	private static Logger instance = null; 
	
	/**This method is a constructor for the Logger:
	 * @param numTeams an integer representing the number of teams in the simulation
	 */
	private Logger(int numTeams){
		this.MAX_TEAMS = numTeams;
		this.clearStats();
	}
	
	public static Logger getInstance(int numTeams){
		if(null == instance){
			instance = new Logger(numTeams);
		}
		return instance;
	}
	
	/**
	 * This method will set the serial value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @param inSerial the value used to set the robot serial stat
	 */
	public void setSerial(Pair<Integer, Integer> robotIdentifier, int inSerial){
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		temp.set(0,inSerial);
	}
	
	
	/**This method will increment the matches played value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @param delMatchesPlayed the value used to increment the robot MatchesPlayed stat
	 */
	public void incrementMatchesPlayed(Pair<Integer, Integer> robotIdentifier, int delMatchesPlayed){
				
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		temp.set(1,delMatchesPlayed);
	}
	
	/**This method will increment the wins value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @param delWins the value used to increment the robot wins stats
	 */
	public void incrementWins(Pair<Integer, Integer> robotIdentifier, int delWins){
			
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		temp.set(2,delWins);
    }
		
	/**This method will increment the losses value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @param delLosses the value used to increment the robot losses stat
	 */
	public void incrementLosses(Pair<Integer, Integer> robotIdentifier, int delLosses){
			
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		temp.set(3,delLosses);
	}
		

	/**This method will increment the executions value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @param delExecutions the value used to increment the robot executions stat
	 */
	public void incrementExecutions(Pair<Integer, Integer> robotIdentifier, int delExecutions){
			
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		temp.set(4,delExecutions);
	}	
	
	/**This method will increment the lived value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @param delLived the value used to increment the robot lived stat
	 */
	public void incrementLived(Pair<Integer, Integer> robotIdentifier, int delLived){
			
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		temp.set(5,delLived); 
	}
	
	/**This method will increment the died value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @param deldied the value used to increment the robot died stat
	 */
	public void incrementDied(Pair<Integer, Integer> robotIdentifier, int delDied){
			
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		temp.set(6,delDied);
	}
		
	/**This method will increment the absorbed value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @param delAbsorbed the value used to increment the robot absorbed stat
	 */
	public void incrementAbsorbed(Pair<Integer, Integer> robotIdentifier, int delAbsorbed){
			
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		temp.set(7,delAbsorbed);
	}
		

	/**This method will set the damages value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @param delDamageDealt the value used to increment the robot damage stat
	 */
	public void incrementDamageDealt(Pair<Integer, Integer> robotIdentifier, int delDamageDealt){
			
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		temp.set(8,delDamageDealt);
	}
	
	/**This method will increment the kills value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @param delKills the value used to increment the robot kills stat
	 */
	public void incrementKills(Pair<Integer, Integer> robotIdentifier, int delKills){
			
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		temp.set(9,delKills);
	}
	
	/**This method will increment the distance value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @param delDistance the value used to increment the robot distance stat
	 */
	public void incrementDistance(Pair<Integer, Integer> robotIdentifier, int delDistance){
			
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		temp.set(10,delDistance);
	}
	
	/**This method will return the distance value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @return an integer value for the serial stat
	 */
	public int getSerial(Pair<Integer, Integer> robotIdentifier){
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		return (temp.get(0));
	}
	
	/**This method will return the distance value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @return an integer value for the MatchesPlayed stat
	 */
	public int getMatchesPlayed(Pair<Integer, Integer> robotIdentifier){
		
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		return (temp.get(1));
	}
	
	/**This method will return the distance value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @return an integer value for the wins stat
	 */
	public int getWins(Pair<Integer, Integer> robotIdentifier){
			
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		return (temp.get(2));
	}
	
	/**This method will return the distance value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @return an integer value for the losses stat
	 */
	public int getLosses(Pair<Integer, Integer> robotIdentifier){
			
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		return (temp.get(3));
	}
	
	/**This method will return the distance value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @return an integer value for the executions stat
	 */
	public int getExecutions(Pair<Integer, Integer> robotIdentifier){
			
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		return (temp.get(4));
	}	
	
	/**This method will return the distance value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @return an integer value for the lived stat
	 */
	public int getLived(Pair<Integer, Integer> robotIdentifier){
			
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		return (temp.get(5));
	}
	
	/**This method will return the distance value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @return an integer value for the died stat
	 */
	public int getDied(Pair<Integer, Integer> robotIdentifier){
			
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		return (temp.get(6));
	}
	
	/**This method will return the distance value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @return an integer value for the absorbed stat
	 */
	public int getAbsorbed(Pair<Integer, Integer> robotIdentifier){
			
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		return (temp.get(7));
	}
		
	/**This method will return the distance value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @return an integer value for the damage dealt stat
	 */
	public int getDamageDealt(Pair<Integer, Integer> robotIdentifier){
			
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		return (temp.get(8));
	}
	
	/**This method will return the distance value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @return an integer value for the kills stat
	 */
	public int getKills(Pair<Integer, Integer> robotIdentifier){
			
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		return (temp.get(9));
	}
	
	/**This method will return the distance value of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @return an integer value for the distance stat
	 */
	public int getDistance(Pair<Integer, Integer> robotIdentifier){
			
		ArrayList<Integer> temp = (robotStatsTeam.get(robotIdentifier.left()-1)).get(robotIdentifier.right()-1);
		return (temp.get(10));
	}
	
	/**This method will construct and return a RobotUpdate object of the robot specified by both the team and robot IDs:
	 * @param robotIdentifier the Pair of Integers that represents <TeamID, RobotID>
	 * @return an integer value for the serial stat
	 */
	public RobotUpdate getRobotUpdate(Pair<Integer, Integer> robotIdentifier){
		int serial = TeamList.getRobot( robotIdentifier ).getSerial();
		
		RobotUpdate update = new RobotUpdate(serial, robotIdentifier.left() );
		update.setWins(this.getWins(robotIdentifier));
		update.setLosses(this.getLosses(robotIdentifier));
		update.setExecutions(this.getExecutions(robotIdentifier));
		update.setLived(this.getLived(robotIdentifier));
		update.setDied(this.getDied(robotIdentifier));
		update.setAbsorbed(this.getAbsorbed(robotIdentifier));
		update.setDamageDealt(this.getDamageDealt(robotIdentifier));
		update.setKills(this.getKills(robotIdentifier));
		update.setDistance(this.getDistance(robotIdentifier));
		
		return update;
	}
		
	
	public void clearStats(){
		
		robotStatsTeam.clear();
		
		/**Initializing the 3D ArrayList for Robot Stats to 0s:*/
	    for (int i = 0; i < MAX_TEAMS; i++){
	    	/**Creating 2D ArrayLists to be added as fields of the 3D ArrayList:*/
	    	ArrayList<ArrayList<Integer>> robotStatsRobot = new ArrayList<ArrayList<Integer>>();
	        for (int j = 0; j < MAX_ROBOTS; j++){
	        	/**Creating 1D ArrayLists to be added as fields of the 2D ArrayList:*/ 
	        	ArrayList<Integer> robotStats = new ArrayList<Integer>();
	            for (int k = 0; k < MAX_ATTRIB; k++){
	                robotStats.add(0);
	            }
	            robotStatsRobot.add(robotStats);
	        }
	        robotStatsTeam.add(robotStatsRobot);
	    }
	}
	
	/**The main method used for testing:
	 * @param args
	 */
	public static void main(String[] args){

	}
}
