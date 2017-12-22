import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Lobby extends JFrame implements ActionListener {
	
	private JButton start;
	private JLabel botNumberLabel;
	private JLabel normalPlayerLabel;
	private JTextArea tekst;
	private JComboBox setBotNumber;
	private JComboBox setNormalPlayers;
	private String[] botNumber = { "0", "1", "2", "3", "4", "5" };
	private String[] normalPlayersNumber = { "1", "2", "3", "4", "5","6" };
	JPanel menu = new JPanel(new GridLayout(3,2));
	
	/**
	 * Klasa Symulacja jest główną klasą programu
	 * 
	 */
	public Lobby() {
		setTitle("Chinese Checker HOST LOBBY");
		start = new JButton("FIND OPPONENTS");
		start.addActionListener(this);
		botNumberLabel = new JLabel("Set ammount of bots");
		normalPlayerLabel = new JLabel("Set ammount of real players");
		tekst= new JTextArea("Remember that this is game for 2,3,4 or 6 players");
		tekst.setEditable(false);
		setBotNumber = new JComboBox(botNumber);
		setNormalPlayers = new JComboBox(normalPlayersNumber);
		
		this.add(menu);
		menu.add(normalPlayerLabel);
		menu.add(setNormalPlayers);
		menu.add(botNumberLabel);
		menu.add(setBotNumber);
		menu.add(start);
		menu.add(tekst);
		
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		pack();
	}
	
	
	public void actionPerformed(ActionEvent e){
	
		int botAmmount=setBotNumber.getSelectedIndex();
		int normalPlayerAmmount=setNormalPlayers.getSelectedIndex()+1;
		int ammountOfPlayers=botAmmount+normalPlayerAmmount;
		if(ammountOfPlayers!=1 && ammountOfPlayers!=5 && ammountOfPlayers<7){
			Window frame = new Window(3);
			frame.setTitle("Chinese checkers");
			
			frame.addWindowListener( new WindowAdapter() {
			
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			this.setVisible(false);
			frame.setVisible(true);
		}
		else
			tekst.setText("You can't play in 1,5 or more than 6 players!!!");
					
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
			//tekst.setText("max wymiar 22x44,ilosc zajecow 20");*/
	}

}