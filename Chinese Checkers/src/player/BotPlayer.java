package player;
import game.Colors;
import game.Game;
/**
 * Class represent BOTplayer, extends abstract clas Player
 * @author Janek
 *
 */
class BOTPlayer extends Player implements Runnable {
    	
    	/**
    	 * constructor
    	 * @param color
    	 * @param game
    	 */
    	public BOTPlayer(Game game) {
            super();
            this.game=game; 
        }
    	
    	/**
    	 * Method responsible for communication client-server and for thread run.
    	 */
    	public void run() {
    			//wait for all players
        		while(game.findOppoments==true){
        		//	try {
						//this.wait();
					//} catch (InterruptedException e) {
						// TODO Auto-generated catch block
					//	e.printStackTrace();
				//	}
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