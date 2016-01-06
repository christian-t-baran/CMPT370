package forth;

/**
 * This class models an integer as a ForthValue
 */
public class ForthInt extends ForthValue {

	// value of the ForthInt
	private final int value;
	
	/**
	 * Constructs a ForthInt with value i
	 * @param i value of ForthInt
	 */
	public ForthInt(int i){
		value = i;
	}
	
	/**
	 * @return false
	 */	
	@Override
	public boolean isWord() {
		return false;
	}

	/**
	 * @return false
	 */
	@Override
	public boolean isVariable() {
		return false;
	}

	/**
	 * @return true
	 */
	@Override
	public boolean isInt() {
		return true;
	}
	
	/**
	 * @return false
	 */
	@Override
	public boolean isBool() {
		return false;
	}
	
	/**
	 * @return false
	 */
	@Override
	public boolean isString() {
		return false;
	}

	/**
	 * @return true
	 */
	@Override
	public boolean isLiteral() {
		return true;
	}
	
	/**
	 * @return value of ForthInt
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * @return "literalInt"
	 */
	@Override
	public String getName() {
		return "literalInt";
	}

	/**
	 * Main method for testing ForthInt
	 */
	public static void main(String[] args){
		ForthInt test = new ForthInt(5);
		
		// Test getting values
		assert( test.getValue() == 5);
		assert( !( test.getValue() == 6) );
		
		// Test is_()
		assert( test.isLiteral() );
		assert( test.isInt() );
		assert( !test.isBool() );
		assert( !test.isString() );
		assert( !test.isVariable() );
		assert( !test.isWord() );
		
	}

}
