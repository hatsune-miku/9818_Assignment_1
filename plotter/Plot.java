package plotter;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import expr.ChartDataI;
import expr.Expression;

@SuppressWarnings("serial")
public class Plot extends JPanel {
	private ChartDataI cd;
	private Color bgColour = new Color(200, 200, 255) ;
	private Color axisColour = new Color(255, 100, 100) ;
	private Color plotColour = Color.BLACK ;
	private Color gridColour = Color.LIGHT_GRAY ;

	Plot( ChartDataI cd ) {
		this.cd = cd ;
		setBackground( bgColour );
	}
	
	@Override public void paintComponent( Graphics g) {
		super.paintComponent( g );
		// TODO: Consider moving this functionality to an iterator. The
		// chart data object could have a makeIterator routine that takes a given
		// height and width and produces an iterator that gives a sequence of (i,j) pairs.
		int width = this.getWidth() ;
		int height = this.getHeight() ;
		g.clearRect(0, 0, width, height);
		Color oldColour = g.getColor() ;

		// Compute some useful numbers
		double xMin = cd.getXMin() ;
		double xMax = cd.getXMax() ;
		double w = Math.max(1, width-1 ) ;
		double xScale = (xMax - xMin) / w ;
		double yMin = cd.getYMin() ;
		double yMax = cd.getYMax() ;
		double h = Math.max(1,  height-1 ) ;
		double yScale = (yMax - yMin) / h ;
		

		// The Grid
		g.setColor( gridColour );
		
		// Vertical grid lines (between 11 and 21)
		double gridSpacing = Math.pow(10, Math.floor( Math.log10(xMax-xMin) - 1 ) ) ;
		if( (xMax-xMin) / gridSpacing > 40 ) gridSpacing *= 5 ;
		else if( (xMax-xMin) / gridSpacing > 20 ) gridSpacing *= 2 ;
		// gridSpacing is of the form 10^x or 2*10^x or 5*10^x where x is an integer.
		double gridLine = Math.floor( xMin / gridSpacing ) * gridSpacing ;
		double lastGridLine = Math.ceil( xMax / gridSpacing ) * gridSpacing ;
		while( gridLine <= lastGridLine ) {
			int i = (int)( (gridLine-xMin) / xScale )  ;
			g.drawLine(i, 0, i, height);
			gridLine += gridSpacing ;
		}
		// Horizontal grid lines (between 11 and 21)
		gridSpacing = Math.pow(10, Math.floor( Math.log10(yMax-yMin) - 1 ) ) ;
		if( (yMax-yMin) / gridSpacing > 40 ) gridSpacing *= 5 ;
		else if( (yMax-yMin) / gridSpacing > 20 ) gridSpacing *= 2 ;
		// gridSpacing is of the form 10^x or 2*10^x or 5*10^x where x is an integer.
		gridLine = Math.floor( yMin / gridSpacing ) * gridSpacing ;
		lastGridLine = Math.ceil( yMax / gridSpacing ) * gridSpacing ;
		while( gridLine <= lastGridLine ) {
			int j = (height-1) - (int)( (gridLine-yMin) / yScale )  ;
			g.drawLine(0, j, width, j);
			gridLine += gridSpacing ;
		}
		
		// Axes
		g.setColor( axisColour );
		int x0 = (int)( (0-xMin) / xScale )  ;
		g.drawLine(x0, 0, x0, height);
		g.drawLine(x0-1, 0, x0-1, height);
		g.drawLine(x0+1, 0, x0+1, height);
		int y0 = (height-1) - (int)( (0-yMin) / yScale ) ;
		g.drawLine(0, y0, width, y0);
		g.drawLine(0, y0+1, width, y0+1);
		g.drawLine(0, y0-1, width, y0-1);
		
		// The plot
		g.setColor( plotColour );
		Expression e = cd.getExpression() ;
		for( int i=1 ; i < width ; ++i) {
			double x =  xMin + xScale * i ;
			try { // Just in case value throws an exception.
				double y = e.value( x ) ;
				int j = (height-1) - (int)( (y-yMin) / yScale ) ;
				g.drawRect(i, j, 1, 1) ;
			} catch( Throwable t ) { }
		}
		
		// Put the colour back
		g.setColor( oldColour ) ;
	}
}
