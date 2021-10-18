package expr;

public class Expressions {
    public static Expression add(Expression x, Expression y) {
        return makeExp(x, y, Double::sum, "%s + %s");
    }

    public static Expression sub(Expression x, Expression y) {
        return makeExp(x, y, (a, b) -> a - b, "%s - %s");
    }

    public static Expression mul(Expression x, Expression y) {
        return makeExp(x, y, (a, b) -> a * b, "%s * %s");
    }

    public static Expression div(Expression x, Expression y) {
        return makeExp(x, y, (a, b) -> a / b, "%s / %s");
    }

    public static Expression sin(Expression x) {
        return makeExp(x, Math::sin, "sin(%s)");
    }

    public static Expression cos(Expression x) {
        return makeExp(x, Math::cos, "cos(%s)");
    }

    public static Expression tan(Expression x) {
        return makeExp(x, Math::tan, "tan(%s)");
    }

    public static Expression ln(Expression x) {
        return makeExp(x, Math::log, "log(%s)");
    }

    public static Expression exp(Expression x) {
        return makeExp(x, Math::exp, "exp(%s)");
    }

    public static Expression parenthesized(Expression x) {
        return makeExp(x, v -> v, "(%s)");
    }

    public static Expression constant(double value) {
        return new TemplateExpr(
            null, null, (d0, d1) -> value, (d) -> "" + value);
    }

    public static Expression x() {
        return new Expression() {
            @Override
            public double value(double x) {
                return x;
            }

            @Override
            public String toString() {
                return "x";
            }
        };
    }

    public static Expression power(Expression x, Expression y) {
        return makeExp(x, y, Math::pow, "%s ** %s");
    }

    public static Expression max(Expression x, Expression y) {
        return makeExp(x, y, Math::max, "max(%s, %s)");
    }

    /////////////////////////////////////////////////////////////////

    private interface ProcTwo<Tp1, Tp2, R> {
        R value(Tp1 x, Tp2 y);
    }

    private interface ProcOne<T, R> {
        R value(T x);
    }

    private static record TemplateExpr(
        Expression p1, Expression p2,
        ProcTwo<Double, Double, Double> valueProc,
        ProcOne<Void, String> formatProc
    ) implements Expression {
        private final static int VALUE_DUMMY = 0;

        @Override
        public double value(double x) {
            return valueProc.value(
                p1 == null ? VALUE_DUMMY : p1.value(x),
                p2 == null ? VALUE_DUMMY : p2.value(x)
            );
        }

        @Override
        public String toString() {
            return formatProc.value(null);
        }
    }

    private static Expression makeExp(
        Expression p1, Expression p2,
        ProcTwo<Double, Double, Double> valueProc,
        String format
    ) {
        return new TemplateExpr(p1, p2, valueProc, (dummy) -> String.format(
            format, p1.toString(), p2.toString()
        ));
    }

    private static Expression makeExp(
        Expression p, ProcOne<Double, Double> valueProc,
        String format
    ) {
        return new TemplateExpr(p, null, (p1, p2null) -> valueProc.value(p1), (dummy) -> String.format(
            format, p.toString()
        ));
    }
}
