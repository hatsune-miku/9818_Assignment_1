package tests;

import static org.junit.Assert.*;

import parser.Parser;
import org.junit.*;
import org.junit.rules.ExpectedException;

import expr.* ;
import parser.* ;

public class TestParser {
	
	@Rule public final ExpectedException thrown = ExpectedException.none();
	
	private ExpressionFactoryI factory = new ExpressionFactory() ;

	@Test
	public void test0() throws ParseException
	{
		Expression a = Parser.parse( "4 - .5 - 6. - 3.5", factory ) ;
		assertEquals(4 - .5 - 6. - 3.5, a.value(0), 0.0001 );
	}
	
	@Test
	public void testMults() throws ParseException
	{
		Expression a = Parser.parse( "-2.1 x + -4 * -3 + 9 (x + 8)", factory ) ;
		double x = .234 ;
		assertEquals(-2.1 * x + -4 * -3 + 9 * (x + 8),
				a.value(x), 0.0001 );
	}

	@Test
	public void testBigNumbers() throws ParseException
	{
		Expression a = Parser.parse( "1e20", factory ) ;
		assertEquals( 1e20, a.value(0), 0.00001 ) ;
		a = Parser.parse( "1e1000", factory ) ;
		assertEquals( Double.POSITIVE_INFINITY, a.value(0), 0.00001 ) ;
	}
	
	@Test
	public void testBadCharacter() throws ParseException
	{
		thrown.expect( ParseException.class );
		Parser.parse( "y", factory ) ;
	}
	
	@Test
	public void testIncomplete() throws ParseException
	{
		thrown.expect( ParseException.class );
		Parser.parse( "(1 + 2", factory ) ;
	}
	
	@Test
	public void testSyntaxError() throws ParseException
	{
		thrown.expect( ParseException.class );
		Parser.parse( "* 9", factory ) ;
	}

}