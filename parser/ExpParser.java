/* ExpParser.java */
/* Generated By:JavaCC: Do not edit this line. ExpParser.java */
package parser;

import java.io.StringReader ;
import expr.ExpressionFactoryI ;
import expr.Expression ;

class ExpParser implements ExpParserConstants {

    private ExpressionFactoryI factory ; ;

    static Expression parse( String str, ExpressionFactoryI factory )
        throws ParseException, TokenMgrError, NumberFormatException
    {

        ExpParser parser = new ExpParser( new StringReader( str )) ;
        parser.factory = factory ;
        return parser.start() ;
    }

  final public Expression start() throws ParseException {Expression a ;
    a = E();
    jj_consume_token(0);
{if ("" != null) return a ;}
    throw new Error("Missing return statement in function");
}

  final public Expression E() throws ParseException {Expression a, b ;
    a = T();
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PLUS:
      case MINUS:{
        ;
        break;
        }
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PLUS:{
        jj_consume_token(PLUS);
        b = T();
a = factory.add( a, b);
        break;
        }
      case MINUS:{
        jj_consume_token(MINUS);
        b = T();
a = factory.subtract( a, b) ;
        break;
        }
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
{if ("" != null) return a ;}
    throw new Error("Missing return statement in function");
}

  final public Expression T() throws ParseException {Expression a, b ;
    a = F();
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case POWER:
      case MULTIPLY:
      case DIVIDE:
      case LPAR:
      case X:
      case SIN:
      case COS:
      case TAN:
      case LN:
      case EXP:
      case MAX:
      case PI:
      case E:
      case CONSTANT:{
        ;
        break;
        }
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case POWER:{
        jj_consume_token(POWER);
        b = F();
a = factory.power(a, b);
        break;
        }
      case MULTIPLY:{
        jj_consume_token(MULTIPLY);
        b = F();
a = factory.multiply( a, b);
        break;
        }
      case LPAR:
      case X:
      case SIN:
      case COS:
      case TAN:
      case LN:
      case EXP:
      case MAX:
      case PI:
      case E:
      case CONSTANT:{
        b = P();
a = factory.multiply( a, b);
        break;
        }
      case DIVIDE:{
        jj_consume_token(DIVIDE);
        b = F();
a = factory.divide( a, b) ;
        break;
        }
      default:
        jj_la1[3] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
{if ("" != null) return a ;}
    throw new Error("Missing return statement in function");
}

  final public Expression F() throws ParseException {Expression a ;
        Token tk ;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case MINUS:{
      jj_consume_token(MINUS);
      a = P();
{if ("" != null) return factory.parenthesized(
                                       factory.subtract(factory.constant(0.0), a) ) ;}
      break;
      }
    case LPAR:
    case X:
    case SIN:
    case COS:
    case TAN:
    case LN:
    case EXP:
    case MAX:
    case PI:
    case E:
    case CONSTANT:{
      a = P();
{if ("" != null) return a ;}
      break;
      }
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public Expression P() throws ParseException {Expression a,b ;
        Token tk ;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case X:{
      jj_consume_token(X);
{if ("" != null) return factory.x() ;}
      break;
      }
    case CONSTANT:{
      tk = jj_consume_token(CONSTANT);
{if ("" != null) return factory.constant( Double.parseDouble( tk.image ) ) ;}
      break;
      }
    case LPAR:{
      jj_consume_token(LPAR);
      a = E();
      jj_consume_token(RPAR);
{if ("" != null) return factory.parenthesized( a ) ;}
      break;
      }
    case SIN:{
      jj_consume_token(SIN);
      a = F();
{if ("" != null) return factory.sin( a ) ;}
      break;
      }
    case COS:{
      jj_consume_token(COS);
      a = F();
{if ("" != null) return factory.cos( a ) ;}
      break;
      }
    case TAN:{
      jj_consume_token(TAN);
      a = F();
{if ("" != null) return factory.tan( a ) ;}
      break;
      }
    case LN:{
      jj_consume_token(LN);
      a = F();
{if ("" != null) return factory.ln( a ) ;}
      break;
      }
    case EXP:{
      jj_consume_token(EXP);
      a = F();
{if ("" != null) return factory.exp( a ) ;}
      break;
      }
    case PI:{
      jj_consume_token(PI);
{if ("" != null) return factory.constant( Math.PI ) ;}
      break;
      }
    case E:{
      jj_consume_token(E);
{if ("" != null) return factory.constant( Math.E ) ;}
      break;
      }
    case MAX:{
      jj_consume_token(MAX);
      jj_consume_token(LPAR);
      a = F();
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 25:{
          ;
          break;
          }
        default:
          jj_la1[5] = jj_gen;
          break label_3;
        }
        jj_consume_token(25);
        b = F();
        jj_consume_token(RPAR);
a = factory.max( a, b);
      }
{if ("" != null) return a;}
      break;
      }
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  /** Generated Token Manager. */
  public ExpParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[7];
  static private int[] jj_la1_0;
  static {
	   jj_la1_init_0();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0x60,0x60,0x3ff780,0x3ff780,0x3ff440,0x2000000,0x3ff400,};
	}

  /** Constructor with InputStream. */
  public ExpParser(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public ExpParser(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new ExpParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public ExpParser(java.io.Reader stream) {
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new ExpParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new ExpParserTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public ExpParser(ExpParserTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ExpParserTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	 return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[27];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 7; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 27; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  private boolean trace_enabled;

/** Trace enabled. */
  final public boolean trace_enabled() {
	 return trace_enabled;
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}