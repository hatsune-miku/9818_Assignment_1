package util;
import java.util.regex.*;
import static org.junit.Assert.*;


public class Matches {
	public static void assertMatches( String regExp, Object obj, String message ) {
		String str = obj.toString() ;
		assertTrue( message,
				Pattern.matches( regExp, str ) ) ; 
	}
	public static void assertMatches( String regExp, Object obj) {
		assertMatches( regExp, obj,
				"String '" +obj+ "' does not match regular expression /" +regExp+ "/") ;
	}
}
