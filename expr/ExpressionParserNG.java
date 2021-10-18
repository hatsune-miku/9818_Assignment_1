package expr;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

// Unfinished!

public class ExpressionParserNG {
    final InputStreamReader reader;

    enum ElementType {
        VALUE_PART, DOT,
        ADD, SUB, MUL, DIV, POW,
        LPAR, RPAR,
        WHITESPACE,
        ERROR, UNKNOWN, EOF,
        VARIABLE
    }

    final Map<ElementType, Integer> priorityMap = new HashMap<>() {{
        put(ElementType.UNKNOWN, 999);
        put(ElementType.LPAR, 1);
        put(ElementType.RPAR, 1);
        put(ElementType.ADD, 2);
        put(ElementType.SUB, 2);
        put(ElementType.MUL, 3);
        put(ElementType.DIV, 3);
        put(ElementType.POW, 4);
    }};

    final static int BYTE_EOF = -1;
    final static int BYTE_ERROR = -2;

    Expression result = null;

    ElementType lastType;
    double number;
    int dotDepth;
    boolean isRightPart;
    boolean numberYield;


    private ExpressionParserNG(InputStream is) {
        reader = new InputStreamReader(is);

        number = 0;

        dotDepth = 0;
        isRightPart = false;
        numberYield = false;
        lastType = ElementType.UNKNOWN;
    }

    private ElementType nextElement() {
        Function<Void, ElementType> nextElementProc = (dummy) -> {
            int first = nextByte();

            // A value finished.
            if (!Character.isDigit(first) && first != '.' && lastType == ElementType.VALUE_PART) {
                isRightPart = false;
                dotDepth = 0;
                numberYield = true;
            }

            switch (first) {
                case BYTE_ERROR: return ElementType.ERROR;
                case BYTE_EOF: return ElementType.EOF;
                case 32: return ElementType.WHITESPACE;
                case '+': return ElementType.ADD;
                case '-': return ElementType.SUB;
                case '*': return ElementType.MUL;
                case '/': return ElementType.DIV;
                case '^': return ElementType.POW;
                case '(': return ElementType.LPAR;
                case ')': return ElementType.RPAR;
                case 'x': return ElementType.VARIABLE;

                case '.': {
                    isRightPart = true;
                    return ElementType.DOT;
                }
            }

            if (Character.isDigit(first)) {
                if (isRightPart) {
                    number += (first - 48) / Math.pow(10, ++dotDepth);
                }
                else {
                    number = number * 10 + (first - 48);
                }
                return ElementType.VALUE_PART;
            }

            return ElementType.UNKNOWN;
        };

        ElementType ret = nextElementProc.apply(null);
        lastType = ret;
        return ret;
    }

    private int nextByte() {
        try {
            return reader.read();
        }
        catch (Throwable e) {
            return BYTE_ERROR;
        }
    }

    private double getNumber() throws Exception {
        if (numberYield) {
            double ret = number;
            number = 0;
            numberYield = false;
            return ret;
        }
        else {
            throw new Exception("Number not yielded.");
        }
    }

    private Expression parse() throws Exception {
        final Stack<Object> st = new Stack<>();
        final Stack<ElementType> operatorStack = new Stack<>();
        final Queue<Object> resultQueue = new ArrayDeque<>();

        if (result != null) {
            return result;
        }

        while (true) {
            ElementType type = nextElement();

            if (numberYield) {
                resultQueue.add(
                    Expressions.constant(getNumber())
                );
            }

            boolean isEOF = false;

            switch (type) {
                case EOF -> isEOF = true;
                case ERROR -> throw new Exception("Parse error.");

                case VARIABLE -> resultQueue.add("x");
                case LPAR -> operatorStack.push(type);
                case RPAR -> {
                    ElementType top;
                    while ((top = operatorStack.pop()) != ElementType.LPAR) {
                        // Pop until rpar.
                        resultQueue.add(top);
                    }
                }
                case ADD, SUB, MUL, DIV, POW -> {
                    while (!operatorStack.isEmpty()
                        && priorityMap.get(type) < priorityMap.get(operatorStack.peek())) {
                        resultQueue.add(operatorStack.pop());
                    }
                    operatorStack.push(type);
                }
            } // switch

            if (isEOF) {
                break;
            }
        } // while

        while (!operatorStack.isEmpty()) {
            resultQueue.add(operatorStack.pop());
        }

        ElementType lastOperator = ElementType.UNKNOWN;

        while (!resultQueue.isEmpty()) {
            Object item = resultQueue.remove();
            if (item instanceof String) {
                // Variable.
                st.push(Expressions.x());
            }
            else if (item instanceof ElementType t) {
                // Operator.
                Expression p1 = (Expression) st.pop();
                Expression p2 = (Expression) st.pop();

                if (priorityMap.get(t) > priorityMap.get(lastOperator)) {
                    p2 = Expressions.parenthesized(p2);
                }
                lastOperator = t;

                st.push(operationMake(t, p2, p1));
            }
            else if (item instanceof Expression) {
                // Value.
                st.push(item);
            }
        }

        return (Expression) st.pop();
    }

    private static Expression operationMake(ElementType operator, Object p1, Object p2) throws Exception {
        Expression exp1, exp2;
        if (p1 instanceof Double) {
            exp1 = Expressions.constant((Double) p1);
        }
        else {
            exp1 = (Expression) p1;
        }

        if (p2 instanceof Double) {
            exp2 = Expressions.constant((Double) p2);
        }
        else {
            exp2 = (Expression) p2;
        }

        switch (operator) {
            case ADD: return Expressions.add(exp1, exp2);
            case SUB: return Expressions.sub(exp1, exp2);
            case MUL: return Expressions.mul(exp1, exp2);
            case DIV: return Expressions.div(exp1, exp2);
            case POW: return Expressions.power(exp1, exp2);
        }

        throw new Exception("Invalid operation.");
    }

    public static Expression parse(String exp) throws Exception {
        return new ExpressionParserNG(new ByteArrayInputStream(exp.getBytes(StandardCharsets.UTF_8)))
            .parse();
    }
}
