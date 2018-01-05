package player;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import game.Colors;
import game.Game;
import game.GameList;

/**
 * Class represens living player, extends abstract class Player
 * @author Janek
 *
 */
public class NormalPlayer extends Player {

		private String stage="LOBBY";
		private String[] parts;
		

		public NormalPlayer(Socket socket,GameList gamelist) {
            super(socket,gamelist);
            isBot=false;
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
            				if (command.startsWith("BOARDSIZE "))
                				try{
                					game.boardSize = Integer.parseInt(command.substring(10));
                				}
                				catch(NumberFormatException e){
                		
                					output.println("MESSAGE Bad ammount");
                				} 
	            				else
	                				if (command.startsWith("CHECKERS "))
	                    				try{
	                    					game.checkersNumber = Integer.parseInt(command.substring(9));
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
			            					System.out.println("afasdfasd");
			            					game.makeBoard();
			            					System.out.println("ha");
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
	            				else if (command.equals("QUIT")) {
	            					output.println("QUIT");
	            					return;
	            				}
	            	}
            	
            	//wait for all players
            	while(game.playerList.size()!=game.players_ammount && !(game.findOppoments)){
            		String command= input.readLine();
	            		if (command.startsWith("QUIT")) {
	            			output.println("QUIT");
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
                		if(command.equals("END TURN")){
                			
                			System.out.println("O tutajj jump " + game.board.board[game.checkedX][game.checkedY].duringLongJump + "i first " + game.board.board[game.checkedX][game.checkedY].firstMove);
                			game.board.board[game.checkedX][game.checkedY].duringLongJump=false;
                			game.board.board[game.checkedX][game.checkedY].firstMove=true;
                			game.turnCounter=(game.turnCounter+1)%game.players_ammount;
                			System.out.println("O tutajj jump " + game.board.board[game.checkedX][game.checkedY].duringLongJump + "i first " + game.board.board[game.checkedX][game.checkedY].firstMove);
                			//System.out.println(game.realPLayers_ammount + " " + game.turnCounter);
                			if(!game.playerList.get(game.turnCounter).isBot)
                				game.playerList.get(game.turnCounter).output.println("YOUR TURN");
                			else
                				game.playerList.get(game.turnCounter).yourTurn=true;
                		}
                		else
                			if(command.equals("END TURN2")){
                    			game.turnCounter=(game.turnCounter+1)%game.players_ammount;
                    			System.out.println(game.realPLayers_ammount + " " + game.turnCounter);
                    			if(!game.playerList.get(game.turnCounter).isBot)
                    				game.playerList.get(game.turnCounter).output.println("YOUR TURN");
                    			else
                    				game.playerList.get(game.turnCounter).yourTurn=true;
                    		}
		                	else
			                    if (command.startsWith("DO MOVE ")) {
			                    	//System.out.println("aa");
			                    	parts=command.split(";");
			                    	//System.out.println(command);
			                    	System.out.println(Integer.parseInt(parts[1])+" "+Integer.parseInt(parts[2])+ " " +Integer.parseInt(parts[3])+ " "+ Integer.parseInt(parts[4]));
			                        if (isMoveLegal(Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]), game.normalPlayerList.get(game.turnCounter))) {
			                        	//System.out.println("o tutaj2");
			                        	game.checkedX=Integer.parseInt(parts[3]);
			                        	game.checkedY=Integer.parseInt(parts[4]);
			                        	for(int i=0;i<game.normalPlayerList.size();i++){
			                            	//if(!game.normalPlayerList.get(i).equals(this)){
			                            		game.normalPlayerList.get(i).output.println(command);
			                            		//System.out.println("update");
			                            	//}
			                            }
			                        	checkerMove(Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]));
			                            //System.out.println("o tutaj3");
			                            if(didPlayerWon(this)){
			                            	System.out.println("VICTORY");
			                            	output.println("VICTORY");
			                            	quitingPlayer();
				                			return;
			                           }
			                    } 
			                       // else {
			                        	//System.out.println("oblales");
			                        	//game.board.showBoard(game.board.board, game.boardSize);
			                          // output.println("MESSAGE ?");
			                     //  }
			                    } 
			                    else 
			                    	if (command.startsWith("CHECKX")) {
			                    		game.checkedX=Integer.parseInt(command.substring(7));	                    	
			                    	}
			                    	else 
			                    		if (command.startsWith("CHECKY")) {
			                    			game.checkedY=Integer.parseInt(command.substring(7));
					                    }
					                    else 
					                    	if (command.startsWith("QUIT")) {
							                    output.println("QUIT");
							                    quitingPlayer();
							                    return;
							                }	
	                }
            } catch (IOException e) {
                System.out.println("Player died: " + e);
            } finally {
                try {socket.close();} catch (IOException e) {}
            }
        }

		private void quitingPlayer() {
			game.board.deleteCheckers(this.color,null);
			for(int i=0;i<game.normalPlayerList.size();i++)
				if(!game.normalPlayerList.get(i).equals(this)){
			    		game.normalPlayerList.get(i).output.println("LOSE " + this.color);
			    		System.out.println("update");
				}
			Boolean x=false;
			if(game.normalPlayerList.get(game.turnCounter).equals(this))
				x=true;
			gameCleaner();
			if(x){
				game.disconnectPlayer(this);
				if(game.players_ammount>0){
					game.turnCounter=(game.turnCounter)%game.players_ammount;
					if(game.turnCounter<game.realPLayers_ammount)
						game.normalPlayerList.get(game.turnCounter).output.println("YOUR TURN");
				}
			}
			else{
				if(game.players_ammount>1)
					if(game.normalPlayerList.get(game.turnCounter).equals(game.normalPlayerList.get(game.realPLayers_ammount-1)))
						game.turnCounter--;
				game.disconnectPlayer(this);
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
				for(int i=0;i<game.normalPlayerList.size();i++)
					game.normalPlayerList.get(i).output.println("COLOR " + i);
				for(int i=game.normalPlayerList.size();i<game.playerList.size();i++)
					game.playerList.get(i).color=Colors.values()[i];
				
				for(int i=0;i<game.normalPlayerList.size();i++)
						game.normalPlayerList.get(i).output.println("START GAME");
				
				Random ran = new Random();
				int x = ran.nextInt(game.players_ammount)+100;
				game.turnCounter=x%game.players_ammount;
    			if(!(game.playerList.get(game.turnCounter).isBot))
    				game.playerList.get(game.turnCounter).output.println("YOUR TURN");
    			else
    				game.playerList.get(game.turnCounter).yourTurn=true;
			}
		}

		/**
		 * That class sets colors for the players
		 * @param playerList
		 */
		private void setColors(ArrayList<Player> playerList) {
			for(int i=0;i<playerList.size();i++)
			playerList.get(i).color=Colors.values()[i];
		}

		/**
		 * Method delete game from gamelist and deletes threads.
		 */
		public void gameCleaner() {
			if(!(game.checkIfSomoneIs())){
				allBotStoper();
				//gamelist.removeGame(game);
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
					output.println("CHECKERSSIZE " + game.checkersNumber);
					output.println("GOOD GAMENAME " + game.players_ammount + " " + game.boardSize);
					startGameIfAllIn();
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