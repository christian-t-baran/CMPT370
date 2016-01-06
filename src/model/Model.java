package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import board.GameBoard;
import board.Tile.tileType;
import containers.Pair;
import entities.Robot;
import entities.RobotUpdate;
import entities.Team;


//TODO
//add wrapper to the tileInfo from the game board
//add wrapper to the get the tile type from the board
/**
 * This class is the Model front-end that the Controller should talk to
 */
public class Model {
	private static final int TURN_LIMIT = 2000;
	
	// game state variables
	private int curTurn = 0;
	private boolean wasDraw = false;

	// Associated game entities
	//probably need the logger here

	private Logger log;
	private final Sequencer sequencer;
	private final Interpreter interpreter;
	private final Console console;
	private final GameBoard board;
	private final UpdateWriter updateWriter;
	private final int numTeams;
	private int teamThatTurnItis;
	private Robot curRobot;
	private final int boardSize;
	// mode?
	
	/**
	 * @param x coordinate of the tile that want the type of
	 * @param y coordinate of the tile that want the type of
	 * @return the type of given tile
	 */
	public tileType getTileType(int x, int y){
		return board.typeOfTile(x, y);
	}

	/**
	 * this initializes the model
	 * @param teams the list of teams
	 * @param boardSize is the size pf the game boards longest row
	 */
	public Model(List<Team> teams, int boardSize){ 
		TeamList.init( teams );
		this.boardSize = boardSize;
		numTeams = TeamList.numberOfTeams();
		log = Logger.getInstance(numTeams);
		board = new GameBoard(boardSize);
		console = new Console();
		sequencer = new Sequencer();
		updateWriter = new UpdateWriter();
		sequencer.beginSequence();
		interpreter = new Interpreter(console, numTeams, board);
	}
	
	/**
	 * runs the robot initialization turns
	 */
	public void begin(){
		//Team list has to be initialized!!!!
		if(boardSize == 7){
			if(numTeams == 2){
				int i = 1;
					while(i < 5){
						board.addRobotToTile(-1, 3, new Pair<Integer,Integer>(1,i));
						i++;
					}
					i = 1;
					while(i < 5){
						board.addRobotToTile(1, -3, new Pair<Integer,Integer>(2,i));
						i++;
					}
			}
			else if (numTeams == 3){
				int i = 1;
				while(i < 5){
					board.addRobotToTile(-1, 3, new Pair<Integer,Integer>(1,i));
					i++;
				}
				i = 1;
				while(i < 5){
					board.addRobotToTile(-2, -1, new Pair<Integer,Integer>(2,i));
					i++;
				}
				i = 1;
				while(i < 5){
					board.addRobotToTile(3,-2, new Pair<Integer,Integer>(3,i));
					i++;
				}
			}
			else if ( numTeams == 6){
				int i = 1;
				while(i < 5){
					board.addRobotToTile(-1, 3, new Pair<Integer,Integer>(1,i));
					i++;
				}
				i = 1;
				while(i < 5){
					board.addRobotToTile(-3, 2, new Pair<Integer,Integer>(2,i));
					i++;
				}
				i = 1;
				while(i < 5){
					board.addRobotToTile(-2,-1, new Pair<Integer,Integer>(3,i));
					i++;
				}
				while(i < 5){
					board.addRobotToTile(1, -3, new Pair<Integer,Integer>(4,i));
					i++;
				}
				i = 1;
				while(i < 5){
					board.addRobotToTile(3, -2, new Pair<Integer,Integer>(5,i));
					i++;
				}
				i = 1;
				while(i < 5){
					board.addRobotToTile(2,1, new Pair<Integer,Integer>(6,i));
					i++;
				}
			}
			else{
				throw new IndexOutOfBoundsException();
			}
		}
		else if(boardSize == 9){
			if(numTeams == 2){
				int i = 1;
					while(i < 5){
						board.addRobotToTile(-2, 4, new Pair<Integer,Integer>(1,i));
						i++;
					}
					i = 1;
					while(i < 5){
						board.addRobotToTile(2, -4, new Pair<Integer,Integer>(2,i));
						i++;
					}
			}
			else if (numTeams == 3){
				int i = 1;
				while(i < 5){
					board.addRobotToTile(-2, 4, new Pair<Integer,Integer>(1,i));
					i++;
				}
				i = 1;
				while(i < 5){
					board.addRobotToTile(-2, -2, new Pair<Integer,Integer>(2,i));
					i++;
				}
				i = 1;
				while(i < 5){
					board.addRobotToTile(4,-2, new Pair<Integer,Integer>(3,i));
					i++;
				}
			}
			else if ( numTeams == 6){
				int i = 1;
				while(i < 5){
					board.addRobotToTile(-2, 4, new Pair<Integer,Integer>(1,i));
					i++;
				}
				i = 1;
				while(i < 5){
					board.addRobotToTile(-4, 2, new Pair<Integer,Integer>(2,i));
					i++;
				}
				i = 1;
				while(i < 5){
					board.addRobotToTile(-2,-2, new Pair<Integer,Integer>(3,i));
					i++;
				}
				i = 1;
				while(i < 5){
					board.addRobotToTile(2, -4, new Pair<Integer,Integer>(4,i));
					i++;
				}
				i = 1;
				while(i < 5){
					board.addRobotToTile(4, -2, new Pair<Integer,Integer>(5,i));
					i++;
				}
				i = 1;
				while(i < 5){
					board.addRobotToTile(2,2, new Pair<Integer,Integer>(6,i));
					i++;
				}
			}
			else{
				throw new IndexOutOfBoundsException();
			}
		}
		else if(boardSize == 11){
			if(numTeams == 2){
				int i = 1;
					while(i < 5){
						board.addRobotToTile(-3, 5, new Pair<Integer,Integer>(1,i));
						i++;
					}
					i = 1;
					while(i < 5){
						board.addRobotToTile(3, -5, new Pair<Integer,Integer>(2,i));
						i++;
					}
			}
			else if (numTeams == 3){
				int i = 1;
				while(i < 5){
					board.addRobotToTile(-3, 5, new Pair<Integer,Integer>(1,i));
					i++;
				}
				i = 0;
				while(i < 5){
					board.addRobotToTile(-2, -3, new Pair<Integer,Integer>(2,i));
					i++;
				}
				i = 0;
				while(i < 5){
					board.addRobotToTile(5,-2, new Pair<Integer,Integer>(3,i));
					i++;
				}
			}
			else if ( numTeams == 6){
				int i = 1;
				while(i < 5){
					board.addRobotToTile(-3, 5, new Pair<Integer,Integer>(1,i));
					i++;
				}
				i = 1;
				while(i < 5){
					board.addRobotToTile(-5, 2, new Pair<Integer,Integer>(2,i));
					i++;
				}
				i = 1;
				while(i < 5){
					board.addRobotToTile(-2,-3, new Pair<Integer,Integer>(3,i));
					i++;
				}
				while(i < 5){
					board.addRobotToTile(3, -5, new Pair<Integer,Integer>(4,i));
					i++;
				}
				i = 1;
				while(i < 5){
					board.addRobotToTile(5, -2, new Pair<Integer,Integer>(5,i));
					i++;
				}
				i = 1;
				while(i < 5){
					board.addRobotToTile(2,3, new Pair<Integer,Integer>(6,i));
					i++;
				}
			}
			else{
				throw new IndexOutOfBoundsException();
			}
		}
		int i = 0;
		while(i < sequencer.numberRobots()){
			Robot rob = sequencer.getNextTurnInit(i);
			interpreter.loadRobot( rob );
			i++;
		}
		
		sequencer.randomizeSequence();
		console.flush();
	}

	
	/**
	 * gives the next robot to run
	 * @return
	 */
	public void getNextRobot(){
		curRobot = sequencer.getNextTurn();
		teamThatTurnItis = curRobot.getTeam();
	}
	
	/**
	 * 
	 * @return the id of the team who goes next
	 */
	public int getNextTeamID(){
		return this.teamThatTurnItis;
	}
		
	/**
	 * Performs a turn
	 */
	public void doTurn(){
		// execute turn (get robot), load into interpreter
		interpreter.loadRobot( curRobot );
		// TO DO - Build entire info for team, health kills
		curTurn += 1;

	}
	
	/**
	 * @return info on the Robot that just performed it's turn
	 */
	public String getTurnInfo(){
		String returnString = "Game hasn't started!";
		
		if( curRobot != null){
			returnString = "Current turn:\n" + "Team " + teamThatTurnItis + 
					" Member " + curRobot.getMemberNum();
			
			returnString += "\n" + curRobot.toString();
		}
		
		return returnString;
	}
	
	/**
	 * Returns List of Strings written to game console
	 * @return
	 */
	public List<String> getConsole(){
		// TO DO - potentially add debug lines to console in Console
		List<String> fullLines = new LinkedList<String>();
		List<String> consoleLines =  console.getAll();

		for(String line: consoleLines){
			fullLines.add(line);
		}
		
		console.flush();
		
		return fullLines;
	}
	/**
	 * Updates stats for a winning team
	 * @param team number that winning
	 */
	private void updateWinningTeam(int team){
		for(int i = 1; i <= 4; i++){
			// build robot identifier
			Pair<Integer, Integer> robot = new Pair<Integer, Integer>(team, i);
			
			// increment wins
			log.incrementWins(robot, 1);
			
			// if robot survived, increment lived
			if( TeamList.getRobot(robot).getCurrentHealth() > 0 ){
				log.incrementLived(robot, 1);
			}
			// else increment died
			else{
				log.incrementDied(robot, 1);
			}
		}
	}
	
	/**
	 * Updates stats for a losing team
	 * @param team number that lost
	 */
	private void updateLosingTeam(int team){
		for(int i = 1; i <= 4; i++){
			// build robot identifier
			Pair<Integer, Integer> robot = new Pair<Integer, Integer>(team, i);
			
			// increment wins
			log.incrementLosses(robot, 1);
			
			// increment deaths
			log.incrementDied(robot, 1);
		}
	}
	
	
	/**
	 * @return true if game is finished, false if game is not
	 */
	public boolean isFinished(){
		boolean finished = false;
		
		// if only one team still alive OR turn limit reached
		
		if( curTurn >= TURN_LIMIT){
			finished = true;
		}
		else{
			int i = 1;
			int numberOfTeamsWithRobotsAlive = 0;
			
			// iterate through teams seeing if they have robots alive
			while( i <= numTeams){
				int alive = TeamList.getNumberOfRobotsAlive(i);
				
				if(alive > 0){
					numberOfTeamsWithRobotsAlive++;
				}
				i++;
			}
			if(numberOfTeamsWithRobotsAlive <= 1){
				finished = true;
			}
			
		}
		return finished;
	}
	
	/**
	 * Builds the update package after the game is completed
	 */
	public void buildUpdate(){
		// if there was a winner, build an update
		if( !wasDraw && isFinished() ){
			List<RobotUpdate> updates = new LinkedList<RobotUpdate>();
			
			// build a robot update for each robot
			for(int i = 1; i <= numTeams ; i++){
				for(int j = 1; j <= 4; j++){
					Pair<Integer, Integer> robot = new Pair<Integer, Integer>(i, j);
					updates.add( log.getRobotUpdate(robot) );
				}
			}
			
			// build a map for unique serials
			Map<Integer, RobotUpdate> uniqueUpdates = new TreeMap<Integer, RobotUpdate>();
			
			// key each unique robot serial into the update map
			for(RobotUpdate r : updates){
				RobotUpdate unique = new RobotUpdate(r.getSerial(), 1);
				uniqueUpdates.put(unique.getSerial(), unique);
			}
			
			// fold in each update into the map entry
			for(RobotUpdate r : updates){
				RobotUpdate fold = uniqueUpdates.get( r.getSerial() );
				fold.combineUpdate(r);
			}
			
			// write each update to a file 'serial'.json
			for(Map.Entry<Integer, RobotUpdate> entry : uniqueUpdates.entrySet() ){
				updateWriter.writeUpdate( entry.getValue() );
			}
		}
	}
	
	/**
	 * Gets the game results in string form
	 * @return string  of robot data
	 */
	public String getGameResults(){
		String returnString = "Game Results\n";
		
		int i = 1;
		int winningTeam = 0;
		int numberOfTeamsWithRobotsAlive = 0;
		
		// iterate through teams seeing if they have robots alive
		// and finding winning team
		while( i <= numTeams){
			int alive = TeamList.getNumberOfRobotsAlive(i);
			
			if(alive > 0){
				numberOfTeamsWithRobotsAlive++;
				winningTeam = i;
				updateWinningTeam(i);
			}
			else{
				updateLosingTeam(i);
			}
			i++;
		}
		
		// check what the outcome of the game was
		if(numberOfTeamsWithRobotsAlive == 0){
			returnString += "Draw: teams killed each other\n";
			wasDraw = true;
		}
		else if( curTurn >= TURN_LIMIT ){
			returnString += "Draw: turn limit exceeded\n";
			wasDraw = true;
		}
		else{
			returnString += "Team " + winningTeam + " wins!\n";
		}
		
		int team = 1;
		
		// loop to build game results
		while(team <= numTeams){
			returnString += "Team " + team + ":\n";
			int robot = 1;
			
			// build team results
			while(robot < 5){
				Pair<Integer,Integer> cur = new Pair<Integer,Integer>(team,robot);
				Robot r = TeamList.getRobot(cur);
				int damage = log.getDamageDealt(cur);
				int damageAdsorbed = log.getAbsorbed(cur);
				int curHealth = r.getCurrentHealth();
				
				returnString += "Robot " + robot + ":  " + "Damage Dealt: " + damage + "  " + 
						"Damage Taken: " + damageAdsorbed + "  " + "Health: " + curHealth + "\n";  
				robot++;
			}
			
			team++;
		}
		
		return returnString;
	}
	
	public Pair<Pair<Integer,Integer>,tileType> tileDrawInfo(int x, int y){
		Pair<Pair<Integer,Integer>,tileType> p = new Pair<Pair<Integer,Integer>,tileType>(board.tileInfo(x, y),board.typeOfTile(x,y));
		return p;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
