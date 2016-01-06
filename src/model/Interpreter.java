package model;
import java.util.*;

import board.GameBoard;
import containers.Pair;
import forth.*;
import entities.Robot;
import exceptions.InterpreterCoordinateException;
import exceptions.InterpreterExecutionException;
import exceptions.InterpreterFailReceiveException;
import model.InterpreterTypeIdentifier;



/**
 * This class models an Interpreter for the Robot Forth Language
 */
public class Interpreter {
	
	// hard limits to prevent infinite recursion and infinite loops
	private static final int MAX_LOOP_ITERATIONS = 100;
	private static final int MAX_EVAL_DEPTH = 100;
	
	// fields
	private Console console;
	
	// fields 
	private Robot curRobot;
	private Map<String,ForthObject> instructions = new TreeMap<String,ForthObject>();
	
	// Stack for computations
	private ForthStack stack;
	
	// variable to hold current loop value (null when not in loop)
	private Integer loopVal;
		
	// Type identifier for the interpreter
	private InterpreterTypeIdentifier comp;
	
	// Coordinate mapper for the interpreter
	private CoordinateMapper coord;
	
	// The game board
	private GameBoard board;
	
	/**
	 * Constructor for Interpreter
	 * @param console to write to
	 * @numTeams number of teams for theta corrections
	 * @board GameBoard
	 */
	public Interpreter(Console console, int numTeams, GameBoard board){
		this.console = console;
		this.board = board;
		
		stack = new ForthStack();
		comp = new InterpreterTypeIdentifier();
		coord = new CoordinateMapper(numTeams);
		loopVal = null;
	}
	
	/**
	 * Loads a Robot into the Interpreter and runs its turn
	 * @param r Robot to load
	 * @throws InterpreterCoordinateException 
	 */
	public void loadRobot(Robot r){
		curRobot = r;
		instructions = r.getInstructions();
		stack.clear();	
		
		if( curRobot.getCurrentHealth() > 0){
			console.writeLine( curRobot.toString() );
			console.writeLine( "Team: " + curRobot.getTeam() + "  Member: " + curRobot.getMemberNum() );
			executeRobot();
		}
		else{
			console.writeLine( curRobot.toString() );
			console.writeLine( "Team: " + curRobot.getTeam() + "  Member: " + curRobot.getMemberNum() );
			console.writeLine( "Can't run dead robot ");
		}
	}
	
	/**
	 * This method attempts to runs a robot's script and handles possible execution errors
	 */
	private void executeRobot(){
		try{
			// evaluate init word if robot's first turn
			if( curRobot.isFirstTurn()){
				doInit();
				
				// this flips the robot's firstTurn boolean to false
				curRobot.startTurn();
			}
			// otherwise restore its movement left and hasShot variables
			// and evaluate turn word
			else{
				curRobot.startTurn();
				doTurn();
			}
		}
		// Kill robot on execution failure
		catch (InterpreterExecutionException e){
			console.writeLine( e.getMessage() );
			TeamList.KillRobot( 
					new Pair<Integer, Integer>( curRobot.getTeam(), curRobot.getMemberNum() ) );
		}
		// End turn and print to console on these errors
		catch (InterpreterFailReceiveException e){
			// flip the firstTurn boolean to false if this occurred during init
			curRobot.startTurn();
			console.writeLine( e.getMessage() );
		} catch (InterpreterCoordinateException e) {
			// flip the firstTurn boolean to false if this occurred during init
			curRobot.startTurn();
			console.writeLine( e.getMessage() );
		}
	}
	
	/**
	 * Obtains the integer value stored in v
	 * @param v
	 * @return int value in v
	 * @throws InterpreterExecutionException
	 */
	private int getIntFromValue(ForthValue v) throws InterpreterExecutionException{
		int returnInt;
		
		// attempt to read int from value
		if( v.isInt() && v.isLiteral() ){
				
			returnInt = ( (ForthInt) v).getValue();
		}
		else{
			throw new InterpreterExecutionException("Value is not an int");
		}
		
		return returnInt;
	}
	
	/**
	 * Returns the boolean value stored in v
	 * @param v
	 * @return boolean value in v
	 * @throws InterpreterExecutionException
	 */
	private boolean getBoolFromValue(ForthValue v) throws InterpreterExecutionException{
		boolean returnBool;

		// attempt to read boolean from value
		if( v.isBool() && v.isLiteral() ){
				
			returnBool = ( (ForthBool) v).getValue();
		}
		else{
			throw new InterpreterExecutionException("Value is not a bool");
		}
		
		return returnBool;
	}
	
	/**
	 * Returns the string value stored in v
	 * @param v
	 * @returnString value stored in v
	 * @throws InterpreterExecutionException
	 */
	private String getStringFromValue(ForthValue v) throws InterpreterExecutionException{
		String returnString;

		// attempt to read string from value
		if( v.isString() && v.isLiteral() ){
				
			returnString = ( (ForthString) v).getValue();
		}
		else{
			throw new InterpreterExecutionException("Value is not a string");
		}
		
		return returnString;
	}
	
	/**
	 * This function builds a Literal string value out of ." escaped Forth code
	 * @param s First token of String
	 * @param st Rest of String tokens
	 * @return String
	 * @throws Exception
	 */
	private String buildString(String s, StringTokenizer st) throws InterpreterExecutionException{	
		String token = s;
		String returnString = s.substring(2);
		
		// get next token and add it to string until terminating " is found
		while( !token.endsWith("\"")){
			if ( !st.hasMoreTokens() ){
				throw new InterpreterExecutionException("Unmatched .\"");
			}
			
			// get the next token and append it onto the return string
			token = st.nextToken();
						
			returnString += " " + token;
		}
		
		// return the built string with the terminating " removed
		return returnString.substring(0, ( returnString.length()-1 ) );
	}
	
	
	/**
	 * This method runs the init word for a Robot
	 * @throws InterpreterFailReceiveException 
	 * @throws InterpreterExecutionException 
	 * @throws InterpreterCoordinateException 
	 */
	private void doInit() throws InterpreterExecutionException, 
		InterpreterFailReceiveException, InterpreterCoordinateException{
		
		// if robot has invalid instructions throw exception
		if( instructions == null){
			throw new InterpreterExecutionException("Instructions are null!");
		}
		else if( !instructions.containsKey("init") ){
			throw new InterpreterExecutionException("No init word defined!");
		}
		
		ForthWord init = (ForthWord) instructions.get("init");

		eval( init.getBody(), 1 );
	}
	
	/**
	 * This method runs the turn word for a Robot
	 * @throws InterpreterExecutionException 
	 * @throws InterpreterFailReceiveException 
	 * @throws InterpreterCoordinateException 
	 */
	private void doTurn() throws InterpreterExecutionException, 
	InterpreterFailReceiveException, InterpreterCoordinateException{
		
		// if robot has invalid instructions throw exception
		if( instructions == null){
			throw new InterpreterExecutionException("Instructions are null!");
		}
		else if( !instructions.containsKey("turn") ){
			throw new InterpreterExecutionException("No turn word defined!");
		}
		
		ForthWord turn = (ForthWord) instructions.get("turn");
		
		eval( turn.getBody(), 1 );
	}
	
	/**
	 * This robot performs evaluation of the body of a Forth word
	 * @param s Body of a Forth word
	 * @param depth Depth of recursion
	 * @throws InterpreterExecutionException 
	 * @throws InterpreterFailReceiveException 
	 * @throws InterpreterCoordinateException 
	 */
	private void eval(String s, int depth) 
			throws InterpreterExecutionException, InterpreterFailReceiveException, InterpreterCoordinateException{
		
		// if the evaluation has hit maximum evaluation depth, kill the robot
		if( depth > MAX_EVAL_DEPTH ){
			throw new InterpreterExecutionException("Evaluation depth "
					+ "limit reached: " + MAX_EVAL_DEPTH);
		}
		
		// tokenize the word body
		StringTokenizer words = new StringTokenizer(s);
		
		
		while( words.hasMoreTokens() ){
			// get the next token
			String word = words.nextToken();
						
			// determine what action to take with token
			
			// read in a literal
			if( comp.isLiteral(word) ){
				doLiteral(word, words);
			}
			// do conditional
			else if( comp.isConditional(word) ){
				doConditional(word, words, depth);
			}
			// do loop
			else if( comp.isLoop(word) ){				
				doLoop(word, words, depth);
			}
			// perform action defined by system word
			else if( comp.isSystemWord(word) ){
				doSystemWord(word, words);
			}
			// handle user defined words and variables
			else if( isDefined(word) ){
				ForthObject defined = instructions.get(word);
				
				// perform variable action (read or write) if it's a variable
				if( defined.isVariable() ){
					doVariable(word);
				}
				// else evaluate the word
				else if( defined.isWord() ){
					ForthWord defWord = (ForthWord) instructions.get(word);
					eval( defWord.getBody(), depth+1 );
				}
			}
			// if word cannot be recognized throw an exception
			else {
				throw new InterpreterExecutionException("Unrecognized word: " + word);
			}
		}
	}
	
	/**
	 * This method pushes a literal onto the stack
	 * @param s current token
	 * @param st a tokenized string
	 * @throws InterpreterExecutionException 
	 */
	private void doLiteral(String s, StringTokenizer st) 
			throws InterpreterExecutionException{		
		
		// Push an int if one is read
		if( comp.isInt(s) ){
			stack.push( new ForthInt( Integer.parseInt(s) ) );
		}
		// Push a boolean if one is read
		else if( comp.isBool(s) ){
			if( s.equals("true") ){
				stack.push( new ForthBool(true) );
			}
			else{
				stack.push( new ForthBool(false) );
			}
		}
		// Push a string if string literal begin token found
		else if( comp.isString(s) ){		
			String litString = this.buildString(s, st);
						
			stack.push( new ForthString(litString) );
		}
		// Push the loop value if token is I
		else if( s.equals("I") ){
			if( loopVal != null){
				stack.push( new ForthInt( loopVal ) );
			}
			else{
				throw new InterpreterExecutionException("Loop variable is null");
			}
			
		}
	}

	/**
	 * This method builds the body of loops and conditionals
	 * @param curToken first token of body
	 * @param st a tokenized string
	 * @param start starting token
	 * @param end ending token
	 * @return the constructed body of the loop/conditional
	 * @throws InterpreterExecutionException 
	 */
	private String buildBody(String curToken, StringTokenizer st, String start, String end) 
			throws InterpreterExecutionException{
		int countStart = 0;
		
		// build the body of the loop
		String body = "";
		while(  !curToken.equals(end) ){			
			body += curToken + " ";
			
			// if starting tokens have been found then the body contains
			// something nested
			if( countStart > 0){
				
				// if an ending token is found, close one of the nested things
				if( curToken.equals( end ) ){
					body += curToken + " ";
					countStart -= 1;
					
				}
			}
			
			// if another starting token is found, something's nested inside 
			if ( curToken.equals( start ) ){
				countStart += 1;
			}
			
			// handle case where 
			if ( !st.hasMoreTokens() ){
				throw new InterpreterExecutionException("Unmatched " + end);
			}
			
			curToken = st.nextToken();
			
			// if thing is nested
			if( countStart > 0){
				// if an ending token is found, close one of the nested things
				if( curToken.equals( end ) ){
					body += curToken + " ";
					countStart -= 1;
					
					if ( !st.hasMoreTokens() ){
						throw new InterpreterExecutionException("Unclosed " + start);
					}
					
					curToken = st.nextToken();
					
				}
			}
			
		}
		
		return body;
	}
	
	/**
	 * This executes the conditional starting at token s
	 * @param s current token
	 * @param st a tokenized string
	 * @param depth recursion depth
	 * @throws InterpreterExecutionException
	 * @throws InterpreterFailReceiveException
	 * @throws InterpreterCoordinateException 
	 */
	private void doConditional(String s, StringTokenizer st, int depth) 
			throws InterpreterExecutionException, InterpreterFailReceiveException, InterpreterCoordinateException{
		// get boolean value determinign branch to take off stack
		ForthValue test = stack.pop();
				
		boolean vTest = ( (ForthBool) test ).getValue();

		// build the true branch of the conditional
		String curToken = st.nextToken();
		String trueBranch = buildBody(curToken, st, "if", "else");
		
		// build the false branch of the conditional
		curToken = st.nextToken();
		String falseBranch = buildBody(curToken, st, "if", "then");
		
		// evaluate the conditional
		if( vTest ){
			eval(trueBranch, depth+1);
		}
		else if( !vTest ){
			eval(falseBranch, depth+1);
		}
	}
	
	/**
	 * This executes the loop starting at token s
	 * @param s current token
	 * @param st a tokenized string
	 * @param depth recursion depth
	 * @throws InterpreterExecutionException
	 * @throws InterpreterFailReceiveException
	 * @throws InterpreterCoordinateException 
	 */
	private void doLoop(String s, StringTokenizer st, int depth)
			throws InterpreterExecutionException, InterpreterFailReceiveException, InterpreterCoordinateException{
		
		// do the type of loop specified in the token
		if( s.equals("do") ){			
			doLoopCounted(s, st, depth);
		}
		else if( s.equals("begin") ){
			doLoopGuarded(s, st, depth);
		}
	}
	
	
	
	
	/**
	 * This executes the counted loop starting at token s
	 * @param s current token
	 * @param st a tokenized string
	 * @param depth recursion depth
	 * @throws InterpreterExecutionException
	 * @throws InterpreterFailReceiveException
	 * @throws InterpreterCoordinateException 
	 */
	private void doLoopCounted(String s, StringTokenizer st, int depth) 
			throws InterpreterExecutionException, InterpreterFailReceiveException, InterpreterCoordinateException{
		ForthValue begin = stack.pop();
		ForthValue end = stack.pop();
		
		// get the beginning and ending counts of loop
		int vBegin = getIntFromValue(begin);
		int vEnd = getIntFromValue(end);

		loopVal = vBegin;
		
		String curToken = st.nextToken();
		
		// build the body of the loop
		String body = buildBody(curToken, st, "do", "loop");
					
		// run loop body once as defined
		loopVal = vBegin;
		eval( body, depth+1 );
		
		// set up counted loop
		for(int i = vBegin+1 ; i <= vEnd ; i++){
			// set loop value "I" in environment (shadowing previous value if necessary)
			loopVal = i;
			
			// evaluate the loop body
			eval( body, depth+1 );
		}
		
		// clear the loop value "I" in the environment 
		// (will be reset if returning from nested loop)
		loopVal = null;
	}
	
	/**
	 * This method evaluates a guarded loop
	 * @param s current token
	 * @param st a tokenized string
	 * @param depth recursion depth
	 * @throws InterpreterExecutionException
	 * @throws InterpreterFailReceiveException
	 * @throws InterpreterCoordinateException 
	 */
	private void doLoopGuarded(String s, StringTokenizer st, int depth) 
			throws InterpreterExecutionException, InterpreterFailReceiveException, InterpreterCoordinateException{
		
		// build the body of the loop (handles nested loops)
		String curToken = st.nextToken();
		String body = buildBody(curToken, st, "begin", "until");
		
		// evaluate the body (in do while fashion)
		eval(body, depth+1);
		
		ForthValue finished = stack.pop();
		
		// get the finished 
		boolean vFinished = ( (ForthBool) finished ).getValue();
		
		int loopIterations = 0;
		
		// while finish condition hasn't been met
		while( vFinished ){
			// Code to prevent infinite looping
			if(loopIterations > MAX_LOOP_ITERATIONS){
				throw new InterpreterExecutionException("Guarded loop exceeded 100 repetitions");
			}
			
			// evaluate the body
			eval(body, depth+1);
			
			// check whether finish condition has been met
			finished = stack.pop();
			vFinished = ( (ForthBool) finished ).getValue();
			
			// increment total number of loop iterations
			loopIterations += 1;
		}
		
	}
	
	/**
	 * This method executes the System word in s
	 * @param s current token
	 * @throws InterpreterExecutionException
	 * @throws InterpreterFailReceiveException 
	 * @throws InterpreterCoordinateException 
	 */
	private void doSystemWord(String s, StringTokenizer st) 
			throws InterpreterExecutionException, InterpreterFailReceiveException, InterpreterCoordinateException{
		
		// do system word type as defined in token
		if( comp.isArithmeticWord(s) ){
			doArithmeticWord(s);
		}
		else if( comp.isComparisonWord(s) ){
			doComparisonWord(s);
		}
		else if( comp.isLogic(s) ){
			doLogic(s);
		}
		else if( comp.isStackWord(s) ){
			doStackWord(s);
		}
		else if( comp.isRobotWord(s) ){	
			doRobotWord(s);
		}
		else if( comp.isUtility(s) ){
			doUtilityWord(s, st);
		}
		else if( comp.isVariableWord(s) ){
			doVariableWord(s);
		}
	}
	
	/**
	 * This method executes the arithmetic word in s
	 * @param s current token
	 * @throws InterpreterExecutionException
	 */
	private void doArithmeticWord(String s) throws InterpreterExecutionException{
		// get top 2 ints off stack
		ForthValue v1f = stack.pop();
		ForthValue v2f = stack.pop();
			
		int v1 = getIntFromValue(v1f);
		int v2 = getIntFromValue(v2f);
		
		// do operation defined by token
		
		// + adds 2 values together and puts result on stack
		if ( s.equals("+") ){
			int result = v1 + v2;
			stack.push( new ForthInt( result ) );
		}
		// - subtracts top value from botton and puts result on stack
		else if( s.equals("-") ){
			int result = v2 - v1;
			stack.push( new ForthInt( result ) );
		}
		// * multiplies 2 values together and puts result on stack
		else if( s.equals("*") ){
			int result = v1 * v2;
			stack.push( new ForthInt( result) );
		}
		// /mod divides top value into bottom
		// 		puts quotient on top of stack, remainder after that
		else if( s.equals("/mod") ){
			
			if( v1 == 0){
				throw new InterpreterExecutionException("Robot attempted to divide by 0");
			}
			
			int quotient = v2 / v1;
			int remainder = v2 % v1;

			stack.push( new ForthInt( remainder ) );
			stack.push( new ForthInt( quotient ) );
		}
	}
	
	/**
	 * This method executes the comparison word in s
	 * @param s current token
	 * @throws InterpreterExecutionException
	 */
	private void doComparisonWord(String s) throws InterpreterExecutionException{

		// get top 2 values
		ForthValue v1f = stack.pop();
		ForthValue v2f = stack.pop();
			
		// if 2 values are ints. do a numerical comparison
		if( v1f.isInt() && v1f.isLiteral() && v2f.isInt() && v2f.isLiteral() ){
			int v1 = getIntFromValue(v1f);
			int v2 = getIntFromValue(v2f);
			
			doComparisonWordNum(s, v1, v2);
		}
		// if 2 values are strings, do a string comparison
		else if (v1f.isString() && v1f.isLiteral() && v2f.isString() && v2f.isLiteral() ){
			String v1 = getStringFromValue(v2f);
			String v2 = getStringFromValue(v2f);
			
			doComparisonWordString(s, v1, v2);
		}
		else{			
			throw new InterpreterExecutionException("Invalid type for comparison");
		}
		
		
	}
	
	/**
	 * This method executes the string comparison word in s
	 * @param s current token
	 * @throws InterpreterExecutionException
	 */
	private void doComparisonWordString(String s, String v1, String v2) 
			throws InterpreterExecutionException{
		// do the matching string comparison in the token
		
		// equal to
		if( s.equals("=") ){
			stack.push( new ForthBool( v1.equals(v2) ) );
		}
		// not equal to
		else if( s.equals("<>") ){
			stack.push( new ForthBool( !v1.equals(v2) ) );
		}
		else{
			throw new InterpreterExecutionException("Invalid operator for string comparison");
		}
				
	}
	
	/**
	 * This method executes the numerical comparison word in s
	 * @param s current token
	 * @throws InterpreterExecutionException
	 */
	private void doComparisonWordNum(String s, int v1, int v2){
		// do the matching numerical comparison in the token
		
		// less than
		if ( s.equals("<") ){
			boolean result = v2 < v1;
			
			stack.push( new ForthBool( result ) );
		}
		// less than or equal to
		else if( s.equals("<=") ){
			boolean result = v2 <= v1;

			stack.push( new ForthBool( result ) );
		}
		// equal to
		else if( s.equals("=") ){		
			boolean result = v2 == v1;
			
			stack.push( new ForthBool( result ) );
		}
		// not equal to
		else if( s.equals("<>") ){		
			boolean result = v2 != v1;
			
			stack.push( new ForthBool( result ) );
		}
		// greater than or equal to
		else if( s.equals("=>") ){		
			boolean result = v2 >= v1;

			stack.push( new ForthBool( result ) );
		}
		// greater than
		else if( s.equals(">") ){		
			boolean result = v2 > v1;

			stack.push( new ForthBool( result ) );
		}
		
	}
	
	/**
	 * This method executes the logic word in s
	 * @param s current token
	 * @throws InterpreterExecutionException
	 */
	private void doLogic(String s) throws InterpreterExecutionException{
		// if the token is and or or, get top 2 values
		if( s.equals("and") || s.equals("or") ){
			ForthValue v1f = stack.pop();
			ForthValue v2f = stack.pop();
			
			boolean v1 = getBoolFromValue(v1f);
			boolean v2 = getBoolFromValue(v2f);
			
			// do specified operation
			if( s.equals("and") ){
				stack.push( new ForthBool( v1 && v2 ) );
			}
			else if( s.equals("or") ){
				stack.push( new ForthBool( v1 || v2 ) );
			}
		}
		// if the token is invert, get top value and invert it
		else if( s.equals("invert") ){
			ForthValue vf = (ForthValue) stack.pop();
		
			boolean v = getBoolFromValue(vf);
			
			stack.push( new ForthBool( !v ) );
		}
		
	}
	
	/**
	 * This method executes the logic word in s
	 * @param s current token
	 * @throws InterpreterExecutionException
	 */
	private void doStackWord(String s) throws InterpreterExecutionException{

		// drop top element on stack
		if( s.equals("drop") ){
			stack.pop();
		}
		// duplicate top value on stack
		else if( s.equals("dup") ){
			ForthValue v = stack.pop();

			stack.push( v );
			stack.push( v );
		}
		// swaps the top 2 elements
		else if( s.equals("swap") ){
			ForthValue v1 = stack.pop();
			ForthValue v2 = stack.pop();
			
			stack.push( v1 );
			stack.push( v2 );
			
			// thus when you pop, you get v2 -> v1
		}
		// rotates top 3 elements
		else if( s.equals("rot") ){
			ForthValue v1 = stack.pop();
			ForthValue v2 = stack.pop();
			ForthValue v3 = stack.pop();
			
			stack.push( v2 );
			stack.push( v1 );
			stack.push( v3 );
			
			// thus when you pop, you get v3 -> v1 -> v2 in order
		}
		
		
	}
	
	/**
	 * This method executes the robot word in s
	 * @param s current token
	 * @throws InterpreterExecutionException
	 * @throws InterpreterFailReceiveException
	 * @throws InterpreterCoordinateException 
	 */
	private void doRobotWord(String s) throws InterpreterExecutionException, 
		InterpreterFailReceiveException, InterpreterCoordinateException{
		// TO DO - Testing

		if( comp.isRobotAction(s) ){
			doRobotActionWord(s);
		}
		else if( comp.isRobotStatus(s) ){
			doRobotStatusWord(s);
		}
		else if( comp.isMailbox(s) ){
			doMailboxWord(s);
		}
	}
	
	/**
	 * This operation executes a robot's shoot operation
	 * @throws InterpreterExecutionException
	 * @throws InterpreterCoordinateException 
	 */
	private void doShoot() throws InterpreterExecutionException, InterpreterCoordinateException{
		// ignore shoot command if robot has fired
		if( curRobot.hasShot() ){
			console.writeLine("Robot has already shot, ignoring shoot command");
		}
		// kill robot if program is illegal
		else if ( curRobot.isFirstTurn() ){
			throw new InterpreterExecutionException("Robot can't fire on first turn.");
		}
		// else perform shoot command if not robot's first turn (the init turn)
		else if ( !curRobot.isFirstTurn() ){
			// get range and direction to fire off of stack
			ForthValue vRange = stack.pop();
			ForthValue vDir = stack.pop();
			
			int range = getIntFromValue(vRange);
			int dir = getIntFromValue(vDir);
			
			// get robot's team number to adjust theta
			int teamNum = curRobot.getTeam();
			
			// get robot's coordinates
			Pair <Integer, Integer> origin = curRobot.getCoordinates();
						
			// get coordinates to fire at by mapping range & dir from robot's coordinates
			Pair <Integer, Integer> fire = coord.getCoords( origin.left() , origin.right(), 
					range, dir, teamNum);
			
			curRobot.shoot();
			
			// Robot fired in out of range
			if( fire == null ){
				console.writeLine("Robot tried firing on invalid range: " + range);
			}
			// Actually fire on game board
			else if( board.checkIfInBounds( fire.left() , fire.right() ) ){
				board.fire( fire.left() , fire.right() , curRobot.getTeamMemberNum() );
				console.writeLine("Robot fired on " + fire);
			}
			// Robot fired out of bounds
			else{
				console.writeLine("Robot tried firing out of bounds");
			}
			
			// if robot has killed itself, throw exception and end execution
			if( curRobot.getCurrentHealth() <= 0){
				console.writeLine( "Robot has killed itself (shooting)" );
				throw new InterpreterCoordinateException("Robot shot itself to death!");
			}
		}
	}
	
	/**
	 * This method executes a Robot's move operation
	 * @throws InterpreterExecutionException
	 * @throws InterpreterCoordinateException 
	 */
	private void doMove() throws InterpreterExecutionException, InterpreterCoordinateException{
		ForthValue vRange = stack.pop();
		ForthValue vDir = stack.pop();
		
		
		int range = getIntFromValue(vRange);
		int dir = getIntFromValue(vDir);
		int teamNum = curRobot.getTeam();
		
		Pair <Integer, Integer> origin = curRobot.getCoordinates();
		int robotX = origin.left();
		int robotY = origin.right();
				
		Pair <Integer, Integer> move = coord.getCoords( robotX, robotY, 
				range, dir, teamNum);
		
		// Robot moved out of range
		if( move == null ){
				console.writeLine("Robot tried moving on invalid range: " + range);
		}
		// attempt to move Robot
		else{
			int moveX = move.left();
			int moveY = move.right();
			
			if( !board.checkIfInBounds( moveX , moveY ) ){
				throw new InterpreterCoordinateException("Robot attempted to move out of bounds, ending turn");
			}
			

			int cost = board.potentialCost( robotX, robotY, moveX, moveY );
			
			if (cost < 0){
				throw new InterpreterCoordinateException("Robot attempted to move onto "
						+ "an unreachable space, ending turn");
			}
			else if( curRobot.getMovesLeft() >= cost){
				board.moveRobotPosition( moveX, moveY, curRobot.getTeamMemberNum() );
				curRobot.decreaseMoves(cost);
				console.writeLine("Robot moving from " + origin + " to " + move);
			}
			else{
				console.writeLine("Robot tried to make too costly of move, ignoring move: " + cost);
			}
		}
		
	}
	
	/**
	 * This method executes a Robot's scan operation
	 * Scans in a concentric circles 0 ->, increasing in order
	 * until it finds 4 robots or runs out of range and puts that number on stack
	 * (does not include self)
	 */
	private void doScan(){
		int range = 0;
		int targetsFound = 0;
		
		// get coordinates of robot
		Pair<Integer, Integer> origin = curRobot.getCoordinates();
		
		int robotX = origin.left();
		int robotY = origin.right();
		int teamNum = curRobot.getTeam();

		// look for robots when still in scanning range and 4 robots haven't been found
		while( ( range < 4 ) && ( targetsFound < 5)){
			List<Integer> thetas = new LinkedList<Integer>();
			
			// build a list of possible thetas (for ranges greater than 0)
			for(int i = 0; i < (range * 6); i++){
				thetas.add(i);
			}
			
			// if range is 0, add 0
			if( range == 0){
				thetas.add(0);
			}			

			// while 4 targets haven't been found, try all thetas, popping one at aa time
			while( ( !thetas.isEmpty() ) && ( targetsFound < 5 ) ){
				// randomize direction
				int rand = getRandom( thetas.size() );
				int direction = thetas.get(rand);
				thetas.remove(rand);
				
				// calculate scanning coordinates
				Pair <Integer, Integer> scan = coord.getCoords( robotX, robotY, 
						range, direction, teamNum );
				int scanX = scan.left();
				int scanY = scan.right();
								
				if( board.checkIfInBounds(scanX, scanY) ){
					targetsFound += board.getRobotList(scanX, scanY).size();
					// if the list contains the current robot, decrement targets found by 1
					if( board.getRobotList(scanX, scanY).contains( curRobot.getTeamMemberNum() ) ){
						targetsFound -= 1;
					}
					
				}
			}
			
			range += 1;
		}
		
		// if 5 or more targets found (i.e. heavily populated tile, knock value down to 4)
		if(targetsFound >= 5){
			targetsFound = 4;
		}
		
		console.writeLine(targetsFound + " targets found");
		
		// Push targets found onto stack
		stack.push( new ForthInt(targetsFound) );
	}
	
	/**
	 * This method executes a Robot's scan operation.  Scans until it identifies the matching target number
	 * between 0 and scan-1
	 * @throws InterpreterExecutionException 
	 */
	private void doIdentify() throws InterpreterExecutionException{
		// get target number off stack
		ForthValue vTarget = stack.pop();
		int target = ( (ForthInt) vTarget ).getValue();
		
		if( ( target < 0 ) || (target > 4) ){
			throw new InterpreterExecutionException( target + " is invalid target number for identify");
		}
		
		// get coordinates of robot
		Pair<Integer, Integer> origin = curRobot.getCoordinates();
		
		int robotX = origin.left();
		int robotY = origin.right();
		int teamNum = curRobot.getTeam();
		
		// create variables to hold target information
		Pair<Integer, Integer> targetPair = null;
		Pair<Integer, Integer> targetCoord = null;
		
		// initialize loop variables
		int curTarget = -1;
		int range = 0;
		int direction = 0;
		
		// look for robot while it hasn't been found yet and still in scanning range
		while( ( range < 4 ) && ( curTarget < target)){
			List<Integer> thetas = new LinkedList<Integer>();
			
			// build a list of possible thetas
			for(int i = 0; i < (range * 6); i++){
				thetas.add(i);
			}

			// if range is 0, add 0
			if( range == 0){
				thetas.add(0);
			}	
			
			
			// while 4 targets haven't been found, try all thetas, popping one at a time
			while( ( !thetas.isEmpty() ) && ( curTarget < target) ){
				
				// randomize direction
				int rand = getRandom( thetas.size() );
				direction = thetas.get(rand);
				thetas.remove(rand);
								
				// calculate scanning coordinates
				Pair <Integer, Integer> scan = coord.getCoords( robotX, robotY, 
						range, direction, teamNum );
				int scanX = scan.left();
				int scanY = scan.right();
				
				// if the board can be scanned and has robots, get the list of robots on the tile
				if( board.checkIfInBounds(scanX, scanY) && board.containsRobots(scanX, scanY) ){
					List< Pair<Integer, Integer> > robotsOnTile = 
							board.getRobotList(scanX, scanY);
					
					List< Pair<Integer, Integer> > destructibleList =
							new LinkedList< Pair<Integer, Integer> >();
					
					// build duplicate list to pull elements out of
					for(Pair<Integer, Integer> p : robotsOnTile){
						destructibleList.add( new 
								Pair<Integer, Integer>( p.left(), p.right() ) );
					}
					
					// remove self from list if in it
					if( destructibleList.contains( curRobot.getMemberNum() ) ){
						destructibleList.remove( curRobot.getMemberNum() );
					}
					
					// Loop through duplicate list, incrementing target number
					while( ( !destructibleList.isEmpty() )&& (curTarget < target) ){
						rand = getRandom( destructibleList.size() );
						targetPair = destructibleList.get(rand);
						
						
						// remove targets from duplicate list
						destructibleList.remove(targetPair);
												
						curTarget += 1;	
					}
					
					// if target was found, store range and direction
					if(curTarget == target){
						targetCoord = new Pair<Integer, Integer>(range, direction);
					}
					
				}
			}
			
			range += 1;
		}
		
		if(curTarget == target){
			// get target robot from team list
			Robot targetRobot = TeamList.getRobot( targetPair );
			
			// get target robot variables from robot
			int targetTeamNum = targetRobot.getTeam();
			int targetRange = targetCoord.left();
			int targetDir = targetCoord.right();
			int targetHealth = targetRobot.getCurrentHealth();
			
			console.writeLine("Target # " + target +" stats are: ");
			console.writeLine("Target team number: " + targetTeamNum );
			console.writeLine("Target range: " + targetRange );
			console.writeLine("Target direction: " + targetDir );
			console.writeLine("Target health: " + targetHealth );

			
			// push relevant variables onto stack
			stack.push( new ForthInt( targetHealth ) );
			stack.push( new ForthInt( targetDir ) );
			stack.push( new ForthInt( targetRange ) );
			stack.push( new ForthInt( targetTeamNum) );
		}
		else{
			console.writeLine("Invalid target to identify!");
		}

	}
	
	/**
	 * This method executes a Robot's hex operation
	 * Returns the number of robots on a given tile as long as it's within a range of 3
	 */
	private void doHex() throws InterpreterExecutionException{
		// get range & direction values off stack
		ForthValue vRange = stack.pop();
		ForthValue vDir = stack.pop();
		
		int range = getIntFromValue(vRange);
		int dir = getIntFromValue(vDir);
				
		// Get coordinates of robot
		Pair<Integer, Integer> origin = curRobot.getCoordinates();
		int robotX = origin.left();
		int robotY = origin.right();
		int teamNum = curRobot.getTeam();

		// Get coordinates of hex call
		Pair<Integer, Integer> hex = coord.getCoords(robotX, robotY, range, dir, teamNum);
		
		
		// number of robots detected
		int numRobots = 0;
				
		// if the tile in range, get the number of robots on the tile
		if( hex != null ){
			int hexX = hex.left();
			int hexY = hex.right();
			
			// Calculate if the tile falls on the board
			if( board.checkIfInBounds(hexX, hexY) ){
				numRobots += board.getRobotList(hexX, hexY).size();
			}
			else {
				console.writeLine("hex command attempted out of bounds");
				
			}
		}
		else{
			console.writeLine("hex command attempted out of range" );
		}

		// if 5 or more targets found (i.e. heavily populated tile, knock value down to 4)
		if(numRobots > 4){
			numRobots = 4;
		}
		
		console.writeLine("hex found " + numRobots + " targets");
		
		stack.push( new ForthInt(numRobots) );
	}
	
	/**
	 * This method executes the robot action word in s
	 * @param s current token
	 * @throws InterpreterExecutionException 
	 * @throws InterpreterCoordinateException 
	 * @throws Exception
	 */
	private void doRobotActionWord(String s) 
			throws InterpreterExecutionException, InterpreterCoordinateException{
		// fire on a tile
		if( s.equals("shoot!") ){
			doShoot();
		}
		// move robot in a range & direction
		else if( s.equals("move!") ){
			doMove();
		}
		// scan for robots in a range of 3
		else if(s.equals("scan!") ){
			doScan();
		}
		// identify robot with corresponding number in scan
		else if(s.equals("identify!") ){
			doIdentify();
			
		}
		// gets number of robots on a specified tile within a range of 3
		else if(s.equals("hex") ){
			doHex();
		}
	}
	
	/**
	 * This method executes the robot status word in s
	 * @param s current token
	 */
	private void doRobotStatusWord(String s){
		// get relevant robot value & push onto the stack
		if( s.equals( "health" ) ){
			stack.push( new ForthInt(  curRobot.getCurrentHealth() ) );
		}
		else if( s.equals( "movesLeft") ){
			stack.push( new ForthInt(  curRobot.getMovesLeft() ) );
		}
		else if( s.equals( "firepower") ){
			stack.push( new ForthInt( curRobot.getFirepower() ) );
		}
		else if( s.equals( "team") ){
			stack.push( new ForthInt( curRobot.getTeam() ) );
		}
		else if( s.equals( "member") ){
			// Internally robots are numbered 0-3
			stack.push( new ForthInt( curRobot.getMemberNum() - 1 ) );
		}
	}
	
	/**
	 * This method executes the robot mailbox word in s
	 * @param s current token
	 * @throws InterpreterExecutionException
	 * @throws InterpreterFailReceiveException
	 */
	private void doMailboxWord(String s) 
			throws InterpreterExecutionException, InterpreterFailReceiveException{
		ForthValue i = stack.pop();
		
		int memberNum = getIntFromValue(i);	// member number of Robot to send or receive from
		memberNum += 1; // add 1 since in our system robot's are numbered 1 to 4
		
		if( (memberNum < 0) && (memberNum >= 4) ){
			throw new InterpreterExecutionException("Attempting to deal "
					+ "with out of range member (mailbox word)");
		}
		
		// send message to robot with memberNUm
		if( s.equals("send!") ){
			
			ForthValue message = stack.pop();

			Robot send = TeamList.getRobot( new Pair<Integer, Integer>(curRobot.getTeam(), memberNum) );
			
			// receiving robot should receive from curRobot's memberNum
			boolean successful = send.recvMessage(message, curRobot.getMemberNum() ); 
			
			stack.push( new ForthBool(successful) );
		}
		// check if message exists from robot with memberNum
		else if( s.equals("mesg?") ){
			boolean messageExists = curRobot.messageExists( memberNum );
			
			stack.push( new ForthBool(messageExists) );
		}
		// attempt to receive message from robot with memberNum
		else if( s.equals("recv!") ){
			// attempt to get message
			Pair<Boolean, ForthValue> receive = curRobot.getMessage(memberNum);

			// get the values in receive
			boolean messageReceived = receive.left();
			ForthValue message = receive.right();
			
			// if no message is received, this should end the robot's turn
			if( !messageReceived ){
				throw new InterpreterFailReceiveException("Robot failed to receive message");
			}
			// else push the message onto the stack
			else{
				stack.push( message );
			}
		}
		
	}
	
	/**
	 * This method pushes the variable represented by s onto the stack
	 * @param s current token
	 * @throws InterpreterExecutionException
	 */
	private void doVariable(String s) throws InterpreterExecutionException{
		ForthVariable var = (ForthVariable) instructions.get(s); 

		stack.push(var);
	}
	/**
	 * This method executes the variable word in s
	 * @param s current token
	 * @throws InterpreterExecutionException 
	 */
	private void doVariableWord(String s) throws InterpreterExecutionException{

		// get value from variable
		if( s.equals("?") ){
			ForthValue v = stack.pop();
			ForthVariable var = null;
			
			// check if var was a variable
			if( v.isVariable() ){
				var = (ForthVariable) v;
			}
			else{
				throw new InterpreterExecutionException("Value was not a variable");
			}
			
		
			if( var.isInt() ){
				stack.push( new ForthInt( var.getInt() ) );
			}
			else if( var.isString() ){
				stack.push( new ForthString( var.getString() ) );
			}
			else if( var.isBool() ){
				stack.push( new ForthBool( var.getBool() ) );
			}
		}
		// store top value in stack in variable
		else if( s.equals("!") ){
			ForthValue store = stack.pop();
			ForthValue v = stack.pop();
			
			ForthVariable var = null;
			
			if( v.isVariable() ){
				var = (ForthVariable) v;
			}
			else{
				throw new InterpreterExecutionException("Value was not a variable");
			}
			
			
			if( store.isInt() ){
				var.setInt( ( (ForthInt) store).getValue() );
			}
			else if( store.isString() ){
				var.setString( ( (ForthString) store).getValue() );
			}
			else if( store.isBool() ){
				var.setBool( ( (ForthBool) store).getValue() );
			}
			else if( store.isVariable() ){
				throw new InterpreterExecutionException("Can't store variables in variables");
			}
		}
	}
	
	/**
	 * Generates an integer between 0 and upperBound (exclusive)
	 * @param upperBound upper bound for random number
	 * @return
	 */
	private int getRandom(int upperBound){	
		int random = (int) (Math.random() * upperBound);
		
		return random;
	}
	
	/**
	 * This method executes the utility word in s
	 * @param s current token
	 * @throws InterpreterExecutionException
	 */
	private void doUtilityWord(String s, StringTokenizer st) 
			throws InterpreterExecutionException{
		
		// emit top string on stack to console
		if( s.equals(".") ){
			ForthValue string = stack.pop();
			
			// type check string
			if( string.isString() && string.isLiteral() ){
				console.writeLine( getStringFromValue(string) );
			}
			else{
				throw new InterpreterExecutionException("Invalid type for .");
			}
		}
		// put random value between 0 and bound (including)
		else if( s.equals("random") ){
			ForthValue bound = stack.pop();
			int boundInt = getIntFromValue( bound );
						
			// check boundInt isn't negative
			if(boundInt < 0){
				throw new InterpreterExecutionException("Out of bounds on random");
			}
			
			int randValue = getRandom(boundInt + 1);
						
			stack.push( new ForthInt( randValue ) );
		}
	}
	
	/**
	 * Checks whether a Forth word or variable is defined by the robot's script
	 * @param s value of object to check
	 * @return 
	 */
	private boolean isDefined(String s){
		return instructions.containsKey(s);
	}
	

	/**
	 * Testing method for Interpreter
	 */
	public static void main(String[] args){
		Console testConsole = new Console();
		Interpreter test = new Interpreter(testConsole, 2, null);
		
		/*
			Testing robots with kill commented out (since requires TeamList)	
			Testing various case commands

		Robot testRobotNull = new Robot(4, 1, 3, 1, null);
		Robot testRobotNoInit = new Robot(4, 1, 3, 1, new HashMap<String, ForthObject>() );
		
		Map<String, ForthObject> instructionsBlank = new HashMap<String, ForthObject>();
		instructionsBlank.put("init", new ForthWord("init", "") );
		instructionsBlank.put("turn", new ForthWord("turn", "") );
		
		Robot testRobotDumb = new Robot(4, 1, 3, 1, instructionsBlank);
		
		CoordinateMapper coord = new CoordinateMapper(2);
		
		test.loadRobot(testRobotNull);
		test.loadRobot(testRobotNoInit);
		testRobotNoInit.startTurn();
		test.loadRobot(testRobotNoInit);
		
		test.loadRobot(testRobotDumb);
		testRobotDumb.startTurn();
		test.loadRobot(testRobotDumb);
		
		*/
		
		
		Map<String, ForthObject> instructionsStatus = new HashMap<String, ForthObject>();
		instructionsStatus.put("init", new ForthWord("init", "health movesLeft firepower "
				+ "team member + + + + 12 = if .\"true\" else .\"false\" then") );
		
		Robot testRobotStatus = new Robot(4, 1, 3, 1, instructionsStatus);
		testRobotStatus.setTeam(3);
		testRobotStatus.setMemberNum(2);
		
		test.loadRobot(testRobotStatus);
	
			
		/*
		 * These eval blocks were either stepped through in the debugger or originally had
		 * console information printed in the method that's since been deleted
		 */
		
		
		// try {
			// test.eval("47 5 /mod 9 = if 2 = .\"right\" . else then", 0);
			// test.eval("5 4 3 rot drop drop drop drop", 0);
			// test.eval("5 5 5 drop drop drop drop", 0);
			// test.eval("5 dup drop drop drop", 0);
			// test.eval("5 4 swap -", 0);
			// test.eval("4 5 swap -", 0);
			// test.eval("5 4 3 rot - -", 0);
			// test.eval("3 random 3 random +", 0);
			// test.eval("true true or if .\"right\" else .\"wrong\" then", 0);
			// test.eval("true false or if .\"right\" else .\"wrong\" then", 0);
			// test.eval("false true or if .\"right\" else .\"wrong\" then", 0);
			// test.eval("false false or if .\"wrong\" else .\"right\" then", 0);
			// test.eval("true true and if .\"right\" else .\"wrong\" then", 0);
			// test.eval("true false and if .\"wrong\" else .\"right\" then", 0);
			// test.eval("false true and if .\"wrong\" else .\"right\" then", 0);
			// test.eval("true invert if .\"wrong\" else .\"right\" then", 0);
			// test.eval("true if true if .\"true true\" else .\"true false\" then else false if .\"false true\"  else .\"false true\" then then .", 0);
			// test.eval("true if .\"true\" else .\"false\" then .", 0);
			// test.eval("0 begin 1 + begin dup 1 + dup 5 <> until dup 5 <> until", 0);
			// test.eval("0 5 0 do 1 + I 0 do 1 + loop loop 1 +", 0);
		// } catch (InterpreterExecutionException
		//		| InterpreterFailReceiveException
		//		| InterpreterCoordinateException e) {
		//	testConsole.writeLine( e.getMessage() );
		// }
		

				
		System.out.println("Console has recorded: ");
		System.out.println( testConsole.getAll() );
		
		// testing scan & identify routine to see if outputs are correct
		
		/*
		int range = 0;
		int curTarget = 0;
		int target = 4;
		
		
		while( ( range < 4 ) && ( curTarget < target)){
			
			System.out.println("Range: " + range);
			
			List<Integer> thetas = new LinkedList<Integer>();
			
			// build a list of possible thetas
			for(int i = 0; i < (range * 6); i++){
				thetas.add(i);
			}

			// while 4 targets haven't been found, try all thetas, popping one at aa time
			while( ( !thetas.isEmpty() ) && ( curTarget < target) ){
				
				int rand = test.getRandom( thetas.size() );
				int direction = thetas.get(rand);
				thetas.remove(rand);
				
				System.out.println("Theta : " + direction);
				
				Pair <Integer, Integer> scan = coord.getCoords( 0, 0, 
						range, direction, 2 );
				int scanX = scan.left();
				int scanY = scan.right();
				
				System.out.println("Mapped coords: " + scan);
				
				/*
				// if the board can be scanned, get the list of robots on the tile
				if( board.checkIfInBounds(scanX, scanY) ){
					List< Pair<Integer, Integer> > robotsOnTile = 
							board.getRobotList(scanX, scanY);
					
					// Loop through robot list, incrementing target number
					while(curTarget < target){
						rand = test.getRandom( robotsOnTile.size() );
						targetPair = robotsOnTile.get(rand);
						targetCoord = new Pair<Integer, Integer>(range, direction);
						target += 1;			
					}
					
				}
				
				
				curTarget += test.getRandom( 2 );
			}
			
			range += 1;
		}
		*/
	}

}
