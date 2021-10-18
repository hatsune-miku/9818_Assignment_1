package expressionTree.tree;

import java.io.PrintWriter;

import expressionTree.interfaces.Environment;
import expressionTree.interfaces.ExpressionNodeI;

public class LiteralExpression implements ExpressionNodeI {
	private boolean value;

	LiteralExpression( boolean value ) {
		this.value = value ;
	}

	@Override
	public boolean evaluate(Environment env) {
		return value;
	}

	@Override
	public void printTo(PrintWriter p) {
		if( value ) p.print("true") ;
		else p.print("false");
	}

}
