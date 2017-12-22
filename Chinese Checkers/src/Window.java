
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

class Window extends Frame {
	
	TextField input;
	Button button;
	Surface output;
	
	int type;
	
	public Window(int type) {
		setResizable(false);
		this.type = type;
	
	    output = new Surface(new ImageIcon("checkers.jpg").getImage(), type);
	   
	    setLayout(new BorderLayout());
	    
	    JPanel menu = new JPanel();
	    menu.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
	    
	    add(menu, BorderLayout.NORTH);
	    add(output, BorderLayout.CENTER);
	    
	    setBounds(100,100,600,400);
	    pack();
	}

	
}
