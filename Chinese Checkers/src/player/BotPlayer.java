package player;
import game.Color;
import game.Game;
/**
 * Class represent BOTplayer, extends abstract clas Player
 * @author Janek
 *
 */
class BOTPlayer extends Player {
    	
    	/**
    	 * constructor
    	 * @param color
    	 * @param game
    	 */
    	public BOTPlayer(Color color, Game game) {
            super();
            this.game=game;
            this.color=color;  
            game.playerList.add(this);
            game.botList.add(this);
        }
    	
    	/**
    	 * Method responsible for communication client-server
    	 */
    	public void run() {
    			//wait for all players
        		while(game.playerList.size()!=game.players_ammount){
        		
        		}
               // botMove(this);
        }
    /**
     * Procedure makes bot moves.
     * @param bot
     */
	public void botMove(BOTPlayer bot) {
		// TODO Auto-generated method stub
		
	}
}