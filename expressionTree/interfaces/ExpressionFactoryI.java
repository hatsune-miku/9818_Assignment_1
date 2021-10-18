package expressionTree.interfaces;

public interface ExpressionFactoryI {
	/** Produce a node representing a literal expression false or true.
	 */
	public ExpressionNodeI makeLiteralNode( boolean b ) ;
	
	/** Produce a node representing a variable.
	 */
	public ExpressionNodeI makeVariableNode( String name ) ;
	
	/** Produce a node representing an 'and' operation.
	 * Precondition: The parameters represent the operands of the operation. 
	 */
	public ExpressionNodeI makeAndNode(
			ExpressionNodeI left,
			ExpressionNodeI right) ;
	
	/** Produce a node representing an 'or' operation.
	 * Precondition: The parameters represent the operands of the operation. 
	 */
	public ExpressionNodeI makeOrNode(
			ExpressionNodeI left,
			ExpressionNodeI right) ;
	
	/** Produce a node representing a 'not' operation.
	 * Precondition: The parameter represents the operand of the operation. 
	 */
	public ExpressionNodeI makeNotNode(
			ExpressionNodeI operand) ;
}
