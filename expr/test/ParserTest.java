package expr.test;

import expr.ExpressionParserNG;
import org.junit.Test;

public class ParserTest {
    @Test
    public void test() {
        try {
            System.out.println(ExpressionParserNG.parse("( 4.2 + 56.55 ) * 5 + x").toString());
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
