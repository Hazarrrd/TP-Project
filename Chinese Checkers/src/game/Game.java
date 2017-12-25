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
 *Game class, responsible for carrying game
 */
public class Game {

    public ArrayList<Player> playerList = new ArrayList<Player>();
    public ArrayList<Player> botList = new ArrayList<Player>();
    public ArrayList<Player> normalPlayerList = new ArrayList<Player>();
    

	public int turnCounter;
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
	
}