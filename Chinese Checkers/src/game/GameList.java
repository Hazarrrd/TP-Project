package game;
import java.util.ArrayList;

public class GameList {
	 public ArrayList<Game> gameList;
	 
	 public GameList () {
		 this.gameList=new ArrayList<Game>();
	 }

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

	public boolean isNameFree(String string) {
		for(int i=0;i<this.gameList.size();i++)
			if(this.gameList.get(i).getGameName().equals(string))
				return false;
		return true;
	}
}
