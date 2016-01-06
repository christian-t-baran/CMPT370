package exceptions;

/**
 * Exception that indicates a Robot failed to receive a message
 */
public class InterpreterFailReceiveException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InterpreterFailReceiveException(String message){
		super(message);
	}
}
