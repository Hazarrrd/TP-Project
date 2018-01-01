package fields;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

import game.Colors;

public abstract class Field extends JPanel {
	
	public int kindOfField;
	public Colors color;
	public int X1;
	public int Y1;
	public Boolean reachedTarget=false;
	public int target;

	public void updatePosition(int x2, int y2) {
	}

	@Override
	protected void paintComponent(Graphics g) {
 
	}


	
}
