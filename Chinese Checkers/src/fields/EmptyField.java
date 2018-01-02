package fields;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class EmptyField extends NotNullField {
	
	
	public EmptyField(int X1, int Y1){
		super(X1,Y1);
		setPreferredSize(new Dimension(30,45));
		this.kindOfField=1;
	}
	public void updatePosition(int X1, int Y1) {
		this.X1=X1;
		this.Y1=Y1;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.LIGHT_GRAY);
		/*
		Graphics2D g2d = (Graphics2D) g;

		// kolo
		g.setColor(Color.BLACK);
		Ellipse2D circle = new Ellipse2D.Double(0, 15, 30, 30);

		g2d.draw(circle);
		g.setColor(Color.GRAY);
		//g2d.fill(circle);
	//	this.setBackground(Color.GRAY);
	//	this.setForeground(Color.GRAY);
	 * */
		Graphics2D g2d = (Graphics2D) g;

		// kolo
		g2d.drawOval(0, 15, 30, 30);
		g2d.fillOval(0, 15,30,30);
		}
	
	

}
