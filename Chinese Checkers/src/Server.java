import java.net.ServerSocket;





public class Server {

  
    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(8901);
        System.out.println("Server is Running");
        try {
            while (true){
                Game game = new Game();
            	
                Game.Host host = game.new Host(listener.accept(), Color.values()[0]);
                host.start();
                while(game.findOppoments==false){
                }
              
                for(int j=1;j<game.realPLayers_ammount;j++)
        			game.normalPlayerList.add(game.new NormalPlayer(listener.accept(), Color.values()[j]));
                
                
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
