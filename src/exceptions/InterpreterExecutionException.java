package exceptions;

/**
 * This exception signifies that an error occurred attempting to execute a Robot script
 */
public class InterpreterExecutionException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InterpreterExecutionException(String message){
		super(message);
	}
	
}
