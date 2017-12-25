package player;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import game.Color;
import game.Game;
import game.GameList;

/**
 * Class represens living player, extends abstract class Player
 * @author Janek
 *
 */
public class NormalPlayer extends Player {

		private String stage="LOBBY";
		

		public NormalPlayer(Socket socket,GameList gamelist) {
            super(socket,gamelist);
        }
		
		/**
		 * Method that communicate with client
		 */
		public void run() {
            try {
            	while(stage=="LOBBY"){
            		String command = input.readLine();
            		if(command.startsWith("JOIN GAME"))
            			setGame(command.substring(10));
            		else
            			if(command.startsWith("CREATE GAME")){
            				stage="GAMESETTINGS";
            				game=new Game();
            				output.println("GAMESETTINGS");
            			}
            	}
            	//Host set up the game (ammount of normal players and bot)
            	while(stage=="GAMESETTINGS"){
            		String command = input.readLine();
            		if (command.startsWith("PLAYERAMMOUNT "))
            			try{
            				game.realPLayers_ammount = Integer.parseInt(command.substring(14));
            			}
            			catch(NumberFormatException e){
            				output.println("MESSAGE Bad ammountCATCH");
            			}
            		else
            			if (command.startsWith("BOTSAMMOUNT "))
            				try{
            					game.bots_ammount = Integer.parseInt(command.substring(12));
            				}
            				catch(NumberFormatException e){
            		
            					output.println("MESSAGE Bad ammount");
            				} 
            			else
            				if (command.startsWith("FINDOPPOMENTS "))
            					if((game.realPLayers_ammount+game.bots_ammount)>=2 && (game.realPLayers_ammount+game.bots_ammount)<=6 && (game.realPLayers_ammount+game.bots_ammount)!=5 ) {
	            					if(gamelist.isNameFree(command.substring(14))){
		            					stage="GAMEWINDOW";
		            					game.players_ammount=game.bots_ammount+game.realPLayers_ammount;
		            					game.setGameName(command.substring(14));
		            					gamelist.addGame(game);
		            					setColor(game);
		            					//botMaker(game.bots_ammount);
		            					output.println("GAMEWINDOW");
	            					}
	            					else
	            						output.println("MESSAGE Game name is already used");
            					}
            					else
            						output.println("MESSAGE Wrong data");
            				else if (command.startsWith("QUIT")) {
            					return;
            				}
            	}
            	
            	//wait for all players
            	while(game.playerList.size()!=game.players_ammount && stage=="GAMEWINDOW"){
            		String command = input.readLine();
            		if (command.startsWith("QUIT")) {
            			game.disconnectPlayer(this);
            			if(!(game.checkIfSomoneIs()))
            				gamelist.removeGame(game);
    					return;
    				}
            	}
                // Actual game starts
                while (stage=="GAMEWINDOW") {
                	String command = input.readLine();
                	if(game.playerList.size()==1)
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
	                            	game.shouldWePlay=false;
	                        } 
	                        else {
	                            output.println("MESSAGE ?");
	                        }
	                    } 
	                    else 
	                    	if (command.startsWith("QUIT")) {
	                    		game.disconnectPlayer(this);
	                			if(!(game.checkIfSomoneIs()))
	                				gamelist.removeGame(game);
	                    		return;
	                    	}
                	while(!game.shouldWePlay){
                		if(command.startsWith("I WANT PLAY"))
                			game.shouldWePlay=true;
                		if (command.startsWith("QUIT")){
                			game.disconnectPlayer(this);
                			if(!(game.checkIfSomoneIs()))
                				gamelist.removeGame(game);
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

		private void setColor(Game game) {
			this.color=Color.values()[game.normalPlayerList.size()];
		}

		/**
		 * Method find the searched game and sets it into player
		 * @param string Game name we are searching
		 */
		private void setGame(String string) {
			if(gamelist.findGame(string)!=null){
				game=gamelist.findGame(string);
				setColor(game);
				stage="GAMEWINDOW";
				output.println("GOOD GAMENAME");
			}
			else
				output.println("WRONG GAMENAME");
				
		}
		
		
		/**
		 * This creates bot objects
		 * @param botNumber number of expected bots
		 */
		private void botMaker(int botNumber) {
			for(int j=0;j<botNumber;j++){
				game.botList.add(new BOTPlayer(Color.values()[Color.values().length-j],game));
				game.botList.get(j).run();
			}
		}
    }