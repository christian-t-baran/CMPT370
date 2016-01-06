package forth;

/**
 * This class models a boolean as a ForthValue
 */
public class ForthBool extends ForthValue {

	// value of the ForthBool
	private final boolean value;
	
	/**
	 * Constructs a ForthBool with value of bool
	 * @param v value for ForthBool
	 */
	public ForthBool(boolean v){
		value = v;
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
	 * @return false
	 */
	@Override
	public boolean isInt() {
		return false;
	}

	/**
	 * @return true
	 */
	@Override
	public boolean isBool() {
		return true;
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
	 * @return "literalBool;
	 */
	@Override
	public String getName() {
		return "literalBool";
	}
	
	public boolean getValue() {
		return value;
	}

	/**
	 * Main method for testing ForthBool
	 */
	public static void main(String[] args){
		ForthBool testTrue = new ForthBool(true);
		ForthBool testFalse = new ForthBool(false);
		
		// Test getting values
		assert( testTrue.getValue() );
		assert( !testFalse.getValue() );
		
		// Test is_()
		assert( testTrue.isBool() );
		assert( testTrue.isLiteral() );
		assert( !testTrue.isInt() );
		assert( !testTrue.isString() );
		assert( !testTrue.isVariable() );
		assert( !testTrue.isWord() );
		
	}
	
}
