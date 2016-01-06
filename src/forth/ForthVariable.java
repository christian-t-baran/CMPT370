package forth;

/**
 * This class models a variable in the Forth Language
 */
public class ForthVariable extends ForthValue{

	// Instance values
	private final String name;
	
	private Integer intPart;
	private String stringPart;
	private Boolean boolPart;
	
	/**
	 * Constructs a ForthVariable and initializes it to numeric value 0
	 * @param name of ForthVariable
	 */
	public ForthVariable(String name){
		this.name = name;
		
		intPart = 0;
		stringPart = null;
		boolPart = null;
	}

	/**
	 * @return True if ForthVariable stores a String, False if not
	 */
	public boolean isInt(){
		return intPart != null;
	}
	
	/**
	 * @return True if ForthVariable stores a String, False if not
	 */
	public boolean isString(){
		return stringPart != null;
	}
	
	/**
	 * @return True if ForthVariable stores a boolean, False if not
	 */
	public boolean isBool(){
		return boolPart != null;
	}
	
	/**
	 * Sets the value in the ForthVariable to i
	 * @param i an int
	 */
	public void setInt(int i){
		intPart = i;
		
		stringPart = null;
		boolPart = null;
	}
	
	/**
	 * Sets the value in the ForthVariable to s
	 * @param s a String
	 */
	public void setString(String s){
		stringPart = s;
		
		intPart = null;
		boolPart = null;
	}
	
	/**
	 * Sets the value in the ForthVariable to b
	 * @param b a boolean
	 */
	public void setBool(boolean b){
		boolPart = b;
		
		intPart = null;
		stringPart = null;
	}
	
	/**
	 * @return int value stored in object
	 * @precond Value stored must be int
	 */
	public int getInt(){
		assert(intPart != null);
		assert(boolPart == null);
		assert(stringPart == null);
		
		return intPart;
	}
	
	/**
	 * @return String value stored in object
	 * @precond Value stored must be String
	 */
	public String getString(){
		assert(stringPart != null);
		assert(intPart == null);
		assert(boolPart == null);
		
		return stringPart;
	}
	
	/**
	 * @return boolean value stored in object
	 * @precond Value stored must be boolean
	 */
	public boolean getBool(){
		assert(boolPart != null);
		assert(intPart == null);
		assert(stringPart == null);
		
		return boolPart;
	}
	
	/**
	 * @return name associated with ForthVariable
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return true
	 */
	public boolean isVariable(){
		return true;
	}
	
	/**
	 * @return false
	 */
	public boolean isWord(){
		return false;
	}
	
	/**
	 * @return false
	 */
	@Override
	public boolean isLiteral() {
		return false;
	}
	
	/**
	 * Main testing method for ForthVariable
	 */
	public static void main(String[] args) {
		ForthVariable test = new ForthVariable("test");
		
		
		// Test is_() methods
		assert( test.isVariable() );
		assert( !test.isWord() );
		
		assert( test.getName().equals("test") );
		assert( test.isInt() );
		assert( !test.isBool() );
		assert( !test.isString() );
		assert( !test.isLiteral() );
		
		assert( test.getInt() == 0 );
		
		// These will fail
		// test.getBool();
		// test.getString();
		
		test.setInt(5);
		
		assert( test.getInt() == 5 );
		
		// These will fail
		// test.getBool();
		// test.getString();
		
		test.setBool(true);
		
		assert( test.getBool() );
		
		assert( !test.isInt() );
		assert( test.isBool() );
		assert( !test.isString() );
		
		// These will fail
		// test.getInt();
		// test.getString();
		
		test.setString("test");
		
		assert( test.getString().equals("test") );

		assert( !test.isInt() );
		assert( !test.isBool() );
		assert( test.isString() );
		
		// These will fail
		// test.getInt();
		// test.getString();
	}



}
