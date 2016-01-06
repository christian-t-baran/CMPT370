package forth;

/**
 * This class models a word in the Forth language
 */
public class ForthWord extends ForthObject{

	// Instance variables for ForthWord
	private final String name;
	private final String body;
	
	/**
	 * Constructor for a ForthWord
	 * @param name of ForthWord
	 * @param body of ForthWord
	 */
	public ForthWord(String name, String body){
		this.name = name;
		this.body = body;
	}
	
	
	/**
	 * @return The body of the ForthWord
	 */
	public String getBody(){
		return body;
	}
	
	/**
	 * @return The name of the ForthWord
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return true
	 */
	public boolean isWord(){
		return true;
	}
	
	/**
	 * @return false
	 */
	public boolean isVariable(){
		return false;
	}
	
	/**
	 * Main testing method for ForthWord
	 */
	public static void main(String[] args){
		ForthWord test = new ForthWord("testName", "testBody");
		
		// Test ForthWord methods
		assert( test.isWord() );
		assert( !test.isVariable() );
		assert( test.getName().equals("testName") );
		assert( test.getBody().equals("testBody") );
		
	}
	
}
