package expr;

import util.Assert;

public class ChartData implements ChartDataI {
    protected Expression expression;
    protected double xMin;
    protected double xMax;
    protected double yMin;
    protected double yMax;

    protected final ExpressionFactory factory =
        new ExpressionFactory();

    protected final static double RANGE_MIN_EXCLUSIVE =
        Double.NEGATIVE_INFINITY;

    protected final static double RANGE_MAX_EXCLUSIVE =
        Double.POSITIVE_INFINITY;


    public ChartData() {
        expression = factory.constant(0);
        xMin = yMin = 0;
        xMax = yMax = 1;
    }

    public void setExpression(Expression e) {
        Assert.check(e != null, "Expression must not be null.");
        this.expression = e;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setXRange(double xMin, double xMax) {
        Assert.check(
            isRangeValid(xMin, xMax),
            String.format(
                "Range must satisfy: %f < xMin < xMax < %f",
                RANGE_MIN_EXCLUSIVE, RANGE_MAX_EXCLUSIVE
            )
        );

        this.xMin = xMin;
        this.xMax = xMax;
    }

    public double getXMin() {
        return xMin;
    }

    public double getXMax() {
        return xMax;
    }

    public void setYRange(double yMin, double yMax) {
        Assert.check(
            isRangeValid(yMin, yMax),
            String.format(
                "Range must satisfy: %f < yMin < yMax < %f",
                RANGE_MIN_EXCLUSIVE, RANGE_MAX_EXCLUSIVE
            )
        );

        this.yMin = yMin;
        this.yMax = yMax;
    }

    public double getYMin() {
        return yMin;
    }

    public double getYMax() {
        return yMax;
    }

    private static boolean isRangeValid(double min, double max) {
        return min < max
            && min > RANGE_MIN_EXCLUSIVE
            && max < RANGE_MAX_EXCLUSIVE;
    }
}
