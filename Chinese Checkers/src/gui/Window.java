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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import board.Board;
import fields.Field;
import game.Colors;


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
	private int height;
	private int width;
	private Boolean isSomethingChecked=false;
	Field checkedField;
	
	int type;
	private int checkedX;
	private int checkedY;
	private boolean mouseListner=false;
	private Colors myColor;
	PrintWriter out;
	BufferedReader in;
	private boolean isYourTurn;
	private int boardSize;
	
	public Window(int type, PrintWriter out, BufferedReader in, int size) {
		this.out=out;
		this.in=in;
		this.boardSize=size;
		//BOARD
		data=new Board(size,type);
		height=data.height;
		width=data.width;
		board = new JPanel(new GridLayout(height, width*2));
		painting();
		//BUTTONS AND TEXT AREAS
		labels = new JPanel(new GridLayout(4, 1));
		
		yourColor = new JLabel("Your color is :");
		whosTurn = new JTextArea("It's not your turn, wait please");
		whosTurn.setEditable(false);
		communicate = new JTextArea("communicate");
		communicate.setEditable(false);
		endTurn = new JButton("End turn");
		endTurn.addActionListener(this);
		
		yourColor.setPreferredSize(new Dimension(215, 50));
		whosTurn.setPreferredSize(new Dimension(215, 50));
		communicate.setPreferredSize(new Dimension(215, 50));
		endTurn.setPreferredSize(new Dimension(215, 10));
		
		adding();
		
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
	    this.setBackground(Color.GRAY);
	    pack();
	}

	public void adding() {
		labels.add(yourColor);
		labels.add(whosTurn);
		labels.add(communicate);
		labels.add(endTurn);
	}
	
	public void run() {
		String response;
		try {
			for(int i=0;i<2;i++){
						response = in.readLine();
					if(response.equals("START GAME")){
						out.println("START GAME");
					}
					else
						if(response.startsWith("COLOR ")){
							this.myColor=Colors.values()[Integer.parseInt(response.substring(6))];
							labels.removeAll();
							yourColor.setText("Your color is : " + myColor.toString());
							adding();
							repaint();
							revalidate();
						}
						else
							if(response.equals("QUIT"))
								return;
			}
					
			while(true){	
					response = in.readLine();
					
						if(response.equals("YOUR TURN")){
							isYourTurn=true;
							labels.removeAll();
							whosTurn.setText("Now this is your turn");
							adding();
							repaint();
							revalidate();
						}
						else
							if(response.startsWith("DO MOVE ")){
								data.doMove(Integer.parseInt(response.split(";")[1]),Integer.parseInt(response.split(";")[2]),Integer.parseInt(response.split(";")[3]),Integer.parseInt(response.split(";")[4]));
								//board.removeAll();
								//board.revalidate();
								board.removeAll();
								painting();
								data.board[Integer.parseInt(response.split(";")[1])][Integer.parseInt(response.split(";")[2])].addMouseListener(this);
								revalidate();
								repaint();
							}
							else
								if(response.equals("QUIT"))
									break;
								else
									if(response.equals("VICTORY")){
										 String msg = "VICTORY! Hope you wll come back, thank you for the game";
								
									  JOptionPane.showMessageDialog(this,                        // okno-w³asciciel
									                                msg,                         // komunikat
									                                "You have won!",             // tytu³
									                                JOptionPane.WARNING_MESSAGE, // rodzaj komnunikatu
									                                null                        // ikona
									                                );
										
									  System.exit(0);
									}
									else
										if(response.startsWith("LOSE ")){								
											data.deleteCheckers(Colors.valueOf(response.substring(5)),this);
											board.removeAll();
											painting();
											 String msg = "DEFEAT! But if you only want, you can still continue the game";
												
											  JOptionPane.showMessageDialog(this,                        // okno-w³asciciel
											                                msg,                         // komunikat
											                                "DEFEAT!",             // tytu³
											                                JOptionPane.WARNING_MESSAGE, // rodzaj komnunikatu
											                                null                        // ikona
											                                );
										}
						labels.removeAll();						
						adding();
						repaint();
						revalidate();
						pack();
											
								
						
				} 
			}
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Field field = (Field) e.getSource();
		if(isYourTurn){
			System.out.println(field.X1 + " " + field.Y1);
			if(field.kindOfField==2 && field.color==myColor){
				if(!isSomethingChecked){
					field.checked=true;
					isSomethingChecked=true;
					checkedField=field;
					checkedX=checkedField.X1; 
					checkedY=checkedField.Y1;
					//field.revalidate();
					board.removeAll();
					painting();
					revalidate();
					pack();
				}
				else 
					if(field.equals(checkedField) && checkedX==checkedField.X1 && checkedY==checkedField.Y1){
						field.checked=false;
						isSomethingChecked=false;
						checkedField=null;
						//field.revalidate();
						board.removeAll();
						painting();
						revalidate();
						pack();
					}
			}
			else
				if(isSomethingChecked){
					if(data.isMoveLegal(checkedField.X1,checkedField.Y1 , field.X1, field.Y1, myColor)){
						//removing();
						//board.revalidate();
						//board.repaint();
						//revalidate();
						//repaint();
						int x=checkedField.X1;
						int y=checkedField.Y1;
						out.println("DO MOVE ;" + x + ";" + y + ";" + field.X1 + ";" + field.Y1);
						data.doMove(x,y , field.X1, field.Y1);
						board.removeAll();
						board.revalidate();
						board.removeAll();
						painting();
						data.board[x][y].addMouseListener(this);
						revalidate();
						repaint();
					}
				}
		}
		
		
				
		   
	}

	public void painting() {
		//board = new JPanel(new GridLayout(height, width*2));
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < width; y++) {
				if(boardSize%2==1){
						if(x%2==0){
			                board.add(data.board[x][y]);
			                if(data.board[x][y].kindOfField!=0){
			                	if(!mouseListner){			                		
			                		data.board[x][y].addMouseListener(this);
			                	}
			                	data.board[x][y].repaint();
			                }
			                board.add(new JPanel());
			   
						}
						else{
							board.add(new JPanel());
							 board.add(data.board[x][y]);
							 if(data.board[x][y].kindOfField!=0){
								 if(!mouseListner){			                		
				                	data.board[x][y].addMouseListener(this);
				                 }
				                 data.board[x][y].repaint();
				             }
			                
						}
				}
				else{
					if(x%2==1){
		                board.add(data.board[x][y]);
		                if(data.board[x][y].kindOfField!=0){
		                	if(!mouseListner){			                		
		                		data.board[x][y].addMouseListener(this);
		                	}
		                	data.board[x][y].repaint();
		                }
		                board.add(new JPanel());
		   
					}
					else{
						board.add(new JPanel());
						 board.add(data.board[x][y]);
						 if(data.board[x][y].kindOfField!=0){
							 if(!mouseListner){			                		
			                	data.board[x][y].addMouseListener(this);
			                 }
			                 data.board[x][y].repaint();
			             }
		                
					}
				}
		    }
		}
		if(!mouseListner)
			mouseListner=true;
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
		if(checkedField!=null){
			checkedField.checked=false;
			checkedField.duringLongJump=false;
			checkedField.firstMove=true;
			board.removeAll();
			painting();
			revalidate();
			isSomethingChecked=false;
			checkedField=null;
			isYourTurn=false;
			out.println("END TURN");
			labels.removeAll();
			whosTurn.setText("It's not your turn, wait please");
			adding();
			repaint();
			revalidate();
			
		}
		else{
			isYourTurn=false;
			out.println("END TURN2");
			labels.removeAll();
			whosTurn.setText("It's not your turn, wait please");
			adding();
			repaint();
			revalidate();
		}
		
	}

	
}
