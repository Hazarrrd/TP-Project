package gui;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This class is GUI, where player choose if he want to create or join game/
 * @author Janek
 *
 */
public class Lobby extends JFrame {
	
	private JButton joinGame;
	private JButton newGame;
	private JLabel joinGameLabel;
	private JLabel newGameLabel;
	JPanel menu = new JPanel(new GridLayout(3,2));
	private JTextField setGameName;
	
	private PrintWriter out;
	private BufferedReader in;
	private JTextArea tekst;
	
//constructor
	public Lobby(PrintWriter out,BufferedReader in) {
		this.out=out;
		this.in=in;
		
		setTitle("Chinese Checker GAME SETTINGS");
		newGame = new JButton("NEW GAME");
		joinGame = new JButton("JOIN GAME");
		newGame.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				out.println("CREATE GAME");
			}

		});
		joinGame.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				out.println("JOIN GAME " + setGameName.getText());
			}
		});
		newGameLabel = new JLabel("Click if you want create the game");
		joinGameLabel = new JLabel("Fill field with 'GameName' and click if you want join the game'");
		tekst= new JTextArea("Fill field on the left with game name");
		tekst.setEditable(false);
		
		setGameName=new JTextField("");
		
		this.add(menu);
		menu.add(newGameLabel);
		menu.add(newGame);
		menu.add(joinGameLabel);
		menu.add(joinGame);
		menu.add(setGameName);
		menu.add(tekst);
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		pack();
		
		String response;
		
	}

/**
 * Method responsible for communication between client-server
 * @param out Printwriter
 * @param in BufferedReader
 */
	public void run(PrintWriter out, BufferedReader in) {
		String response;
		while(true){
			try {
				response = in.readLine();	
				if(response.equals("WRONG GAMENAME"))
					tekst.setText("This game doesn't exist");
				else
					if(response.equals("GOOD GAMENAME")){
						Window frame = new Window(3);
				
						frame.setTitle("Chinese checkers : " + setGameName.getText());
				
						frame.addWindowListener( new WindowAdapter() {
				
							public void windowClosing(WindowEvent e) {
								System.exit(0);
							}
							});
						this.setVisible(false);
						frame.setVisible(true);
						break;
					}
					else
						if(response.equals("GAMESETTINGS")){
							GameSettings frame = new GameSettings(out,in);
							
							frame.setTitle("Game Settings");
							
							frame.addWindowListener( new WindowAdapter() {
							
								public void windowClosing(WindowEvent e) {
									out.println("QUIT");
									System.exit(0);
								}
							});
							
							this.setVisible(false);
							frame.setVisible(true);
							frame.run(out, in);
							break;
						}
			} catch (IOException e1) {
				System.out.println("Cos nie tak");
				e1.printStackTrace();
			}
		}
	}
	

}