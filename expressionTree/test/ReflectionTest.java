package expressionTree.test;

import static org.junit.Assert.*;
import org.junit.Test;
import util.Reflection;

public class ReflectionTest {
    @Test
    public void test() {
        System.out.println(
            new Reflection.Field<Byte[]>("12333", "value")
                .get().toString()
        );
    }
}
