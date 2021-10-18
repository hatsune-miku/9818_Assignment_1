package expressionTree.interfaces;

import java.io.PrintWriter;

/** Each object implementing this interface represents a boolean expression.
 * 
 * @author Theodore Norvell
 *
 */
public interface ExpressionNodeI {
	/**
	 * Evaluate the expression given a particular environment.
	 * <p>
	 * @Requires All variables used in the expression are
     * guaranteed to be in the environment.
     * <p>
     * @Effects No side effects.
     * <p>
     * @Ensures The result of evaluate will be the value
     * of the expression represented by this node and its descendants
     * in the given environment.
	 */
	public boolean evaluate( Environment env ) ; 
	
	/** Print the expression to the given PrintWriter
	 * <p>
     * @Effects Makes calls to the PrintWriter.
     * <p>
     * @Ensures The expression has been printed via the printWriter
     * Binary operators will be printed surrounded by parentheses.
     */
	public void printTo( PrintWriter p) ; 
}
