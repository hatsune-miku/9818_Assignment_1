package expr.test;

import expr.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class ExpressionTest {
    @Test
    public void test() {
        ExpressionFactory f = new ExpressionFactory();

        // f(x) = (x + 4 * (5 + 6)) / 9
        // f(1) = 5
        Expression e = f.divide(
            f.parenthesized(
                f.add(
                    f.x(),
                    f.multiply(
                        f.constant(4),
                        f.parenthesized(
                            f.add(
                                f.constant(5),
                                f.constant(6)
                            )
                        )
                    )
                )
            ),
            f.constant(9)
        );
        System.out.println(e.toString());

        assertTrue(
            feq(
                e.value(1),
                5.0f
            )
        );
        assertTrue(
            feq(
                f.constant(1).value(0 /*meaningless*/),
                f.add(f.constant(1), f.ln(f.x())).value(1)
            )
        );
    }

    boolean feq(double x, double y) {
        return Math.abs(x - y) < 1e-5f;
    }
}
