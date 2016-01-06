package testing;

import model.Model;
import teambuilder.TeamBuilderLogicLayer;

/**
 * This class is used for testing game flow independent of the GUI
 */
public class ModelTestHarness {

	/**
	 * Testing class for Model
	 * Requires ROBOT_DIRECTORY = "librarian/robots/" in LibrarianClient
	 */
	public static void main(String[] args){
		TeamBuilderLogicLayer t = new TeamBuilderLogicLayer(2);

		// populate Team 1
		t.addRobotToTeam(1, 1);
		t.addRobotToTeam(1, 2);
		t.addRobotToTeam(1, 2);
		t.addRobotToTeam(1, 2);
		
		// populate Team 2
		t.addRobotToTeam(2, 2);
		t.addRobotToTeam(2, 2);
		t.addRobotToTeam(2, 4);
		t.addRobotToTeam(2, 3);
	
		// populate Team 3
		/*
		t.addRobotToTeam(3, 1);
		t.addRobotToTeam(3, 5);
		t.addRobotToTeam(3, 5);
		t.addRobotToTeam(3, 1);
		*/
		
		// populate Team 4
		
		/*
		t.addRobotToTeam(4, 1);
		t.addRobotToTeam(4, 1);
		t.addRobotToTeam(4, 1);
		t.addRobotToTeam(4, 1);
		*/	
				
		// populate Team 5
		
		/*
		t.addRobotToTeam(5, 1);
		t.addRobotToTeam(5, 1);
		t.addRobotToTeam(5, 1);
		t.addRobotToTeam(5, 1);
		*/
				
		// populate Team 6
		
		/*
		t.addRobotToTeam(6, 1);
		t.addRobotToTeam(6, 1);
		t.addRobotToTeam(6, 1);
		t.addRobotToTeam(6, 1);
		*/
		
		Model m = new Model(t.startMatch(), 7);
		
		m.begin();
		
		while ( !m.isFinished() ){
			m.getNextRobot();
			m.doTurn();
			
			System.out.println( "Console: ");
			System.out.println( m.getConsole() );
			
		}
		
		
		System.out.println( "Game is over?");
		
		System.out.println( m.getGameResults() );

		// build a game update
		m.buildUpdate();
	}
	
}
