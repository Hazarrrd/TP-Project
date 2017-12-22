

import java.util.*;

public class Board {
	
	public static final int WIDTH = 13;
	public static final int HEIGHT = 17;
	
	private static final int[] STAR = {1,2,3,4,13,12,11,10,9,10,11,12,12,4,3,2,1};
	
	private static final int[] OTHER = {6,5,5,4,0,0,1,1,0,0,4,5,5,6};
	
	private final Color[][] grid;
	
	Board(){
		grid = new Color[HEIGHT][];
		for(int y = 0; y< HEIGHT; y++)
			grid[y] = new Color[STAR[y]];
		
	}
	
	Board(Board board) {
		grid = board.grid;
	}
}
