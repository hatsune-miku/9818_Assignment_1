package plotter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import parser.Parser;
import expr.ChartData;
import expr.ChartDataI;
import expr.Expression;
import expr.ExpressionFactory;
import expr.ExpressionFactoryI;

import java.awt.* ;

@SuppressWarnings("serial")
public class PlotterMain extends JFrame {
	GridBagLayout lm = new GridBagLayout() ;
	JTextField error = new JTextField("") ;
	JTextField xMin = new JTextField("-30.0", 10) ;
	JTextField xMax = new JTextField("+30.0", 10) ;
	JTextField yMin = new JTextField("-15.0", 10) ;
	JTextField yMax = new JTextField("+15.0", 10) ;
	JTextField expression = new JTextField( "max(-1, -max(sin(1/30x),cos(7/3x)))" ) ;
	ChartDataI chartData = new ChartData() ;
	Plot plot = new Plot( chartData ) ;
	private Color parseErrorColor = new Color(255, 200, 200);
	private Color warningColor = Color.YELLOW ;
	private Color goodColor = Color.WHITE ;
	private ExpressionFactoryI expressionFactory = new ExpressionFactory() ;
	
	
	PlotterMain() {
		setLayout( lm ) ;
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = c.gridheight = 1 ;
		c.weightx = 0 ; c.weighty = 0 ;
		c.fill = GridBagConstraints.NONE ;
		
		c.gridx = 0 ; c.gridy = 2 ;
		c.anchor = GridBagConstraints.NORTHEAST ;
		add( yMax, c ) ;
		
		c.gridx = 0 ; c.gridy = 4 ;
		c.anchor = GridBagConstraints.SOUTHEAST ;
		add( yMin, c ) ;

		c.gridx = 2 ; c.gridy = 0 ; c.weightx = 1 ;
		c.gridwidth = 3 ;
		c.anchor = GridBagConstraints.NORTHWEST ;
		c.fill = GridBagConstraints.HORIZONTAL ;
		add( expression, c ) ;
		
		c.gridx = 2 ; c.gridy = 1 ; c.weightx = 1 ;
		c.gridwidth = 3 ;
		c.anchor = GridBagConstraints.NORTHWEST ;
		c.fill = GridBagConstraints.HORIZONTAL ;
		add( error, c ) ;
		
		c.gridx = 2 ; c.gridy = 2 ; c.weightx = 1 ; c.weighty = 1 ;
		c.gridwidth = 3 ; c.gridheight = 3 ;
		c.anchor = GridBagConstraints.NORTHWEST ;
		c.fill = GridBagConstraints.BOTH ;
		add( plot, c ) ;
		
		c.gridwidth = 1 ; c.gridheight = 1 ;  c.weightx = 0 ; c.weighty = 0 ;
		c.fill = GridBagConstraints.NONE ;
		
		c.gridx = 2 ; c.gridy = 6 ;
		c.anchor = GridBagConstraints.NORTHWEST ;
		add( xMin, c ) ;
		
		c.gridx = 4 ; c.gridy = 6 ;
		c.anchor = GridBagConstraints.NORTHEAST;
		add( xMax, c ) ;
	
		// Use the Observer pattern to react to any changes to
		// the input fields.
		DocumentListener listener = new Listener() ;
		xMin.getDocument().addDocumentListener( listener );
		xMax.getDocument().addDocumentListener( listener );
		yMin.getDocument().addDocumentListener( listener );
		yMax.getDocument().addDocumentListener( listener );
		expression.getDocument().addDocumentListener( listener );
		
		//error.setBorder(BorderFactory.createLineBorder(Color.black)) ;
		
		error.setEditable( false ); 
		error.setFocusable( true ); 
		error.setBackground( getContentPane().getBackground() );
		plot.setBorder(BorderFactory.createLineBorder(Color.BLACK)) ;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		update() ;
		setSize(800, 500);
		setVisible( true );
	}
	
	void update() {
		double xMinVal, xMaxVal, yMinVal, yMaxVal ; 
		boolean ok = true ;
		try { 
			xMinVal = Double.parseDouble( xMin.getText() ) ;  }
		catch( NumberFormatException e) {
			xMin.setBackground( parseErrorColor  );
			xMinVal = Double.NaN ; 
			ok = false ; }
		try { 
			xMaxVal = Double.parseDouble( xMax.getText() ) ;}
		catch( NumberFormatException e) {
			xMax.setBackground( parseErrorColor );
			xMaxVal = Double.NaN ; 
			ok = false ; }
		try { 
			yMinVal = Double.parseDouble( yMin.getText() ) ; }
		catch( NumberFormatException e) {
			yMin.setBackground( parseErrorColor  ); 
			yMinVal = Double.NaN ; 
			ok = false ; }
		try { 
			yMaxVal = Double.parseDouble( yMax.getText() ) ; }
		catch( NumberFormatException e) {
			yMax.setBackground( parseErrorColor );
			yMaxVal = Double.NaN ; 
			ok = false ; }
		
		if( Double.NEGATIVE_INFINITY < xMinVal
		 && xMinVal < xMaxVal
		 && xMaxVal < Double.POSITIVE_INFINITY ) {
			xMin.setBackground( goodColor );
			xMax.setBackground( goodColor ); }
		else if( !Double.isNaN(xMinVal) && !Double.isNaN(xMaxVal) ) {
			xMin.setBackground( warningColor );
			xMax.setBackground( warningColor );
			ok = false ; }
		
		if( Double.NEGATIVE_INFINITY < yMinVal
		 && yMinVal < yMaxVal
		 && yMaxVal < Double.POSITIVE_INFINITY ) {
			yMin.setBackground( goodColor );
			yMax.setBackground( goodColor ); }
		else if( !Double.isNaN(yMinVal) && !Double.isNaN(yMaxVal) ) {
			yMin.setBackground( warningColor );
			yMax.setBackground( warningColor ); 
			ok = false ; }
		
		Expression e ;
		try {
			e = Parser.parse( expression.getText(), expressionFactory  ) ;
			expression.setBackground( goodColor );
			error.setText(e.toString());}
		catch( Throwable t ) {
			expression.setBackground( parseErrorColor );
			error.setText( t.getMessage() );
			error.setCaretPosition(0);
			e = null ; 
			ok = false ; }
		
		if( ok ) {
			chartData.setExpression( e );
			chartData.setXRange(xMinVal, xMaxVal);
			chartData.setYRange(yMinVal, yMaxVal);
			repaint();
		}
	}
	
	class Listener implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {
			update() ;
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			update() ;
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			update() ;
		} }
	

	public static void main(String[] args) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override public void run() {
				new PlotterMain() ;
			} });
	}
}
