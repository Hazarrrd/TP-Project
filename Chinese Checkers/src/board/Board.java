package board;


import java.awt.Color;
import java.awt.Window;
import java.awt.event.MouseListener;
import java.io.ObjectOutputStream.PutField;
import java.nio.channels.ShutdownChannelGroupException;
import java.util.ArrayList;

import clientserver.Client;
import fields.Checker;
import fields.EmptyField;
import fields.Field;
import fields.NullField;
import game.Colors;
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
	public ArrayList<Field> checker1;
	public ArrayList<Field> checker2;
	public ArrayList<Field> checker3;
	public ArrayList<Field> checker4;
	public ArrayList<Field> checker5;
	public ArrayList<Field> checker6;
	
	public ArrayList<Field> color0;
	public ArrayList<Field> color1;
	public ArrayList<Field> color2;
	public ArrayList<Field> color3;
	public ArrayList<Field> color4;
	public ArrayList<Field> color5;
	
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
	public boolean isMoveLegal(int x1, int y1, int x2, int y2, Colors color) {
		Field target = board[x2][y2];
		Field checker = board[x1][y1];
		
		if(target.kindOfField!=1){
			System.out.println("nie puste");
			return false;
		}
		
		if(checker.kindOfField!=2){
			System.out.println("nie pionek");
			return false;
		}
		else
			if(checker.color!=color){
				System.out.println("zly kolor");
				return false;
			}
		if(areTheyNeighbours(target,checker) && checker.firstMove)
			return true;
		else if(checker.firstMove || checker.duringLongJump){
			//System.out.println("xd");
			ArrayList<Field> neighbours= giveNeighbours(checker);
			for(int i=0;i < neighbours.size(); i++){
				boolean memory=neighbours.get(i).reachedTarget;
				neighbours.get(i).reachedTarget=false;
				if(neighbours.get(i).kindOfField==2)
					if(areTheyNeighbours(target,neighbours.get(i)))
						if(!(checker.reachedTarget) || checkIfInTriangle(target,checker.target)){
							neighbours.get(i).reachedTarget=memory;
							return true;
						}
				neighbours.get(i).reachedTarget=memory;
			}
				
		}
		System.out.println("first " + checker.firstMove);
		System.out.println("during"+ checker.duringLongJump);
		System.out.println("reached"+ checker.reachedTarget);
		showBoard(this.board, this.side);
		return false;
		
	}
	
	/**
	 * Method puts into the array neighbours of given field
	 * @param target
	 * @return
	 */
	private ArrayList<Field> giveNeighbours(Field target) {
		ArrayList<Field> neighbours = new ArrayList<Field>();
		//System.out.println(target.color);
			if(side%2==1)
				giveNeighboursHelper(target, neighbours, target.X1%2, 1);
			else
				giveNeighboursHelper(target, neighbours, target.X1%2, 0);
			
		return neighbours;
	}

	public void giveNeighboursHelper(Field target, ArrayList<Field> neighbours, int x, int y) {
		if(x==y){
			try {
				if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1+1][target.Y1],target.target))
					giveNeighbourToArray(target.X1+1,target.Y1, neighbours);
			} catch(ArrayIndexOutOfBoundsException exception) {
			}
			try {
				if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1-1][target.Y1],target.target))
					giveNeighbourToArray(target.X1-1,target.Y1, neighbours);
			} catch(ArrayIndexOutOfBoundsException exception) {
			}
			try {
				if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1][target.Y1+1],target.target))
					giveNeighbourToArray(target.X1,target.Y1+1, neighbours);
			} catch(ArrayIndexOutOfBoundsException exception) {
			}
			try {
				if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1][target.Y1-1],target.target))
					giveNeighbourToArray(target.X1,target.Y1-1, neighbours);
			} catch(ArrayIndexOutOfBoundsException exception) {
			}
			try {
				if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1+1][target.Y1+1],target.target))
					giveNeighbourToArray(target.X1+1,target.Y1+1, neighbours);
			} catch(ArrayIndexOutOfBoundsException exception) {
			}
			try {
				if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1-1][target.Y1+1],target.target))
					giveNeighbourToArray(target.X1-1,target.Y1+1, neighbours);
			} catch(ArrayIndexOutOfBoundsException exception) {
			}
	
		}	
		else{
			try {
				if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1+1][target.Y1],target.target))
					giveNeighbourToArray(target.X1+1,target.Y1, neighbours);
			} catch(ArrayIndexOutOfBoundsException exception) {
			}
			try {
				if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1-1][target.Y1],target.target))
					giveNeighbourToArray(target.X1-1,target.Y1, neighbours);
			} catch(ArrayIndexOutOfBoundsException exception) {
			}
			try {
				if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1][target.Y1+1],target.target))
					giveNeighbourToArray(target.X1,target.Y1+1, neighbours);
			} catch(ArrayIndexOutOfBoundsException exception) {
			}
			try {
				if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1][target.Y1-1],target.target))
					giveNeighbourToArray(target.X1,target.Y1-1, neighbours);
			} catch(ArrayIndexOutOfBoundsException exception) {
			}
			try {
				if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1+1][target.Y1-1],target.target))
					giveNeighbourToArray(target.X1+1,target.Y1-1, neighbours);
			} catch(ArrayIndexOutOfBoundsException exception) {
			}
			try {
				if(!(target.reachedTarget) || checkIfInTriangle(board[target.X1-1][target.Y1-1],target.target))
					giveNeighbourToArray(target.X1-1,target.Y1-1, neighbours);
			} catch(ArrayIndexOutOfBoundsException exception) {
			}
		}
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
		ArrayList<Field> neighbours= giveNeighbours(checker);
		//System.out.println(neighbours.size());
		for(int i=0;i < neighbours.size(); i++){
			System.out.println("sasiady " + checker.X1 + " " + checker.Y1 + " to " + neighbours.get(i).X1 + " " + neighbours.get(i).Y1);
			if(neighbours.get(i).equals(target))
				return true;
		}
		return false;		
	}

	/**
	 * This metods return array board
	 * @return
	 */
	public void doMove(int X1, int Y1, int X2, int Y2 ) {
		if(!areTheyNeighbours(this.board[X2][Y2],this.board[X1][Y1] ))
			this.board[X1][Y1].duringLongJump=true;
		this.board[X2][Y2]=this.board[X1][Y1];
		this.board[X2][Y2].updatePosition(X2,Y2);
		this.board[X2][Y2].firstMove=false;
		checkIfInTarget(this.board[X2][Y2]);
		this.board[X1][Y1]=new EmptyField(X1,Y1);
	}
	
	/**
	 * Delete checker than belongs to one color
	 * @param X1
	 * @param Y1
	 * @param X2
	 * @param Y2
	 */
	public void deleteCheckers(Colors color,MouseListener window) {
		for(int i=0;i<height;i++)
			for(int j=0;j<width;j++)
				if(board[i][j].kindOfField==2 && board[i][j].color==color){
					board[i][j]=new EmptyField(i,j);
					try {
						if(!window.equals(null))
							board[i][j].addMouseListener(window);
					} catch (NullPointerException e){ 
					}
				}
	}
	
	
	/**
	 * Checks if player has won
	 * @param color
	 * @return
	 */
	public Boolean didPlayerWin(Colors color) {
		switch(color){
		
			case GREEN :
				return didPlayerWinHelper(color0);
				
			case YELLOW :
				return didPlayerWinHelper(color1);
				
			case RED :
				return didPlayerWinHelper(color2);
				
			case  BLUE:
				return didPlayerWinHelper(color3);
				
			case  ORANGE:
				return didPlayerWinHelper(color4);
				
			case PINK :
				return didPlayerWinHelper(color5);
			
		}
		return null;
	}
	
	private Boolean didPlayerWinHelper(ArrayList<Field> list) {
		for(int i=0;i<list.size();i++)
			if(!list.get(i).reachedTarget)
				return false;
		return true;
	}
	/**
	 * This method puts checkers on their starting place in the board
	 */
	private void putCheckers() {
		switch (playerNumber){
		case 2 :
			fillerFirst(0);
			color0= checker1;
			fillerFourth(1);
			color1=checker4;
			break;
		case 3 :
			fillerFirst(0);
			color0= checker1;
			fillerThird(1);
			color1= checker3;
			fillerFifth(2);
			color2= checker5;
			
			break;
		case 4 :
			fillerSecond(0);
			color0= checker2;
			fillerThird(1);
			color1= checker3;
			fillerFifth(2);
			color2= checker5;
			fillerSixth(3);
			color3= checker6;
			break;
		case 6 :
			fillerFirst(0);
			color0= checker1;
			fillerSecond(1);
			color1= checker2;
			fillerThird(2);
			color2= checker3;
			fillerFourth(3);
			color3= checker4;
			fillerFifth(4);
			color4= checker5;
			fillerSixth(5);
			color5= checker6;
			break;
		}
	}
	
	public void fillerFirst(int color) {
		checker1= new ArrayList<Field>();
		for(int i=0;i<side-1;i++)
			for(int j=0; j<width;j++)
				if(board[i][j].kindOfField!=0){
					board[i][j]=new Checker(Colors.values()[color],i,j,4);
					checker1.add(board[i][j]);
				}
	}
	
	public void fillerSecond(int color) {
		checker2= new ArrayList<Field>();
		int counter=0;
		int limit=side-1;
		int j=1;
		for(int i=side-1;i<2*(side-1); i++){
			while(counter<limit){
				if(board[i][width-j].kindOfField!=0){
					board[i][width-j]=new Checker(Colors.values()[color],i,width-j,5);
					checker2.add(board[i][width-j]);
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
		checker3= new ArrayList<Field>();
		int counter=0;
		int limit=1;
		int j=1;
		for(int i=2*side-1;i<3*side-2;i++){
			while(counter<limit){
				if(board[i][width-j].kindOfField!=0){
					board[i][width-j]=new Checker(Colors.values()[color],i,width-j,6);
					checker3.add(board[i][width-j]);
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
		checker4= new ArrayList<Field>();
		for(int i=0;i<side-1;i++)
			for(int j=0; j<width;j++)
				if(board[height-1-i][j].kindOfField!=0){
					board[height-1-i][j]=new Checker(Colors.values()[color],height-1-i,j,1);
					checker4.add(board[height-1-i][j]);
				}
	}
	
	public void fillerFifth(int color) {
		checker5= new ArrayList<Field>();
		int counter=0;
		int limit=1;
		int j=0;
		for(int i=2*side-1;i<3*side-2;i++){
			while(counter<limit){
				if(board[i][j].kindOfField!=0){
					board[i][j]=new Checker(Colors.values()[color],i,j,2);
					checker5.add(board[i][j]);
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
		checker6= new ArrayList<Field>();
		int counter=0;
		int limit=side-1;
		int j=0;
		for(int i=side-1;i<2*(side-1); i++){
			while(counter<limit){
				if(board[i][j].kindOfField!=0){
					board[i][j]=new Checker(Colors.values()[color],i,j,3);
					checker6.add(board[i][j]);
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

		if(checkIfInTriangle(checker, checker.target)){
			checker.reachedTarget=true;
			return true;
		}
		return false;
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
			triangle=arraySixth();
			return checkIfInTargetHelper(checker, triangle);
		}
		return false;	
	}

	public Boolean checkIfInTargetHelper(Field checker, ArrayList<Field> triangle) {
		for(int i=0;i < triangle.size(); i++)
		if(triangle.get(i).equals(checker)){
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
			//if(side%2!=0)
			nullFields=(width-freeFields)/2;
			for(int j=0;j<nullFields;j++){
				Board[i][j]=new NullField();
				Board[height-1-i][j]=new NullField();
				
				Board[i][width-1-j]=new NullField();
				Board[height-1-i][width-1-j]=new NullField();
			}
			if(side%2!=0){
				if(i%2==1){
					Board[i][width-1-nullFields]=new NullField();
					Board[height-1-i][width-1-nullFields]=new NullField();
				}
			}
			else
				if(i%2==0){
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
			if(side%2!=0){
				if(i%2==1){
					Board[i][width-1-nullFields]=new NullField();
					Board[height-1-i][width-1-nullFields]=new NullField();
				}
			}
			else
				if(i%2==0){
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
		if(side%2==0)
			for(int z=nullFields+freeFields;z<width;z++){
				Board[2*side-2][z]=new NullField();
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
		 Board board = new Board(4,3);
		 //board.doMove(13, 4, 5, 7);
		// board.doMove(3, 4, 13, 4);
		//board.doMove(13, 4, 12, 4);
		// if(board.isMoveLegal(13,5,12,5,Colors.values()[3]))
		// {
		 showBoard(board.board,4);
		// board.doMove(5, 6, 5, 6);
		// }	
	    }

}
