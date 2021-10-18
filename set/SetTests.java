package set;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import set.implementation.SetFactory;

class SetTests {
	private SetFactoryI factory = new SetFactory() ;
	
	private void check( SetI s, int lowest, int highest, boolean [] expected ) {
		for( int x = lowest ; x <= highest ; ++x ) {
			if( expected[ x - lowest ] ) {
				assertTrue( s.contains(x), "Expected set to contain " +x+ ", but it did not." ) ;
			} else {
				assertTrue( ! s.contains(x), "Expected set not to contain " +x+ ", but it did." ) ;
			}
		}
	}
	
	private void checkIterator( SetI s, int lowest, int highest, boolean [] expected ) {
		int[] count = new int[highest - lowest + 1] ;
		for( int x : s ) {
			if( lowest <= x && x <= highest) count[ x - lowest] += 1 ;
		}
		for( int x = lowest ; x <= highest ; ++x ) {
			if( expected[ x - lowest ] ) {
				assertTrue( count[x - lowest]==1, "Expected count for " +x+
						    " to be 1, but it is " + count[x - lowest] ) ;
			} else {
				assertTrue( count[x - lowest]==0, "Expected count " +x+
						    ", to be 0 but it is " + count[x - lowest] ) ;
			}
		}
	}
	
	@Test
	void testIterator0() {
		SetI emptySet = factory.makeSet() ;
		Iterator<Integer> it = emptySet.iterator() ;
		assertTrue( ! it.hasNext() ) ;
	}
	
	@Test
	void testEmpty() {
		SetI emptySet = factory.makeSet() ;int lowest = -100 ;
		int highest = +100 ;
		boolean [] expected = new boolean[  highest - lowest + 1 ] ;
		check( emptySet, lowest, highest, expected ) ;
		checkIterator( emptySet, lowest, highest, expected ) ;
	}

	@ParameterizedTest
	@ValueSource( ints = {0, 1, 2, 50, 100, -1, -2, -49, -100} )
	void testSingleton(int x ) {
		SetI s = factory.makeSet( x ) ;
		int lowest = -100 ;
		int highest = +100 ;
		boolean [] expected = new boolean[  highest - lowest + 1 ] ;
		expected[ x - lowest ] = true ;
		check( s, lowest, highest, expected ) ;
		checkIterator( s, lowest, highest, expected ) ;
	}

	@ParameterizedTest
	@CsvSource({
	    "1, 2",
	    "-1, 100",
	    "20, 20",
	    "0, -100" })
	void testUnion2(int x, int y) {
		SetI s = factory.makeSet( x ) ;
		SetI t = factory.makeSet( y ) ;
		SetI u = s.union(t) ;
		int lowest = -100 ;
		int highest = +100 ;
		boolean [] expected = new boolean[  highest - lowest + 1 ] ;
		expected[ x - lowest ] = true ;
		check( s, lowest, highest, expected ) ;
		expected[ y - lowest ] = true ;
		check( u, lowest, highest, expected ) ;
		expected[ x - lowest ] = false ;
		expected[ y - lowest ] = true ;
		check( t, lowest, highest, expected ) ;
		checkIterator( t, lowest, highest, expected ) ;
	}
	
	@Test
	void testUnion10( ) {
		SetI s = factory.makeSet() ;
		int lowest = -100 ;
		int highest = +100 ; 
		boolean [] expected = new boolean[  highest - lowest + 1 ] ;
		for( int x=1 ; x < 10 ; ++x ) {
			s = s.union( factory.makeSet(x) ) ;
			expected[ x-lowest ] = true ;
		}
		check( s, lowest, highest, expected ) ;
	}
	
	@Test
	void testRange( ) {
		SetI s = factory.makeSet(1, 9) ;
		int lowest = -100 ;
		int highest = +100 ; 
		boolean [] expected = new boolean[  highest - lowest + 1 ] ;
		for( int x=1 ; x < 10 ; ++x ) {
			expected[ x-lowest ] = true ;
		}
		check( s, lowest, highest, expected ) ;
		checkIterator( s, lowest, highest, expected ) ;
	}
	
	@Test
	void testIntersection( ) {
		SetI s = factory.makeSet(1, 9) ;
		SetI t = factory.makeSet( ) ;
		int lowest = -100 ;
		int highest = +100 ; 
		boolean [] expected = new boolean[  highest - lowest + 1 ] ;
		for( int x= -3 ; x < 20 ; ++x ) {
			if( x%2 == 0 ) t = factory.makeSet( x ).union( t ) ;
			if( 1 <= x && x <= 9 && x%2 == 0 ) expected[ x-lowest ] = true ;
		}
		check( s.intersection( t ), lowest, highest, expected ) ;
		check( t.intersection( s ), lowest, highest, expected ) ;
		checkIterator( s.intersection( t ), lowest, highest, expected ) ;
		checkIterator( t.intersection( s ), lowest, highest, expected ) ;
	}
	
	@Test
	void testSubtract( ) {
		SetI s = factory.makeSet(1, 9) ;
		SetI t = factory.makeSet( ) ;
		int lowest = -100 ;
		int highest = +100 ; 
		boolean [] expected = new boolean[  highest - lowest + 1 ] ;
		for( int x = -3 ; x < 20 ; ++x ) {
			if( x%2 == 0 )
				t = factory.makeSet( x ).union( t ) ;
			if( (x%2 == 0)  && !(1 <= x && x <= 9) )
				expected[ x-lowest ] = true ;
		}
		check( t.subtract( s ), lowest, highest, expected ) ;
		checkIterator( t.subtract( s ), lowest, highest, expected ) ;
	}
	
	@Test
	void testEqualsAndSubset( ) {
		SetI s = factory.makeSet( 3 ) ;
		s = s.union( factory.makeSet( 5, 2 ) ) ;
		
		SetI t = factory.makeSet(3, 4) ;
		t = t.subtract( factory.makeSet( 4 ) ) ;
		
		assertEquals( s, t ) ;
		assertEquals( t, s ) ;
		assertEquals( s, s ) ;
		
		assertTrue( s.subset(s) ) ;
		assertTrue( s.subset(t) ) ;
		assertTrue( t.subset(s) ) ;
		
		SetI u = s.union( factory.makeSet(11) ) ;

		assertTrue( s.subset(u) ) ;
		assertTrue( ! u.subset(s) ) ;
		

		assertTrue( ! u.equals(s) ) ;
		assertTrue( ! s.equals(u) ) ;
	}
	
	@Test
	void testToString( ) {
		SetI s = factory.makeSet( ) ;
		
		assertEquals( "{}", s.toString() ) ;
		
		s = factory.makeSet( 3 ) ;
			
		assertEquals( "{3}", s.toString() ) ;
		
		s = s.union( factory.makeSet( 5 ) ) ;
		
		String str = s.toString();
			
		assertTrue( str.equals( "{3, 5}") || str.equals( "{5, 3}"),
				    "Expected {3, 5} or {5, 3} but got " +str ) ;
	}
}
