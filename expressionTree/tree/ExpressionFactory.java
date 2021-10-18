package expressionTree.tree;

import expressionTree.interfaces.ExpressionFactoryI;
import expressionTree.interfaces.ExpressionNodeI;

public class ExpressionFactory implements ExpressionFactoryI {

	@Override
	public ExpressionNodeI makeLiteralNode(boolean b) {
		return new LiteralExpression( b ) ;
	}

	@Override
	public ExpressionNodeI makeVariableNode(String name) {
		return new VariableExpression( name ) ;
	}

	@Override
	public ExpressionNodeI makeAndNode(ExpressionNodeI left, ExpressionNodeI right) {
		return new AndExpression(left, right);
	}

	@Override
	public ExpressionNodeI makeOrNode(ExpressionNodeI left, ExpressionNodeI right) {
		return new OrExpression(left, right);
	}

	@Override
	public ExpressionNodeI makeNotNode(ExpressionNodeI operand) {
		return new NotExpression( operand ) ;
	}

}
