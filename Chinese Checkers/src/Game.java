import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


/**
 * @author Janek
 *Game class, responsible for carrying game
 */
class Game {

  
    
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
  
	/**
	 * Abstract class Player for NormallPlayer and BOTplayer
	 * @author Janek
	 *
	 */
    abstract class Player extends Thread {

    	Color color;
        Player opponent;
        Socket socket;
        BufferedReader input;
        PrintWriter output;
        int actualLocation;

        
        public Player(Socket socket, Color color) {
        	playerList.add(this);
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
  
    /**
     *class Host represents first player in the game. He set up ammount of player and bots. He starts the game
     */
    class Host extends Player {

		public Host(Socket socket, Color color) {
            super(socket,color);
        }
		
		public void run() {
            try {
            	//Host set up the game (ammount of normal players and bot)
            	while(findOppoments==false){
            		String command = input.readLine();
            		if (command.startsWith("PLAYERAMMOUNT"))
            			try{
            				realPLayers_ammount = Integer.parseInt(command.substring(14));
            			}
            			catch(NumberFormatException e){
            				output.println("MESSAGE Bad ammount");
            			}
            		else
            			if (command.startsWith("BOTSAMMOUNT"))
            				try{
            					bots_ammount = Integer.parseInt(command.substring(14));
            				}
            				catch(NumberFormatException e){
            					output.println("MESSAGE Bad ammount");
            				} 
            			else
            				if (command.startsWith("FINDOPPOMENTS") && (realPLayers_ammount+bots_ammount)>=2 && (realPLayers_ammount+bots_ammount)<=6 && (realPLayers_ammount+bots_ammount)!=5 ){
            					findOppoments=true;
            					players_ammount=bots_ammount+realPLayers_ammount;
            					botMaker(bots_ammount);
            					output.println("DONE");
            				}
            					else if (command.startsWith("QUIT")) {
            						return;
            					}
            	}
            	//wait for all players
            	while(playerList.size()!=players_ammount){
            		
            	}
                // Actual game starts
                while (true) {
                	String command = input.readLine();
                	if(playerList.size()==1)
                		output.println("DEFEAT");
                	if(command.startsWith("MOVE FROM"))
                		actualLocation = Integer.parseInt(command.substring(10));
                	else
	                    if (command.startsWith("MOVE TO")) {
	                        int locationTO = Integer.parseInt(command.substring(8));
	                        if (isMoveLegal(actualLocation,locationTO, this)) {
	                            output.println("VALID_MOVE");
	                            if(didPlayerWon(this))
	                            	output.println("VICTORY");
	                            	shouldWePlay=false;
	                        } 
	                        else {
	                            output.println("MESSAGE ?");
	                        }
	                    } 
	                    else 
	                    	if (command.startsWith("QUIT")) {
	                    		return;
	                    	}
                	while(!shouldWePlay){
                		if(command.startsWith("I WANT PLAY"))
                			shouldWePlay=true;
                		if (command.startsWith("QUIT")){
                			playerList.remove(this);
                			normalPlayerList.remove(this);
                    		return;
                		}
                	}
                		
                }
            } catch (IOException e) {
                System.out.println("Player died: " + e);
            } finally {
                try {socket.close();} catch (IOException e) {}
            }
        }

		/**
		 * Method will prepere bots.
		 * @param botNumber
		 */
		private void botMaker(int botNumber) {
			for(int j=0;j<botNumber;j++){
				botList.add(new BOTPlayer(Color.values()[Color.values().length-j]));
				botList.get(j).run();
			}
		}
    }
    
    class NormalPlayer extends Player {
    	public NormalPlayer(Socket socket, Color color) {
            super(socket,color);
        }
    	
    	public void run() {
            try {
            	//wait for all players
            	while(playerList.size()!=players_ammount){
            		
            	}
                output.println("MESSAGE All players connected");

                // Tell the first player that it is her turn.
                if (color == Color.values()[turnCounter]) {
                    output.println("MESSAGE Your move");
                }

                // Repeatedly get commands from the client and process them.
                while (true) {
                	String command = input.readLine();
                	if(playerList.size()==1)
                		output.println("LOSE");
                	if(command.startsWith("MOVE FROM"))
                		actualLocation = Integer.parseInt(command.substring(10));
                	else
	                    if (command.startsWith("MOVE TO")) {
	                        int locationTO = Integer.parseInt(command.substring(8));
	                        if (isMoveLegal(actualLocation,locationTO, this)) {
	                            output.println("VALID_MOVE");
	                            if(didPlayerWon(this))
	                            	output.println("WIN");
	                            	shouldWePlay=false;
	                        } 
	                        else {
	                            output.println("MESSAGE ?");
	                        }
	                    } 
	                    else 
	                    	if (command.startsWith("QUIT")) {
	                    		return;
	                    	}
                	while(!shouldWePlay){
                		if(command.startsWith("I WANT PLAY"))
                			shouldWePlay=true;
                		if (command.startsWith("QUIT")){
                			playerList.remove(this);
                			normalPlayerList.remove(this);
                    		return;
                		}
                	}
                		
                }
            } catch (IOException e) {
                System.out.println("Player died: " + e);
            } finally {
                try {socket.close();} catch (IOException e) {}
            }
        }
    }
    
    class BOTPlayer extends Player {
    	
    	
    	public BOTPlayer(Color color) {
            super();
            this.color=color;  
            playerList.add(this);
        }
    	
    	public void run() {
    			//wait for all players
        		while(playerList.size()!=players_ammount){
        		
        		}
                botMove(this);
        }
    }

    /**
     * Procedure makes bot moves.
     * @param bot
     */
	public void botMove(BOTPlayer bot) {
		// TODO Auto-generated method stub
		
	}

	
}