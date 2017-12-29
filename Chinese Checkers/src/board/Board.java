package board;


import java.io.ObjectOutputStream.PutField;
import java.nio.channels.ShutdownChannelGroupException;

import clientserver.Client;
import fields.Checker;
import fields.EmptyField;
import fields.Field;
import fields.NullField;
import game.Color;

public class Board {
	
	public int width ;
	public int height;
	public Field[][] board;
	public int side;
	public int playerNumber;
	
	Board(int side,int playerNumber){
		this.playerNumber=playerNumber;
		this.side=side;
		this.width=(side-2)+2*side;
		this.height=4*side-3;
		this.board=makeEmptyBoard(this.side);
		putCheckers();
	}
	
	private void putCheckers() {
		switch (playerNumber){
		case 2 :
			fillerFirst(0);
			fillerFourth(1);
			break;
		case 3 :
			fillerFirst(0);
			fillerThird(1);
			fillerFifth(2);
			
			break;
		case 4 :
			fillerSecond(0);
			fillerThird(1);
			fillerFifth(2);
			fillerSixth(3);
			break;
		case 6 :
			fillerFirst(0);
			fillerSecond(1);
			fillerThird(2);
			fillerFourth(3);
			fillerFifth(4);
			fillerSixth(5);
			break;
		}
	}
	
	public void fillerFirst(int color) {
		for(int i=0;i<side-1;i++)
			for(int j=0; j<width;j++)
				if(board[i][j].kindOfField==1)
					board[i][j]=new Checker(Color.values()[color]);
	}
	
	public void fillerSecond(int color) {
		int counter=0;
		int limit=side-1;
		int j=1;
		for(int i=side-1;i<2*(side-1); i++){
			while(counter<limit){
				if(board[i][width-j].kindOfField==1){
					board[i][width-j]=new Checker(Color.values()[color]);
					counter++;
				}
				j++;
			}
			counter=0;
			limit--;
			j=1;
		}
		
	}

	public void fillerThird(int color) {
		int counter=0;
		int limit=1;
		int j=1;
		for(int i=2*side-1;i<3*side-2;i++){
			while(counter<limit){
				if(board[i][width-j].kindOfField==1){
					board[i][width-j]=new Checker(Color.values()[color]);
					counter++;
				}
				j++;
			}
			counter=0;
			limit++;
			j=1;
		}
	}
	
	public void fillerFourth(int color) {
		for(int i=0;i<side-1;i++)
			for(int j=0; j<width;j++)
				if(board[height-1-i][j].kindOfField==1)
					board[height-1-i][j]=new Checker(Color.values()[color]);
	}
	
	public void fillerFifth(int color) {
		int counter=0;
		int limit=1;
		int j=0;
		for(int i=2*side-1;i<3*side-2;i++){
			while(counter<limit){
				if(board[i][j].kindOfField==1){
					board[i][j]=new Checker(Color.values()[color]);
					counter++;
				}
				j++;
			}
			counter=0;
			limit++;
			j=0;
		}
	}
	
	public void fillerSixth(int color) {
		int counter=0;
		int limit=side-1;
		int j=0;
		for(int i=side-1;i<2*(side-1); i++){
			while(counter<limit){
				if(board[i][j].kindOfField==1){
					board[i][j]=new Checker(Color.values()[color]);
					counter++;
				}
				j++;
			}
			counter=0;
			limit--;
			j=0;
		}
	}
	

	public static Field[][] makeEmptyBoard (int side){
		int height=4*side-3;
		int width=(side-2)+2*side;
		int nullFields,freeFields;
		
		Field[][] Board = new Field[height][width];
		//first part
		for(int i=0;i<side-1;i++){
			freeFields=i+1;
			nullFields=(width-freeFields)/2;
			for(int j=0;j<nullFields;j++){
				Board[i][j]=new NullField();
				Board[height-1-i][j]=new NullField();
				
				Board[i][width-1-j]=new NullField();
				Board[height-1-i][width-1-j]=new NullField();
			}
			if(i%2==1){
				Board[i][width-1-nullFields]=new NullField();
				Board[height-1-i][width-1-nullFields]=new NullField();
			}
			for(int z=nullFields;z<nullFields+freeFields;z++){
				Board[i][z]=new EmptyField();
				Board[height-1-i][z]=new EmptyField();
			}
		}
		//second part
		freeFields=3*side-1;
		for(int i=side-1;i<2*side-2;i++){
			freeFields--;
			nullFields=(width-freeFields)/2;
			for(int j=0;j<nullFields;j++){
				Board[i][j]=new NullField();
				Board[height-1-i][j]=new NullField();
				
				Board[i][width-1-j]=new NullField();
				Board[height-1-i][width-1-j]=new NullField();
			}
			if(i%2==1){
				Board[i][width-1-nullFields]=new NullField();
				Board[height-1-i][width-1-nullFields]=new NullField();
			}
			for(int z=nullFields;z<nullFields+freeFields;z++){
				Board[i][z]=new EmptyField();
				Board[height-1-i][z]=new EmptyField();
			}
		}
		//middle
		freeFields--;
		nullFields=(width-freeFields)/2;
		for(int j=0;j<nullFields;j++){
			Board[2*side-2][j]=new NullField();
			Board[2*side-2][width-1-j]=new NullField();
		}
		for(int z=nullFields;z<nullFields+freeFields;z++){
			Board[2*side-2][z]=new EmptyField();
		}
		
		return Board;
		
	}
	
	public static void showBoard(Field[][] board, int side){
		int height=4*side-3;
		int width=(side-2)+2*side;
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++)
				switch(board[i][j].kindOfField){
					case 0 :
						System.out.print("X ");
						break;
					case 1 :
						System.out.print("# ");
						break;
					case 2 :
						System.out.print("* ");
						break;
				}
			System.out.println("");
		}
				
	}
	
	
	 public static void main(String[] args) throws Exception {
		 Board board = new Board(5,6);
		 showBoard(board.board,5);
	     	
	    }
}
