package entities;

/**
 * This class models Update information for a Robot
 */
public class RobotUpdate {
	
	private boolean[] teamsExamined = new boolean[6];
	
	private final int serial;
	private final int team;
	
	// Update metadata
	private int wins;
	private int losses;
	private int executions;
	private int lived;
	private int died;
	private int damageAbsorbed;
	private int damageDealt;
	private int kills;
	private int distance;
	
	/**
	 * Constructs a RobotUpdate value for Robot with serial r
	 * @param serial
	 * @param teamNumber
	 */
	public RobotUpdate(int serial, int team){
		this.serial = serial;
		this.team = team;
		
		// Set metadata values to 0
		wins = 0;
		losses = 0;
		executions = 0;
		lived = 0;
		died = 0;
		damageAbsorbed = 0;
		damageDealt = 0;
		kills = 0;
		distance = 0;
		
		for(int i = 0; i < 6; i++){
			teamsExamined[i] = false;
		}
	}
	
	/**
	 * Combines another RobotUpdate into this one
	 * @param combine
	 */
	public void combineUpdate(RobotUpdate combine){
		this.executions += 1;
		this.lived += combine.getLived();
		this.died += combine.getDied();
		this.damageAbsorbed += combine.getAbsorbed();
		this.damageDealt += combine.getDamageDealt();
		this.kills += combine.getKills();
		this.distance = combine.getDistance();
		
		if( !teamsExamined[ combine.getTeam() - 1] ){
			this.wins += combine.getWins();
			this.losses += combine.getLosses();
			
			teamsExamined[ combine.getTeam() - 1] = true;
		}
	}
	
	/**
	 * @return Serial of robot update is for
	 */
	public int getSerial(){
		return serial;
	}
	
	/**
	 * @return team number of associated robot
	 */
	public int getTeam(){
		return team;
	}
	
	/**
	 * @return Number of matches the Robot has won
	 */
	public int getWins(){
		return wins;
	}
	
	/**
	 * Sets the number of matches the Robot has won to i
	 * @param i
	 */
	public void setWins(int i){
		assert(i >= 0);
		
		wins = i;
	}
	
	/**
	 * @return Number of matches the Robot has lost
	 */
	public int getLosses(){
		return losses;
	}

	/**
	 * Sets the number of matches the Robot has lost to i
	 * @param i
	 */
	public void setLosses(int i){
		assert(i >= 0);
		
		losses = i;
	}
	
	/**
	 * @return Total times Robot script has been executed
	 */
	public int getExecutions(){
		return executions;
	}

	/**
	 * Sets the number of times the Robot script has been executed to i
	 * @param i
	 */
	public void setExecutions(int i){
		assert(i >= 0);

		executions = i;
	}
	
	/**
	 * @return Total times robot has survived a match
	 */
	public int getLived(){
		return lived;
	}
	
	/**
	 * Sets Robot's total matches survived to i
	 * @param i
	 */
	public void setLived(int i){
		assert(i >= 0);
		
		lived = i;
	}

	/**
	 * @return Total times Robot has died
	 */
	public int getDied(){
		return died;
	}

	/**
	 * Sets Robot's total deaths to i
	 * @param i
	 */
	public void setDied(int i){
		assert(i >= 0);
		
		died = i;
	}
	
	/**
	 * @return Total number of kills by Robot
	 */
	public int getKills(){
		return kills;
	}
	
	/**
	 * Sets Robot's total kills to i
	 * @param i
	 */
	public void setKills(int i){
		assert(i >= 0);
		
		kills = i;
	}
	
	/**
	 * @return Total damage absorbed by Robot
	 */
	public int getAbsorbed(){
		return damageAbsorbed;
	}
	
	/**
	 * Sets Robot's total absorbed damage to i
	 * @param i
	 */
	public void setAbsorbed(int i){
		assert(i >= 0);
		
		damageAbsorbed = i;
	}
	
	/**
	 * @return Total damage dealt by Robot
	 */
	public int getDamageDealt(){
		return damageDealt;
	}
	
	/**
	 * Sets total damage dealt by Robot to i
	 * @param i
	 */
	public void setDamageDealt(int i){
		assert(i >= 0);
		
		damageDealt = i;
	}
	
	/**
	 * @return Total distance traveled by Robot
	 */
	public int getDistance(){
		return distance;
	}
	
	/**
	 * Sets total distance traveled by Robot to i
	 * @param i
	 */
	public void setDistance(int i){
		assert(i >= 0);
		
		distance = i;
	}
	
	/**
	 * Tests equality between two robotupdates (if serials are identical)
	 * @param r RobotUpdate to check equality against
	 * @return true if equal, false if not
	 */
	public boolean equals(Object r){
		boolean equals = false;
		
		if( r instanceof RobotUpdate){
			RobotUpdate rob = ( RobotUpdate ) r;
			equals = this.getSerial() == rob.getSerial();
		}
		
		return equals;
	}
	public static void main(String[] args){
		
	}
	
}
