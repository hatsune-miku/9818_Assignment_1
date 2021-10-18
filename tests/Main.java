package tests;
import expr.* ;

public class Main {

	public static void main(String[] args) {
		ExpressionFactoryI f = new ExpressionFactory() ;
		Expression a = f.multiply( f.constant(2.0), f.x() ) ;
		Expression b = f.divide( f.constant( Math.PI ), f.constant(2.0) ) ;
		Expression c = f.sin( f.add( a, b ) ) ;
		double x = 0.39270 ;
		System.out.println( "The value of " +c+ " at " +x+
				            " is " +c.value(x) ) ;
		double y = 1.0 ;
		System.out.println( "The value of " +c+ " at " +y+
				            " is " +c.value(y) ) ;
	}
}
