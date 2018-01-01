package fields;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.Color;

import javax.swing.JPanel;

import game.Colors;

public class Checker extends NotNullField {
	

	public Checker(Colors color,int X1, int Y1, int target){
		super(X1,Y1);
		this.kindOfField=2;
		this.color=color;
		this.target=target;
		setPreferredSize(new Dimension(30,45));
	}
	
	public void updatePosition(int X1, int Y1){
		this.X1=X1;
		this.Y1=Y1;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		switch(color){
		
		case GREEN :
			g.setColor(Color.GREEN);
			break;
		case YELLOW :
			g.setColor(Color.YELLOW);
			break;
		case RED :
			g.setColor(Color.RED);
			break;
		case  BLUE:
			g.setColor(Color.BLUE);
			break;
		case  ORANGE:
			g.setColor(Color.ORANGE);
			break;
		case PINK :
			g.setColor(Color.PINK);
			break;
		}
		// kolo
		Ellipse2D circle = new Ellipse2D.Double(0, 15, 30, 30);

		g2d.draw(circle);
		g2d.fill(circle);
	}
	
	


}
