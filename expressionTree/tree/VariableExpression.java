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
public class VariableExpression implements ExpressionNodeI {
	
	private String name;

	VariableExpression( String name ) {
		this.name = name ; 
	}

	@Override
	public boolean evaluate(Environment env) {
		return env.get( name ) ;
	}

	@Override
	public void printTo(PrintWriter p) {
		p.print( name );
	}

}
