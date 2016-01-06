package forth;

/**
 * This class is an abstract entity that models a value in the Forth language
 */
public abstract class ForthValue extends ForthObject {

	// Methods for determining type
	@Override
	abstract public boolean isWord();
	@Override
	abstract public boolean isVariable();
	abstract public boolean isInt();
	abstract public boolean isBool();
	abstract public boolean isString();
	abstract public boolean isLiteral();
	
	// Method to get name of value (in case of variables)
	@Override
	abstract public String getName();
	

}
