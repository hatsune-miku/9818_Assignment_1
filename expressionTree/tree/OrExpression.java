package expressionTree.tree;

import expressionTree.interfaces.Environment;
import expressionTree.interfaces.ExpressionNodeI;

import java.io.PrintWriter;

public class OrExpression implements ExpressionNodeI {
    protected ExpressionNodeI operand0;
    protected ExpressionNodeI operand1;

    public OrExpression(
        ExpressionNodeI operand0,
        ExpressionNodeI operand1
    ) {
        this.operand0 = operand0;
        this.operand1 = operand1;
    }

    @Override
    public boolean evaluate(Environment env) {
        return operand0.evaluate(env)
            || operand1.evaluate(env);
    }

    @Override
    public void printTo(PrintWriter p) {
        operand0.printTo(p);
        p.write(" \\/ ");
        operand1.printTo(p);
    }
}
