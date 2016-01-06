package forth;

import java.util.Stack;

import exceptions.InterpreterExecutionException;

/**
 * This class models a stack of ForthValues

 */
public class ForthStack {
	
	// Underlying stack
	private Stack<ForthValue> stack;
	
	/**
	 * Constructs a new ForthStack
	 */
	public ForthStack(){
		stack = new Stack<ForthValue>();
	}
	
	/**
	 * Retrieves the top value from the stack
	 * @return top value in ForthStack
	 * @precond stack must not be empty
	 * 	 * @throws InterpreterExecutionException

	 */
	public ForthValue pop() throws InterpreterExecutionException{
		if( stack.isEmpty() ){
			throw new InterpreterExecutionException("Attempting to pop from empty stack");
		}
		return stack.pop();
	}
	
	/**
	 * Pushes v onto the stack
	 * @param v
	 */
	public void push(ForthValue v){
		stack.push(v);
	}
	
	/**
	 * Clears the stack
	 */
	public void clear(){
		stack.clear();
	}
	
	public static void main(String[] args){
		ForthStack testStack = new ForthStack();
		
		try{
			testStack.pop();
		}
		catch( Exception e ){
			assert( e.getMessage().equals("Attempting to pop from empty stack") );
		}
		
		testStack.push( new ForthInt(5) );
		
		try{
			testStack.pop();
			System.out.print("Popping successful");
		}
		catch( Exception e ){
			assert( e.getMessage().equals("Attempting to pop from empty stack") );
		}
		
		
	}
	
}
