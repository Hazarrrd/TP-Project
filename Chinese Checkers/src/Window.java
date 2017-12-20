package chess;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class Window extends Frame implements ActionListener{
	
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
	
	public void actionPerformed(ActionEvent event) {
	    
		
	}
	
	public static void main(String[] args) {
  		
  		Object[] options = {"OK", "Zamknij"};
  		
  		int type = JOptionPane.showOptionDialog(null, "Wybierz ilosc wszystkich graczy oraz botow", "Chinese checkers", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

  		if(type == 0)
  		{
  			Window frame = new Window(type);
  			frame.setTitle("Chinese checkers");
  			
  			frame.addWindowListener( new WindowAdapter() {
  			
  				public void windowClosing(WindowEvent e) {
  					System.exit(0);
  				}
  			});
  			
  			frame.setVisible(true);
  			//frame.listenSocket();
  		}
  		else {
  			System.exit(0);
  		}
  	}
}
