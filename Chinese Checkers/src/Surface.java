
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Surface extends JPanel{
	
	Image image;
	//Board board;
	
	public Surface(Image image, int type) 
	{
	    this.image = image;
	   
	    setPreferredSize(new Dimension(600,600));
		
	   // board = new Board();
	   
		repaint();
	}
	
	private void doDrawing(Graphics g) {
	      
	  	Graphics2D g2d = (Graphics2D) g;
	  	
	}
	
	 public void paintComponent(Graphics g) {
		  	
		  	g.drawImage(image, 0, 0, null);
		  	doDrawing(g);        
		  }
}
