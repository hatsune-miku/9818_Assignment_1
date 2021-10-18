package util;

public class Assert {
	public static void check( boolean b ) {
		if( !b ) throw new AssertionError() ;
	}
	
	public static void check( boolean b, String message ) {
		if( !b ) throw new AssertionError(message) ;
	}
	
	public static void toBeDone() {
		throw new AssertionError("To be done.") ;
	}
}
