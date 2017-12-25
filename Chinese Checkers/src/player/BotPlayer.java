package player;
import game.Color;
import game.Game;

class BOTPlayer extends Player {
    	
    	
    	public BOTPlayer(Color color, Game game) {
            super();
            this.game=game;
            this.color=color;  
            game.playerList.add(this);
        }
    	
    	public void run() {
    			//wait for all players
        		while(game.playerList.size()!=game.players_ammount){
        		
        		}
                botMove(this);
        }
    /**
     * Procedure makes bot moves.
     * @param bot
     */
	public void botMove(BOTPlayer bot) {
		// TODO Auto-generated method stub
		
	}
}