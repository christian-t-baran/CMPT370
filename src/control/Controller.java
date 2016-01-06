package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

import board.Board;
import board.Tile.tileType;
import containers.Pair;
import entities.Robot;
import entities.Team;
import model.Model;
import view.*;



/**This class handles communication between the model and the view
 * @author group B2
 */
@SuppressWarnings("unused")
public class Controller implements ActionListener{
	//default wait value
	public static final int DEFAULT_RUN = 500;
	//wait value if fastforward is called
	public static final int FAST_FORWARD = 250;
	//wait value if double fast forward is called
	public static final int DOUBLE_FAST_FORWARD = 100;
	//make the program not wait if needs to run to completion
	public static final int COMPLETE = 0;
	//this is the sequence that robots get turns
	
	
	
	private GameInfoPanel gameInfo;
	
	private Board gameBoard;
	private volatile Model model;
	private int currTeam;
	
	private volatile boolean paused = false;
	private volatile boolean isFinished = false;
	private volatile boolean hasStarted = false;
	private volatile boolean gameStopped = false;
	
	
	//this is the amount of time to wait to loop 
	int waitTime;
	
	/**This builds the controller for the game
	 * @param t is the list containing the team data
	 * @param boardSize is an integer containing the size of the game board.
	 */
	public Controller(List<Team> t, int boardSize){
		waitTime = 500;// wait for half a second
		model = new Model(t,boardSize);
		isFinished = false;
		currTeam = 0;
		
		makeGameThread();
	}
	
	
	/**
	 * Generates the thread that runs the main game loop
	 */
	private void makeGameThread(){
		Thread t = new Thread(){
			@Override
			public void run(){
				// run the loop when the game has started, 
				// hasn't been stopped, and isn't finished
				while(!isFinished  && hasStarted && !gameStopped){
					// if paused, sleep thread
					while(paused){
						// sleep for a while and wait to see if paused state has changed
						try{
							sleep(100);
						}
						catch (InterruptedException e){}
					}
					
					// run main gameLoop
					model.getNextRobot();
					model.doTurn();
					isFinished = model.isFinished();
					
					// update board
					gameBoard.repaint();
					
					// update results
					gameInfo.revalidate();
					gameInfo.repaint();
					
					// sleep and wait to see if user has given any input
					try{
						sleep((long) waitTime);
					}
					catch (InterruptedException e){}
				}
				
				// code to build updates and display results when game finishes
				if (isFinished){
					gameInfo.repaint();
					gameBoard.repaint();
					
					model.buildUpdate();
				}
			}
			
		};
		
		// start up the thread
		t.start();
	}
	

	/**This method takes an event, and sends the appropriate commands to the model based on the command that the event sends:
	 * 
	 * @param event an actionEvent representing an instruction from the View.
	 */
	public void actionPerformed(ActionEvent event){
		gameBoard = Board.getBoard();
		gameInfo = GameInfoPanel.getGameInfo();
		
		String actionCommand = event.getActionCommand();
		
		// play button commands
		if (actionCommand.equals("play")) {
            /** this is passed in as an ActionListener */
            if( !hasStarted){
            	makeGameThread();
				waitTime = DEFAULT_RUN;
				hasStarted = true;
				isFinished = model.isFinished();
            }
            else{
            	paused = false;
            	waitTime = DEFAULT_RUN;
            }
        }
		
		// fastforward button commands
		if ( actionCommand.equals("fastForward") ) {
			paused = false;
			waitTime = FAST_FORWARD;
		}
		
		// fastforwardX2 button commands
		if ( actionCommand.equals("fastForwardX2") ) {
			paused = false;
			waitTime = DOUBLE_FAST_FORWARD;
		}
		
		// pause button commands (pause game until user runs some sort of play
		if ( actionCommand.equals("pause") ) {
			paused = true;
		}
		
		// stop game
		if ( actionCommand.equals("stop") ) {
			gameStopped= true;
		}
		
		
		// handle stepback button
		if ( actionCommand.equals("home") ){
			JOptionPane.showMessageDialog(null, "This button does not work at this time, sorry.",
		                   		"ERROR", JOptionPane.WARNING_MESSAGE);
		}
		// handle stepback button
		if ( actionCommand.equals("stepback") ){
			JOptionPane.showMessageDialog(null, "This button does not work at this time, sorry.",
                   				"ERROR", JOptionPane.WARNING_MESSAGE);
		}
		
	}


	
	public Pair<Pair<Integer,Integer>,tileType> drawBoardInfo(int x, int y){
		return model.tileDrawInfo(x, y);
	}
	
	public boolean gameIsOver(){
		return isFinished;
	}
	
	public String getTurnInfo(){
		return model.getTurnInfo();
	}
	
	public String getGameResults(){
		return model.getGameResults();
	}
	
	public int getCurrentTeamNumber(){
		
		return currTeam;
	}
	
	public List<String> getConsole(){
		return model.getConsole();
	}
	
	public void begin(){
		
		model.begin();
	}

}
