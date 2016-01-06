package testing;

import containers.Pair;
import teambuilder.TeamBuilderLogicLayer;
import model.Console;
import board.GameBoard;
import model.Interpreter;
import model.TeamList;

/**
 * Class for testing Interpreter methods requiring a gameboard and teams
 */
public class InterpreterTestHarness {
	
	/**
	 * Testing class for Interpreter
	 * Requires ROBOT_DIRECTORY = "testing/librarian/" in LibrarianClient
	 */
	public static void main(String[] args){
		
		TeamBuilderLogicLayer t = new TeamBuilderLogicLayer(6);
		// for send 0 mailbox tests
		t.addRobotToTeam(1, 2);
		t.addRobotToTeam(1, 3);
		t.addRobotToTeam(1, 3);
		t.addRobotToTeam(1, 3);
	
		// for send 3 mailbox tests
		t.addRobotToTeam(2, 5);
		t.addRobotToTeam(2, 5);
		t.addRobotToTeam(2, 5);
		t.addRobotToTeam(2, 4);
		
		// for variable tests
		t.addRobotToTeam(3, 6);
		t.addRobotToTeam(3, 7);
		t.addRobotToTeam(3, 8);
		t.addRobotToTeam(3, 9);

		// for hex tests
		t.addRobotToTeam(4, 10);
		t.addRobotToTeam(4, 10);
		t.addRobotToTeam(4, 10);
		t.addRobotToTeam(4, 10);

		t.addRobotToTeam(5, 11);
		t.addRobotToTeam(5, 11);
		t.addRobotToTeam(5, 8);
		t.addRobotToTeam(5, 9);

		t.addRobotToTeam(6, 6);
		t.addRobotToTeam(6, 7);
		t.addRobotToTeam(6, 8);
		t.addRobotToTeam(6, 9);

		
		TeamList.init( t.startMatch() );
		
		
		GameBoard board = new GameBoard(9);
		Console console = new Console();
		
		Interpreter interpreter = new Interpreter(console, 6, board);
		
		/*
			test sending and then failing
		
		for(int i = 0; i < 7; i++){
			Pair<Integer, Integer> robot = new Pair<Integer, Integer>(1, 1);
			interpreter.loadRobot( TeamList.getRobot( robot ) );
			
			
			System.out.println( console.getAll() );
			console.flush();
		}
		
		*/
		
		/*
			test sending to dead robot
			
		Pair<Integer, Integer> robot = new Pair<Integer, Integer>(1, 1);		
		TeamList.getRobot( new Pair<Integer,Integer> (1, 2) ).setCurrentHealth(0);
		
		interpreter.loadRobot( TeamList.getRobot( robot ) );

		System.out.println( console.getAll() );
		console.flush();
		
		*/
		
		/*
		for(int i = 4; i > 1; i--){
			Pair<Integer, Integer> robot = new Pair<Integer, Integer>(2, i);
			interpreter.loadRobot( TeamList.getRobot( robot ) );
			
			
			System.out.println( console.getAll() );
			console.flush();
		}
		*/
		
		/*
		Movement tests
		
		System.out.println( "Starting coordinates: " + TeamList.getRobot(robot1).getCoordinates() );
		
		interpreter.loadRobot( TeamList.getRobot(robot1) );

		System.out.println( "Ending coordinates: " + TeamList.getRobot(robot1).getCoordinates() );
		
		System.out.println( console.getAll() );
		console.flush();
		
		*/
		
		/*
		Pair<Integer, Integer> robot11 = new Pair<Integer, Integer>(1, 1);
		Pair<Integer, Integer> robot12 = new Pair<Integer, Integer>(1, 2);
		Pair<Integer, Integer> robot13 = new Pair<Integer, Integer>(1, 3);
		Pair<Integer, Integer> robot14 = new Pair<Integer, Integer>(1, 4);
		board.addRobotToTile(0, 2, robot11);
		board.addRobotToTile(2, 0, robot12);
		board.addRobotToTile(-2, 0, robot13);
		board.addRobotToTile(3, 0, robot14);

		*/
		
		// Variable tests
		
		// test setting variables

		/*
		Pair<Integer, Integer> robot31 = new Pair<Integer, Integer>(3, 1);
		interpreter.loadRobot( TeamList.getRobot(robot31) );
		
		System.out.println( console.getAll() );
		console.flush();
		*/
		
		// test persistence across turns
		
		/*
		interpreter.loadRobot( TeamList.getRobot(robot31) );

		System.out.println( console.getAll() );
		console.flush();
		*/
		
		// Firing tests

		/*
		
		Pair<Integer, Integer> robot11 = new Pair<Integer, Integer>(1, 1);
		Pair<Integer, Integer> robot12 = new Pair<Integer, Integer>(1, 2);
		Pair<Integer, Integer> robot13 = new Pair<Integer, Integer>(1, 3);
		Pair<Integer, Integer> robot14 = new Pair<Integer, Integer>(1, 4);
		
		Pair<Integer, Integer> robot32 = new Pair<Integer, Integer>(3, 2);
		Pair<Integer, Integer> robot33 = new Pair<Integer, Integer>(3, 3);

		board.addRobotToTile(0, 2, robot11);
		board.addRobotToTile(2, 0, robot12);
		board.addRobotToTile(-2, 0, robot13);
		board.addRobotToTile(3, 0, robot14);
		board.addRobotToTile(0, 0, robot33);
		board.addRobotToTile(0, -1, robot32);
		
		interpreter.loadRobot( TeamList.getRobot(robot33) );
		
		System.out.println( console.getAll() );
		console.flush();
	
		interpreter.loadRobot( TeamList.getRobot(robot33) );

		System.out.println( console.getAll() );
		console.flush();
		System.out.println("Robot 3 3's health (firing robot): " +  TeamList.getRobot(robot33).getCurrentHealth() );

		System.out.println("Robot 1 1's health (should go down sometimes): " +  TeamList.getRobot(robot11).getCurrentHealth() );
		System.out.println("Robot 1 2's health (should go down sometimes): " +  TeamList.getRobot(robot12).getCurrentHealth() );
		System.out.println("Robot 1 3's health (should go down sometimes): " +  TeamList.getRobot(robot13).getCurrentHealth() );
		System.out.println("Robot 1 4's health (should go down sometimes): " +  TeamList.getRobot(robot14).getCurrentHealth() );
		System.out.println("Robot 3 2's health (should go down sometimes): " +  TeamList.getRobot(robot32).getCurrentHealth() );
		
		*/
		

		 
		// Identify tests
		
		/*
		Pair<Integer, Integer> robot34 = new Pair<Integer, Integer>(3, 4);

		board.addRobotToTile(0, 0, robot34);
		
		Pair<Integer, Integer> robot11 = new Pair<Integer, Integer>(1, 1);
		Pair<Integer, Integer> robot12 = new Pair<Integer, Integer>(1, 2);
		Pair<Integer, Integer> robot13 = new Pair<Integer, Integer>(1, 3);
		Pair<Integer, Integer> robot14 = new Pair<Integer, Integer>(1, 4);
		Pair<Integer, Integer> robot21 = new Pair<Integer, Integer>(2, 1);
		Pair<Integer, Integer> robot22 = new Pair<Integer, Integer>(2, 2);


		board.addRobotToTile(0, 2, robot11);
		board.addRobotToTile(2, 0, robot12);
		board.addRobotToTile(-2, 0, robot13);
		board.addRobotToTile(3, 0, robot14);
		board.addRobotToTile(0, 1, robot21);
		board.addRobotToTile(0, -2, robot22);

		interpreter.loadRobot( TeamList.getRobot(robot34) );
		
		System.out.println( console.getAll() );
		console.flush();
		
		interpreter.loadRobot( TeamList.getRobot(robot34) );
		
		System.out.println( console.getAll() );
		console.flush();
		
		*/
		
		// Move tests
		
		// Robot moving around origin
		
		/*
		Pair<Integer, Integer> robot51 = new Pair<Integer, Integer>(5, 1);
		
		board.addRobotToTile(0 , 0,  robot51);

		interpreter.loadRobot( TeamList.getRobot(robot51) );
		
		System.out.println( console.getAll() );
		console.flush();
		*/
		
		// Robot moving on border
		
		/*
		Pair<Integer, Integer> robot52 = new Pair<Integer, Integer>(5, 2);
		
		board.addRobotToTile(4 , -2,  robot52);

		interpreter.loadRobot( TeamList.getRobot(robot52) );
		
		System.out.println( console.getAll() );
		console.flush();
		*/
		
		// Hex tests
		
		/*
		Pair<Integer, Integer> robot51 = new Pair<Integer, Integer>(5, 1);
		Pair<Integer, Integer> robot52 = new Pair<Integer, Integer>(5, 2);
		Pair<Integer, Integer> robot53 = new Pair<Integer, Integer>(5, 3);
		Pair<Integer, Integer> robot54 = new Pair<Integer, Integer>(5, 4);
		Pair<Integer, Integer> robot61 = new Pair<Integer, Integer>(6, 1);
		
		board.addRobotToTile(0, -4, robot51);
		board.addRobotToTile(0, -4, robot52);
		board.addRobotToTile(0, -4, robot53);
		board.addRobotToTile(0, -4, robot54);
		board.addRobotToTile(0, -4, robot61);
		
		Pair<Integer, Integer> robot41 = new Pair<Integer, Integer>(4, 1);
		board.addRobotToTile(-1, -3, robot41);
		
		interpreter.loadRobot( TeamList.getRobot(robot41) );
		
		System.out.println( console.getAll() );
		console.flush();
		
		System.out.println( board.checkIfInBounds(-2, -3) );
		
		*/
	}
	
}
