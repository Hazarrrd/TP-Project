package fields;

import game.Color;

public abstract class Field {
	
	public int kindOfField;
	public Color color;
	public int X1;
	public int Y1;
	public Boolean reachedTarget=false;
	public int target;

	public void updatePosition(int x2, int y2) {
	}

}
