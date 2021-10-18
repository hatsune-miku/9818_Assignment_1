package expressionTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;

import expressionTree.generator.StackMachine;
import expressionTree.interfaces.Environment;
import expressionTree.interfaces.ExpressionFactoryI;
import expressionTree.interfaces.ExpressionNodeI;
import expressionTree.parser.ParseException;
import expressionTree.parser.Parser;
import expressionTree.tree.ExpressionFactory;

public class TruthTableMain {

	/** Read a boolean expression from the System.in and print a truth table to System.out.
	 * @param args
	 */
	public static void main(String[] args) {
		Reader r = new InputStreamReader( System.in ) ;
		PrintWriter w = new PrintWriter( new OutputStreamWriter( System.out ), true ) ;
		while( true )
			execute( r, w ) ;
	}


	/** Read a boolean expression from the input and print a truth table.
	 * 
	 * @param reader The input source from which to read the expression
	 * @param pwriter The output sink to which to send all output
	 */
	static void execute( Reader reader, PrintWriter pwriter) {
		
		pwriter.println("Please enter a boolean expression." ) ;
		pwriter.println("(Use /\\ for 'and', \\/ for 'or', and ~ for 'not'.)" ) ;
		
		// Step 0. Read an expression from the input.
		// Also collect all variables mentioned in the expression in
		// an environment object 
		ExpressionNodeI expression ;
		Environment env ;
		{
			BufferedReader bufferedReader = new BufferedReader(reader) ;
			ExpressionFactoryI factory = new ExpressionFactory() ;
			while( true ) {
				try {
					// Read and parse one line.
					env = new Environment() ;
					Parser parser = new Parser( bufferedReader, pwriter, env, factory ) ;
					expression = parser.parse()  ;
					// If we get here there was no error and we proceed.
					break ; }
				catch( IOException ex ) {
					pwriter.println( "An I/O error occurred while reading the line.") ;
					pwriter.println( "Please retype the line." ) ; }
				catch( ParseException ex ) {
					pwriter.println( "Please retype the line." ) ; } }
		}

		int varCount = env.getNames().size();
		if( varCount > 30 ) {
			pwriter.println("Sorry too many variables") ;
			return ; }
		
		// printTruthTable(pwriter, expression, env, varCount);

		StackMachine.generate(expression);
	}


	private static void printTruthTable(PrintWriter pwriter, ExpressionNodeI expression, Environment env,
			int varCount) {
		// Step 1. Print the first few lines of the truth table.
		pwriter.println("Truth table") ;
		// The names of the variables
		for( String name : env.getNames() ) {
			pwriter.print(name) ; pwriter.print('\t') ; }
		// The expression itself
		expression.printTo( pwriter ) ;
		pwriter.println() ;
		
		// Step 2. Print the rows of the truth table.
		// Now the rows of the the table.
		int rows = 1 << varCount ; // Compute 2 to the varCount.
		for( int row = 0 ; row < rows ; ++row ) {
			for( String name : env.getNames() ) {
				pwriter.print( env.get(name) ) ; pwriter.print('\t') ; }
			pwriter.print( expression.evaluate(env) ) ;
			pwriter.println() ;
			env.advanceToNextState() ; }
	}
}