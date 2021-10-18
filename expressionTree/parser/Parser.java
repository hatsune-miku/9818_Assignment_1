package expressionTree.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import expressionTree.interfaces.Environment;
import expressionTree.interfaces.ExpressionFactoryI;
import expressionTree.interfaces.ExpressionNodeI;

/** The parser is responsible for reading in one line of input and converting it to an expression tree.
 * <p>
 * The parser is used in two steps.  First, the parser is created.
 * Second, method parse is called. The parse method will read in one
 * line of input and parse it into an expression tree.
 * A second call to parse will read the next line of input, but
 * will reuse the same environment.
 * <p>
 * In order to construct the nodes of the tree the parser uses a
 * class implementing interface
 * {@link expressionTree.interfaces.ExpressionFactoryI}.
 * <p>
 * This is an example of the 'Abstract Factory' pattern
 * from /Design Patterns/ by Gamma, Helm, Johnson, and Vlissides.
 * <p>
 * The parsing algorithm used is the so-called "classic algorithm",
 * which is a standard recursive descent method, concisely
 * described in "Parsing Expressions by Recursive Descent" by Theodore Norvell
 * at http://www.engr.mun.ca/~theo/Misc/exp_parsing.htm . 
 * 
 * @author Theodore Norvell
 *
 */
public class Parser {
	private Environment environment ;
	private ExpressionFactoryI factory ;
	private BufferedReader bufferedReader ;
	private PrintWriter pwriter ;
	private String line ;
	private static enum  TokenKind { END, FALSE, TRUE,
									VAR, LPAR, RPAR,
									AND, OR, NOT}  ;
	private TokenKind nextTokenKind ;
	private String nextToken ;

	private final static Pattern spacePattern = Pattern.compile("\\s*") ;
	private final static Pattern falsePattern = Pattern.compile( "false" ) ;
	private final static Pattern truePattern = Pattern.compile( "true" ) ;
	private final static Pattern varPattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9]*") ;
	private final static Pattern lparPattern = Pattern.compile("\\(") ;
	private final static Pattern rparPattern = Pattern.compile("\\)") ;
	private final static Pattern andPattern = Pattern.compile("\\/\\\\") ; 
	private final static Pattern orPattern = Pattern.compile("\\\\\\/") ;
	private final static Pattern notPattern = Pattern.compile("\\~") ;

	public Parser( BufferedReader r,
			       PrintWriter p,
			       Environment env,
			       ExpressionFactoryI factory )
	{
		this.bufferedReader = r ;
		this.pwriter = p ;
		this.environment = env ;
		this.factory = factory ;
	}
	
	public ExpressionNodeI parse() throws ParseException, IOException {
		line = bufferedReader.readLine() ;
		getNextToken() ;
		ExpressionNodeI result = parseExpression() ;
		expect( TokenKind.END ) ;
		return result ;
	}
	
	private ExpressionNodeI parseExpression() throws ParseException {
		ExpressionNodeI result = parseTerm() ;
		while( nextTokenIs( TokenKind.OR ) ) {
			getNextToken() ;
			result = factory.makeOrNode(result, parseTerm() ) ; }
		return result ;
	}
	
	private ExpressionNodeI parseTerm() throws ParseException {
		ExpressionNodeI result = parsePrimary() ;
		while( nextTokenIs( TokenKind.AND ) ) {
			getNextToken() ;
			result = factory.makeAndNode(result, parsePrimary() ) ; }
		return result ;
	}
	

	private ExpressionNodeI parsePrimary() throws ParseException {
		ExpressionNodeI result ;
		
		if( nextTokenIs( TokenKind.FALSE ) ) {
			result = factory.makeLiteralNode( false ) ;
			getNextToken() ; }
		
		else if( nextTokenIs( TokenKind.TRUE ) ) {
			result = factory.makeLiteralNode( true ) ;
			getNextToken() ; }
		
		else if( nextTokenIs( TokenKind.VAR ) ) {
			if( ! environment.has( nextToken ) ) 
				environment.add( nextToken ) ;
			result = factory.makeVariableNode( nextToken ) ;
			getNextToken() ; }
		
		else if( nextTokenIs( TokenKind.LPAR ) ) {
			getNextToken() ;
			result = parseExpression() ;
			expect( TokenKind.RPAR ) ; }
		
		else if( nextTokenIs( TokenKind.NOT ) ) {
			getNextToken() ;
			result = factory.makeNotNode( parsePrimary() ) ; }
		
		else {
			pwriter.println( "Unexpected token <"+nextToken+">") ;
			pwriter.println( "Remaining line is: <"+line+">" ) ;
			throw new ParseException() ; }
		
		return result ;
	}

	// prepare next token
	private void getNextToken() throws ParseException {
		// Remove spaces
			Matcher matcher = spacePattern.matcher(line) ;
			matcher.lookingAt() ;
			line = line.substring(matcher.end() ) ;
			
		// Is it the end?
			if( line.length() == 0 ) {
				nextToken = "END" ;
				nextTokenKind = TokenKind.END ;
				return ; }
		
		// Now try each token kind.
			if( tryMatching( falsePattern, TokenKind.FALSE ) ) return ;
			if( tryMatching( truePattern, TokenKind.TRUE ) ) return ;
			if( tryMatching( varPattern, TokenKind.VAR ) ) return ;
			if( tryMatching( lparPattern, TokenKind.LPAR ) ) return ;
			if( tryMatching( rparPattern, TokenKind.RPAR ) ) return ;
			if( tryMatching( andPattern, TokenKind.AND ) ) return ;
			if( tryMatching( orPattern, TokenKind.OR ) ) return ;
			if( tryMatching( notPattern, TokenKind.NOT ) ) return ;
			
		// If we get here.  There is an error.
			pwriter.println( "Could not find token.") ;
			pwriter.println( "Remaining line is: <"+line+">" ) ;
			throw new ParseException() ;
	}
	
	private boolean tryMatching( Pattern pattern, TokenKind kind ) {
		Matcher matcher = pattern.matcher(line) ;
		if( matcher.lookingAt() ) {
			nextToken = line.substring(0, matcher.end() ) ;
			nextTokenKind = kind ;
			line = line.substring(matcher.end() ) ;
			return true; }
		else {
			return false ; }
	}
	
	private void expect( TokenKind tokenKind ) throws ParseException {
		if( nextTokenKind != tokenKind ) {
			pwriter.println( "Unexpected token <"+nextToken+">") ;
			pwriter.println( "Remaining line is: <"+line+">" ) ;
			throw new ParseException() ;
		}
		getNextToken() ;
	}
	
	private boolean nextTokenIs( TokenKind tokenKind ) {
		return nextTokenKind == tokenKind ;
	}
}
