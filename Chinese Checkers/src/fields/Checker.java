package fields;

import game.Color;

public class Checker extends Field {
	
	public Color color;

	public Checker(Color color){
		this.kindOfField=2;
		this.color=color;
	}

}
