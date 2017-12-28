package board;


import java.nio.channels.ShutdownChannelGroupException;

import clientserver.Client;
import fields.EmptyField;
import fields.Field;
import fields.NullField;
import game.Color;

public class Board {
	
	public static final int WIDTH = 13;
	public static final int HEIGHT = 17;
	
	private static final int[] STAR = {1,2,3,4,13,12,11,10,9,10,11,12,12,4,3,2,1};
	
	private static final int[] OTHER = {6,5,5,4,0,0,1,1,2,1,1,0,0,4,5,5,6};
	
	private final Color[][] grid;
	
	Board(){
		grid = new Color[HEIGHT][];
		for(int y = 0; y< HEIGHT; y++)
			grid[y] = new Color[STAR[y]];
		
	}
	
	Board(Board board) {
		grid = board.grid;
	}
	
	public static Field[][] makeBoard (int side){
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
		Field field;
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
	
	
	// public static void main(String[] args) throws Exception {
		// /showBoard(makeBoard(3),3);
	     	
	  //  }
}
