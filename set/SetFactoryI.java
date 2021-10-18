package set;


public interface SetFactoryI {
	
	/** Create an empty set. */
	public SetI makeSet( ) ;
	
	/** Create a set with one integer in it. */
	public SetI makeSet( int x ) ;
	
	/** Create a set that contains a range of integers
	 * from first to first+count-1. */
	public SetI makeSet( int first, int count ) ;
}
