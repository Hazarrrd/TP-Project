package game;
import java.util.ArrayList;

/**
 * That class contains all games that are created and are existing  
 * @author Janek
 *
 */
public class GameList {
	 public ArrayList<Game> gameList;
	 
	 public GameList () {
		 this.gameList=new ArrayList<Game>();
	 }

	 /**
	  * That method returns game with name that is given
	  * @param string This is name of the game we are searching
	  * @return The game we were searching, or if it's not exist - null
	  */
	public Game findGame(String string) {
		for(int i=0;i<this.gameList.size();i++)
			System.out.println(this.gameList.get(i).getGameName());
		for(int i=0;i<this.gameList.size();i++)
			if(this.gameList.get(i).getGameName().equals(string))
				return this.gameList.get(i);
		return null;
	}
	
	public void addGame(Game game) {
		this.gameList.add(game);
	}
	
	public void removeGame(Game game) {
		this.gameList.remove(game);
	}
/**
 * That method check if any game is using given name
 * @param string Game name that we are checking
 * @return True if this name wasn't used and false if it was.
 */
	public boolean isNameFree(String string) {
		for(int i=0;i<this.gameList.size();i++)
			if(this.gameList.get(i).getGameName().equals(string))
				return false;
		return true;
	}
}
