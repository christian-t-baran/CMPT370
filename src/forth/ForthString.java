package forth;

public class ForthString extends ForthValue {

	private final String value;
	
	/**
	 * Constructs a ForthString with value v
	 * @param v value of ForthString
	 */
	public ForthString(String v){
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
	 * @return false
	 */
	@Override
	public boolean isBool() {
		return false;
	}

	/**
	 * @return true
	 */
	@Override
	public boolean isString() {
		return true;
	}

	/**
	 * @return true
	 */
	@Override
	public boolean isLiteral() {
		return true;
	}

	/**
	 * 
	 */
	@Override
	public String getName() {
		return "literalString";
	}
	
	/**
	 * Returns value of ForthString
	 * @return
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Main method for testing ForthString
	 */
	public static void main(String[] args){
		ForthString test = new ForthString("test");
		
		// Test getValue()
		assert( test.getValue().equals("test") );
		assert( !( test.getValue().equals("Fail") ) );
		
		// Test is_()
		assert( test.isLiteral() );
		assert( test.isString() );
		assert( !test.isInt() );
		assert( !test.isBool() );
		assert( !test.isVariable() );
		assert( !test.isWord() );		
	}

}
