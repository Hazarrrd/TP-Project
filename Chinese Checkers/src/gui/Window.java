package gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

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
	
	public void run(PrintWriter out, BufferedReader in) {
		String response;
		while(true){
			try {
				response = in.readLine();
			if(response.equals("START GAME")){
				out.println("START GAME");
			}
			
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	
}
