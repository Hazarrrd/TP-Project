package player;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import game.Color;
import game.Game;
import game.GameList;


public class NormalPlayer extends Player {

		private String stage="LOBBY";
		private int realPLayers_ammount;
		private int bots_ammount;
		private int players_ammount;
		private ArrayList<Player> playerList;
		private boolean shouldWePlay;
		private ArrayList<Player> normalPlayerList;

		public NormalPlayer(Socket socket, Color color,GameList gamelist) {
            super(socket,color,gamelist);
        }
		
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
		            					game.players_ammount=bots_ammount+realPLayers_ammount;
		            					//botMaker(game.bots_ammount);
		            					setValues(game);
		            					game.setGameName(command.substring(14));
		            					gamelist.addGame(game);
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
            		
            	}
                // Actual game starts
                while (stage=="GAMEWINDOW") {
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
		private void setGame(String string) {
			if(gamelist.findGame(string)!=null){
				game=gamelist.findGame(string);
				setValues(game);
				stage="GAMEWINDOW";
				output.println("GOOD GAMENAME");
			}
			else
				output.println("WRONG GAMENAME");
				
		}
		
		private void setValues(Game game){
			realPLayers_ammount=game.realPLayers_ammount;
			bots_ammount=game.bots_ammount;
			players_ammount=game.players_ammount;
			playerList=game.playerList;
			shouldWePlay=game.shouldWePlay;
			normalPlayerList=game.normalPlayerList;
		}
		
		private void botMaker(int botNumber) {
			for(int j=0;j<botNumber;j++){
				game.botList.add(new BOTPlayer(Color.values()[Color.values().length-j],game));
				//game.botList.get(j).run();
			}
		}
    }