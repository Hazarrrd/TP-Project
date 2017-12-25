package clientserver;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gui.Lobby;

/**
 * Client class. There is also GUI.
 * @author Janek
 *
 */
public class Client {
//DO USUNIECIA
    private JFrame frame = new JFrame("Tic Tac Toe");
    private JLabel messageLabel = new JLabel("");
    private ImageIcon icon;
    private ImageIcon opponentIcon;

    private Square[] board = new Square[9];
    private Square currentSquare;
//^^^^^^^
    private static int PORT = 8901;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

  
    public Client(String serverAddress) throws Exception {

        // Setup networking
        socket = new Socket(serverAddress, PORT);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // DO USUNIECIA
       /* messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, "South");

        JPanel boardPanel = new JPanel();
        boardPanel.setBackground(Color.black);
        boardPanel.setLayout(new GridLayout(3, 3, 2, 2));
        for (int i = 0; i < board.length; i++) {
            final int j = i;
            board[i] = new Square();
            board[i].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    currentSquare = board[j];
                    out.println("MOVE " + j);}});
            boardPanel.add(board[i]);
        }
        frame.getContentPane().add(boardPanel, "Center");
        // ^^^^^^^^^^^^^^^ */
       Lobby ekran1 = new Lobby(out,in);
       ekran1.run(out,in);
    }

    public void play() throws Exception {
        String response;
        try {
            response = in.readLine();
            if (response.startsWith("WELCOME")) {
                frame.setTitle("Tic Tac Toe - Player " );
            }
            while(true){
            	response = in.readLine();
            	if (response.startsWith("DONE"))
            		break;
            }
            while (true) {
                response = in.readLine();
               
                if (response.startsWith("VALID_MOVE")) {
                    messageLabel.setText("Valid move, please wait");
                    currentSquare.setIcon(icon);
                    currentSquare.repaint();
                } else if (response.startsWith("OPPONENT_MOVED")) {
                    int loc = Integer.parseInt(response.substring(15));
                    board[loc].setIcon(opponentIcon);
                    board[loc].repaint();
                    messageLabel.setText("Opponent moved, your turn");
                } else if (response.startsWith("VICTORY")) {
                    messageLabel.setText("You win");
                    break;
                } else if (response.startsWith("DEFEAT")) {
                    messageLabel.setText("You lose");
                    break;
                } else if (response.startsWith("TIE")) {
                    messageLabel.setText("You tied");
                    break;
                } else if (response.startsWith("MESSAGE")) {
                    messageLabel.setText(response.substring(8));
                }
            }
            out.println("QUIT");
        }
        finally {
            socket.close();
        }
    }



    //DO USUNIECIA
    static class Square extends JPanel {
        JLabel label = new JLabel((Icon)null);

        public Square() {
            setBackground(Color.white);
            add(label);
        }

        public void setIcon(Icon icon) {
            label.setIcon(icon);
        }
    }
    // ^^^^^^^^^^^^^^

    /**
     * Runs the client as an application.
     */
    public static void main(String[] args) throws Exception {
       // while (true) {
            String serverAddress = (args.length == 0) ? "localhost" : args[1];
            Client client = new Client(serverAddress);
            //client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          //  client.frame.setSize(240, 160);
           // client.frame.setVisible(true);
           // client.frame.setResizable(false);
            //client.play();
         //   break;
        //}
    }
}