package expressionTree.tree;

import expressionTree.interfaces.Environment;
import expressionTree.interfaces.ExpressionNodeI;

import java.io.PrintWriter;

public class AndExpression implements ExpressionNodeI {
    protected ExpressionNodeI operand0;
    protected ExpressionNodeI operand1;

    public AndExpression(
        ExpressionNodeI operand0,
        ExpressionNodeI operand1
    ) {
        this.operand0 = operand0;
        this.operand1 = operand1;
    }

    @Override
    public boolean evaluate(Environment env) {
        return operand0.evaluate(env)
            && operand1.evaluate(env);
    }

    @Override
    public void printTo(PrintWriter p) {
        boolean f0 = operand0 instanceof OrExpression;
        boolean f1 = operand1 instanceof OrExpression;

        if (f0) p.write("(");
        operand0.printTo(p);
        if (f0) p.write(")");

        p.write(" /\\ ");

        if (f1) p.write("(");
        operand1.printTo(p);
        if (f1) p.write(")");
    }
}
