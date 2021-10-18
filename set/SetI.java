package set;


public interface SetI extends Iterable<Integer> {
	
	/** Does this set contain x? */
	boolean contains( int x ) ;
	
	/** How many items are in this set. */
	int size() ;
	
	/** Returns a set that contains everything in this set and also every thing in other. */
	SetI union( SetI other ) ;
	
	/** Returns a set that contains everything in this set that is also in other. */
	SetI intersection( SetI other ) ;
	
	/** Returns a set that contains everything in this set that is not in the other. */
	SetI subtract( SetI other ) ;
	
	/** Returns true if and only if everything in this set is also in other */
	boolean subset( SetI other ) ;
	
	/** Convert to a string. */
	@Override String toString( ) ;
	
	/** Is other a set with the same contents. */
	@Override boolean equals( Object other ) ;
}
