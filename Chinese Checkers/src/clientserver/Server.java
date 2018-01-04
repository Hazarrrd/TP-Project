package clientserver;
import java.net.ServerSocket;

import game.Colors;
import game.GameList;
import player.NormalPlayer;




/**
 * That class is server of the application
 * @author Janek
 *
 */
public class Server {

	 	private static Server INSTANCE;
	 
	 	private Server() {}
	 
	 	 public static Server getInstance() {
	         if (INSTANCE == null)
	             synchronized (Server.class) {
	                 if (INSTANCE == null)
	                     INSTANCE = new Server();
	             }
	         return INSTANCE;
	     }
	
    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(8901);
        GameList gamelist=new GameList();
        System.out.println("Server is Running");
        try {
        	//Game game = new Game();
            while (true){
            	
                NormalPlayer player = new NormalPlayer(listener.accept(), gamelist);
                player.start();
               // while(game.findOppoments==false){
               // }
              
              //  for(int j=1;j<game.realPLayers_ammount;j++)
        		//	host.game.normalPlayerList.add(new NormalPlayer(listener.accept(), Color.values()[j]));
                
                
            }
        } finally {
            listener.close();
        }
    }
}


/*
                switch(game.players_ammount){
                case 0 :
                	break;
                case 1 :{
                	Game.Player player1 = game.new NormalPlayer(listener.accept(), Color.values()[1]);
                	player1.start();
                	break;
                }
                case 2 :{
                	Game.Player player1 = game.new NormalPlayer(listener.accept(), Color.values()[1]);
                	Game.Player player2 = game.new NormalPlayer(listener.accept(), Color.values()[2]);
                	player1.start();
                	player2.start();
                	break;
                }
                case 3 :{
                	Game.Player player1 = game.new NormalPlayer(listener.accept(), Color.values()[1]);
                	Game.Player player2 = game.new NormalPlayer(listener.accept(), Color.values()[2]);
                	Game.Player player3 = game.new NormalPlayer(listener.accept(), Color.values()[3]);
                	player1.start();
                	player2.start();
                	player3.start();
                	break;
                }
                case 4 :{
                	Game.Player player1 = game.new NormalPlayer(listener.accept(), Color.values()[1]);
                	Game.Player player2 = game.new NormalPlayer(listener.accept(), Color.values()[2]);
                	Game.Player player3 = game.new NormalPlayer(listener.accept(), Color.values()[3]);
                	Game.Player player4 = game.new NormalPlayer(listener.accept(), Color.values()[4]);
                	player1.start();
                	player2.start();
                	player3.start();
                	player4.start();
                	break;
                }
                case 5 :{
                	Game.Player player1 = game.new NormalPlayer(listener.accept(), Color.values()[1]);
                	Game.Player player2 = game.new NormalPlayer(listener.accept(), Color.values()[2]);
                	Game.Player player3 = game.new NormalPlayer(listener.accept(), Color.values()[3]);
                	Game.Player player4 = game.new NormalPlayer(listener.accept(), Color.values()[4]);
                	Game.Player player5 = game.new NormalPlayer(listener.accept(), Color.values()[5]);
                	player1.start();
                	player2.start();
                	player3.start();
                	player4.start();
                	player5.start();
                	break;
            */
