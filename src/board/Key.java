package board;

/**produces the key for the hashMap of the board*/
public class Key {
	//x and y coordinate
    private final int x;
    private final int y;

    //constructor of the key
    public Key(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * checks if 2 keys are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return x == key.x && y == key.y;
    }

    /**
     *This creates a hashcode for the key 
     */
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
