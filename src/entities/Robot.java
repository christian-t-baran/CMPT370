package entities;

import java.util.Map;

import containers.Pair;
import exceptions.InterpreterExecutionException;
import forth.ForthObject;
import forth.ForthValue;

/**
 * This class models a Robot for use in the RoboSport370 simulator
 */
public class Robot {

	// Constants for Robot values
	private static final int COMBINED_FIREPOWER_HEALTH_MAX = 5;
	private static final int COMBINED_FIREPOWER_HEALTH_MIN = 2;
	private static final int COMBINED_FIREPOWER_MOVES = 4;
	private static final int MAXIMUM_FIREPOWER = 4;
	private static final int MINIMUM_FIREPOWER = 1;
	private static final int MAXIMUM_HEALTH = 4;
	private static final int MINIMUM_HEALTH = 1;
	
	// Constants for Team numbers
	private static final int MAX_TEAMS = 6;
	private static final int MIN_TEAMS = 1;
	
	// Constants for Robot positions on teams
	private static final int MAX_POSITION = 4;
	private static final int MIN_POSITION = 1;
	
	// Robot permanent data
	private final int health;
	private final int firepower;
	private final int moves;
	private final int serial;
	private final Map<String, ForthObject> instructions;
	
	// Robot team data
	private int team;
	private int memberNum;
	
	// Robot match data
	private boolean firstTurn;
	private boolean hasShot;
	private int healthCur;
	private int movesLeft;
	private int posX;
	private int posY;
	private final RobotMailbox[] mailbox;
	
	// Robot metadata
	private String name;
	
	/**
	 * Temporary constructor for Robot (final will use a JSON array)
	 * @param health	Robot's max health
	 * @param firepower	Robot's firepower
	 * @param moves	Robot's max moves
	 * @param serial	Robot's serial number
	 * @param instructions	Robot's instructions
	 */
	public Robot(int health, int firepower, int moves, int serial, Map<String, ForthObject> instructions){
				
		// ensure health is in valid ranges
		assert( (firepower + health) >= COMBINED_FIREPOWER_HEALTH_MIN);
		assert( (firepower + health) <= COMBINED_FIREPOWER_HEALTH_MAX);
		assert(health <= MAXIMUM_HEALTH);
		assert(health >= MINIMUM_HEALTH);
		this.health = health;
		
		// ensure firepower in valid range
		assert(firepower <= MAXIMUM_FIREPOWER);
		assert(firepower >= MINIMUM_FIREPOWER);
		assert( COMBINED_FIREPOWER_MOVES == (firepower + moves) );
		this.firepower = firepower;

		// moves fall in valid range by default;
		this.moves = moves;
		
		this.serial = serial;
		this.instructions = instructions;
		
		this.mailbox = new RobotMailbox[4];
		
		// Initialize match values
		this.healthCur = this.health;
		this.movesLeft = this.moves;
		this.firstTurn = true;
		
		// Create mailboxes
		for(int i = 0; i < 4; i++){
			this.mailbox[i] = new RobotMailbox();
		}
	}
	
	// Permanent data getters
	
	/**
	 * @return Maximum health of Robot
	 */
	public int getMaxHealth(){
		return health;
	}

	/**
	 * @return Max moves of Robot
	 */
	public int getMaxMoves(){
		return moves;
	}
	
	/**
	 * @return Firepower of Robot
	 */
	public int getFirepower(){
		return firepower;
	}
	
	/**
	 * @return Map<String, ForthObject> of Robot's instructions
	 */
	public Map<String, ForthObject> getInstructions(){
		return instructions;
	}

	/**
	 * @return Robot's serial number
	 */
	public int getSerial(){
		return serial;
	}

	// Team data getters/setters
	
	/**
	 * Sets Robot's team number to i
	 * @param i new Team number
	 */
	public void setTeam(int i){
		assert( i <= MAX_TEAMS );
		assert( i >= MIN_TEAMS );
		
		team = i;
	}
	
	/**
	 * 
	 * @return Team number robot is on
	 */
	public int getTeam(){
		return team;
	}

	/**
	 * Sets robot's position on team to i
	 * @param i sets
	 */
	public void setMemberNum(int i){
		assert( ( i >= MIN_POSITION ) && (i <= MAX_POSITION) );
		memberNum = i;
	}
	
	/**
	 * @return member number of Robot on team
	 */
	public int getMemberNum(){
		return memberNum;
	}
	
	public Pair<Integer, Integer> getTeamMemberNum(){
		return new Pair<Integer, Integer>(team, memberNum);
	}
	
	// Match data getters/setters

	/**
	 * @return Current health
	 */
	public int getCurrentHealth(){
		return healthCur;
	}

	/**
	 * Sets health remaining to i
	 * @param i new value for health
	 */
	public void setCurrentHealth(int i){
		assert(i <= healthCur);
		
		healthCur = i;
	}

	/**
	 * @return Number of moves remaining for Robot
	 */
	public int getMovesLeft(){
		return movesLeft;
	}

	/**
	 * Sets number of moves remaining in turn for i
	 * @param i new number of moves
	 */
	public void decreaseMoves(int i){
		assert(i <= movesLeft);
		
		movesLeft -= i;
	}
	
	/**
	 * Checks if it is Robot's first turn
	 * @return true if first turn, false if not
	 */
	public boolean isFirstTurn(){
		return firstTurn;
	}
	
	/**
	 * Checks if a Robot has shot on this turn yet
	 * @return true if it has, false if it hasn't
	 */
	public boolean hasShot(){
		return hasShot;
	}

	/**
	 * Sets flag that Robot has fired to true
	 */
	public void shoot(){
		hasShot = true;
	}

	/**
	 * Returns x and y coordinates of Robot on game board
	 * @return Pair of x coordinate and y coordinate
	 */
	public Pair<Integer, Integer> getCoordinates(){
		return new Pair<Integer, Integer>(posX, posY);
	}
	
	/**
	 * Sets coordinates of Robot to (posX, posY)
	 * @param posX
	 * @param posY
	 */
	public void setCoordinates(int posX, int posY){
		this.posX = posX;
		this.posY = posY;
	}

	
	/**
	 * Gets the top message from Robot in i position on team
	 * @param i Position of Robot to get message from
	 * @return <true, message> if message existed, <false, null> if message did not
	 * @throws InterpreterExecutionException 
	 */
	public Pair<Boolean, ForthValue> getMessage(int i) 
			throws InterpreterExecutionException{
		return this.mailbox[i-1].getMessage();
	}
	
	/**
	 * Pushes message v onto 
	 * @param v message to receive
	 * @param i Position of Robot to receive message from
	 * @return true if message successfully received, false if not
	 * @throws InterpreterExecutionException 
	 */
	public boolean recvMessage(ForthValue v, int i) throws InterpreterExecutionException{
		boolean successful = false;
		
		// if robot's dead, can't receive
		if( healthCur <= 0){
			successful = false;
		}
		// otherwise attempt to send
		else{
			successful = this.mailbox[i-1].pushMessage(v);
		}
		
		return successful;
	}
	
	/**
	 * Checks if a message exists from Team Member i
	 * @param i
	 * @return true if message exists, false if not
	 */
	public boolean messageExists(int i) {
		return !( this.mailbox[i-1].isEmpty() );
	}
	
	// Metadata getters/setters

	/**
	 * @return Name of Robot
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Sets Name of Robot to s
	 * @param s
	 */
	public void setName(String s){
		name = s;
	}
	
	// Initialization methods
	
	/**
	 * Resets the Robot's moves to its maximum at the beginning of a turn
	 * @postcond Robot's moves left are at maximum
	 */
	public void startTurn(){
		movesLeft = moves;
		hasShot = false;
		
		if( isFirstTurn() ){
			firstTurn = false;
		}
	}
	
	// Display method
	
	public String toString(){
		return ( this.getSerial() + " - " + this.getName() );
	}
		
	
	// Equality methods
	
	/**
	 * Tests equality between two robots (if serials are identical)
	 * @param r Robot to check equality against
	 * @return true if equal, false if not
	 */
	public boolean equals(Object r){
		boolean equals = false;
		
		if( r instanceof Robot){
			Robot rob = ( Robot ) r;
			equals = this.getSerial() == rob.getSerial();
		}
		
		return equals;
	}
	
	/**
	 * Main function containing tests for Robot
	 * @param args
	 */
	public static void main(String[] args){
		// Create Robot with valid parameters
		
		// Crate Robot with invalid parameters (1 out of range at a time)
		
		// Test getters/setters on valid robot
		
		// Test initialize() & startTurn()
	}

}
