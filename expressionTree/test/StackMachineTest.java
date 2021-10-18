package expressionTree.test;

import expressionTree.generator.StackMachine;
import expressionTree.interfaces.Environment;
import expressionTree.interfaces.ExpressionNodeI;
import expressionTree.parser.ParseException;
import expressionTree.parser.Parser;
import expressionTree.tree.ExpressionFactory;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

public class StackMachineTest {
    @Test
    public void test() {
        Parser parser = new Parser(
            new BufferedReader(new StringReader("x \\/ y")),
            new PrintWriter(System.out),
            new Environment(),
            new ExpressionFactory()
        );

        try {
            ExpressionNodeI root = parser.parse();
            StackMachine.generate(root);
        }
        catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }
}
