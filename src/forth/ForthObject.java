package forth;

/**
 * This class models an abstract object in Forth
 */
public abstract class ForthObject {
	
	// Methods for determining what type of object it is
	public abstract boolean isWord();
	public abstract boolean isVariable();
	
	// Method to get name of object
	public abstract String getName();
}
