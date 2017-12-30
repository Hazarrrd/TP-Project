package player;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

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
            	//Player set up the game (ammount of normal players and bot)
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
		            					game.makeBoard();
		            					gamelist.addGame(game);	            					
		            					game.connectPlayer(this);
		            					botMaker(game.bots_ammount);
		            					startGameIfAllIn();
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
            	while(game.playerList.size()!=game.players_ammount && !(game.findOppoments)){
            		String command= input.readLine();
	            		if (command.startsWith("QUIT")) {
	            			game.disconnectPlayer(this);
	            			gameCleaner();
	    					return;
	    				}	         	            	  		
            	}
            	System.out.println("Wyszedlem");
            	/*
            	//wait for everybody to start a game
            	while(!game.shouldWePlay){
            		if(command.startsWith("I WANT PLAY"))
            			game.shouldWePlay=true;
            		if (command.startsWith("QUIT")){
            			game.disconnectPlayer(this);
            			gameCleaner();
                		return;
            		}
            	}
            	*/
       
                // Actual game starts
                while (stage=="GAMEWINDOW") {
                	String command = input.readLine();
                	if(game.playerList.size()==1)
                		output.println("DEFEAT");
                	if(command.startsWith("MOVE FROM")){
                		X1 = Integer.parseInt(command.substring(10));
                		Y1 = Integer.parseInt(command.substring(10));
                	}
                	else
	                    if (command.startsWith("MOVE TO")) {
	                        int X2 = Integer.parseInt(command.substring(8));
	                        int Y2 = Integer.parseInt(command.substring(8));
	                        if (isMoveLegal(X1,Y1,X2,Y2, this)) {
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
	                			gameCleaner();
	                    		return;
	                    	}	
                }
            } catch (IOException e) {
                System.out.println("Player died: " + e);
            } finally {
                try {socket.close();} catch (IOException e) {}
            }
        }

		/**
		 * Method that starts game when all player are connected
		 */
		public void startGameIfAllIn() {
			if(game.playerList.size()==game.players_ammount){
				//System.out.println("Wyszedlem");
				// Remove Game from list, to prevent someone from join to game in progress
				gamelist.removeGame(game);
				// Setting queue of moves and colors
				Collections.shuffle(game.playerList);
				setColors(game.playerList);
				game.actualPlayer=game.playerList.get(game.turnCounter);
				game.findOppoments=true;
				for(int i=0;i<game.normalPlayerList.size()-1;i++)
						game.normalPlayerList.get(i).output.println("START GAME");
			}
		}

		/**
		 * That class sets colors for the players
		 * @param playerList
		 */
		private void setColors(ArrayList<Player> playerList) {
			for(int i=0;i<playerList.size();i++)
			playerList.get(i).color=Color.values()[i];
		}

		/**
		 * Method delete game from gamelist and deletes threads.
		 */
		public void gameCleaner() {
			if(!(game.checkIfSomoneIs())){
				allBotStoper();
				gamelist.removeGame(game);
			}
		}
		

		/**
		 * Method find the searched game and sets it into player
		 * @param string Game name we are searching
		 */
		private void setGame(String string) {
			if(gamelist.findGame(string)!=null)
				if(isThereSlot(gamelist.findGame(string))){
				game=gamelist.findGame(string);
				game.connectPlayer(this);
				stage="GAMEWINDOW";
				startGameIfAllIn();
				output.println("GOOD GAMENAME");
				}
				else
					output.println("NO SLOT");
			else
				output.println("WRONG GAMENAME");
				
		}
		
		/**
		 * Method checks if the searched game have slots for one more player
		 * @param game
		 * @return true if it have, false if no
		 */
		private boolean isThereSlot(Game game) {
				if(game.normalPlayerList.size()<game.realPLayers_ammount)
					return true;
				return false;
		}

		/**
		 * This creates bot objects
		 * @param botNumber number of expected bots
		 */
		private void botMaker(int botNumber) {
			for(int j=0;j<botNumber;j++){
				game.botList.add(new BOTPlayer(game));
				game.botThreadsList.add(new Thread(game.botList.get(j)));
				game.playerList.add(game.botList.get(j));
			}
			for(int j=0;j<botNumber;j++)
				game.botThreadsList.get(j).start();
		}
		/**
		 * Method deletes threads
		 */
		private void allBotStoper() {
			int size=game.botThreadsList.size();
			for(int j=0;j<size;j++)
				game.botThreadsList.remove(0);
		}
		
    }