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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This class makes GUI, where player can set up the game
 * @author Janek
 *
 */
public class GameSettings extends JFrame implements ActionListener {
	
	private JButton start;
	private JLabel botNumberLabel;
	private JLabel normalPlayerLabel;
	private JLabel boardSizeLabel;
	private JLabel checkersNumberLabel;
	private JTextArea tekst;
	private JComboBox setBotNumber;
	private JComboBox setNormalPlayers;
	private JComboBox setBoardSize;
	private String[] botNumber = { "0", "1", "2", "3", "4", "5" };
	private String[] normalPlayersNumber = { "1", "2", "3", "4", "5","6" };
	private String[] boardSize = { "S", "M", "L", "XL", "XXL" };
	JPanel menu = new JPanel(new GridLayout(6,2));
	private JLabel gameNameLabel;
	private JTextField setGameName;
	private JTextField setCheckersNumber;
	
	private PrintWriter out;
	private BufferedReader in;
	
	private int ammountOfPlayers;
	private int side;
	private int checkersNumberInt;
	
	//konstruktor
	public GameSettings(PrintWriter out, BufferedReader in) {
		this.out=out;
		this.in=in;
		
		setTitle("Chinese Checker GAME SETTINGS");
		start = new JButton("FIND OPPONENTS");
		start.addActionListener(this);
		botNumberLabel = new JLabel("Set ammount of bots");
		normalPlayerLabel = new JLabel("Set ammount of real players");
		gameNameLabel = new JLabel("Set gameName");
		boardSizeLabel = new JLabel("Set board size");
		checkersNumberLabel = new JLabel("Set number of checkers");
		tekst= new JTextArea("Remember that this is game for 2,3,4 or 6 players");
		tekst.setEditable(false);
		setGameName=new JTextField("");
		setCheckersNumber=new JTextField("");
		setBotNumber = new JComboBox(botNumber);
		setNormalPlayers = new JComboBox(normalPlayersNumber);
		setBoardSize = new JComboBox(boardSize);
		setBoardSize.setSelectedItem("XL");
		
		this.add(menu);
		menu.add(normalPlayerLabel);
		menu.add(setNormalPlayers);
		menu.add(botNumberLabel);
		menu.add(setBotNumber);
		menu.add(boardSizeLabel);
		menu.add(setBoardSize);
		menu.add(gameNameLabel);
		menu.add(setGameName);
		menu.add(checkersNumberLabel);
		menu.add(setCheckersNumber);
		menu.add(start);
		menu.add(tekst);
		
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
			
	}

/**
 * This method is reponsible for communmicaton between client-server
 * @param out Printwriter
 * @param in BufferedReder
 */
	public void run(PrintWriter out, BufferedReader in) {
		String response;
		while(true){
			try {
				response = in.readLine();
				System.out.println(response);
			if(response.equals("GAMEWINDOW")){
				Window frame = new Window(ammountOfPlayers,out, in, side, checkersNumberInt);
				frame.setTitle("Chinese checkers : " + setGameName.getText());
				
				frame.addWindowListener( new WindowAdapter() {
				
					public void windowClosing(WindowEvent e) {
						out.println("QUIT");
						System.exit(0);
					}
				});
				this.setVisible(false);
				frame.setVisible(true);
				frame.run();
				break;
			}
			else
				if(response.startsWith("MESSAGE"))
					tekst.setText(response.substring(8));
				else
					if(response.equals("QUIT"))
						break;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	public void actionPerformed(ActionEvent e){
	
		int botAmmount=setBotNumber.getSelectedIndex();
		int normalPlayerAmmount=setNormalPlayers.getSelectedIndex()+1;
		side=setBoardSize.getSelectedIndex()+2;
		ammountOfPlayers=botAmmount+normalPlayerAmmount;
		String gameNameString=setGameName.getText();
		String checkersNumber=setCheckersNumber.getText();
		try {
			checkersNumberInt=Integer.parseInt(checkersNumber);
			int checkersLimit=0;
			for(int i=0;i<side;i++)
				checkersLimit+=i;
			if(0<checkersNumberInt && checkersNumberInt<=checkersLimit)
				if(!(gameNameString.equals(""))){
					out.println("PLAYERAMMOUNT " + Integer.toString(normalPlayerAmmount));
					out.println("BOTSAMMOUNT " + Integer.toString(botAmmount));
					out.println("BOARDSIZE " + Integer.toString(side));
					out.println("CHECKERS " + checkersNumber);
					out.println("FINDOPPOMENTS " + gameNameString);
				}
				else
					tekst.setText("You must name your game!");
			else{
				tekst.setText("Checkers number must be beetween " + "1 and " + checkersLimit);
				pack();
			}
		} catch (NumberFormatException e1) {
			tekst.setText("Bad format in checkersNumber. You must write in a number!");
			pack();
		}
		
	}
}
		
	
			
					
		/*String pamiec1, pamiec2, pamiec3, pamiec4;
		double n1,n2,n3,n4;
	
		pamiec1=szerokosc.getText();
		pamiec2=wysokosc.getText();
		pamiec3=opoznienie.getText();
		pamiec4=ile_zajacy.getText();
		
		n1=(double)parsowanie(pamiec1);
		n2=(double)parsowanie(pamiec2);
		n3=(double)parsowanie(pamiec3);
		n4=(double)parsowanie(pamiec4);
	
		if(n1>0 && n1 <=44 && n2<=21 && n2>0 && n3>=0 && n4>0 && n4<21){
			
		plansza =new Plansza(n1,n2,n3,n4);
		this.add(plansza);
		menu.setVisible(false);
		plansza.setVisible(true);
		zacznij=new Thread(plansza);
	    zacznij.start();
	  this.setSize((int)n1*Plansza.dlugosc+(int)n1, (int)n2*Plansza.dlugosc+(int)n2);
		}
		else
			System.out.println(" zle dane (maksymalne wymiary to 22x44, a ilosc zajecow 20");
			//tekst.setText("max wymiar 22x44,ilosc zajecow 20");
	}

}*/