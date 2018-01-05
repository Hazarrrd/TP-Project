package player;
import java.util.ArrayList;

import fields.Checker;
import fields.Field;
import game.Colors;
import game.Game;
/**
 * Class represent BOTplayer, extends abstract clas Player
 * @author Janek
 *
 */
class BOTPlayer extends Player implements Runnable {
    	
	
	
		ArrayList<Checker> botCheckers;
		Field botTarget;
		Boolean yourTurn=false;
		
    	/**
    	 * constructor
    	 * @param color
    	 * @param game
    	 */
    	public BOTPlayer(Game game) {
            super();
            this.game=game; 
            isBot=true;
        }
    	
    	/**
    	 * Method responsible for communication client-server and for thread run.
    	 */
    	public synchronized void run() {
    			//wait for all players
        		while(game.findOppoments==true){
        			try {
						this.wait();
					} catch (InterruptedException e) {
					}
        		}
        		
        		while(true){
        			while(yourTurn){
            			try {
            				botMove();
    						this.wait();
    					} catch (InterruptedException e) {
    					}
            		}
        		}
        }
    /**
     * Procedure makes bot moves.
     * @param bot
     */
	private void botMove() {
		game.turnCounter=(game.turnCounter+1)%game.players_ammount;
		yourTurn=false;
	}
}