/**
 * @author Zhen Guan
 * @MUN# 202191382
 * @email zguan@mun.ca
 *
 * @note This file was prepared by Zhen Guan.
 * It was completed by me alone.
 */

package expressionTree.generator;

import expressionTree.interfaces.ExpressionNodeI;
import expressionTree.tree.*;
import util.Reflection;

import java.util.Stack;

public class StackMachine {
    public static void generate(ExpressionNodeI root) {
        Stack<ExpressionNodeI> st = new Stack<>();
        Stack<String> stmt = new Stack<>();

        st.push(root);
        while (!st.empty()) {
            ExpressionNodeI node = st.pop();

            if (isLiteral(node)) {
                stmt.push(String.format(
                    // Literal evaluates without an env
                    "load \"%s\"", node.evaluate(null)
                ));
            }
            else if (isNotGate(node)) {
                stmt.push("not");
                st.push(
                    getOperand((NotExpression) node)
                );
            }
            else if (isAndGate(node)) {
                stmt.push("and");
                st.push(getRight(node));
                st.push(getLeft(node));
            }
            else if (isOrGate(node)) {
                stmt.push("or");
                st.push(getRight(node));
                st.push(getLeft(node));
            }
            else if (isVariable(node)) {
                stmt.push(String.format(
                    "load \"%s\"", getName((VariableExpression) node)
                ));
            }
        }

        while (!stmt.empty()) {
            doOutput(stmt.pop());
        }
    }

    private static void doOutput(String statement) {
        System.out.println(statement);
    }

    private static boolean isLiteral(ExpressionNodeI n) {
        return n instanceof LiteralExpression;
    }

    private static boolean isNotGate(ExpressionNodeI n) {
        return n instanceof NotExpression;
    }

    private static boolean isAndGate(ExpressionNodeI n) {
        return n instanceof AndExpression;
    }

    private static boolean isOrGate(ExpressionNodeI n) {
        return n instanceof OrExpression;
    }

    private static boolean isVariable(ExpressionNodeI n) {
        return n instanceof VariableExpression;
    }

    private static ExpressionNodeI getLeft(ExpressionNodeI node) {
        return new Reflection.Field<ExpressionNodeI>(node, "operand0")
            .get();
    }

    private static ExpressionNodeI getRight(ExpressionNodeI node) {
        return new Reflection.Field<ExpressionNodeI>(node, "operand1")
            .get();
    }

    private static ExpressionNodeI getOperand(NotExpression node) {
        return new Reflection.Field<ExpressionNodeI>(node, "operand")
            .get();
    }

    private static String getName(VariableExpression node) {
        return new Reflection.Field<String>(node, "name").get();
    }
}
