package game;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import player.*;


/**
 * @author Janek
 *Game class, responsible for carrying single game
 */
public class Game {

    public ArrayList<Player> playerList = new ArrayList<Player>();
    public ArrayList<Player> botList = new ArrayList<Player>();
    public ArrayList<Player> normalPlayerList = new ArrayList<Player>();
    public ArrayList<Thread> botThreadsList = new ArrayList<Thread>();
    

    public Player actualPlayer;
	public int turnCounter=0;
	public boolean findOppoments=false;
	public int realPLayers_ammount=1;
	public int bots_ammount=0;
	// color=bots+player ammounts
	public int players_ammount;
	public boolean shouldWePlay=true;
	private String gameName;
	
	public void setGameName(String string){
		this.gameName=string;
	}
	
	public String getGameName(){
		return this.gameName;
	}
	
	public void connectPlayer(Player player){
		this.playerList.add(player);
		this.normalPlayerList.add(player);
	}
	
	public void disconnectPlayer(Player player){
		this.playerList.remove(player);
		this.normalPlayerList.remove(player);
	}
	
	/**
	 * 
	 * @return true if is not empty, false if it is
	 */
	public Boolean checkIfSomoneIs(){
		if(this.normalPlayerList.size()==0)
			return false;
		return true;
	}
	
	public void nextPlayerTurn(){
		turnCounter=(turnCounter+1)%playerList.size();
		actualPlayer=playerList.get(turnCounter);
	}
	
}