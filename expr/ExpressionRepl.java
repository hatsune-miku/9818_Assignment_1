package expr;

import java.util.Scanner;

public class ExpressionRepl {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Input expression: ");
            String line = scanner.nextLine().strip();

            try {
                Expression exp = ExpressionParserNG.parse(line);
                System.out.println("==> " + exp);
                System.out.println("on x=1: " + exp.value(1));
                System.out.println("on x=2: " + exp.value(2));
                System.out.println("on x=3: " + exp.value(3));
            }
            catch (Throwable e) {
                System.out.println("Illegal expression!");
            }

            System.out.println();
        }
    }
}
