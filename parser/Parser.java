package parser;

import expr.Expression;
import expr.ExpressionFactoryI;

public class Parser {
	static public Expression parse(String str, ExpressionFactoryI factory )
	        throws ParseException, TokenMgrError, NumberFormatException
    {

        return ExpParser.parse(str, factory ) ;
    }
}