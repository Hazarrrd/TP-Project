package fields;

import game.Color;

public class Checker extends NotNullField {
	

	public Checker(Color color,int X1, int Y1, int target){
		super(X1,Y1);
		this.kindOfField=2;
		this.color=color;
		this.target=target;
	}
	
	public void updatePosition(int X1, int Y1){
		this.X1=X1;
		this.Y1=Y1;
	}

}
