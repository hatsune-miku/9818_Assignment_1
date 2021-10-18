/**
 * 
 */
package expressionTree.tree;

import java.io.PrintWriter;

import expressionTree.interfaces.Environment;
import expressionTree.interfaces.ExpressionNodeI;

/**
 * @author theo
 *
 */
public class NotExpression implements ExpressionNodeI {
	
	private ExpressionNodeI operand;

	NotExpression( ExpressionNodeI operand) {
		this.operand = operand ;
	}

	@Override
	public boolean evaluate(Environment env) {
		return ! operand.evaluate( env );
	}

	@Override
	public void printTo(PrintWriter p) {
		p.print( "~ ");
		operand.printTo( p ); 
	}

}
