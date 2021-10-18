package tests;
import static org.junit.Assert.*;

import expr.Expression;
import expr.ExpressionFactory;
import org.junit.Test;

import static util.Matches.* ;
import expr.ExpressionFactoryI ;


public class TestExpressionFactory {
	ExpressionFactoryI f = new ExpressionFactory() ;

	@Test public void testConstant() {
		double v = 1.2345 ;
		Expression a = f.constant( v ) ;
		assertEquals(v, a.value( 0.0 ), 0.0001 ) ;
		String str = a.toString() ;
		double d = Double.parseDouble( str ) ;
		assertEquals(v, d, 0.0001 ) ;
	}

	@Test public void testX() {
		Expression a = f.x( ) ;
		double v = -6.876e100  ;
		assertEquals(v, a.value( v ), 0.0001 ) ;
		String str = a.toString() ;
		assertEquals( "x", str ) ;
	}

	@Test public void testAdd() {
		double v = 9.0 ;
		double x = 0.5 ;
		Expression a = f.constant(9.0) ;
		Expression b = f.x( ) ;
		Expression c = f.add( a, b ) ;
		
		assertEquals(v+x, c.value( x ), 0.0001 ) ;
		
		assertMatches( " *9[.]0* *[+] *x *", c) ;
	}

	@Test public void testSubtract() {
		double v = 9.0 ;
		double x = 0.5 ;
		Expression a = f.constant(9.0) ;
		Expression b = f.x( ) ;
		Expression c = f.subtract( a, b ) ;
		
		assertEquals(v-x, c.value( x ), 0.0001 ) ;
		
		assertMatches( " *9[.]0* *- *x *", c) ;
	}

	@Test public void testMultiply() {
		double v = 9.0 ;
		double x = 0.5 ;
		Expression a = f.constant(9.0) ;
		Expression b = f.x( ) ;
		Expression c = f.multiply( a, b ) ;
		
		assertEquals(v*x, c.value( x ), 0.0001 ) ;
		
		assertMatches( " *9[.]0* *[*]? *x *", c) ;
	}
	
	@Test public void testDivide() {
		double v = 9.0 ;
		double x = 0.5 ;
		Expression a = f.constant(9.0) ;
		Expression b = f.x( ) ;
		Expression c = f.divide( a, b ) ;
		
		assertEquals(v/x, c.value( x ), 0.0001 ) ;
		
		assertMatches( " *9[.]0* */ *x *", c) ;
	}

	
	@Test public void testParens() {
		double v = 9.0 ;
		double x = 0.5 ;
		Expression a = f.constant(9.0) ;
		Expression b = f.x( ) ;
		Expression c = f.add( a, b ) ;
		Expression d = f.parenthesized(c) ;
		assertEquals(v+x, d.value( x ), 0.0001 ) ;
		
		assertMatches( " *[(] *9[.]0* *[+] *x *[)] *", d) ;
	}
	
	@Test public void testSin() {
		double x = 0.5 ;
		Expression a = f.x( ) ;
		Expression b = f.sin( a ) ;
		assertEquals(Math.sin(x), b.value( x ), 0.0001 ) ;
		
		assertMatches( " *sin[(]? *x *[)]? *", b) ;
	}

	
	@Test public void testCos() {
		double x = 0.5 ;
		Expression a = f.x( ) ;
		Expression b = f.cos( a ) ;
		assertEquals(Math.cos(x), b.value( x ), 0.0001 ) ;
		
		assertMatches( " *cos[(]? *x *[)]? *", b) ;
	}

	
	@Test public void testTan() {
		double x = 0.5 ;
		Expression a = f.x( ) ;
		Expression b = f.tan( a ) ;
		assertEquals(Math.tan(x), b.value( x ), 0.0001 ) ;
		
		assertMatches( " *tan[(]? *x *[)]? *", b) ;
	}

	
	@Test public void testLn() {
		double x = 0.5 ;
		Expression a = f.x( ) ;
		Expression b = f.ln( a ) ;
		assertEquals(Math.log(x), b.value( x ), 0.0001 ) ;
		
		assertMatches( " *ln[(]? *x *[)]? *", b) ;
	}

	
	@Test public void testExp() {
		double x = 0.5 ;
		Expression a = f.x( ) ;
		Expression b = f.exp( a ) ;
		assertEquals(Math.exp(x), b.value( x ), 0.0001 ) ;
		
		assertMatches( " *exp[(]? *x *[)]? *", b) ;
	}

	@Test public void testCompound() {
		Expression a = f.multiply( f.constant(2.0), f.x() ) ;
		Expression b = f.divide( f.constant( Math.PI ), f.constant(2.0) ) ;
		Expression c = f.sin( f.add( a, b ) ) ;
		double x = 0.39270 ;
		assertEquals(Math.sin( 2.0*x + Math.PI/2), c.value( x ), 0.0001 ) ;
		
		assertMatches( " *sin[(]? *2[.]0* *[*]? *x *[+] *(\u03C0|3.141592[0-9]*) */ *2[.]0* *[)]? *", c) ;
	}
}
