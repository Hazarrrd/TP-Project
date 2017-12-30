package board;


import java.io.ObjectOutputStream.PutField;
import java.nio.channels.ShutdownChannelGroupException;
import java.util.ArrayList;

import clientserver.Client;
import fields.Checker;
import fields.EmptyField;
import fields.Field;
import fields.NullField;
import game.Color;
import player.Player;

/**
 * That class is represenatation of the Board
 * @author Janek
 *
 */
public class Board {
	
	public int width ;
	public int height;
	public Field[][] board;
	public int side;
	public int playerNumber;
	
	public Board(int side,int playerNumber){
		this.playerNumber=playerNumber;
		this.side=side;
		this.width=(side-2)+2*side;
		this.height=4*side-3;
		this.board=makeEmptyBoard(this.side);
		putCheckers();
	}
	
	/**
	 * Method checks if it is possible to make a move, that player want to do
	 * @param x1 
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param color
	 * @return
	 */
	public boolean isMoveLegal(int x1, int y1, int x2, int y2, Color color) {
		Field target = board[x2][y2];
		Field checker = board[x1][y1];
		
		if(target.kindOfField!=1)
			return false;
		
		if(checker.kindOfField!=2)
			return false;
		else
			if(checker.color!=color)
				return false;
		
		if(areTheyNeighbours(target,checker))
			return true;
		else{
			ArrayList<Field> neighbours= giveNeighbours(checker);
			for(int i=0;i < neighbours.size(); i++){
				if(neighbours.get(i).kindOfField==2)
					if(areTheyNeighbours(target,neighbours.get(i)))
						if(!(checker.reachedTarget) || checkIfInTriangle(neighbours.get(i),checker.target))
							return true;
			}
				
		}
		return false;
		
	}
	
	/**
	 * Method puts into the array neighbours of given field
	 * @param target
	 * @return
	 */
	private ArrayList<Field> giveNeighbours(Field target) {
		ArrayList<Field> neighbours = new ArrayList<Field>();
		
		if(target.X1%2==1){
			if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1+1][target.Y1],target.target))
				giveNeighbourToArray(target.X1+1,target.Y1, neighbours);
			if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1-1][target.Y1],target.target))
				giveNeighbourToArray(target.X1-1,target.Y1, neighbours);
			if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1][target.Y1+1],target.target))
				giveNeighbourToArray(target.X1,target.Y1+1, neighbours);
			if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1][target.Y1-1],target.target))
				giveNeighbourToArray(target.X1,target.Y1-1, neighbours);
			if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1+1][target.Y1+1],target.target))
				giveNeighbourToArray(target.X1+1,target.Y1+1, neighbours);
			if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1-1][target.Y1+1],target.target))
				giveNeighbourToArray(target.X1-1,target.Y1+1, neighbours);
		}	
		else{
			if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1+1][target.Y1],target.target))
				giveNeighbourToArray(target.X1+1,target.Y1, neighbours);
			if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1-1][target.Y1],target.target))
				giveNeighbourToArray(target.X1-1,target.Y1, neighbours);
			if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1][target.Y1+1],target.target))
				giveNeighbourToArray(target.X1,target.Y1+1, neighbours);
			if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1][target.Y1-1],target.target))
				giveNeighbourToArray(target.X1,target.Y1-1, neighbours);
			if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1+1][target.Y1-1],target.target))
				giveNeighbourToArray(target.X1+1,target.Y1-1, neighbours);
			if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1-1][target.Y1-1],target.target))
				giveNeighbourToArray(target.X1-1,target.Y1-1, neighbours);
		}
			
		return neighbours;
	}

	/*
	 * Method puts into the array neighbour of given field
	 */
	public void giveNeighbourToArray(int x1, int y1, ArrayList<Field> neighbours) {
		try{
			if(board[x1][y1].kindOfField!=0)
				neighbours.add(board[x1][y1]);
		}
		catch(ArrayIndexOutOfBoundsException exception) {
		}
	}

	/**
	 * this method checks, if two fields are neigbours
	 * @param target
	 * @param checker
	 * @return
	 */
	private boolean areTheyNeighbours(Field target, Field checker) {
		ArrayList<Field> neighbours= giveNeighbours(target);
		
		for(int i=0;i < neighbours.size(); i++)
			if(neighbours.get(i).equals(checker))
				return true;
		return false;		
	}

	/**
	 * This metods return array board
	 * @return
	 */
	public void doMove(int X1, int Y1, int X2, int Y2 ) {
		this.board[X2][Y2]=this.board[X1][Y1];
		this.board[X2][Y2].updatePosition(X2,Y2);
		checkIfInTarget(this.board[X2][Y2]);
		this.board[X1][Y1]=new EmptyField(X1,Y1);
	}
	/**
	 * This method puts checkers on their starting place in the board
	 */
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
				if(board[i][j].kindOfField!=0)
					board[i][j]=new Checker(Color.values()[color],i,j,4);
	}
	
	public void fillerSecond(int color) {
		int counter=0;
		int limit=side-1;
		int j=1;
		for(int i=side-1;i<2*(side-1); i++){
			while(counter<limit){
				if(board[i][width-j].kindOfField!=0){
					board[i][width-j]=new Checker(Color.values()[color],i,width-j,5);
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
				if(board[i][width-j].kindOfField!=0){
					board[i][width-j]=new Checker(Color.values()[color],i,width-j,6);
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
				if(board[height-1-i][j].kindOfField!=0)
					board[height-1-i][j]=new Checker(Color.values()[color],height-1-i,j,1);
	}
	
	public void fillerFifth(int color) {
		int counter=0;
		int limit=1;
		int j=0;
		for(int i=2*side-1;i<3*side-2;i++){
			while(counter<limit){
				if(board[i][j].kindOfField!=0){
					board[i][j]=new Checker(Color.values()[color],i,j,2);
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
				if(board[i][j].kindOfField!=0){
					board[i][j]=new Checker(Color.values()[color],i,j,3);
					counter++;
				}
				j++;
			}
			counter=0;
			limit--;
			j=0;
		}
	}
	
	//Methods that are returning array of target's fields
	
	public ArrayList<Field> arrayFirst() {
		ArrayList<Field> triangle = new ArrayList<Field>();
		
		for(int i=0;i<side-1;i++)
			for(int j=0; j<width;j++)
				if(board[i][j].kindOfField!=0)
					triangle.add(board[i][j]);
		return triangle;
	}
	
	public ArrayList<Field> arraySecond() {
		ArrayList<Field> triangle = new ArrayList<Field>();
		int counter=0;
		int limit=side-1;
		int j=1;
		for(int i=side-1;i<2*(side-1); i++){
			while(counter<limit){
				if(board[i][width-j].kindOfField!=0){
					triangle.add(board[i][width-j]);
					counter++;
				}
				j++;
			}
			counter=0;
			limit--;
			j=1;
		}
		return triangle;
		
	}

	public ArrayList<Field> arrayThird() {
		ArrayList<Field> triangle = new ArrayList<Field>();
		int counter=0;
		int limit=1;
		int j=1;
		for(int i=2*side-1;i<3*side-2;i++){
			while(counter<limit){
				if(board[i][width-j].kindOfField!=0){
					triangle.add(board[i][width-j]);
					counter++;
				}
				j++;
			}
			counter=0;
			limit++;
			j=1;
		}
		return triangle;
	}
	
	public ArrayList<Field> arrayFourth() {
		ArrayList<Field> triangle = new ArrayList<Field>();
		for(int i=0;i<side-1;i++)
			for(int j=0; j<width;j++)
				if(board[height-1-i][j].kindOfField!=0)
					triangle.add(board[height-1-i][j]);
		return triangle;
	}
	
	public ArrayList<Field> arrayFifth() {
		ArrayList<Field> triangle = new ArrayList<Field>();
		int counter=0;
		int limit=1;
		int j=0;
		for(int i=2*side-1;i<3*side-2;i++){
			while(counter<limit){
				if(board[i][j].kindOfField!=0){
					triangle.add(board[i][j]);
					counter++;
				}
				j++;
			}
			counter=0;
			limit++;
			j=0;
		}
		return triangle;
	}
	
	public ArrayList<Field> arraySixth() {
		ArrayList<Field> triangle = new ArrayList<Field>();
		int counter=0;
		int limit=side-1;
		int j=0;
		for(int i=side-1;i<2*(side-1); i++){
			while(counter<limit){
				if(board[i][j].kindOfField!=0){
					triangle.add(board[i][j]);
					counter++;
				}
				j++;
			}
			counter=0;
			limit--;
			j=0;
		}
		return triangle;
	}
	
	/**
	 * This method checks if checker is in his target
	 */
	public Boolean checkIfInTarget(Field checker) {

		return checkIfInTriangle(checker, checker.target);
	}
	
	public Boolean checkIfInTriangle(Field checker, int Triangle) {

		ArrayList<Field> triangle;
		switch (Triangle)
		{
		case 1 :
			triangle=arrayFirst();
			return checkIfInTargetHelper(checker, triangle);		
		case 2 :
			triangle=arraySecond();
			return checkIfInTargetHelper(checker, triangle);
		case 3 :
			triangle=arrayThird();
			return checkIfInTargetHelper(checker, triangle);
		case 4 :
			triangle=arrayFourth();
			return checkIfInTargetHelper(checker, triangle);
		case 5 :
			triangle=arrayFifth();
			return checkIfInTargetHelper(checker, triangle);
		case 6 :
			triangle=arraySecond();
			return checkIfInTargetHelper(checker, triangle);
		}
		return false;	
	}

	public Boolean checkIfInTargetHelper(Field checker, ArrayList<Field> triangle) {
		for(int i=0;i < triangle.size(); i++)
		if(triangle.get(i).equals(checker)){
			checker.reachedTarget=true;
			return true;
		}
		return false;
	}
	

	/**
	 * This method creates represtation of Board that exist in array
	 * @param side
	 * @return array that is represenation of the Board
	 */
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
				Board[i][z]=new EmptyField(i,z);
				Board[height-1-i][z]=new EmptyField(height-1-i,z);
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
				Board[i][z]=new EmptyField(i,z);
				Board[height-1-i][z]=new EmptyField(height-1-i,z);
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
			Board[2*side-2][z]=new EmptyField(2*side-2,z);
		}
		
		return Board;
		
	}
	
	/**
	 * This method display array by characters
	 * @param board
	 * @param side
	 */
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
		 board.doMove(5, 6, 5, 6);
	     	
	    }

}
