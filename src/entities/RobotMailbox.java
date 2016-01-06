package entities;

import containers.Pair;
import exceptions.InterpreterExecutionException;
import forth.ForthBool;
import forth.ForthInt;
import forth.ForthStack;
import forth.ForthString;
import forth.ForthValue;
import forth.ForthVariable;

/**
 * This class models a mailbox for a Robot entity
 */
public class RobotMailbox {
	
	// Maximum number of messages
	private static final int MAX_SIZE = 6;
	
	// Size of mailbox
	private int size;
	
	// Stack that serves as mailbox
	private final ForthStack mailbox;
	
	/**
	 * Constructs a new RobotMailbox()
	 */
	public RobotMailbox(){
		size = 0;
		mailbox = new ForthStack();
	}
	
	/**
	 * Attempts to get the top message from the Robot's mailbox
	 * @return <true, value> if successful, <false, null> if not
	 * @throws InterpreterExecutionException 
	 */
	public Pair<Boolean, ForthValue> getMessage() 
			throws InterpreterExecutionException{
		Boolean retBool = false;
		ForthValue retVal = null;
		
		if( !isEmpty() ){
			size -= 1;
			retBool = true;
			retVal = mailbox.pop();
		}
		
		return new Pair<Boolean, ForthValue>(retBool, retVal);
	}
	
	/**
	 * 
	 * @param message
	 * @return true if message successfully added, false if not
	 * @throws InterpreterExecutionException 
	 */
	public boolean pushMessage(ForthValue message) throws InterpreterExecutionException{
		boolean successful = false;
		
		if( ! message.isLiteral() ){
			throw new InterpreterExecutionException("Messages must be literals");
		}
		
		if( !isFull() ){
			size += 1;
			mailbox.push(message);
			successful = true;
		}
		
		return successful;
	}
	
	/**
	 * @return true if mailbox is empty, false if not
	 */
	public boolean isEmpty(){
		return ( 0 == size);
	}
	
	/**
	 * @return true if mailbox is empty, false if not
	 */
	public boolean isFull(){
		return ( MAX_SIZE == size);
	}
	
	/**
	 * Testing method for RobotMailbox
	 */
	public static void main(String[] args){
		RobotMailbox test = new RobotMailbox();
		
		assert( test.isEmpty() );
		try {
			assert( test.getMessage().left() == false );
		} catch (InterpreterExecutionException e) {
			System.out.println( e.getMessage() ); // Should not trigger
		}
		
		try{
			assert( test.pushMessage( new ForthString("Accept") ) );
			assert( test.getMessage().left() );
		}catch (InterpreterExecutionException e) {
			System.out.println( e.getMessage() );
		}
		
		try{
			assert( test.pushMessage( new ForthBool(true) ) );
			assert( test.getMessage().left() );
		}catch (InterpreterExecutionException e) {
			System.out.println( e.getMessage() );
		}
		
		try{
			assert( test.pushMessage( new ForthVariable("throw") ) );
		}catch (InterpreterExecutionException e) {
			System.out.println( "Pushing variable should result in exception" );
			System.out.println( e.getMessage() );
		}
		
		for(int i = 0; i < 6; i++){
			try {
				assert( test.pushMessage( new ForthInt(i) ) );
			} catch (InterpreterExecutionException e) {
				System.out.println( e.getMessage() );
			}
		}
		
		assert ( test.isFull() );

		try {
			assert( !test.pushMessage( new ForthInt(5) ) );
		} catch (InterpreterExecutionException e) {
			System.out.println( e.getMessage() );
		}
		
		for(int i = 5; i > -1; i--){
			try {
				assert( ( (ForthInt) test.getMessage().right() ).getValue() == i );
			} catch (InterpreterExecutionException e) {
				System.out.println( e.getMessage() ); // Should not trigger
			}
		}
		
		try {
			assert( test.getMessage().left() == false );
		} catch (InterpreterExecutionException e) {
			System.out.println( e.getMessage() ); // Should not trigger
		}
	}
}
