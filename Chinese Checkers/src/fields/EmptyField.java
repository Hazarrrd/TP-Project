package fields;

public class EmptyField extends NotNullField {
	
	
	public EmptyField(int X1, int Y1){
		super(X1,Y1);
		this.kindOfField=1;
	}
	public void updatePosition(int x2, int y2) {
		this.X1=X1;
		this.Y1=Y1;
	}

}
