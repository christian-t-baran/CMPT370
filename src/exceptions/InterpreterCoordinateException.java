package exceptions;

/**
 * This exception indicates that a Robot script attempted to make an illegal move
 */
public class InterpreterCoordinateException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InterpreterCoordinateException(String message){
		super(message);
	}
	
}
