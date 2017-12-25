package player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import game.Color;
import game.Game;
import game.GameList;



/**
	 * Abstract class Player for NormallPlayer and BOTplayer
	 * @author Janek
	 *
	 */
public abstract class Player extends Thread {

	Color color;
    Player opponent;
    Socket socket;
    BufferedReader input;
    PrintWriter output;
    int actualLocation;
    Game game;
    GameList gamelist;

    
    public Player(Socket socket, Color color, GameList gamelist) {
    	//game.playerList.add(this);
    	this.gamelist=gamelist;
        this.socket = socket;
        this.color = color;
        try {
            input = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("WELCOME " + color);
            output.println("MESSAGE Waiting for opponent to connect");
        } catch (IOException e) {
            System.out.println("Player died: " + e);
        }
    }
    
    public Player(){	
    }
    
    /**
     * Method check if Move is legal (If it was done by player, who has actually turn and if chosen location is correct. If its all OK, then it 
     	use method checkerMove and move a checker. )
     * @param actualLocation
     * @param locationTO
     * @param player
     * @return true if Move is legal, false if not.
     */
    protected boolean isMoveLegal(int actualLocation, int locationTO, Player player) {
		// TODO Auto-generated method stub
		return false;
	}
    
    /**
     * Procedure moves checker from one place to another.
     * @param actualLocation
     * @param locationTO
     * @param player
     */
    protected void checkerMove(int actualLocation, int locationTO, Player player) {
		// TODO Auto-generated method stub
	}
    
    /**
     * Check if player has won the game.
     * @param player
     */
    protected boolean didPlayerWon(Player player) {
		return false;
		// TODO Auto-generated method stub
	}
    

    /**
     * The run method of this thread.
     */
    public abstract void run() ;
}   