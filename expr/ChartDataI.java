package expr;

public interface ChartDataI {
    void setExpression(Expression e);
    Expression getExpression();
    void setXRange(double xMin, double xMax);
    double getXMin();
    double getXMax();
    void setYRange(double yMin, double yMax);
    double getYMin();
    double getYMax();
}
