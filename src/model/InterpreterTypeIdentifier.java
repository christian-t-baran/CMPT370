package model;

/**
 * This class performs type identification on tokens for the Interpreter class
 */
public class InterpreterTypeIdentifier {
	
	/**
	 * Checks if a token represents an integer
	 * @param s String to check
	 * @return true if it is an integer, false if not
	 */
	public boolean isInt(String s){
		return s.matches("[-+]?\\d*\\.?\\d+"); //regex check if number
	}
	
	/**
	 * Checks if a token represents a boolean
	 * @param s String to check
	 * @return true if it is a boolean, false if not
	 */
	public boolean isBool(String s){
		return ( s.equals("true") 
				|| s.equals("false") );
	}
	
	/**
	 * Checks if a token represents an String
	 * @param s String to check
	 * @return true if it is a String, false if not
	 */
	public boolean isString(String s){
		return s.startsWith(".\"");
	}
	
	/**
	 * Checks if a token represents a literal
	 * @param s String to check
	 * @return true if it is a literal, false if not
	 */
	public boolean isLiteral(String s){
		return( isInt(s)
				|| isBool(s) 
				|| isString(s) 
				|| s.equals("I") );
	}
	
	/**
	 * Checks if a token represents a loop
	 * @param s String to check
	 * @return true if it is a loop, false if not
	 */
	public boolean isLoop(String s){
		return ( s.equals("do")
				|| s.equals("begin") );
	}
	
	/**
	 * Checks if a token represents a conditional
	 * @param s String to check
	 * @return true if it is a conditional, false if not
	 */
	public boolean isConditional(String s){
		return s.equals("if");
	}	
	
	/**
	 * Checks if a token represents a stack word
	 * @param s String to check
	 * @return true if it is a stack word, false if not
	 */
	public boolean isStackWord(String s){
		return ( s.equals("drop")
				|| s.equals("dup") 
				|| s.equals("swap")
				|| s.equals("rot") );
	}
	
	/**
	 * Checks if a token represents an arithmetic word
	 * @param s String to check
	 * @return true if it is an arithmetic word, false if not
	 */
	public boolean isArithmeticWord(String s){
		return( s.equals("+")
				|| s.equals("-")
				|| s.equals("*")
				|| s.equals("/mod"));
	}
	
	/**
	 * Checks if a token represents a comparison word
	 * @param s String to check
	 * @return true if it is a comparison word, false if not
	 */
	public boolean isComparisonWord(String s){
		return( s.equals("<")
				|| s.equals("<=")
				|| s.equals("=")
				|| s.equals("<>")
				|| s.equals("=>")
				|| s.equals(">") );
	}
	
	/**
	 * Checks if a token represents a logic word
	 * @param s String to check
	 * @return true if it is a logic word, false if not
	 */
	public boolean isLogic(String s){
		return( s.equals("and")
				|| s.equals("or")
				|| s.equals("invert") );
	}
	
	/**
	 * Checks if a token represents a utility word
	 * @param s String to check
	 * @return true if it is a utility word, false if not
	 */
	public boolean isUtility(String s){
		return( s.equals(".")
				|| s.equals("random") );
	}
	
	/**
	 * Checks if a token represents a variable word
	 * @param s String to check
	 * @return true if it is a variable word, false if not
	 */
	public boolean isVariableWord(String s){
		return( s.equals("?")
				|| s.equals("!") );
	}
	
	/**
	 * Checks if a token represents a Robot status word
	 * @param s String to check
	 * @return true if it is a status word, false if not
	 */
	public boolean isRobotStatus(String s){
		return( s.equals("health") 
				|| s.equals("movesLeft")
				|| s.equals("firepower")
				|| s.equals("team")
				|| s.equals("member") );
	}
	
	/**
	 * Checks if a token represents a Robot action word
	 * @param s String to check
	 * @return true if it is an action word, false if not
	 */
	public boolean isRobotAction(String s){
		return( s.equals("shoot!")
				|| s.equals("move!")
				|| s.equals("scan!")
				|| s.equals("identify!") 
				|| s.equals("hex") );
	}
	
	/**
	 * Checks if a token represents a Robot mailbox word
	 * @param s String to check
	 * @return true if it is a mailbox word, false if not
	 */
	public boolean isMailbox(String s){
		return( s.equals("send!")
				|| s.equals("mesg?")
				|| s.equals("recv!") );
	}
	
	/**
	 * Checks if a token represents a Robot word
	 * @param s String to check
	 * @return true if it is a Robot word, false if not
	 */
	public boolean isRobotWord(String s){
		return( isRobotStatus(s) 
				|| isRobotAction(s)
				|| isMailbox(s) );
	}
	
	/**
	 * Checks if a token represents a System word
	 * @param s String to check
	 * @return true if it is a System word, false if not
	 */
	public boolean isSystemWord(String s){
		return( isStackWord(s)
				|| isArithmeticWord(s) 
				|| isComparisonWord(s) 
				|| isLogic(s)
				|| isUtility(s)
				|| isVariableWord(s)
				|| isRobotWord(s) );
	}
	
	/**
	 * Testing method for InterpreterTypeIdentifier
	 */
	public static void main(String[] args){
		InterpreterTypeIdentifier test = new InterpreterTypeIdentifier();
		
		String testInt = "5";
		String testTrue = "true";
		String testFalse = "false";
		String testString = ".\"This is a string\"";
		
		// Integer tests
		assert( test.isInt(testInt) );
		assert( !test.isInt(testTrue) );
		assert( !test.isInt(testFalse) );
		assert( !test.isInt(testString) );
		
		// Boolean tests
		assert( test.isBool(testTrue) );
		assert( test.isBool(testFalse) );
		assert( !test.isInt(testTrue) );
		assert( !test.isString(testTrue) );

		// String tests
		assert( test.isString(testString) );
		assert( !test.isString(testInt) );
		assert( !test.isString(testFalse) );
		assert( !test.isString(testTrue) );
		
		
		
		// Literal tests
		assert( test.isLiteral(testInt) );
		assert( test.isLiteral(testTrue) );
		assert( test.isLiteral(testFalse) );
		assert( test.isLiteral(testString) );
		assert( test.isLiteral("I") );
		assert( !test.isLiteral("regularword") );
		
		// Loop tests
		assert( test.isLoop("do") );
		assert( test.isLoop("begin") );
		assert( !test.isLoop("do-wop") );
		
		// Conditional tests
		assert( test.isConditional("if") );
		assert( !test.isConditional("ifn") );
		
		// Stack word tests
		assert( test.isStackWord("drop") );
		assert( test.isStackWord("dup") );
		assert( test.isStackWord("swap") );
		assert( test.isStackWord("rot") );
		assert( !test.isStackWord("roti") );
	
		// Arithmetic word tests
		assert( test.isArithmeticWord("+") );
		assert( test.isArithmeticWord("-") );
		assert( test.isArithmeticWord("*") );
		assert( test.isArithmeticWord("/mod") );
		assert( !test.isArithmeticWord("/") );
		
		// Comparison word tests
		assert( test.isComparisonWord("<") );
		assert( test.isComparisonWord("<=") );
		assert( test.isComparisonWord("=") );
		assert( test.isComparisonWord("<>") );
		assert( test.isComparisonWord("=>") );
		assert( test.isComparisonWord(">") );
		assert( !test.isComparisonWord("><") );

		// Logic word tests
		assert( test.isLogic("and") );
		assert( test.isLogic("or") );
		assert( test.isLogic("invert") );
		assert( !test.isLogic("an") );

		// Utility word tests
		assert( test.isUtility(".") );
		assert( test.isUtility("random") );
		assert( !test.isUtility(".\"") );
		
		// Utility word tests
		assert( test.isVariableWord("?") );
		assert( test.isVariableWord("!") );
		assert( !test.isVariableWord("??") );
		
		// Robot Status word tests
		assert( test.isRobotStatus("health") );
		assert( test.isRobotStatus("movesLeft") );
		assert( test.isRobotStatus("firepower") );
		assert( test.isRobotStatus("team") );
		assert( test.isRobotStatus("member") );
		assert( !test.isRobotStatus("Health") );
	
		// Robot Action word tests
		assert( test.isRobotAction("shoot!") );
		assert( test.isRobotAction("move!") );
		assert( test.isRobotAction("scan!") );
		assert( test.isRobotAction("identify!") );
		assert( test.isRobotAction("hex") );
		assert( !test.isRobotAction("shoot") );

		// Robot Mailbox word tests
		assert( test.isMailbox("send!") );
		assert( test.isMailbox("mesg?") );
		assert( test.isMailbox("recv!") );
		assert( !test.isMailbox("send?") );


		// Robot word tests
		assert( test.isRobotWord("health") );
		assert( test.isRobotWord("shoot!") );
		assert( test.isRobotWord("send!") );
		assert( !test.isRobotWord("dup") );
		
		// System word tests
		assert( test.isSystemWord("dup") );
		assert( test.isSystemWord("+") );
		assert( test.isSystemWord("<") );
		assert( test.isSystemWord("and") );
		assert( test.isSystemWord("health") );
		assert( test.isSystemWord(".") );
		assert( test.isSystemWord("?") );
		assert( !test.isSystemWord("do") );		
	}
}
