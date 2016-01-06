package model;

import entities.Robot;
import entities.Team;
import teambuilder.TeamBuilderLogicLayer;

import java.util.*;



/**sets the sequence that the robots get there turns*/
public class Sequencer {
	public static final int ROBOTS_PER_TEAM = 4;
	//The Sequencer array that holds the oder that robots get turns
	ArrayList<Robot> turnSequence;
	int turn; // this is the index of the robot whos turn it is next
	int initTurn; // this is the number of robots in the list
	private boolean randomized; 
	
	TeamList l = TeamList.getInstance();
	/**Constructor for the sequence class*/
	public Sequencer(){
		turnSequence = new ArrayList<Robot>();
		turn=0;
		randomized = false;
	}
	
	/**This method takes in an arraylist and  randomizes the arraylist
	 * @param ar an array of robots
	 * @precondition The arraylist is not empty
	 * @postcondition The arraylist is randomized at the end
	 * */
	private static void randomize(ArrayList<Robot> ar){
		assert( !ar.isEmpty() );
		Random r = new Random();
		for(int i = 0; i < ar.size(); i++){
			int randomPosition = r.nextInt(ar.size());
			Robot temp = ar.get(i);
			ar.set(i, ar.get(randomPosition));
			ar.set(randomPosition, temp); 
		}
		return;
	}
	/**this function takes in the team list and places the robots in the
	 *sequence array
	 */
	private void setTeamList() {
		//  check if the team list is empty assert an error
		assert( !l.isEmpty() );
		//loop over the list and take the robots out of each team and place 
		// them in the sequence list
		Iterator<Team> iter = TeamList.iterator();
		while(iter.hasNext()){
			Team currentTeam = iter.next();
			
			if(!currentTeam.isFull()){
//				break; // uncomment this line to run tests!
				throw new InputMismatchException("To few robots on team!");
			}
			for(int i = 1; i <= ROBOTS_PER_TEAM; i++){
				Robot r = currentTeam.getRobot(i);
				turnSequence.add(r);
			}
		}
			
		return;
	}
	
	/**Sets the sequence that robots get there turn*/
	public void beginSequence(){
		//make the list of robots
		setTeamList();
		initTurn = turnSequence.size();
		return;
	}
	
	/**
	 * 
	 */
	public void randomizeSequence(){
		//randomize this list to set the order that robots get there turn
		randomize(turnSequence);
		randomized = true;
		return;
	}
	
	/**
	 * 
	 * @return number of robots in the turns array
	 */
	public int numberRobots(){
		return turnSequence.size();
	}
	
	/**
	 * 
	 * @param robot index to get
	 * @return the robot at that location
	 */
	public Robot getNextTurnInit(int robot){
		if(randomized == false){
			return turnSequence.get(robot);
		}
		return null;
	}
	
	/**This return the next robot who's turn it is to go
	 * @precondition: there exists a robot to go next
	 * @postcondition robot is selected to go next */
	public Robot getNextTurn(){
		if(turn >= turnSequence.size()){
			throw new IndexOutOfBoundsException();
		}
		Robot r;
		do{
		r = turnSequence.get(turn);
		turn++;
		}while( r.getCurrentHealth()<= 0 && (turn < turnSequence.size() ));
		//if we have reached the end of the array we loop back to the start
		if(turn >= turnSequence.size()){
			turn = 0;
		}
		return r;
	}
	
	/**Internal main method for testing */
	public static void main(String[] args){
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
		Sequencer s = new Sequencer();
		s.beginSequence();
		
		
		

	}
}
