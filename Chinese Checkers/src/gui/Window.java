package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import board.Board;


class Window extends Frame implements MouseListener, ActionListener {
	
	private TextField input;
	private Button button;
	private Surface output;
	private JPanel board;
	private JPanel labels;
	private Board data;
	private JLabel yourColor;
	private JTextArea whosTurn;
	private JTextArea communicate;
	private JButton endTurn;
	
	int type;
	
	public Window(int type) {
		
		//BOARD
		data=new Board(5,6);
		int height=data.height;
		int width=data.width;
		
		board = new JPanel(new GridLayout(height, width*2));
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < width; y++) {
						if(x%2==0){
			                board.add(data.board[x][y]);
			                if(data.board[x][y].kindOfField!=0){
			                	data.board[x][y].addMouseListener(this);
			                	data.board[x][y].repaint();
			                }
			                board.add(new JPanel());
			   
						}
						else{
							board.add(new JPanel());
							 board.add(data.board[x][y]);
							 if(data.board[x][y].kindOfField!=0){
				                	data.board[x][y].addMouseListener(this);
				                	data.board[x][y].repaint();
				             }
			                
						}
		            }
		        }
		//BUTTONS AND TEXT AREAS
		labels = new JPanel(new GridLayout(4, 1));
		
		yourColor = new JLabel("Your color is");
		whosTurn = new JTextArea("Now it is ... turn");
		whosTurn.setEditable(false);
		communicate = new JTextArea("communicate");
		communicate.setEditable(false);
		endTurn = new JButton("End turn");
		endTurn.addActionListener(this);
		
		yourColor.setPreferredSize(new Dimension(215, 50));
		whosTurn.setPreferredSize(new Dimension(215, 50));
		communicate.setPreferredSize(new Dimension(215, 50));
		endTurn.setPreferredSize(new Dimension(215, 10));
		
		labels.add(yourColor);
		labels.add(whosTurn);
		labels.add(communicate);
		labels.add(endTurn);
		
		setResizable(false);
		this.type = type;
	
	   // output = new Surface(new ImageIcon("checkers.jpg").getImage(), type);
	   
	  //  setLayout(new BorderLayout());
	    
	  //  JPanel menu = new JPanel();
	   // menu.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
	    
	    add(board, BorderLayout.WEST);
	    add(labels, BorderLayout.EAST);
	 //   add(output, BorderLayout.CENTER);
	    
	    setBounds(100,100,1000,900);
	    //pack();
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

	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println("xxxxxxxx");
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		//System.out.println("xxxxxxxx");
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		//System.out.println("xxxxxxxx");
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		//System.out.println("xxxxxxxx");
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		//System.out.println("xxxxxxxx");
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("qqqqq");
		
	}

	
}
