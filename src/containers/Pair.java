package containers;

/**
 * This class models a generic pair of objects
 * @param <S> Type of left object of pair
 * @param <T> Type of right object of pair
 */
public class Pair<S, T> {
	
	// Member variables
	private final S left;
	private final T right;
	
	/**
	 * Constructor for Pair
	 * @param left left part of pair
	 * @param right right part of pair
	 */
	public Pair(S left, T right){
		this.left = left;
		this.right = right;
	}
	
	
	/**
	 * @return left object in pair
	 */
	public S left(){
		return left;
	}
	
	/**
	 * @return right object in pair
	 */
	public T right(){
		return right;
	}
	
	/**
	 * @return String representation of Pair
	 */
	public String toString(){
		return "< " + left.toString() + ", " + right.toString() + " >";
	}
	
	/**
	 * @return true if object is equal, false if not
	 */
	public boolean equals(Object o){
		boolean equals = false;
		
		if( o instanceof Pair){
			Pair<S, T> p = ( Pair<S, T> ) o;
			equals = ( left.equals( p.left() ) ) && ( right.equals( p.right() ) );
		}
		
		return equals;
	}
}
