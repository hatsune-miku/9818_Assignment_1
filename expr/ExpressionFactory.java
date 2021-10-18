package expr;

public class ExpressionFactory implements ExpressionFactoryI {
    public Expression add( Expression a, Expression b ) {
        return Expressions.add(a, b);
    }

    public Expression subtract( Expression a, Expression b ) {
        return Expressions.sub(a, b);
    }

    public Expression multiply( Expression a, Expression b ) {
        return Expressions.mul(a, b);
    }

    public Expression divide( Expression a, Expression b ) {
        return Expressions.div(a, b);
    }

    public Expression constant( double value ) {
        return Expressions.constant(value);
    }

    public Expression x( ) {
        return Expressions.x();
    }

    public Expression sin( Expression a ) {
        return Expressions.sin(a);
    }

    public Expression cos( Expression a ) {
        return Expressions.cos(a);
    }

    public Expression tan( Expression a ) {
        return Expressions.tan(a);
    }

    public Expression ln( Expression a ) {
        return Expressions.ln(a);
    }

    public Expression exp( Expression a ) {
        return Expressions.exp(a);
    }

    public Expression power(Expression a, Expression b) {
        return Expressions.power(a, b);
    }

    public Expression max(Expression a, Expression b) {
        return Expressions.max(a, b);
    }

    public Expression parenthesized(Expression a ) {
        return Expressions.parenthesized(a);
    }
}
